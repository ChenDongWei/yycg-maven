<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd HTML 4.01 transitional//EN" "http://www.w3.org/tr/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引用jquery easy ui的js库及css -->
<link rel="stylesheet" type="text/css"
	href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
<title>用户管理</title>
<script type="text/javascript">
	//datagrid列定义
	var columns_v = [ [ {
		field : 'userid',//对应json中的key
		title : '账号',
		width : 120
	}, {
		field : 'username',//对应json中的key
		title : '名称 ',
		width : 160
	}, {
		field : 'groupname',//对应json中的key
		title : '用户类型',
		width : 120,
	}, {
		field : 'sysmc',//对应json中的key
		title : '所属单位',
		width : 160
	}, {
		field : 'userstate',//对应json中的key
		title : '状态',
		width : 120,
		formatter : function(value, row, index) {//通过此方法格式化显示内容,value表示从json中取出该单元格的值，row表示这一行的数据，是一个对象,index:行的序号
			if (value == '1') {
				return "正常";
			} else if (value == '0') {
				return "暂停";
			}
		}
	}, {
		field : 'delete',
		title : '删除',
		width : 100,
		formatter : function(value, row, index) {
			return "<a href=javascript:deleteSysuser('"+row.id+"')>删除</a>";
		}
	}, {
		field : 'update',
		title : '修改',
		width : 100,
		formatter : function(value, row, index) {
			return "<a href=javascript:updateSysuser('"+row.id+"')>修改</a>";
		}
	} ] ];

	//定义 datagird工具
	var toolbar_v = [
			{//工具栏
				id : 'btnadd',
				text : '添加用户',
				iconCls : 'icon-add',
				handler : function() {
					//打开一个窗口，用户添加页面
					//参数：窗口的title、宽、高、url地址
				    createmodalwindow("添加用户信息", 600, 250,
							'${baseurl}user/addsysuser.action');
				}
			}];

	//加载datagrid
	$(function() {
		$('#sysuserlist').datagrid({
			// title : '用户查询',//数据列表标题
			nowrap : true,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
			striped : true,//条纹显示效果
			url : '${baseurl}user/queryuser_result.action',//加载数据的连接，引连接请求过来是json数据
			idField : 'id',//此字段很重要，数据结果集的唯一约束(重要)，如果写错影响 获取当前选中行的方法执行
			loadMsg : '',
			singleSelect:false,//如果是true只允许选中一行
			//fit:true,
			singleSelect : true,
			pagination : true,
			columns : columns_v,
			pagination : true,//是否显示分页
			rownumbers : true,//是否显示行号
			pageList : [ 15, 30, 50 ],
			toolbar : toolbar_v,
			onDblClickRow : function(rowIndex, rowData) {
				updateSysuser(rowData.id);
			}
		});
	});

	//查询用户
	function queryuser() {
		//datagrid的方法load方法要求传入json数据，最终将 json转成key/value数据传入action
		//将form表单数据提取出来，组成一个json
		var formdata = $("#sysuserqueryForm").serializeJson();
		$('#sysuserlist').datagrid('load', formdata);
	}
	
	//删除用户
	function deleteSysuser(id) {
		//第一个参数是提示信息，第二个参数，取消执行的函数指针，第三个参是，确定执行的函数指针
		_confirm('您确认删除吗？',null,function (){

			//将要删除的id赋值给deleteid，deleteid在sysuserdeleteform中
			$("#delete_id").val(id);
			//使用ajax的from提交执行删除
			//sysuserdeleteform：form的id，userdel_callback：删除回调函数，
			//第三个参数是url的参数
			//第四个参数是datatype，表示服务器返回的类型
			jquerySubByFId('sysuserdeleteFrom',userdel_callback,null,"json");
			
		});
	}
	
	//删除用户的回调
	function userdel_callback(data){
		message_alert(data);
		//刷新 页面
		//在提交成功后重新加载 datagrid
		//取出提交结果
		var type=data.resultInfo.type;
		if(type==1){
			//成功结果
			//重新加载 datagrid
			queryuser();
		}
	}
	
	//修改用户
	function updateSysuser(id) {
		createmodalwindow("修改用户信息", 600, 250, '${baseurl}user/editsysuser.action?id='+id);
	}
	
	//注册键盘事件
	document.onkeyup = function (event) {
        if(event.keyCode == 13){
        	$("#btn").click();
        }
    }
	
	function doClean() {
		$("#userid").val("");
		$("#username").val("");
		$("#sysmc").val("");
		$("#groupid").combobox("setValue", "");
	}
</script>
</head>
<body>
	<div class="easyui-layout table_search" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false">
			<form id="sysuserqueryForm">
				<!-- 查询条件 -->
				<div class="chen-toolbar-search">
					<label>用户账号：</label><input id="userid" class="chen-text" type="text"
						name="sysuserCustom.userid" style="width: 100px"> 
					<label>用户名称：</label><input id="username" class="easyui-text" type="text" 
						name="sysuserCustom.username" style="width: 100px"> 
					<label>单位名称：</label><input id="sysmc" class="easyui-text" type="text" 
					name="sysuserCustom.sysmc" style="width: 100px"> 
					<label>用户类型：</label> 
					<select id="groupid" class="easyui-combobox" name="sysuserCustom.groupid"
						panelHeight="auto" style="width: 100px">
						<option value="">--请选择--</option>
						<c:forEach items="${groupList}" var="dictinfo">
						   <option value="${dictinfo.dictcode }">${dictinfo.info}</option>
						</c:forEach>
					</select>
					<a href="#" class="easyui-linkbutton" id="btn"
						iconCls="icon-search" onclick="queryuser()">查询</a>
					<a href="#" class="easyui-linkbutton" id="btn"
						iconCls="icon-search" onclick="doClean()">清空条件</a>
				</div>
			</form>
			<!-- 查询列表 -->
			<div region="center" split="true" style="border:0">
				<table id="sysuserlist"></table>
			</div>
			<form id="sysuserdeleteFrom" action="${baseurl}user/deletesysuser.action" method="post">
				<input type="hidden" id="delete_id" name="id">
			</form>
		</div>
	</div>
</body>
</html>