<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="taglibs.jsp"%>
<script type="text/javascript">
var message_info = "";
<c:if test="${not empty actionMessages}">
	message_info = "<div class=\"alert alert-success center tCenter\" style=\"margin-bottom:-10px;\">" +
 				"<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button> "+
 				"<strong>提示信息：</strong> ${actionMessages} </div> ";
</c:if>

<c:if test="${not empty actionErrors}">
message_info = "<div class=\"alert alert-error center tCenter\" style=\"margin-bottom:-10px;\">" +
				"<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button> "+
				"<strong>错误信息：</strong> ${actionErrors} </div> ";
</c:if>

if(message_info.length > 0){
	$(".jt-container").prepend(message_info);
	$(".alert").animate({ opacity: "0.3"},400).animate({ opacity: "1"},400).animate({ opacity: "0.3"},400).animate({ opacity: "1"},400).animate({ opacity: "0.3"},400).animate({ opacity: "1"}, 400);
	
	setTimeout(function(){
		$(".alert").hide(500);
	},5000);
}     

</script>