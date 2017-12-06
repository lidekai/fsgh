<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<title><%=PublicType.SYSTEM_NAME %></title>
<link href="<c:url value='/css/base.css' />" rel="stylesheet" type="text/css"/>
<link href="<c:url value='/lib/weebox-0.4/src/weebox.css' />" rel="stylesheet" type="text/css"/>
<link href="<c:url value='/css/login.css' />" rel="stylesheet" type="text/css"/>
<link href="<c:url value='/css/lrtk.css'/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value='/lib/jquery-1.4.4.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/bgiframe.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/weebox.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib-ext/common-min.js'/>"  ></script>
<script type="text/javascript" src="<c:url value='/lib-ext/Validator.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib-ext/PwdUtils.js'/>"  ></script>
<script type="text/javascript" src="<c:url value='/multiselect/js/jquery-1.5.1.min.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/multiselect/js/tab.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/multiselect/js/global.js'/>" ></script>


<script type="text/javascript">

var username = "${username}";
var error = "${actionErrors}";
var message = "${actionMessages}";
$(document).ready(function(){
	if(error != null && error.length > 2){
		alert(error);
	}
});

function login(){
	var username = $("#username1").val();
	var password = $("#password").val();
	if(username == null || username == ""){
		alert("请输入用户名");
		$("#username1").focus();
	}else if(password == null || password == ""){
		alert("请输入密码");
		$("#password").focus();
	}else{
		//用户名和密码字段加密
		$("#username").val(encode($("#username1").val()));
		$("#password").val(encode($("#password").val()));
		$("#loginForm").submit();
	}
}
</script>



</head>
<body>
<form name="loginForm" action="<c:url value='/login/login.jhtml'/>" method="post" id="loginForm">
	<dl class="login" >
	<dt>
     <table class="table" cellpadding="0" cellspacing="0">
      <tr>
       <th><img src="<c:url value='/images/loginName.gif'/>"  /></th>
       <td>
        <p>
         <label>用户名：</label><input type="text" name="username1" value="" class="login-user" id="username1"/>
         <input type="hidden" name="username" id="username"/> 
    	   <label>密　码：</label><input type="password" name="password" value=""  class="login-pwd" id="password"/>
         <span class="login-btn"><input type="button" value="登 录" class="login-btn" onclick="login()"/>
        </p>
       </td>
      </tr>
     </table>
    </dt>
    <dd><span class="copyright">中山富士化工有限公司 2017 </span></dd>
</dl>

</form>

</body>
</html>
