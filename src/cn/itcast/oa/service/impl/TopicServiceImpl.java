package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.TopicService;



@Service
@Transactional
public class TopicServiceImpl extends DaoSupportImpl<Topic> implements TopicService {
	
	@Deprecated
	public List<Topic> findByForum(Forum forum) {
		return getSession().createQuery(//
				// 鎺掑簭锛氭墍鏈夌疆椤跺笘鍦ㄦ渶涓婇潰锛屽苟鎸夋渶鍚庢洿鏂版椂闂存帓搴忥紝璁╂柊鐘舵�佺殑鍦ㄤ笂闈��
				"FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.list();
	}
	

	@Override
	public void save(Topic topic) {
		//璁剧疆灞炴�у苟淇濆瓨
		topic.setLastReply(null);
		topic.setLastUpdateTime(topic.getPostTime());
		topic.setReplyCount(0);
		topic.setType(Topic.TYPE_NORMAL);
		getSession().save(topic);
		
		//缁存姢鐩稿叧鐨勭壒娈婂睘鎬�
		Forum forum = topic.getForum();
		forum.setTopicCount(forum.getTopicCount()+1);
		forum.setArticleCount(forum.getArticleCount()+1);
		forum.setLastTopic(topic);
		getSession().update(forum);
		
	}

	public PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum) {
		//鏌ヨ鍒楄〃
		List list = getSession()//
		.createQuery("FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC")//
		.setParameter(0, forum)//
		.setFirstResult((pageNum - 1) * pageSize)
		.setMaxResults(pageSize)//
		.list();
		//鏌ヨ鎬绘暟閲�
		Long count = (Long)getSession()//
		.createQuery("SELECT COUNT(*) FROM  Topic t WHERE t.forum = ?")//
		.setParameter(0, forum)
		.uniqueResult();
		return new PageBean(pageNum,pageSize,count.intValue(),list);
	}
	
	


}
