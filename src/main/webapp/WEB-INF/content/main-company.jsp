<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>管理后台首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link href="<c:url value='/css/admin.css'/>" rel="stylesheet"
	type="text/css" />
<!--[if lte IE 8]>
<script type="text/javascript" src="<c:url value='/lib/jquery-1.10.1.min.js'/>"></script>
<![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<script type="text/javascript"
	src="<c:url value='/lib/jquery-2.0.0.min.js'/>"></script>
<!--<![endif]-->
<script src="<c:url value='/lib-ext/common.js'/>" type="text/javascript"></script>
<script src="<c:url value='/lib-ext/function.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/lib-ext/jquery.elastislide.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/lib-ext/gallery.js'/>"
	type="text/javascript"></script>
<style>
html {
	overflow-x: hidden;
	overflow-y: hidden;
}
</style>
<script type="text/javascript">
<!--/*自适应高度*/
window.onload=midAutoH;
window.onresize=midAutoH;
-->
</script>
<script type="text/javascript">
var root;
$(document).ready(function(){

});

function setReturnValue(obj){
	$("#mainFrame")[0].contentWindow.setReturnValue(obj)
}
</script>

</head>
<body sroll="no">
	<div class="jt-container">
		<div class="header">
			<dl class="clear">
				<dt>
					<img src="<c:url value='/images/headImg.jpg'/>" alt="" />
				</dt>
				<dd class="menu">
					<div id="rg-gallery" class="rg-gallery">
						<div class="top-menu-wrapper">
							<div class="top-nav">
								<span class="top-nav-prev">Previous</span> <span
									class="top-nav-next">Next</span>
							</div>
							<div class="top-menu">
								<ul>
									<li><a href="<c:url value='/hd/message/company.jhtml'/>"
										target="mainFrame" class="menu-icon icon-model"><i><span></span></i><sup></sup>互动窗口</a></li>
								</ul>
							</div>
						</div>
					</div>
				</dd>
			</dl>
			<div id="drop-nav">
				<UL class="drop-menu">
					<LI class="menu-group menu-item">
						<div class="menu-box">
							<A class=menu-hd href="#" target=_top rel=nofollow>${USER_INFO.userName }<B></B></A>
							<div class=menu-bd>
								<div class=menu-bd-panel>
									<div>
										<A href="#">个人信息<i class="icon-info"></i></A> <A href="#">修改密码<i
											class="icon-pw"></i></A>
										<!--<A href="#">帮助信息<i class="icon-help"></i></A> -->
										<input name="" type="button" value="退出系统" />
									</div>
								</div>
							</div>
						</div>
					</LI>
					<LI class=cart><A href="<c:url value='/login/logout.jhtml'/>"
						rel=nofollow>退出 </A></LI>
				</UL>
			</div>
		</div>
		<!--header end*-*-*-*-*-*-*-*-* Menu-tit-curr-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-->

		<div id="middle-content" class="middle-content clear">

			<div class="main-content floatL">
				<div class="main-content-in">
					<div id="content" class="content">
						<iframe id="mainFrame" name="mainFrame"
							src="<c:url value='/hd/message/company.jhtml'/>" width="100%"
							height="100%" frameborder="0"></iframe>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">Copyright 2015.All Rights Reserved. 版权所有
			清远市质量技术监督局</div>
	</div>

</body>
</html>
