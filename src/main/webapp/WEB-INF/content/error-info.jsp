<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>消息提示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link href="<c:url value='/css/admin.css'/>" rel="stylesheet"
	type="text/css" />
<script src="<c:url value='/lib/jquery-2.0.0.min.js'/>"
	type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#rtBtn").click(function(){
		history.back(-1);
	});
});
</script>
</head>
<body>
	<div class="jt-container">
		<div class="span404">
			<dl>
				<dt>
					<h1>操作出现异常</h1>
					<h2>
						<s:actionmessage />
					</h2>
					<span>如果您看到这个页面，请重新操作或联系管理员</span>
				</dt>
				<dd>
					<input type="button" id="rtBtn" value="返     回" class="btn" />
				</dd>
			</dl>
		</div>

	</div>
</body>
</html>


