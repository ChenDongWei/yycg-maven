<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
	<head>
		<title><fmt:message key="title" bundle="${messagesBundle}" /></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		
		<link rel="stylesheet" type="text/css" href="${baseurl}styles/style.css">
		<link rel="stylesheet" type="text/css" href="${baseurl}styles/login.css">
		<link rel="stylesheet" type="text/css"	href="${baseurl}js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"	href="${baseurl}js/easyui/themes/icon.css">

		<style type="text/css">
			.btnalink {
				cursor: hand;
				display: block;
				width: 80px;
				height: 29px;
				float: left;
				margin: 12px 28px 12px auto;
				line-height: 30px;
				background: url('${baseurl}images/login/btnbg.jpg') no-repeat;
				font-size: 14px;
				color: #fff;
				font-weight: bold;
				text-decoration: none;
			}
		</style>
		<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
		<script type="text/javascript">
			$(document).ready(function(){
			//*****************表单校验******************
			$.formValidator.initConfig({
				formID : "loginform",
				mode:'AlertTip',
				onError : function(msg) {
					alert(msg);
				},
				onAlert : function(msg) {
					alert(msg);
				}
			});
			$("#userid").formValidator({
				onShow : "",
				onCorrect:"&nbsp;"
			}).inputValidator({
				min : 1,
				onError:"请输入用户名"
			});
			$("#password").formValidator({
				onShow : "",
				onCorrect:"&nbsp;"
			}).inputValidator({
				min : 1,
				onError:"请输入密码"
			});
			$("#randomcode").formValidator({
				onShow : "",
				onCorrect:"&nbsp;"
			}).inputValidator({
				min : 1,
				onError:"请输入验证码"
			});
			//*****************表单校验******************
		});
		
			//校验表单输入
			function checkinput() {
				//return $('#loginform').form('validate');
				return $.formValidator.pageIsValid();
			}
		
			//登录提示方法
			function loginsubmit() {
				if(checkinput()){//校验表单，如果校验通过则执行jquerySubByFId
					//ajax form提交
					jquerySubByFId('loginform', login_commit_callback,null,'json'); 
				}
		
			}
			//登录提示回调方法
			function login_commit_callback(data) {
				message_alert(data);
				var type = data.resultInfo.type;
				if (1 == type) {//如果登录成功，这里1秒后执行跳转到首页
					setTimeout("tofirst()", 1000);
				} else {
					//登录错误，重新刷新验证码
					randomcode_refresh();
				}
		
			}
			//刷新验证码
			//实现思路，重新给图片的src赋值，后边加时间，防止缓存 
			function randomcode_refresh() {
				$("#randomcode_img").attr('src',
						'${baseurl}validatecode.jsp?time' + new Date().getTime());
			}
			//回首页
			function tofirst(){
				
				if(parent.parent.parent){
					parent.parent.parent.location='${baseurl}first.action';
				}else if(parent.parent){
					parent.parent.location='${baseurl}first.action';
				}else if(parent){
					parent.location='${baseurl}first.action';
				}else{
					window.location='${baseurl}first.action';
				}  
				//window.location='${baseurl}first.action';
			}
		</script>
	</head>
	<body style="background: #f6fdff url(${baseurl}images/login/bg1.jpg) repeat-x;">
		<form id="loginform" name="loginform" action="${baseurl}loginsubmit.action" method="post">
			<div class="logincon">
				<div class="logo">
					<img alt="" src="${baseurl}images/login/loginlogo.png" />
				</div>
				<div class="title">
					<img alt="" src="${baseurl}images/login/logo.png"/>
				</div>
	
				<div class="cen_con">
					<img alt="" src="${baseurl}images/login/loginbox.png"/>
				</div>

				<div class="tab_con">
					<div class="input username" id="username">
	                    <label for="userName">用户名</label>
	                    <span></span>
	                    <input type="text" id="userid" name="userid"/>
	                </div>
	                <div class="input psw" id="psw">
	                    <label for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>
	                    <span></span>
	                    <input type="password" id="password" name="pwd"/>
	                </div>
	                <div class="input validate" id="validate">
	                    <label for="valiDate">验证码</label>
	                    <input type="text" id="randomcode" name="validateCode"/>
	                    <div class="value">
	                    	<a href=javascript:randomcode_refresh()>
	                    		<img id="randomcode_img" src="${baseurl}validatecode.jsp" alt=""
									width="104" height="38" align='absMiddle' />
							</a>
						</div>
	                </div>
	                <div id="btn" class="loginButton">
	                    <input type="button" class="button" onclick="loginsubmit()" value="登录" />
	                </div>
                	<div id="btn" class="resetButton">
                    	<input type="reset" class="button" value="重置" />
                	</div>
				</div>
			</div>
		</form>
	</body>
</html>
