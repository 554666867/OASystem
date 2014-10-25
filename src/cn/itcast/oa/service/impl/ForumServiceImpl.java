package cn.itcast.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.dao.DepartmentDao;
import cn.itcast.oa.dao.RoleDao;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.service.ForumService;
import cn.itcast.oa.service.RoleService;

@Service
@Transactional
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements ForumService {

	public List<Forum> findAll(){
		return getSession().createQuery("FROM Forum f ORDER BY f.position").list();
	}
	 
	public void  save(Forum forum){
		super.save(forum);
		forum.setPosition(forum.getId().intValue());
	}
	
	public void moveDown(Long id) {
		//找出相关的forum
		Forum forum = getById(id);	//本对象
		Forum other = (Forum)getSession()//下一个对象
		.createQuery("FROM Forum f WHERE f.position>? ORDER BY f.position ASC")//
		.setParameter(0, forum.getPosition())//
		.setFirstResult(0)//
		.setMaxResults(1)
		.uniqueResult();
		
		if(other == null){
			return;
		}
		
		//交换position的值
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		//更新到数据库，可以不写，因为持久化状态自动更新
		getSession().update(forum);
		getSession().update(other);
	}

	public void moveUp(Long id) {
		//找出相关的forum
		Forum forum = getById(id);	//本对象
		Forum other = (Forum)getSession()//上一个对象
		.createQuery("FROM Forum f WHERE f.position<? ORDER BY f.position DESC")//
		.setParameter(0, forum.getPosition())//
		.setFirstResult(0)//
		.setMaxResults(1)
		.uniqueResult();
		
		if(other == null){
			return;
		}
		
		//交换position的值
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		//更新到数据库，可以不写，因为持久化状态自动更新
		getSession().update(forum);
		getSession().update(other);
	}

}
