<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
<style type="text/css">
	table,tr,th,td{
		border:1px solid black;
		border-collapse:collapse;
	}
</style>
</head>
<body>
	<table>
		<tr>
			<th>用户ID</th>
			<th>用户名</th>
			<th>所属角色</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pm.list}" var="user">
			<tr>
				<td>${user.usersId}</td>
				<td>${user.username}</td>
				<td>${user.role.roleName}</td>
				<td>
					<a href="user?method=deleteUser&userId=${user.usersId}">删除</a>
					<a href="">修改</a>
					<a href="user?method=grantUser&userId=${user.usersId}&username=${user.username}">授权</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<input type="button" value="首页" onclick="goFirst()" id="first"/>
	<input type="button" value="上一页" onclick="goPrev()" id="prev"/>
	<input type="button" value="下一页" onclick="goNext()" id="next"/>
	<input type="button" value="最后一页" onclick="goLast()" id="last"/>
	<script type="text/javascript">
		totalPage='${pm.totalPage}';
		currentPage='${currentPage}';
		if(currentPage==1){
			document.getElementById("first").disabled=true;
			document.getElementById("prev").disabled=true;
			document.getElementById("next").disabled=false;
			document.getElementById("last").disabled=false;
		}
		else if(currentPage==totalPage){
			document.getElementById("first").disabled=false;
			document.getElementById("prev").disabled=false;
			document.getElementById("next").disabled=true;
			document.getElementById("last").disabled=true;
		}
		function goFirst(){
			//JavaScript如何给一个Servelt发送请求
			window.location.href="user?method=viewUser&currentPage=1";
		}
		function goPrev(){
			currentPage=parseInt(currentPage)-1;
			window.location.href="user?method=viewUser&currentPage="+currentPage;
		}
		function goNext(){
			currentPage=parseInt(currentPage)+1;
			window.location.href="user?method=viewUser&currentPage="+currentPage;
		}
		function goLast(){
			window.location.href="user?method=viewUser&currentPage="+totalPage;
		}
	</script>
</body>
</html>