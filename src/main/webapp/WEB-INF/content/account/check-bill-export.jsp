<%@page import="com.kington.fshg.common.PublicType"%>
<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>待核单</title>

<script>
$(document).ready(function(){
	//确定按钮事件
	$("button").eq(0).click(function(){
		getHeader();
		form1.submit();
	});
	
	$(".jt-container").css({  "background-color": "#ebf4fb" });
	
	$(".label").each(function(i){
		$(this).addClass("label-success");
	});

	$(".label").click(function(){
		if($(this).hasClass("label-success")){
			$(this).removeClass("label-success");
		}else{
			$(this).addClass("label-success");
		}
	});
	
	
});
function getHeader(){
	var names="";
	var bhgxm="";
	$(".label-success").each(function(){
		names = names + $(this).attr("att-name")+ ",";
	});
	$("#header").val(names);
}


</script>


</head>
<body>
<div class="jt-container" style="max-width:900px;">
 <div class="container-fluid">
  <div class="row-fluid">
   <div class="span12">
     <form name="form1" action="doExport.jhtml" class="form-horizontal" method="post">
     <input type="hidden"  name="header" id="header" />
     <input type="hidden"  name="vo.code" value="${vo.code}"/>
     <input type="hidden"  name="vo.customName" value="${vo.customName}"/>
     <input type="hidden"  name="vo.beginTime" value="${vo.beginTime}"/>
      <input type="hidden"  name="vo.endTime" value="${vo.endTime}"/>
    
	   <h5><span id="formTitle">导出应收单</span></h5>
		
		<div class="row-fluid">
   			<div class="span12">
   			<div class="control-group">
   			 <label class="control-label">表头信息</label>
      		<div class="controls">
      			<c:forEach items="${checkBillHeader}" var="hl">
      					<span class="label" att-name="${hl}">${hl}</span>
      			</c:forEach>
      			<blockquote><small>说明：标签显示为绿色表示已选中的标签</small></blockquote>
      		</div>
      		</div>
   			</div>
		</div>
		
       <div class="control-group" style="text-align:center;">
        <div class="controls">
         	<button type="button"  class="btn btn-primary">确 定</button>
        </div>
       </div>
       
     </form>
   </div>
  </div>
 </div>
</div>
</body>
</html>