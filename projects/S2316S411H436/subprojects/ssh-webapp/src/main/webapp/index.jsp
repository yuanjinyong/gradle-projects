<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>演示：最新SSH2架构原型：Struts2.3.16+Spring4.1.1+Hibernate4.3.6</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<body>

	演示：最新SSH2架构原型：Struts2.3.16+Spring4.1.1+Hibernate4.3.6<br/>
	<a href="login.jsp">登录</a>
	
</body>
</html>
