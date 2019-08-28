<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>角色授权</title>
	<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.excheck.js"></script>
	<SCRIPT type="text/javascript">
	
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey:"permissionId",
					pIdKey:"pid",
					
				}
			}
		};
		
		$(document).ready(function(){
			roleId='${rolesId}';
			$.ajax({
				url:"permission?method=getAllPermission&roleId="+roleId	,
				async:false,
				dataType:"json",
				success:function(data){
					zNodes=data;
				}
			});
			$.fn.zTree.init($("#treeDemo"),setting,zNodes);	
		});
		function grant(){
			var permissionIds=new Array();
			
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getCheckedNodes(true);
			for(var i=0;i<nodes.length;i++){
				permissionIds.push(nodes[i].permissionId);
			}
			console.log(permissionIds[1]);
			$.ajax({
				url:'role?method=saveGrant',
				data:{"roleId":roleId,"permissionIds":permissionIds},
				contentType: "application/x-www-form-urlencoded; charset=GBK",
				dataType:"text",
				success:function(data){
					alert(data);
				}
			});
		}
	</script>
</head>
<body>
	被授权角色：${roleName}
	<div>
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<input type="button" onclick="grant()" value="授权"/>
</body>
</html>