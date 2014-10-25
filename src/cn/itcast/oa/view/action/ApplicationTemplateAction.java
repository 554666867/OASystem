package cn.itcast.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.ModelDrivenBaseAction;
import cn.itcast.oa.domain.ApplicationTemplate;
import cn.itcast.oa.service.ApplicationTemplateService;

@Controller
@Scope("prototype")
public class ApplicationTemplateAction extends ModelDrivenBaseAction<ApplicationTemplate>{

	private File upload;
	private InputStream inputStream;

	public String list() throws Exception{	//列表
		List<ApplicationTemplate> applicationTemplateList = applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList", applicationTemplateList);
		return "list";
	}
	
	public String delete() throws Exception{	//删除，同时删除文件
		applicationTemplateService.delete(model.getId()) ;
		return "toList";
	}
	
	public String addUI() throws Exception{	//添加页面
		//准备数据
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		return "saveUI";
	}
	
	public String add() throws Exception{	//添加模板
		String path = saveUploadFile(upload);
		model.setPath(path);
		//保存
		applicationTemplateService.save(model);
		return "toList";
	}
	
	public String edit() throws Exception{	//修改
		//从BD中取出源对象
		ApplicationTemplate applicationTemplate = applicationTemplateService.getById(model.getId());
		//设置修改的属性
		applicationTemplate.setName(model.getName());
		applicationTemplate.setProcessDefinitionKey(model.getProcessDefinitionKey());
		if(upload!=null){	//如果上传了新文件
			//删除老文件
			File file = new File(applicationTemplate.getPath());
			if(file.exists()){
				file.delete();
			}
			//使用新文件
			String path = saveUploadFile(upload);
			applicationTemplate.setPath(path);	
		}
		//更新到DB
		applicationTemplateService.update(applicationTemplate);
		return "toList";
	}
	
	public String editUI() throws Exception{	//修改页面
		//准备数据
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		//准备回显的数据
		ApplicationTemplate applicationTemplate = applicationTemplateService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(applicationTemplate);
		return "saveUI";
	}
	
	public String download() throws Exception{	//下载模板
		//准备下载的资源
		ApplicationTemplate applicationTemplate = applicationTemplateService.getById(model.getId());
		inputStream = new FileInputStream(applicationTemplate.getPath());
		//准备文件名(解决乱码问题)
		String fileName = URLEncoder.encode(applicationTemplate.getName(), "utf-8"); 
		ActionContext.getContext().put("fileName", fileName);
		System.out.println("---------->download");
		return "download";
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public File getUpload() {
		return upload;
	}
	
	public void setUpload(File upload) {
		this.upload = upload;
	}
	
}
