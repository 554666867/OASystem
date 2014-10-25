package cn.itcast.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.ModelDrivenBaseAction;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;

@Controller
@Scope("prototype")
public class ReplyAction extends ModelDrivenBaseAction<Reply>{
	
	private Long topicId;

	public String add() throws Exception{	//发表回复
		//封装
		//表单字段，已经封装
		//model.setTitle(title);
		//model.setContent(content);
		model.setTopic(topicService.getById(topicId));
		//当前信息
		model.setAuthor(getCurrentUser());
		model.setPostTime(new Date());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		//保存
		replyService.save(model);
		return "toTopicShow";	//发完回到主帖+回帖列表
	}
	
	public String addUI() throws Exception{	//发表回复页面
		//准备数据
		Topic topic = topicService.getById(topicId);
		ActionContext.getContext().put("topic", topic);
		return "addUI";
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
}
