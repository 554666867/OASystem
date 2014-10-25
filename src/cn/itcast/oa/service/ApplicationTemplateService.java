package cn.itcast.oa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.ApplicationTemplate;
import cn.itcast.oa.domain.Department;


public interface ApplicationTemplateService extends DaoSupport<ApplicationTemplate> {

}
