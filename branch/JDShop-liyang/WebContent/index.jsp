<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<style type="text/css">
			.ztree li span.button.pIcon01_ico_open{margin-right:2px; background: url(css/zTreeStyle/img/diy/1_open.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
			.ztree li span.button.pIcon01_ico_close{margin-right:2px; background: url(css/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
			.ztree li span.button.pIcon02_ico_open, .ztree li span.button.pIcon02_ico_close{margin-right:2px; background: url(css/zTreeStyle/img/diy/2.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
			.ztree li span.button.icon01_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/3.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
			.ztree li span.button.icon02_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/4.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
			.ztree li span.button.icon03_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/5.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
			.ztree li span.button.icon04_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/6.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
			.ztree li span.button.icon05_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/7.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
			.ztree li span.button.icon06_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/8.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
			.title span{
				font-size: 24px;
				font-weight:500;
			}
			.logout-button{
				display:none;
			}
		</style>
		<title>商城后台管理</title>
		<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="easyui/demo.css">
		<script type="text/javascript" src="easyui/jquery.min.js"></script>
		<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
		<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="js/jquery.ztree.core.js"></script>
		<script type="text/javascript">
			var setting = {
				data: {
					simpleData: {
						enable: true,
						idKey: "permissionId",
						pIdKey: "pid"
					}
				},
				callback:{
					onClick:zTreeOnClick
				}
			};
			function zTreeOnClick(event,treeId,treeNode){
				if(treeNode.isParent){
					var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
					treeObj.expandNode(treeNode);	
				}
			};
			$(document).ready(function () {
				username='${user.username}';
				$.ajax({
					async:false,//发送同步请求：此代码块执行完毕，才能往下执行
					url:"user?method=getMenu&username="+username,
					method:"GET",
					dataType:"json",
					success:function(data){
						zNodes=data;//全局变量
					}
				});
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			});
		</script>
	</head>
	<body class="easyui-layout">
		<div class="title" data-options="region:'north',border:false" style="height:60px;background:#F1F1F1;padding:10px">
			<span>商 城 后 台 管 理</span>
			<a class="logout-button" href="user?method=logout">安全退出</a>
		</div>
		<div data-options="region:'west',split:true,title:'权限管理'" style="width:250px;padding:10px;">
			<div class="zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
		<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">
			
		</div>
		<div data-options="region:'south',border:false" style="height:50px;background:#F1F1F1;padding:10px;">
		</div>
		<div data-options="region:'center',title:'表单'">
			<iframe src="" style="width:100%;height:100%;" name="center"></iframe>
		</div>
	</body>
</html>