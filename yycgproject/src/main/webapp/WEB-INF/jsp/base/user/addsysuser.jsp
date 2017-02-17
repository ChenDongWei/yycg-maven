<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/tr/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK rel="stylesheet" type="text/css"
	href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
<title>添加用户</title>
<script type="text/javascript">
	function sysusersave() {
		//准备使用jquery 提供的ajax Form提交方式
		//将form的id传入，方法自动将form中的数据组成成key/value数据，通过ajax提交，提交方法类型为form中定义的method，
		//使用ajax form提交时，不用指定url，url就是form中定义的action
		//此种方式和原始的post方式差不多，只不过使用了ajax方式

		//第一个参数：form的id
		//第二个参数：sysusersave_callback是回调函数，sysusersave_callback当成一个方法的指针
		//第三个参数：传入的参数， 可以为空
		//第四个参数：dataType预期服务器返回的数据类型,这里action返回json
		//根据form的id找到该form的action地址
		jquerySubByFId('userform', sysusersave_callback, null, "json");

	}
	//ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
	function sysusersave_callback(data) {

		message_alert(data);

	}
</script>
</head>
<body>

	<form id="userform" action="${baseurl}user/addsysusersubmit.action" method="post">
		<table border=0 cellSpacing=0 cellPadding=0 width="100%"
			bgColor=#c4d8ed>

			<tbody>
				<tr>
					<td background=images/r_0.gif width="100%">
						<table cellSpacing=0 cellPadding=0 width="100%">
							<tbody>
								<tr>
									<td>&nbsp;系统用户信息</td>
									<td align=right>&nbsp;</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table class="toptable grid" border=1 cellSpacing=1 cellPadding=4
							align=center>
							<tbody>
								<tr>
									<td height=30 width="15%" align=right>用户账号：</td>
									<td class=category width="35%">
										<div>
											<input type="text" id="sysuser_userid"
												name="sysuserCustom.userid" />
										</div> <!-- sysuser_useridTip用于显示提示信息，提示div的id等于校验input的id+Tip -->
										<div id="sysuser_useridTip"></div><span style="color:red"> * </span>
									</td>
									<td height=30 width="15%" align=right>用户名称：</td>
									<td class=category width="35%">
										<div>
											<input type="text" id="sysuser_username"
												name="sysuserCustom.username" />
										</div>
										<div id="sysuser_usernameTip"></div><span style="color:red"> * </span>
									</td>
								</tr>
								<tr>
									<td height=30 width="15%" align=right>用户密码：</td>
									<td class=category width="35%">
										<div>
											<input type="password" id="sysuser_password"
												name="sysuserCustom.pwd" />
										</div>
										<div id="sysuser_passwordTip"></div><span style="color:red"> * </span>
									</td>
									<td height=30 width="15%" align=right>用户类型：</td>
									<td class=category width="35%">
										<div>
											<select name="sysuserCustom.groupid" id="sysuser_groupid">

												<option value="">--请选择--</option>
												<option value="1">卫生局</option>
												<option value="2">卫生院</option>
												<option value="3">卫生室</option>
												<option value="4">供货商</option>
												<option value="0">系统管理员</option>

											</select>
										</div>
										<div id="sysuser_groupidTip"></div><span style="color:red"> * </span>
									</td>


								</tr>
								<tr>
									<td height=30 width="15%" align=right>用户单位名称：</td>
									<!-- 用处：根据名称获取单位id -->
									<td class=category width="35%">
										<input type="text" name="sysuserCustom.sysmc"/><span style="color:red"> * </span>
									</td>
									<td height=30 width="15%" align=right>用户状态：</td>
									<td class=category width="35%"><input type="radio"
										name="sysuserCustom.userstate" value="1" checked="checked" />正常
										<input type="radio" name="sysuserCustom.userstate" value="0" />暂停
									</td>

								</tr>
								<tr>
									<td colspan=4 align=center class=category><a
										id="submitbtn" class="easyui-linkbutton" iconCls="icon-ok"
										href="#" onclick="sysusersave()">确定</a> <a id="closebtn"
										class="easyui-linkbutton" iconCls="icon-cancel" href="#"
										onclick="parent.closemodalwindow()">取消</a></td>
								</tr>

							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>