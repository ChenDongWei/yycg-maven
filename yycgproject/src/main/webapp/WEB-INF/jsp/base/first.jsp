<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>药品采购平台</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">

<link rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
<script type="text/javascript">
	var tabOnSelect = function(title) {
		//根据标题获取tab对象
		var currTab = $('#tabs').tabs('getTab', title);
		var iframe = $(currTab.panel('options').content);//获取标签的内容

		var src = iframe.attr('src');//获取iframe的src
		//当重新选中tab时将ifram的内容重新加载一遍，目的是获取当前页面的最新内容
		if (src)
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : createFrame(src)
				//ifram内容
				}
			});

	};
	var _menus;
	$(function() {//预加载方法
		//通过ajax请求菜单
		$.ajax({
			url : '${baseurl}menu.json',
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				_menus = data;
				initMenu(_menus);//解析json数据，将菜单生成
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});

		//tabClose();
		//tabCloseEven();
		//加载欢迎页面
		$('#tabs').tabs('add', {
			iconCls: 'icon-tip',
			title : '欢迎使用',
			content : createFrame('${baseurl}welcome.action')
		}).tabs({
			//当重新选中tab时将ifram的内容重新加载一遍
			onSelect : tabOnSelect
		});

		//修改密码
		$('#modifypwd').click(menuclick);

	});

	//退出系统方法
	function logout() {
		_confirm('您确定要退出本系统吗?', null, function() {
			location.href = '${baseurl}logout.action';
		})
	}
	
	//帮助
	function showhelp() {
		window.open('${baseurl}help/help.html', '帮助文档');
	}
	
	//修改密码
	function updatePwd() {
		createmodalwindow("修改用户信息", 600, 250, '${baseurl}user/editsysuser.action');
	}
</script>

</head>

<body style="overflow-y: hidden;" class="easyui-layout" scroll="no">
	<!-- begin of header -->
	<div class="chen-header"
		data-options="region:'north',border:false,split:true">
		<div class="chen-header-left">
			<h1>医药集中采购系统</h1>
		</div>
		<div class="chen-header-right">
			<p>
				<strong>${activeUser.username}</strong>，欢迎您！
			</p>
			<p>
				<a href="#">网站首页</a>|
				<a title='修改密码' ref='modifypwd' href=javascript:updatePwd() rel='${baseurl}user/updatepwd.action' icon='icon-null' id="modifypwd">修改密码</a>|
				<a href=javascript:showhelp()>帮助中心</a>|
				<a id="loginOut" href=javascript:logout()>安全退出</a>
			</p>
		</div>
	</div>
	<!-- end of header -->

	<!-- begin of footer -->
	<div class="chen-footer" data-options="region:'south',border:true,split:false">
    	<div class="footer">
			&copy; 系统版本号：${version_number}&nbsp;&nbsp;&nbsp;发布日期：${version_date}</div>
		</div>
    </div>
    <!-- end of footer -->

	<!-- begin of sidebar -->
	<div style="width: 180px;" id="west" title="导航菜单" split="true"
		region="west" hide="true">

		<div id="nav" class="easyui-accordion" data-options="border:false,fit:true">
			<!--  导航内容 -->
		</div>
	</div>
	<!-- end of sidebar -->

	<div class="chen-main" style="background: #eeeeee;" id="mainPanle" data-options="region:'center'">
		<div id="tabs" class="easyui-tabs" data-options="border:false,fit:true">
		</div>
	</div>


</body>
</html>
