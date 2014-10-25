package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.ReplyService;


@Service
@Transactional
public class ReplyServiceImpl extends DaoSupportImpl<Reply> implements ReplyService {

	public List<Reply> findByTopic(Topic topic) {
		return getSession().createQuery(//
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")//
				.setParameter(0, topic)//
				.list();
	}

	@Override
	public void save(Reply reply) {
		getSession().save(reply);
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();
		forum.setArticleCount(forum.getArticleCount()+1);
		topic.setReplyCount(topic.getReplyCount()+1);
		topic.setLastReply(reply);
		topic.setLastUpdateTime(reply.getPostTime());
		getSession().update(topic);
		getSession().update(forum);
	}

	public PageBean getPageBeanByTopic(int pageNum, int pageSize, Topic topic) {
		//查询列表
		List list = getSession().createQuery(//
		"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")//
		.setParameter(0, topic)//
		.setFirstResult((pageNum - 1) * pageSize)//
		.setMaxResults(pageSize)//
		.list();
		//查询总数量
		Long count = (Long)getSession()//
		.createQuery("SELECT COUNT(*) FROM Reply r WHERE r.topic = ?")//
		.setParameter(0, topic)
		.uniqueResult();
		return new PageBean(pageNum,pageSize,count.intValue(),list);
	}
	

	


}
