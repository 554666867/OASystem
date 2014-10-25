package cn.itcast.oa.util;

import org.aopalliance.intercept.Invocation;

import cn.itcast.oa.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CheckPrivilegeIntercepter extends AbstractInterceptor {

	public String intercept(ActionInvocation invocation) throws Exception {
		User user = (User)ActionContext.getContext().getSession().get("user");
		String nameSpace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String privUrl = nameSpace + actionName;
			
		if(user == null){
			if(privUrl.startsWith("/user_login")){
				return invocation.invoke();
			}else{
				return "loginUI" ;				
			}
		}else{
			if(user.hasPrivilegeByUrl(privUrl)){
				return invocation.invoke();
			}else{
				return "noPrivilegeError";
			}
		}
	}

}
