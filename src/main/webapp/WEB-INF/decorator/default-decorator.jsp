<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<link href="<c:url value='/lib/cssreset.css'/>" rel="stylesheet"
	type="text/css" />
<link href="<c:url value='/lib/bootstrap-2.3.2/css/bootstrap.min.css'/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/main.css'/>" rel="stylesheet"
	type="text/css" />
<link href="<c:url value='/css/common.css'/>" rel="stylesheet"
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
<script type="text/javascript"
	src="<c:url value='/lib-ext/My97DatePicker-4.72/calendar.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/lib-ext/My97DatePicker-4.72/WdatePicker.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/lib-ext/Validator.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/jquery-1.4.4.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/jquery.jqprint-0.3.js'/>"></script>

<script type="text/javascript">
	
		window.onload = function() {
			$("#objectsPerPage").val("${vo.objectsPerPage}");
			if (parent != null) {
				if (parent.setIframeHeight != null) {
					parent.setIframeHeight();
				}
			}
		}

		function edit(url){
			location.href=url;
		}

		var ids="",keys="";
		function dels() {
			getCheckBox();
			if (ids == '') {
				alert('请选择要删除的信息');
				return;
			}
			if (!confirm('确定要删除您勾选的记录？'))
				return;
			//location.href = 'delete.jhtml?ids=' + ids + "&keys="+keys;
			$("#ids").val(ids);
			$("#keys").val(keys);
			$("#form1").attr("action","delete.jhtml");
			$("#form1").submit();
		}
		
		function getCheckBox() {
			ids="",keys="";
			var obj = document.getElementsByName('cid');
			for ( var i = 0; i < obj.length; i++) {
				if (obj[i].checked == true) {
					var ss = obj[i].value.split(",");
					ids += ss[0] + ',';
					keys += ss[1] + ',';
				}
			}
		}

		/*
		 * display 分页 form 提交。 
		 */
		var PAGE_BY_FORM = true; // 默认为 form 提交分页 
		function pageByPost(page) {
			if (!PAGE_BY_FORM) {
				return true;
			}

			var form = $("form").get(0);
			var c = $("#objectsPerPage").val();
			if(isNaN(c)){
				alert("每页条数请输入正确的数字");
				$("#objectsPerPage").focus();
			}else{
				$(form).append(
						'<input name="vo.objectsPerPage" type="hidden" value="' + c + '" />');
				$(form).append(
						'<input name="page" type="hidden" value="' + page + '" />');
				form.submit();
			}

			return false;
		}
		
		function setPerPaper(){
			if (!PAGE_BY_FORM) {
				return true;
			}
			var form = $("form").get(0);
			var c = $("#objectsPerPage").val();
			if(isNaN(c) || !checkRate(c)){
				alert("每页条数请输入正确的数字");
				$("#objectsPerPage").focus();
			}else{
				$(form).append(
						'<input name="vo.objectsPerPage" type="hidden" value="' + c + '" />');
				$(form).append(
						'<input name="page" type="hidden" value="0" />');
				form.submit();
			}
			return false;
		}
		
		//判断正整数
		function checkRate(s)
		{
			var re = /^[0-9]*[1-9][0-9]*$/ ;  
		    return re.test(s);
		}
		
		function print(){
			$("#print").jqprint();
		}
		
</script>

<decorator:head />
<title><decorator:title /></title>
<style>
html {
	overflow-y: auto;
}
</style>
</head>
<body>


	<!-- 这里是功能的内容区域 -->
	<decorator:body />

	<!-- 这里是操作信息提示区域 -->
	<%@ include file="/WEB-INF/common/message.jsp"%>
</body>
</html>
