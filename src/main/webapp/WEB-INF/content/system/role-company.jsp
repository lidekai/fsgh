<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/stylepc.css' />" /> 
<script type="text/javascript" src="<c:url value='/lib/jquery-2.0.0.min.js'/>"></script>
<title>公司资质</title>
<style type="text/css">
.btn {
    background: rgba(0, 0, 0, 0) url("../../images/s_btn.gif") no-repeat scroll 0 0;
    border: medium none;
    display: block;
    float: left;
    height: 20px;
    padding: 0;
    width: 29px;
}
.btn:hover {
    background: rgba(0, 0, 0, 0) url("../../images/s_btn_h.gif") no-repeat scroll 0 0;
}
.in {
    border: 1px solid #d1cdcd;
    color: #888;
    float: left;
    height: 20px;
    line-height: 16px;
    padding: 0 2px;
    width: 150px;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$("#search_btn2").click(function(){
		$("#form").submit();
	})
})
function viewImg(url,id){
	$(".selected").each(function(){
		$(this).attr("class","");;
	})
	$("#" + id).attr("class","selected");
	$("#image").attr("src","../../" + url);
}
</script>
</head>
<body>
	<div class="container">
		<div class="menu">
			
			<ul class="ulmenu1" style="display: block;">
				<li>
					<form action="company.jhtml" id="form" method="post">
						<input placeholder="输入文件名称" name="attachVO.name" class="in" value="${attachVO.name}"/>
						<input id="search_btn2" class="btn" type="button">
					</form>
				</li>
				<c:forEach items="${list}" var="vo" varStatus="s">
					<li><a class="${s.index == 0 ? 'selected' : '' }" href="javascript:viewImg('${fn:replace(vo.path,'\\','/')}','${s.index}')" id="${s.index}" title="${vo.name}">${vo.name}</a></li>
				</c:forEach>
			</ul>
		</div>
		
		<div class="content">
			<img src="<c:url value='../../${list[0].path}' />" id="image" style="width:600px;height:auto;"/>
		</div>

	</div>
</body>
</html>