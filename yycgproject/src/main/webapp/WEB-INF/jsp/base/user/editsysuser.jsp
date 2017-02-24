<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
		<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
		<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
		<title>修改用户</title>
		<script type="text/javascript">
		  function sysusersave(){
			
			  jquerySubByFId('userform',sysusersave_callback,null,"json");
			  
		  }
		  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
		  function sysusersave_callback(data){
			  message_alert(data);
			  if(data.resultInfo.type == '1'){
				  //延迟1秒执行关闭方法
				  setTimeout("parent.closemodalwindow()", 1000);
			  }
		  }
		</script>
	</head>
	<body>
	
		<form id="userform" action="${baseurl}user/editsysusersubmit.action" method="post">
			<!-- 更新用户的id -->
			<input type="hidden" name="id" value="${sysuserCustom.id}"/>
			<table border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
			
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
												<input type="text" id="sysuser_userid" name="sysuserCustom.userid" value="${sysuserCustom.userid}" disabled="disabled"/>
											</div>
											<!-- sysuser_useridTip用于显示提示信息，提示div的id等于校验input的id+Tip -->
											<div id="sysuser_useridTip"></div>
										</td>
										<td height=30 width="15%" align=right >用户名称：</td>
										<td class=category width="35%">
											<div>
												<input type="text" id="sysuser_username" name="sysuserCustom.username" value="${sysuserCustom.username}" />
											</div>
											<div id="sysuser_usernameTip"></div><span style="color:red"> * </span>
										</td>
									</tr>
									<tr>
										<td height=30 width="15%" align=right >用户密码：</td>
										<td class=category width="35%">
											<div>
												<input type="password" id="sysuser_password" name="sysuserCustom.pwd" disabled="disabled"/>
											</div>
											<div id="sysuser_passwordTip"></div>
										</td>
										<td height=30 width="15%" align=right >用户类型：</td>
										<td class=category width="35%">
											<div>
												<select name="sysuserCustom.groupid" id="sysuser_groupid">
												
													<option value="">--请选择--</option>
													<option value="1" <c:if test="${sysuserCustom.groupid=='1'}">selected</c:if>>卫生局</option>
													<option value="2" <c:if test="${sysuserCustom.groupid=='2'}">selected</c:if>>卫生院</option>
													<option value="3" <c:if test="${sysuserCustom.groupid=='3'}">selected</c:if>>卫生室</option>
													<option value="4" <c:if test="${sysuserCustom.groupid=='4'}">selected</c:if>>供货商</option>
													<option value="0" <c:if test="${sysuserCustom.groupid=='0'}">selected</c:if>>系统管理员</option>
				
												</select>
											</div>
											<div id="sysuser_groupidTip"></div><span style="color:red"> * </span>
										</td>
									</tr>
									<tr>
										<td height=30 width="15%" align=right >用户单位名称：</td><!-- 用处：根据名称获取单位id -->
										<td class=category width="35%">
											<input type="text" name="sysuserCustom.sysmc" value="${sysuserCustom.sysmc}"/><span style="color:red"> * </span>
										</td>
										<td height=30 width="15%" align=right>用户状态：</td>
										<td class=category width="35%">
											<input type="radio" name="sysuserCustom.userstate" value="1" <c:if test="${sysuserCustom.userstate=='1'}">checked</c:if>/>正常
											<input type="radio" name="sysuserCustom.userstate" value="0" <c:if test="${sysuserCustom.userstate=='0'}">checked</c:if>/>暂停
										</td>
											
									</tr>
									<tr>
										<td colspan=4 align=center class=category>
											<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="sysusersave()">提交</a>
											<a id="closebtn"  class="easyui-linkbutton" iconCls="icon-cancel" href="#" onclick="parent.closemodalwindow()">关闭</a>
										</td>
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