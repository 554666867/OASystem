package cn.itcast.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.base.ModelDrivenBaseAction;

@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends BaseAction{
	
	private String id;//key+version
	private File upload;	//上传的文件
	private InputStream inputStream;
	private String key;	//指定版本的名称
	
	public String list() throws Exception{	//列表
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		return "list";
	}
	
	public String delete() throws Exception{	//删除
		//key = new String(key.getBytes("ISO8859-1"),"UTF-8");	//处理GET方式的乱码错误
		key = URLDecoder.decode(key, "utf-8");
		processDefinitionService.deleteByKey(key);
		return "toList";
	}
	
	public String addUI() throws Exception{	//部署页面
		return "addUI";
	}
	
	public String add() throws Exception{	//部署
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(upload));
		try {
			processDefinitionService.deploy(zipInputStream);
		}finally{
			zipInputStream.close();
		}
		return "toList";
	}
	
	public String downloadProcessImage() throws Exception{	//展示流程图片
		//inputStream = new FileInputStream("c:/a.png");
		//自己再进行一次URL编码
		id = URLDecoder.decode(id,"utf-8");
		inputStream = processDefinitionService.getProcessImageResourceAsStream(id);
		return "downloadProcessImage";	//不对应页面，只是对应请求
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
