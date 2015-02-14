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
	<title>SSH2架构原型：登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<body>

	<form action="${pageContext.request.contextPath}/login.do" method="post">
		username：<input type="text" name="empname" /> <br /> 
		password：<input type="password" name="password" /> <br /> 
		<input type="submit" value="login" />
		<input type="reset" value="reset" />
	</form>
	
</body>
</html>
