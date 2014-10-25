<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib prefix="s" uri="/struts-tags" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>My JSP 'list.jsp' starting page</title>
  </head>
  
  <body>
	<s:iterator value="#roleList">
		<s:property value="id"/>
		<s:property value="name"/>
		<s:property value="description"/>
		<s:a action="role_delete?id=%{id}" onclick="return confirm('确定要删除吗？')">删除</s:a>
		<s:a action="role_editUI?id=%{id}">修改</s:a><br>
	</s:iterator>
	<s:a action="role_addUI">添加</s:a>
  </body>
</html>
