package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;


public interface ReplyService extends DaoSupport<Reply> {

	@Deprecated
	List<Reply> findByTopic(Topic topic);	//鏌ヨ鎸囧畾涓婚涓殑鎵�鏈夊洖澶嶅垪琛紝鎸夋椂闂村崌搴忔帓鍒�

	@Deprecated
	PageBean getPageBeanByTopic(int pageNum, int pageSize, Topic topic);	//鏌ヨ鍒嗛〉淇℃伅


}
