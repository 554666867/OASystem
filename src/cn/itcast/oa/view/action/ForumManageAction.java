package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.ModelDrivenBaseAction;
import cn.itcast.oa.domain.Forum;

@Controller
@Scope("prototype")
public class ForumManageAction extends ModelDrivenBaseAction<Forum> {
	public String list() throws Exception{	//列表
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	public String delete() throws Exception{	//删除
		forumService.delete(model.getId());
		return "toList";
	}
	public String addUI() throws Exception{	//添加页面
		return "saveUI";
	}
	public String add() throws Exception{	//添加
		forumService.save(model);
		return "toList";
	}
	public String edit() throws Exception{	//修改
		Forum forum = forumService.getById(model.getId());
		forum.setName(model.getName());
		forum.setDescription(model.getDescription());
		forumService.update(forum);
		return "toList";
	}
	public String editUI() throws Exception{	//修改页面
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(forum);
		return "saveUI";
	}
	public String moveUp() throws Exception{	//上移
		forumService.moveUp(model.getId());
		return "toList";
	}
	public String moveDown() throws Exception{	//下移
		forumService.moveDown(model.getId());
		return "toList";
	}
}
