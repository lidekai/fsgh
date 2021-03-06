<%@page import="com.kington.fshg.common.PublicType"%>
<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>门店销售信息表</title>

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
     <form name="form1" action="doExportCompare.jhtml" class="form-horizontal" method="post">
     <input type="hidden"  name="header" id="header" />
     <input type="hidden"  name="vo.storeName" value="${vo.storeName}"/>
     <input type="hidden"  name="vo.productName" value="${vo.productName}"/>
      <input type="hidden"  name="vo.customName" value="${vo.customName}"/>
      <input type="hidden"  name="vo.parentAreaId" value="${vo.parentAreaId}"/>
      <input type="hidden"  name="vo.areaId" value="${vo.areaId}"/>
      <input type="hidden"  name="vo.startMonth" value="${vo.startMonth}"/>
      <input type="hidden"  name="vo.endMonth" value="${vo.endMonth}"/>
	   <h5><span id="formTitle">导出门店同期销售对比表</span></h5>
		
		<div class="row-fluid">
   			<div class="span12">
   			<div class="control-group">
   			 <label class="control-label">表头信息</label>
      		<div class="controls">
      			<c:forEach items="${compareHeader}" var="hl">
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