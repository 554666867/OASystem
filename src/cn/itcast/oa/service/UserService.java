package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.User;

public interface UserService extends DaoSupport<User> {

	User findByLoginNameAndPassword(String name, String password);


}
