<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=PublicType.SYSTEM_NAME %></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link href="<c:url value='/css/admin.css'/>" rel="stylesheet"
	type="text/css" />
<link
	href="<c:url value='/lib/bootstrap-2.3.2/css/bootstrap2.min.css'/>"
	rel="stylesheet" type="text/css" />
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
<script src="<c:url value='/lib/bootstrap-2.3.2/js/bootstrap.js'/>"
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
	root = $("#rootMenu");
	
<c:forEach items="${funcList }" var="func">
	dealTree('${func.funcCde}','${func.funcName}','${func.parent.funcCde}','<c:url value="${func.link}"/>','${func.sort}');
</c:forEach>
	$("#rootMenu dd").each(function(){
		var obj = $(this).html(); 
		if(obj == ""){
			$(this).remove();
		}
	});
	$("#rootMenu ul").each(function(){
		var obj = $(this).html(); 
		if(obj == ""){
			$(this).remove();
		}
	});

	//$("#menuleft .Menu-tit").first().addClass("Menu-tit-curr");
	$("#menuleft .Menu-tit").click(function(){
		if($(this).text() == "企业数据管理"){
			$("#mainFrame").attr("src","<c:url value='/analysis/analysis/analysis.jhtml'/>");
		}
		if($(this).text() == "监督抽查管理"){
			$("#mainFrame").attr("src","<c:url value='/jc/jdcc/countByYear.jhtml'/>");
		}
		if($(this).text() == "监督检查管理"){
			$("#mainFrame").attr("src","<c:url value='/analysis/analysis/analysisZhjg.jhtml'/>");
		}
	})
	initMenuEven();
	
	$("#logout").click(function(){
		location.href="<c:url value='/login/logout.jhtml'/>";
	});
	
	<pt:checkFunc code="QYHD_HDCK">
		//setTimeout('qryNoReadMsgNum()',8000);
	</pt:checkFunc>
	
	cg(1000);
	
});

<pt:checkFunc code="QYHD_HDCK">
var isrun=false;
function qryNoReadMsgNum(){
	if(isrun)	return;
	isrun = true;
	$.ajax({
		type: "post",
		url:"<c:url value='/hd/message/getNotReadNum.jhtml'/>",
		data:{},
		dataType: "text",
		success: function(data){
			$("#messNum").html(data);
			isrun = false;
			setTimeout('qryNoReadMsgNum()',8000);
		}, 
        error: function (XMLHttpRequest, textStatus, errorThrown) { 
			console.log(errorThrown); 
			isrun = false;
			setTimeout('qryNoReadMsgNum()',8000);
		} 
	});
}
</pt:checkFunc>
function dealTree(cde,name,pcde,link,sort){
	if(sort == '1'){
		var h1 = '<dd><span class="Menu-tit"><i class="icon-user"></i>';
		if(link != ""){
			h1 = h1 + '<a href="'+link+'" target="mainFrame">'+name+'</a>';
		}else{
			h1 = h1 + name;
		}
		h1 = h1 + '</span></dd><dd class="Menua"><ul id="'+cde+'"></ul></dd>';
		root.append(h1);
	}else if(sort == '2'){
		var par = $("#"+pcde);
		var h2 = '<li><a class="curr-a" ';
		if(link != ""){
			h2 = h2 + ' href="'+link+'" ';
		}
		h2 = h2 + 'target="mainFrame"><i class="icon-square"></i>'+name+'</a>';
		h2 = h2 + '<ul class="Menub" id="'+cde+'"></ul></li>';
		par.append(h2);
	}else if(sort == '3'){
		var par = $("#"+pcde);
		var h3 = '<li id="'+cde+'"><a class="curr-b" href="'+link+'" target="mainFrame" ><i class="icon-arrow"></i>'+name+'</a></li>';
		par.append(h3);
	}
}
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
									<li><a target="_black" class="menu-icon icon-011"
											href="<c:url value='/system/role/company.jhtml'/>"><i><span></span></i>公司资质<img
												src="" /></a></li>
									<li><a target="mainFrame" class="menu-icon icon-032"
										href="<c:url value='/main/welcome.jhtml'/>"><i><span></span></i>首页<img
											src="1.jpg" /></a></li>
									<pt:checkFunc code="SYS_USER">
										<li><a target="mainFrame" class="menu-icon icon-008"
											href="<c:url value='/system/user/list.jhtml'/>"><i><span></span></i>用户管理<img
												src="" /></a></li>
									</pt:checkFunc>
								</ul>
							</div>
						</div>
					</div>
				</dd>
			</dl>

			<div id="drop-nav">
				<ul class="drop-menu">
					<li class="cart"><a target="mainFrame"
						href="<c:url value='/system/user/editPwd.jhtml?vo.id=${USER_INFO.id }&key=${USER_INFO.key }'/>"
						class="btn btn-small btn-info"><i
							class="icon-edit icon-white"></i>修改密码</a>&nbsp;
						<button id="logout" class="btn btn-small btn-info" type="button">
							<i class="icon-off icon-white"></i> 退出系统
						</button></li>
					<li class="cart f14">欢迎您：<a href="#" rel="nofollow">${USER_INFO.userName }
					</a></li>
					<li class="cart">登录时间：${loginTime }</li>
				</ul>
			</div>
		</div>
		<!--header end*-*-*-*-*-*-*-*-* Menu-tit-curr-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-->

		<div id="middle-content" class="middle-content clear">

			<div class="sider-left floatL" id="menuleft">
				<dl id="rootMenu">
					<dt></dt>

				</dl>
			</div>
			<div class="toggle floatL">
				<div class="sider-toggle">
					<span title="收缩">切换</span>
				</div>
			</div>

			<div class="main-content floatL">
				<div class="main-content-in">
					<div id="content" class="content">
						<iframe id="mainFrame" name="mainFrame"
							src="<c:url value='/main/welcome.jhtml'/>" width="100%"
							height="100%" frameborder="0"></iframe>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">中山富士化工有限公司 2017</div>
	</div>

</body>
</html>
