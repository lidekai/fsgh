<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待核单表头余额表</title>

<script type="text/javascript" src="<c:url value='/lib/fixedHeader/demo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/jquery.fixedheadertable.js'/>"></script>
<link href="<c:url value='/css/fixedHeader/960.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/defaultTheme.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/myTheme.css'/>" rel="stylesheet" type="text/css" />
<script>
$(document).ready(function(){
 	
	$('#result').fixedHeaderTable({
		altClass: 'odd',
		fixedColumns:3
	}); 
	
	//导出按钮事件
	$("#expBtn").click(function(){
		var customName=$("#customName").val();
		var start = $("#orderDateStart").val();
		var end = $("#orderDateEnd").val();
		var url="<c:url value='/account/check-bill/exportStatBill.jhtml'/>";
		url+="?vo.customName=" + customName 
			+"&vo.beginTime="+ start+ "&vo.endTime="+ end;
		
		top.showView("请选择导出字段", url , 900);
	});
});
</script>

</head>
<body>
<div class="jt-container">
 <div class="container-fluid">
  <div class="row-fluid">
   <div class="span12" style="min-width:1000px;">
     <form name="form1" action="statBill.jhtml" class="form-inline" method="post" id="form1">
      <h5><span>待核单管理</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  id="customName" name="vo.customName" value="${vo.customName}" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
       			<label>制单时间：</label>
       			<input type="text" class="input-medium Wdate" name="vo.beginTime" value="${vo.beginTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
       			<input type="text" class="input-medium Wdate" name="vo.endTime" value="${vo.endTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
       		</li>
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
   </div><!-- / span12 end -->
  </div><!-- / row-fluid end -->
 </div><!--container-fluid end-->
<div class="container_12 divider">
 		<div class="grid_4 height400" id="page1">
	 	<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
	    <thead>
			<tr> 
				<th style="min-width:20px">序号</th>
				<th style="min-width:40px">客户编号</th>
				<th style="min-width:140px">客户名称</th>
				
				<th style="min-width:50px">本币价税合计</th>
				<th style="min-width:50px">已收款合计</th>
				<th style="min-width:50px">实际收款额</th>
				<th style="min-width:50px">待费用发票额</th>
				<th style="min-width:50px">待退货额</th>
				<th style="min-width:50px">暂扣额</th>
				<th style="min-width:50px">其他余额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${statList}"  var="object" varStatus="s">
			<tr>
				<td>${s.index + 1}</td>
				<td>${object[0]}</td>
				<td>${object[1]}</td>
				
				<td><fmt:formatNumber value="${object[2]}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${object[3]}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${object[4]}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${object[5]}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${object[6]}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${object[7]}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${object[8]}" pattern="#.##"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</div><!--jt-container end-->

</body>
</html>