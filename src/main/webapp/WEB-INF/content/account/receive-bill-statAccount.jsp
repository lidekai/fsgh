<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账期统计</title>

<script type="text/javascript" src="<c:url value='/lib/fixedHeader/demo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/jquery.fixedheadertable.js'/>"></script>
<link href="<c:url value='/css/fixedHeader/960.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/defaultTheme.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/myTheme.css'/>" rel="stylesheet" type="text/css" />
<script>
$(document).ready(function(){
 	
	$('#result').fixedHeaderTable({
		altClass: 'odd',
		fixedColumns:2
		
	}); 
	
	//导出按钮事件
	$("#expBtn").click(function(){
		var customName=$("#customName").val();
		var start = $("#orderDateStart").val();
		var end = $("#orderDateEnd").val();
		var statTime = $("#statTime").val();
		var url="<c:url value='/account/receive-bill/exportStatAccount.jhtml'/>";
		url+="?vo.customName=" + customName + "&vo.beginTime="+ start+ "&vo.endTime="+ end + "&vo.statTime=" + statTime;
		
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
     <form name="form1" action="statAccount.jhtml" class="form-inline" method="post" id="form1">
      <h5><span>账期统计</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  id="customName" name="vo.customName" value="${vo.customName}" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
       			<label>发票日期：</label>
       			<input type="text" class="input-medium Wdate" name="vo.beginTime" value="${vo.beginTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
       			<input type="text" class="input-medium Wdate" name="vo.endTime" value="${vo.endTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
       		</li>
 	        <li>
       			<label>统计时间：</label>
       			<input type="text" class="input-medium Wdate" name="vo.statTime" value="${vo.statTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="statTime"/>
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
				
				<th style="min-width:50px">总余额</th>
				<th style="min-width:50px">1-30天</th>
				<th style="min-width:50px">31-60天</th>
				<th style="min-width:50px">61-90天</th>
				<th style="min-width:50px">91-120天</th>
				<th style="min-width:50px">121-150天</th>
				<th style="min-width:50px">151-180天</th>
				<th style="min-width:50px">半年至1年</th>
				<th style="min-width:50px">1年至2年</th>
				<th style="min-width:50px">2年至3年</th>
				<th style="min-width:50px">3年以上</th>
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
				<td><fmt:formatNumber value="${object[9]}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${object[10]}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${object[11]}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${object[12]}" pattern="#.##"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</div><!--jt-container end-->

</body>
</html>