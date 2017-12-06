<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业销售订单管理平台</title>
<style>
html {
	overflow-x: hidden;
	overflow-y: hidden;
}

body {
	background: #fff;
	color: #999999;
	font-size: 12px;
	font-family: "宋体";
	line-height: 20px;
	background: #f3faff;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

#myModal .modal-body {
	height: 500px;
}

#myModal {
	width: 900px;
	margin-left: -450px;
}
</style>
<!--  -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<link href="<c:url value='/lib/bootstrap-2.3.2/css/bootstrap.min.css'/>"
	rel="stylesheet" type="text/css" />
<!--[if lte IE 8]>
<script type="text/javascript" src="<c:url value='/lib/jquery-1.10.1.min.js'/>"></script>
<![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<script type="text/javascript"
	src="<c:url value='/lib/jquery-2.0.0.min.js'/>"></script>
<!--<![endif]-->
<script src="<c:url value='/lib/bootstrap-2.3.2/js/bootstrap.js'/>"
	type="text/javascript"></script>
<script type="text/javascript">
<!--/*自适应高度*/
window.onload=midAutoH;
window.onresize=midAutoH;

function midAutoH(){
	document.getElementById("content").style.height=document.documentElement.clientHeight+"px";
}
-->
</script>
<script type="text/javascript">

function showMyModal(lable,url,widths,footer){
	var modalId = "myModal";
	if(widths == null || isNaN(widths)) widths = 900;
	var heights = 542;
	if(url.indexOf("?")>0){
		url = url + "&d_9="+new Date().getTime();
	}else{
		url = url + "?d_9="+new Date().getTime();
	}
	var fHtml = "";
	if(footer){
		fHtml='<div class="modal-footer" ><a href="#" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">关闭</a><a href="#" class="btn btn-primary" id="mySaveBtn">确定</a></div>';
	}
	var html= '<div id="'+modalId+'" class="modal hide fade" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="true" style="width:'+widths+'px;height:'+heights+'px; border:solid 2px #317eaa;">' +
			 	' <div class="modal-header" style="background-color:#0d96cc" > ' +
	  			' <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> ' +
	  			' <h3>'+lable+'</h3> ' +
				' </div><div class="modal-body" style="height:'+heights+'px;background-color:#ebf4fb;"> ' +
				' <iframe id="frame'+modalId+'" name="frame'+modalId+'" src="'+url+'" width="100%" height="400px" frameborder="0"></iframe>	</div> ' + fHtml+
	  			' </div>';
	$(html).appendTo(document.body);
	$('#'+modalId).modal({
		show: true,
	}).on('hidden', function () {
		$(this).remove();
	});
	
	if(footer){
		$('#mySaveBtn').click(function(){
			var returnValue = $("#frame"+modalId)[0].contentWindow.myReturnValue();
			if(returnValue == undefined){
				returnValue = "";
			}
			$("#enterFrame")[0].contentWindow.setReturnValue(returnValue);
			if(returnValue.length!=""){
				$('#'+modalId).modal('hide');
			}
		});
	}
}

function showView(lable,url,widths){
	var modalId = "myModal";
	if(widths == null || isNaN(widths)) widths = 900;
	var heights = 542;
	if(url.indexOf("?")>0){
		url = url + "&d_9="+new Date().getTime();
	}else{
		url = url + "?d_9="+new Date().getTime();
	}
	var fHtml='<div class="modal-footer" ><a href="#" class="btn btn-primary"  data-dismiss="modal" aria-hidden="true">关闭</a></div>';
	var html= '<div id="'+modalId+'" class="modal hide fade in" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="true" style="width:'+widths+'px;height:'+heights+'px;display: none; border:solid 2px #317eaa;">' +
			 	' <div class="modal-header" style="background-color:#0d96cc" > ' +
	  			' <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> ' +
	  			' <h3>'+lable+'</h3> ' +
				' </div><div class="modal-body" style="height:'+heights+'px;background-color:#ebf4fb;"> ' +
				' <iframe id="frame'+modalId+'" name="frame'+modalId+'" src="'+url+'" width="100%" height="400px" frameborder="0"></iframe>	</div> ' + fHtml+
	  			' </div>';
	$(html).appendTo(document.body);
	$('#'+modalId).modal({
		show: true,
	}).on('hidden', function () {
		$(this).remove();
	});
}

function test(){
	var url = "<c:url value='/zsgl/company/get4ndzc.jhtml'/>";
	top.showMyModal("选择自查企业",url,900,true);
}
</script>
</head>
<body sroll="no">
	<div id="content" class="content">
		<div style="position: absolute; display: none;">
			<a href="#" onclick="javascript:test();">我的测试</a>
		</div>
		<iframe id="enterFrame" src="<c:url value='/main/index.jhtml' />"
			width="100%" height="100%" frameborder="0"></iframe>
	</div>
</body>
</html>