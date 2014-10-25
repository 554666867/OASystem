package cn.itcast.oa.base;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.ApplicationService;
import cn.itcast.oa.service.ApplicationTemplateService;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.service.ForumService;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.service.ProcessDefinitionService;
import cn.itcast.oa.service.ReplyService;
import cn.itcast.oa.service.RoleService;
import cn.itcast.oa.service.TopicService;
import cn.itcast.oa.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction extends ActionSupport{
	
	protected int pageNum = 1;
	protected int pageSize = 10;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	protected String saveUploadFile(File upload) {
		//获取年月日
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
		//封装
		//获得真实路径
		String basePath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload_files");
		String subPath = sdf.format(new Date());
		File dir = new File(basePath + subPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String path = basePath + subPath + UUID.randomUUID().toString();
		upload.renameTo(new File(path));
		return path;
	}
	

	
	//获取当前登录的User用户
	public User getCurrentUser(){
		return (User)ActionContext.getContext().getSession().get("user");
	}
	
	//存放各个模块的service
	
	@Resource
	protected DepartmentService departmentService;
	
	@Resource
	protected RoleService roleService;


	@Resource
	protected UserService userService;

	@Resource
	protected PrivilegeService privilegeService;

	@Resource
	protected ForumService forumService;

	@Resource
	protected ReplyService replyService;
	
	@Resource
	protected TopicService topicService;

	@Resource
	protected ApplicationTemplateService applicationTemplateService;

	@Resource
	protected ProcessDefinitionService processDefinitionService;
	
	@Resource
	protected ApplicationService applicationService;

	
}
