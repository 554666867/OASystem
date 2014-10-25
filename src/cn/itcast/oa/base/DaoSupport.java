package cn.itcast.oa.base;

import java.util.List;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

public interface DaoSupport<T> {
	void save(T Entity);
	void delete(Long id);
	void update(T Entity);
	T getById(Long id);
	List<T> getByIds(Long[] ids);
	List<T> findAll();
	

	@Deprecated
	PageBean getPageBean(int pageNum, int pageSize, String hql,List<Object> parameters);
	PageBean getPageBean(int pageNum, int pageSize, QueryHelper queryHelper);
}
