<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<title>���Ȩ��</title>
<script>
	

	function savePermission() {
		$.ajax({
			type : "POST",
			url : "permission",
			data : $('#fm').serialize(),// ���л���ֵ
			async : false,
			dataType:"text",
			contentType: "application/x-www-form-urlencoded; charset=GBK",
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
	<div class="newPermission">
		<form id="fm">
			<input type="hidden" name="method" value="savePermission" /> 
			Ȩ�����ƣ�<input type="text" id="pname" name="name" /> 
			Ƥ����<input type="text" name="iconSkin" />
			url��<input type="text" name="url" /> 
			�Ƿ��Ǹ��ڵ㣺 
			<select name="isParent">
				<option value="true">��</option>
				<option value="false">��</option>
			</select> 
			���˵���
			<select name="pid">
				<c:forEach var="permission" items="${permissions}">
					<c:if test="${permission.isParent eq 'true'}">
						<option value="${permission.permissionId}">${permission.name}</option>
					</c:if>
				</c:forEach>
			</select> 
			<input type="button" value="����" onclick="savePermission()" />
		</form>
	</div>
</body>
</html>