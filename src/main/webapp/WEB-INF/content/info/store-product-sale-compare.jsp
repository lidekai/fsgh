<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>门店同期销售对比表</title>
<script type="text/javascript" src="<c:url value='/lib/jquery-1.4.4.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/jquery.jqprint-0.3.js'/>"></script>

<script type="text/javascript" src="<c:url value='/lib/fixedHeader/demo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/jquery.fixedheadertable.js'/>"></script>
<link href="<c:url value='/css/fixedHeader/960.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/defaultTheme.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/myTheme.css'/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function(){
	
	$('#result').fixedHeaderTable({
		altClass: 'odd',
		fixedColumns:4,
		footer: true
	});
	
	$("#expBtn").click(function(){
		var pareaId = $("#pareaId").val();
		var areaId = $("#areaId").val();
		var customName = $("#customName").val();
		var storeName = $("#storeName").val();
		var productName = $("#productName").val();
		var start = $("#start").val();
		var end = $("#end").val();
		
		var url="<c:url value='/info/store-product-sale/exportCompare.jhtml'/>";
		url+="?vo.parentAreaId=" + pareaId +"&vo.areaId=" + areaId + "&vo.customName="+ customName + "&vo.storeName=" + storeName;
		url+="&vo.productName=" + productName + "&vo.startMonth="+ start+ "&vo.endMonth="+ end;
		top.showView("请选择导出字段", url , 900);
	});
})

function getChildren(){
	var parentId = $("#pareaId").val();
	var html = "<option value=\"\">请选择</option>";
	if(parentId == "" || parentId == null)
		$("#areaId").html(html);
	else{
		$.ajax({
			type:"post",
			url:"<c:url value='/info/area/getChildrenJson.jhtml'/>",
			data:{"areaId":parentId},
			dataType:"text",
			success:function(data){
				var date = eval(data);
				var result = "<option value=\"\">请选择</option>";;
				for(var i=0;i<date.length;i++){
					 result += "<option value='" + date[i].id + "'>" + date[i].name + "</option>";
				}
				$("#areaId").html(result);  
			}
		});
	}; 
}
</script>
</head>
<body>
<div class="jt-container">
 <div class="container-fluid">
  <div class="row-fluid">
   <div class="span12" style="min-width:1000px;">
     <form name="form1" action="compare.jhtml" class="form-inline" method="post" id="form1">
      <h5><span>门店同期销售对比表</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
 	        <li><label>所属大区：</label>
 	        	<select name="vo.parentAreaId" class="input-medium" id="pareaId" onchange="getChildren()">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="${parentAreas }" var="area">
 	        			<option value="${area.id}" <c:if test="${vo.parentAreaId == area.id }">selected="selected"</c:if>>${area.areaName}</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	        <li>
 	        	<label>所属地区：</label>
 	        	<select name="vo.areaId" class="input-medium" id="areaId">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="${areas}" var="area">
 	        			<option value="${area.id}" <c:if test="${vo.areaId == area.id }">selected="selected"</c:if>>${area.areaName}</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium" id="customName" name="vo.customName" value="${vo.customName}" type="text" placeholder="输入需要搜索的名称" />
 	        </li>
 	        <li>
 	        	<label>门店名称：</label>
 	        	<input class="input-medium" id="storeName" name="vo.storeName" value="${vo.storeName}" type="text" placeholder="输入需要搜索的名称" />
 	        </li>
 	        <li>
 	        	<label>产品名称：</label>
 	        	<input class="input-medium" id="productName" name="vo.productName" value="${vo.productName}" type="text" placeholder="输入需要搜索的名称" />
 	        </li> 	        
 	        <li>
       			<label>对比时间：</label>
       			<input id="start" type="text" class="input-medium Wdate" name="vo.startMonth" value="${vo.startMonth}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" />至
       			<input id="end" type="text" class="input-medium Wdate" name="vo.endMonth" value="${vo.endMonth}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/>
       		</li>
        </div>
        <div class="span2">
        	<li><label><button name="searchBtn" class="btn btn-primary" type="submit">搜 索</button></label></li>
        </div>
      </ul>
     </form>
   </div><!-- / span12 end-->
  </div><!-- / row-fluid end-->
  <div class="row-fluid">
   <div class="span12" style="min-width:1000px;">
   		<div class="pull-right  mB10">
           	<button class="btn" id="expBtn"><i class="icon-list-alt"></i>导出</button>
          	<button class="btn" onclick="print()"><i class="icon-print"></i>打印</button>
         </div>
         <div class="pull-right  mB10">
         </div>
   </div><!-- / span12 end -->
  </div><!-- / row-fluid end -->
 </div><!--container-fluid end-->
 <div class="container_12 divider">
 		<div class="grid_4 height400">
	 	<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
	         	<thead>
	   				<tr>
	   					<th rowspan="2" style="vertical-align:middle;min-width:50px;">所属大区</th>
	   					<th rowspan="2" style="vertical-align:middle;min-width:50px;">所属地区</th>
	   					<th rowspan="2" style="vertical-align:middle;min-width:50px;">客户编码</th>
	   					<th rowspan="2" style="vertical-align:middle;min-width:150px;">客户名称</th>
	   					
	   					<th style="vertical-align:middle;min-width: 80px;">门店编码</th>
	   					<th style="vertical-align:middle;min-width: 80px;">门店名称</th>
	   					<th style="vertical-align:middle;min-width: 80px;">产品编码</th>
	   					<th style="vertical-align:middle;min-width: 80px;">产品名称</th>
	   					
	   					<th style="vertical-align:middle;min-width: 80px;">前期销售数量</th>
	   					<th style="vertical-align:middle;min-width: 80px;">后期销售数量</th>
	   					<th style="vertical-align:middle;min-width: 80px;">销售数量对比</th>
	   					<th style="vertical-align:middle;min-width: 80px;">前期销售额</th>
	   					<th style="vertical-align:middle;min-width: 80px;">后期销售额</th>
	   					<th style="vertical-align:middle;min-width: 80px;">销售额对比</th>
	   				</tr>
	   			</thead>
	   			<tbody>
	   				 <c:forEach items="${reslutList}" var="object" varStatus="s">
	   				 	<tr>
	   				 		<td>${object[0]}</td>
	   				 		<td>${object[1]}</td>
	   				 		<td>${object[2]}</td>
	   				 		<td>${object[3]}</td>
	   				 		<td>${object[4]}</td>
	   				 		<td>${object[5]}</td>
	   				 		<td>${object[6]}</td>
	   				 		<td>${object[7]}</td>
	   				 		<td>${object[8]}</td>
	   				 		<td>${object[10]}</td>
	   				 		<td><fmt:formatNumber value="${object[8] == 0 ? '0' : (object[10]-object[8])/object[8]*100}" pattern="#.##"/>%</td>
	   				 		<td>${object[9]}</td>
	   				 		<td>${object[11]}</td>
	   				 		<td><fmt:formatNumber value="${object[9] == 0 ? '0' : (object[11]-object[9])/object[9]*100}" pattern="#.##"/>%</td>
	   				 	</tr>
	   				</c:forEach>
	   			</tbody>
	         </table>
	         </div>
	         </div>
</div><!--jt-container end-->
</body>
</html>