<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>物流费用明细表</title>
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
			var areaId = $("#areaId").val();
			var time = $("#startTime").val();
			var url ="<c:url value='/logistic/logistics-analysis/exportAnalysis.jhtml'/>?vo.custom.area.parentArea.id=" + areaId+"&vo.orderStartTime="+ time;
			top.showView("请选择导出字段", url , 900);
		});
	});
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<form name="form1" action="logisticsAnalysis.jhtml"
						class="form-inline" method="post" id="form1">
						<h5>
							<span>物流费用分析表</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>所属大区：</label> <select
									name="vo.custom.area.parentArea.id" class="input-medium"
									id="areaId">
										<option value="">请选择</option>
										<c:forEach items="${parentAreas }" var="area">
											<option value="${area.id}"
												<c:if test="${vo.custom.area.parentArea.id == area.id }">selected="selected"</c:if>>${area.areaName}</option>
										</c:forEach>
								</select></li>
								<li><label>统计年月：</label> <input id="startTime" type="text"
									class="input-medium Wdate" name="vo.orderStartTime"
									value="${vo.orderStartTime}" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM'})" /></li>
							</div>
							<div class="span2">
								<li><label><button name="searchBtn"
											class="btn btn-primary" type="submit">搜 索</button></label></li>
							</div>
						</ul>
					</form>
				</div>
				<!-- / span12 end-->
			</div>
			<!-- / row-fluid end-->
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<div class="pull-right  mB10">
						<pt:checkFunc code="LOG_ANALYSIS_EXP">
							<button class="btn" id="expBtn">
								<i class="icon-list-alt"></i>导出
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="LOG_ANALYSIS_PRI">
							<button class="btn" onclick="print()">
								<i class="icon-print"></i>打印
							</button>
						</pt:checkFunc>
					</div>
				</div><!-- / span12 end -->
			</div><!-- / row-fluid end -->
		</div><!--container-fluid end-->
		<div class="container_12 divider">
 		<div class="grid_4 height400">
	 	<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
	         	<thead>
	   				<tr>
	   					<th>序号</th>
	   					<th>所属大区</th>
	   					<th>当年费用比</th>
	   					<th>去年费用比</th>
	   					<th>累计本币金额</th>
	   					<th>累计物流费用</th>
	   					<th>累计费用比</th>
	   					<th>当月本币金额</th>
	   					<th>当月物流费用</th>
	   					<th>当月累计费用比</th>
	   				</tr>
	   			</thead>
	   			<tbody>
	   				 <c:forEach items="${resultList}" var="object" varStatus="s">
   						<tr>
							<td >${s.index + 1}</td>
							<td >${object[1]}</td>
							<td ><fmt:formatNumber pattern="#.##" value="${(object[2] != null && (object[3] + object[4]) != null) ? (object[3] + object[4]) / object[2] * 100 : 0}" />%</td>
							<td ><fmt:formatNumber pattern="#.##" value="${(object[5] != null && (object[6] + object[7]) != null) ? (object[6] + object[7]) / object[5] * 100 : 0}" />%</td>
							<td ><fmt:formatNumber value="${object[8] != null ? object[8] : 0 }" type="currency" /></td>
							<td ><fmt:formatNumber value="${(object[9] + object[10]) != null ? (object[9] + object[10]) : 0 }" type="currency" /></td>
							<td ><fmt:formatNumber pattern="#.##" value="${(object[8] != null && (object[9] + object[10]) != null) ? (object[9] + object[10]) / object[8] * 100 : 0}" />%</td>
							<td ><fmt:formatNumber value="${object[11] != null ? object[11] : 0 }" type="currency" /></td>
							<td ><fmt:formatNumber value="${(object[12] + object[13]) != null ? (object[12] + object[13]) : 0 }" type="currency" /></td>
							<td ><fmt:formatNumber pattern="#.##" value="${(object[11] != null && (object[12] + object[13]) != null) ? (object[13] + object[12]) / object[11] * 100 : 0}" />%</td>
   						</tr>
	   				</c:forEach>
	   			</tbody>
	         </table>
	         </div>
	         </div>
	</div>
	<!--jt-container end-->
</body>
</html>