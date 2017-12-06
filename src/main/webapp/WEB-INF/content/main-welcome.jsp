<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@page import="com.kington.fshg.webapp.security.UserContext"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎页面</title>
<link href="<c:url value='/lib/cssreset.css'/>" rel="stylesheet"
	type="text/css" />
<link href="<c:url value='/lib/bootstrap-2.3.2/css/bootstrap.min.css'/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/main.css'/>" rel="stylesheet"
	type="text/css" />
<!--[if lte IE 8]>
<script type="text/javascript" src="<c:url value='/lib/jquery-1.10.1.min.js'/>"></script>
<![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<script type="text/javascript"
	src="<c:url value='/lib/jquery-2.0.0.min.js'/>"></script>
<!--<![endif]-->
<script type="text/javascript"
	src="<c:url value='/lib/bootstrap-2.3.2/js/bootstrap.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/main.js'/>"></script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid" align="center">
			<img src="<c:url value='/images/welcome-pic.jpg'/>" />
		</div>
		<!--jt-container end-->
</body>
</html>
