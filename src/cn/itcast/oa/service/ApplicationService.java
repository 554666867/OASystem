package cn.itcast.oa.service;

import java.util.List;
import java.util.Set;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Application;
import cn.itcast.oa.domain.ApproveInfo;
import cn.itcast.oa.domain.TaskView;
import cn.itcast.oa.domain.User;

public interface ApplicationService extends DaoSupport<Application> {

	/**
	 * 提交申请：
	 * 
	 * 1，保存申请信息
	 * 
	 * 2，启动流程开始流转
	 * 
	 * @param application
	 */
	void submit(Application application);

	/**
	 * 查询我的任务信息列表
	 * 
	 * @param currentUser
	 * @return
	 */
	List<TaskView> getMyTaskViewList(User currentUser);

	/**
	 * 执行审批处理（保存审批信息，办理完任务，维护申请的状态）
	 * @param approveInfo
	 * @param taskId 
	 * @param outcome 使用指定路线离开本活动，为null的时候表示只有一个路径
	 */
	void approve(ApproveInfo approveInfo, String taskId, String outcome);

	/**
	 *获取指定任务活动中所有流出的连线名称
	 */
	Set<String> getOutComesByTaskId(String taskId);

}
