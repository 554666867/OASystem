package cn.itcast.oa.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.dao.DepartmentDao;
import cn.itcast.oa.dao.RoleDao;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Role;

@Deprecated
@Repository
public class DepartmentDaoImpl extends DaoSupportImpl<Department> implements DepartmentDao{

}
