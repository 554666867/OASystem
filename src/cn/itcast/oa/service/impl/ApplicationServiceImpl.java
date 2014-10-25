package cn.itcast.oa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Application;
import cn.itcast.oa.domain.ApproveInfo;
import cn.itcast.oa.domain.TaskView;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.ApplicationService;

@Service
public class ApplicationServiceImpl extends DaoSupportImpl<Application> implements ApplicationService {

	@Resource
	private ProcessEngine processEngine;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public void submit(Application application) {
		// 1，设置属性并保存application
		application.setApplyTime(new Date()); // 申请时间，当前时间
		application.setTitle(application.getApplicationTemplate().getName()// 格式为：（模版名称）_（申请人姓名）_（申请时间）
				+ "_" + application.getApplicant().getName()
				+ "_" + sdf.format(application.getApplyTime()));
		application.setStatus(Application.STATUS_RUNNING);
		getSession().save(application); // 保存

		// 2，启动程程实例开始流转
		// >> 准备流程变量
		Map<String, Object> variablesMap = new HashMap<String, Object>();
		variablesMap.put("application", application);
		// >> 启动程程实例
		String pdKey = application.getApplicationTemplate().getProcessDefinitionKey();
		ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey(pdKey, variablesMap);
		// >> 办理完第1个任务“提交申请”，并带上流程变量（当前的申请信息）
		Task task = processEngine.getTaskService()	//查询出本流程实例中当前仅有的一个“提交南申请”
				.createTaskQuery()//
				.processInstanceId(pi.getId())//
				.uniqueResult();
		String taskId = task.getId();
		processEngine.getTaskService().completeTask(taskId);
	}

	public List<TaskView> getMyTaskViewList(User currentUser) {
		// 查询我的任务列表
		String userId = currentUser.getLoginName(); // 约定使用loginName作为JBPM用的用户标识符
		List<Task> taskList = processEngine.getTaskService().findPersonalTasks(userId);

		// 找出每个任务对应的申请信息
		List<TaskView> resultList = new ArrayList<TaskView>();
		for (Task task : taskList) {
			Application application = (Application) processEngine.getTaskService().getVariable(task.getId(), "application");
			resultList.add(new TaskView(task, application));
		}

		// 返回“任务--申请信息”的结果
		return resultList;
	}

	public void approve(ApproveInfo approveInfo, String taskId,String outcome) {
		//保存审批信息，
		getSession().save(approveInfo);
		//办理完任务，
		Task task = processEngine.getTaskService().getTask(taskId);	//一定要先取出任务对象，再完成任务，否则拿不到，因为完成后任务被存放在历史的表中
		if(outcome == null){	//outcome使用指定路线离开本活动，为null的时候表示只有一个路径,则使用它离开
			//取出所属的流程实例，如果取出的是null，说明流程结束了，否则还未结束
			processEngine.getTaskService().completeTask(taskId);	
		}else{
			processEngine.getTaskService().completeTask(taskId,outcome);
		}
		ProcessInstance pi = processEngine.getExecutionService().findProcessInstanceById(task.getExecutionId());
		//维护申请的状态
		Application application = approveInfo.getApplication();
		if(!approveInfo.isApproval()){	//如果本环节不同意，则流程结束，申请状态为不通过
			if(pi != null){	//如果流程还未结束的情况下，直接结束流程
				processEngine.getExecutionService().endProcessInstance(task.getExecutionId(), ProcessInstance.STATE_ENDED);		
			}
			application.setStatus(application.STATUS_REJECTED);
		}else{
			if(pi == null){	//如果本环节同意而且本环节是最后一个环节，则流程结束，申请状态为已通过
				application.setStatus(application.STATUS_APPROVED);
			}
		}
		getSession().update(application);
	}

	public Set<String> getOutComesByTaskId(String taskId) {
		//获取指定任务活动中所有流出的连线名称
		return processEngine.getTaskService().getOutcomes(taskId);
	}

}
