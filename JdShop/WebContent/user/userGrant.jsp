<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<body>
	<form action="user" method="post">
		<input type="hidden" name="method" value="saveGrant" />
		<input type="hidden" name="userId" value="${userId}" />
		被授权的用户：${username} <select name="roleId">
			<c:forEach var="role" items="${requestScope.roles}">
				<option value="${role.rolesId}">${role.roleName}</option>
			</c:forEach>
		</select> <input type="submit" value="授权" />
	</form>
</body>
</html>