<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>My JSP 'list.jsp' starting page</title>

	</head>

	<body>
		<s:form action="role_edit">
			<s:hidden name="id" />
			<s:textfield name="name"></s:textfield><br>
			<s:textarea name="description"></s:textarea><br>
			<s:submit value="�ύ"></s:submit>
		</s:form>
	</body>
</html>
