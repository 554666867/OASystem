package cn.itcast.oa.view.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.ModelDrivenBaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

@Controller
@Scope("prototype")
public class ForumAction extends ModelDrivenBaseAction<Forum>{
	
	
	/**
	 * 0表示查看全部主题
	 * 1表示只看精华帖
	 */
	private int orderBy = 0;
	/**
	 * 0表示默认排序（所有置顶帖在前面，并按最后更新时间降序排序）<br>
	 * 1表示只按最后更新时间排序<br>
	 * 2表示只按主题发表时间排序<br>
	 * 3表示只按回复数量排序<br>
	 */
	private int viewType = 0;
	/**
	 * true表示升序
	 * false表示降序
	 * 
	 */
	private boolean asc = false;
	
	
	public String list() throws Exception{	//鐗堝潡鍒楄〃
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	
	/** 鏄剧ず鍗曚釜鐗堝潡锛堜富棰樺垪琛級 */
	public String show() throws Exception {
		// 鍑嗗鏁版嵁锛歠orum
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		new QueryHelper(Topic.class, "t")
				.addCondition("t.forum=?", forum)//
				.addCondition((viewType == 1), "t.type=?", Topic.TYPE_BEST) // 1 琛ㄧず鍙湅绮惧崕甯�
				.addOrderProperty((orderBy == 1), "t.lastUpdateTime", asc) // 1 琛ㄧず鍙寜鏈�鍚庢洿鏂版椂闂存帓搴�
				.addOrderProperty((orderBy == 2), "t.postTime", asc) // 2 琛ㄧず鍙寜涓婚鍙戣〃鏃堕棿鎺掑簭
				.addOrderProperty((orderBy == 3), "t.replyCount", asc) // 3 琛ㄧず鍙寜鍥炲鏁伴噺鎺掑簭
				.addOrderProperty((orderBy == 0), "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)//
				.addOrderProperty((orderBy == 0), "t.lastUpdateTime", false) // 0 琛ㄧず榛樿鎺掑簭(鎵�鏈夌疆椤跺笘鍦ㄥ墠闈紝骞舵寜鏈�鍚庢洿鏂版椂闂撮檷搴忔帓鍒�)
				.preparePageBean(topicService, pageNum, pageSize);

		return "show";
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}
}
