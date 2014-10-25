package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.Role;

public interface ForumService extends DaoSupport<Forum> {

	void moveUp(Long id);

	void moveDown(Long id);

}
