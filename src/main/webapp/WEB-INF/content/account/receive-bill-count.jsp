<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>应收单明细余额表</title>

<script type="text/javascript" src="<c:url value='/lib/fixedHeader/demo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/jquery.fixedheadertable.js'/>"></script>
<link href="<c:url value='/css/fixedHeader/960.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/defaultTheme.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/myTheme.css'/>" rel="stylesheet" type="text/css" />
<script>
$(document).ready(function(){
 	
	$('#result').fixedHeaderTable({
		altClass: 'odd',
		fixedColumns:1,
		footer: true
	}); 
	
	//导出按钮事件
	$("#expBtn").click(function(){
		var csbvcode=$("#csbvcode").val();
		var customName=$("#customName").val();
		var start = $("#orderDateStart").val();
		var end = $("#orderDateEnd").val();
		var url="<c:url value='/account/receive-bill/exportReceiveBill.jhtml'/>";
		url+="?act=COUNT&vo.csbvcode=" + csbvcode +"&vo.customName=" + customName ;
		url+="&vo.beginTime="+ start+ "&vo.endTime="+ end ;
		
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
     <form name="form1" action="list.jhtml" class="form-inline" method="post" id="form1">
     <input type="hidden" name="ids" value="" id="ids"/>
     <input type="hidden" name="keys" value="" id="keys"/>
      <input type="hidden" name="act" value="${act}"/>
      <h5><span>应收单明细余额表</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
 	        <li><label>销售发票号：</label><input id="csbvcode" class="input-medium" name="vo.csbvcode" value="${vo.csbvcode }" type="text" placeholder="输入发货单号" /></li>
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  id="customName" name="vo.customName" value="${vo.customName }" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
       			<label>发票日期：</label>
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
				<th style="min-width:20px">销售发票号</th>
				<th style="min-width:30px">凭证号</th>
				<th style="min-width:30px">发票日期</th>
				<th style="min-width:30px">到期日期</th>
				<th style="min-width:30px">客户类型</th>
				<th style="min-width:30px">客户地区</th>
				<th style="min-width:30px">客户编码</th>
				<th style="min-width:140px">客户名称</th>
				
				<th style="min-width:50px">本币实际发票额</th>
				<th style="min-width:50px">已收款合计</th>
				<th style="min-width:50px">实际收款额</th>
				<th style="min-width:50px">待费用发票额</th>
				<th style="min-width:50px">待退货额</th>
				<th style="min-width:50px">暂扣额</th>
				<th style="min-width:50px">其他余额</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td colspan="9">合计,共${fn:length(pageList.list)}条记录</td>
				<c:forEach items="${sumList }" var="s">
					<td><fmt:formatNumber value="${s[2]}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${s[3]}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${s[5]}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${s[4]}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${s[6]}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${s[7]}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${s[8]}" pattern="#.##"/></td>
				</c:forEach> 
			</tr>
		</tfoot>
		<tbody>
		<c:forEach items="${pageList.list}"  var="vo" varStatus="s">
			<tr>
				<td>${s.index + 1}</td>
				<td>${vo.csbvcode}</td>
				<td>${vo.number}</td>
				<td><fmt:formatDate value="${vo.billDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${vo.maturityDate }" pattern="yyyy-MM-dd"/></td>
				<td>${vo.customType}</td>
				<td>${vo.customArea}</td>
				<td>${vo.customCde}</td>
				<td>${vo.customName}</td>
				
				<td><fmt:formatNumber value="${vo.countPrice1}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${vo.receivePrice}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${vo.actualPrice}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${vo.chargePrice}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${vo.returnPrice}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${vo.holdPrice}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${vo.otherPrice}" pattern="#.##"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</div><!--jt-container end-->

</body>
</html>