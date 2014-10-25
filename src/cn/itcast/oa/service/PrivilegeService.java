package cn.itcast.oa.service;

import java.util.Collection;
import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.Role;

public interface PrivilegeService extends DaoSupport<Privilege> {

	List<Privilege> findTopList();

	Collection<String> getAllPrivilegeUrls();


}
