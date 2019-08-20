<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
<style type="text/css">
table, tr, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}

.newPermission {
	height: 400px;
	display: none;
}
</style>
<script src="jquery-3.3.1.js"></script>
<script>
	function showSaveP() {
		$(".newPermission").show();
	}
	function savePermission() {
		$.ajax({
			type : "POST",
			url : "permission",
			data : $('#fm').serialize(),// 序列化表单值
			async : false,
			dataType:"text",
			error : function(request) {
				alert("Connection error");
			},
			success : function(data) {
				alert(data);
			}
		});
		$(".newPermission").hide();
	}
</script>
</head>
<body>
	<table>
		<tr>
			<th>权限ID</th>
			<th>权限名</th>
			<th>权限图标</th>
			<th>权限图标皮肤</th>
			<th>URL</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pm.list}" var="permission">
			<tr>
				<td>${permission.permissionid}</td>
				<td>${permission.name}</td>
				<td>${permission.icon}</td>
				<td>${permission.iconSkin}</td>
				<td>${permission.url}</td>
				<td><a href="">删除</a> <a href="">修改</a> <!-- <a href="http://www.bing.com" onclick="showSaveP()">添加</a> -->
					<!-- 阻止默认行为 --> <a href="javascript:void(0)" onclick="showSaveP()">添加</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<input type="button" value="首页" onclick="goFirst()" id="first" />
	<input type="button" value="上一页" onclick="goPrev()" id="prev" />
	<input type="button" value="下一页" onclick="goNext()" id="next" />
	<input type="button" value="最后一页" onclick="goLast()" id="last" />

	<div class="newPermission">
		<form id="fm">
			<input type="hidden" name="method" value="savePermission" /> 
			权限名称：<input type="text" name="name" /> 
			皮肤：<input type="text" name="iconSkin" />
			url：<input type="text" name="url" /> 
			是否是父节点： 
			<select name="isParent">
				<option value="true">是</option>
				<option value="false">否</option>
			</select> 
			父菜单：
			<select name="pid">
				<c:forEach var="permission" items="${permissions}">
					<c:if test="${permission.isParent eq 'true'}">
						<option value="${permission.permissionid}">${permission.name}</option>
					</c:if>
				</c:forEach>
			</select> 
			<input type="button" value="保存" onclick="savePermission()" />
		</form>
	</div>
	<script type="text/javascript">
		totalPage = '${pm.totalPage}';
		currentPage = '${currentPage}';
		if (currentPage == 1) {
			document.getElementById("first").disabled = true;
			document.getElementById("prev").disabled = true;
			document.getElementById("next").disabled = false;
			document.getElementById("last").disabled = false;
		} else if (currentPage == totalPage) {
			document.getElementById("first").disabled = false;
			document.getElementById("prev").disabled = false;
			document.getElementById("next").disabled = true;
			document.getElementById("last").disabled = true;
		}
		function goFirst() {
			//JavaScript如何给一个Servelt发送请求
			window.location.href = "permission?method=viewPermission&currentPage=1";
		}
		function goPrev() {
			currentPage = parseInt(currentPage) - 1;
			window.location.href = "permission?method=viewPermission&currentPage="
					+ currentPage;
		}
		function goNext() {
			currentPage = parseInt(currentPage) + 1;
			window.location.href = "permission?method=viewPermission&currentPage="
					+ currentPage;
		}
		function goLast() {
			window.location.href = "permission?method=viewPermission&currentPage="
					+ totalPage;
		}
	</script>
</body>
</html>