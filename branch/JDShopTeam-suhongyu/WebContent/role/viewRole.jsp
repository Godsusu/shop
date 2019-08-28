<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<style>
	table,tr,th,td{
		border:1px solid black;
		border-collapse:collapse;
	}
</style>
</head>
<body>
	<table>
		<tr>
			<th>角色ID</th>
			<th>角色名</th>
			<th>操作</th>
		</tr>
		
		<c:forEach items="${roles}" var="role">
			<tr>
				<td>${role.roleName}</td>
				<td>${role.rolesId}</td>
				<td>
					<a href="role?method=grantRole&rolesId=${role.rolesId}&rolesName=${role.roleName}">授权</a>
				</td>
			</tr>
		</c:forEach>
		
	</table>
</body>
</html>