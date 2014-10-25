package cn.itcast.oa.service;

import java.util.List;

import org.jbpm.api.ProcessDefinition;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;


public interface TopicService extends DaoSupport<Topic> {

	@Deprecated
	List<Topic> findByForum(Forum forum);	//鏌ヨ鎸囧畾鐗堝潡涓殑鎵�鏈変富棰�

	@Deprecated
	PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum);


}
