<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>预提核销明细表</title>
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
		fixedColumns:6,
		footer: true
	});
	
	$("#expBtn").click(function(){
		var name=$("#name").val();
		var code = $("#code").val();
		var pareaId = $("#pareaId").val();
		var areaId = $("#areaId").val();
		var start = $("#start").val();
		var end = $("#end").val();
		var url="<c:url value='/budget/analysis/exportCharge.jhtml'/>";
		url+="?vo.custom.customName=" + name +"&vo.custom.customCde=" + code + "&vo.custom.area.parentArea.id="+ pareaId + "&vo.custom.area.id=" + areaId;
		url+="&vo.dateStart="+ start+ "&vo.dateEnd="+ end;
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
     <form name="form1" action="charge.jhtml" class="form-inline" method="post" id="form1">
      <h5><span>核销明细表</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
      		 <li>
 	        	<label>所属大区：</label>
 	        	<select name="vo.custom.area.parentArea.id" class="input-medium" id="pareaId" onchange="getChildren()">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="${parentAreas }" var="area">
 	        			<option value="${area.id}" <c:if test="${vo.custom.area.parentArea.id == area.id }">selected="selected"</c:if>>${area.areaName}</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	        <li>
 	        	<label>所属地区：</label>
 	        	<select name="vo.custom.area.id" class="input-medium" id="areaId">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="${areas}" var="area">
 	        			<option value="${area.id}" <c:if test="${vo.custom.area.id == area.id }">selected="selected"</c:if>>${area.areaName}</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	       <li>
 	        	<label>客户名称：</label>
 	        	<input id="name" class="input-medium" name="vo.custom.customName" value="${vo.custom.customName}" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
 	        	<label>客户编码：</label>
 	        	<input id="code" class="input-medium" name="vo.custom.customCde" value="${vo.custom.customCde}" type="text" placeholder="输入需要搜索的客户编码" />
 	        </li>
 	        <li>
       			<label>统计年月：</label>
       			<input id="start" type="text" class="input-medium Wdate" name="vo.dateStart" value="${vo.dateStart}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />至
       			<input id="end" type="text" class="input-medium Wdate" name="vo.dateEnd" value="${vo.dateEnd}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
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
   					<th rowspan="2" style="vertical-align:middle;min-width:150px;">客户名称</th>
   					<th rowspan="2" style="vertical-align:middle;min-width:30px;">客户编码</th>
   					<th rowspan="2" style="vertical-align:middle;min-width: 50px;">年月</th>
   					<th rowspan="2" style="vertical-align:middle;min-width: 30px;">类型</th>
   					
   					<th style="vertical-align:middle;min-width: 80px;">编码</th>
   					<th style="vertical-align:middle;min-width: 80px;">项目</th>
   					<th style="vertical-align:middle;min-width: 80px;">费用</th>
   				
   					<th style="vertical-align:middle;min-width: 80px;">编码</th>
   					<th style="vertical-align:middle;min-width: 80px;">进场费</th>
   					<th style="vertical-align:middle;min-width: 80px;">固定费用</th>
   					<th style="vertical-align:middle;min-width: 80px;">年返金</th>
   					<th style="vertical-align:middle;min-width: 80px;">月返金</th>
   					<th style="vertical-align:middle;min-width: 80px;">网络信息费</th>
   					<th style="vertical-align:middle;min-width: 80px;">配送服务费</th>
   					<th style="vertical-align:middle;min-width: 80px;">海报费</th>
   					<th style="vertical-align:middle;min-width: 80px;">促销陈列费</th>
   					<th style="vertical-align:middle;min-width: 80px;">赞助费</th>
   					<th style="vertical-align:middle;min-width: 80px;">损耗费</th>
   					<th style="vertical-align:middle;min-width: 80px;">固定折扣</th>
   					<th style="vertical-align:middle;min-width: 80px;">堆头费</th>
   					<th style="vertical-align:middle;min-width: 80px;">市场费</th>
   					<th style="vertical-align:middle;min-width: 80px;">现款现货返利</th>
   					<th style="vertical-align:middle;min-width: 80px;">其他费用</th>
   					<th style="vertical-align:middle;min-width: 80px;">费用总和</th>
   				</tr>
   				<tr>
   					<th colspan="3" style="vertical-align:middle;">合同外</th>
   					<th colspan="17" style="vertical-align:middle;">合同内</th>
   				</tr>
   			</thead>
   			<tbody>
   				 <c:forEach items="${customList}" var="custom" varStatus="s">
   					
   						<%-- <c:if test="${fn:length(outFeeMap[custom.id]) > 0}">
   							<c:forEach items="${outFeeMap[custom.id]}" var="outFee" varStatus="s">
   								<tr>
   									<td style="vertical-align:middle;">${outFee.custom.area.parentArea.areaName}</td>
   									<td style="vertical-align:middle;">${outFee.custom.area.areaName}</td>
   									<td style="vertical-align:middle;">${outFee.custom.customName}</td>
   									<td style="vertical-align:middle;">${outFee.custom.customCde}</td>
	   								<td style="vertical-align:middle;"><fmt:formatDate value="${outFee.provisionTime }" pattern="yyyy-MM-dd"/></td>
   									<td style="vertical-align:middle;">预提</td>
	   								<td style="vertical-align:middle;">${outFee.provisionCode}</td>
	   								<td style="vertical-align:middle;">${outFee.provisionProject.feeName}</td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${outFee.totalFee}" type="currency" /></td>
	   								<td colspan="17"></td>
   								</tr>
	   						</c:forEach>
   						</c:if> --%>
   						
   						<c:if test="${fn:length(outFeeVerMap[custom.id]) > 0}">
   							<c:forEach items="${outFeeVerMap[custom.id]}" var="outFeeVer" varStatus="s">
   								<tr>
   									<td style="vertical-align:middle;">${outFeeVer.custom.area.parentArea.areaName}</td>
   									<td style="vertical-align:middle;">${outFeeVer.custom.area.areaName}</td>
   									<td title="${outFeeVer.custom.customName}" style="vertical-align:middle;">${fn:length(outFeeVer.custom.customName) > 12 ? fn:substring(outFeeVer.custom.customName,0,12): outFeeVer.custom.customName}
											${fn:length(outFeeVer.custom.customName) > 12 ? '...': ''}
									</td>
   									<td style="vertical-align:middle;">${outFeeVer.custom.customCde}</td>
	   								<td style="vertical-align:middle;"><fmt:formatDate value="${outFeeVer.verTime}" pattern="yyyy-MM-dd"/></td>
   									<td style="vertical-align:middle;">核销</td>
	   								<td style="vertical-align:middle;">${outFeeVer.verCode}</td>
	   								<td style="vertical-align:middle;">${outFeeVer.outFeeProvision.provisionProject.feeName}</td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${outFeeVer.totalFee}" type="currency" /></td>
	   								<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
	   								<td></td><td></td><td></td><td></td><td></td><td></td><td></td>
   								</tr>
	   						</c:forEach>
   						</c:if>
   						
   						<%-- <c:if test="${fn:length(inFeeMap[custom.id]) > 0}">
   							<c:forEach items="${inFeeMap[custom.id]}" var="inFee" varStatus="s">
   								<tr>
   									<td style="vertical-align:middle;">${inFee.custom.area.parentArea.areaName}</td>
   									<td style="vertical-align:middle;">${inFee.custom.area.areaName}</td>
   									<td style="vertical-align:middle;">${inFee.custom.customName}</td>
   									<td style="vertical-align:middle;">${inFee.custom.customCde}</td>
	   								<td style="vertical-align:middle;"><fmt:formatDate value="${inFee.createTime }" pattern="yyyy-MM"/></td>
   									<td style="vertical-align:middle;">预提</td>
	   								<td colspan="3"></td>
	   								<td style="vertical-align:middle;">${inFee.code}</td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.enterFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.fixedFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.yearReturnFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.monthReturnFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.netInfoFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.deliveryFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.posterFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.promotionFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.sponsorFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.lossFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.fixedDiscount}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.pilesoilFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.marketFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.caseReturnFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.otherFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFee.inFeeCount}" type="currency" /></td>
   								</tr>
	   						</c:forEach>
   						</c:if> --%>
   						
   						<c:if test="${fn:length(inFeeVerMap[custom.id]) > 0}" >
   							<c:forEach items="${inFeeVerMap[custom.id]}" var="inFeeVer" varStatus="s">
   								<tr>
   									<td style="vertical-align:middle;">${inFeeVer.custom.area.parentArea.areaName}</td>
   									<td style="vertical-align:middle;">${inFeeVer.custom.area.areaName}</td>
   									<td title="${inFeeVer.custom.customName}" style="vertical-align:middle;">${fn:length(inFeeVer.custom.customName) > 12 ? fn:substring(inFeeVer.custom.customName,0,12): inFeeVer.custom.customName}
											${fn:length(inFeeVer.custom.customName) > 12 ? '...': ''}
									</td>
   									<td style="vertical-align:middle;">${inFeeVer.custom.customCde}</td>
	   								<td style="vertical-align:middle;"><fmt:formatDate value="${inFeeVer.verDate }" pattern="yyyy-MM"/></td>
   									<td style="vertical-align:middle;">核销</td>
	   								<td></td><td></td><td></td>
	   								<td style="vertical-align:middle;">${inFeeVer.verCode}</td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.enterFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.fixedFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.yearReturnFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.monthReturnFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.netInfoFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.deliveryFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.posterFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.promotionFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.sponsorFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.lossFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.fixedDiscount}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.pilesoilFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.marketFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.caseReturnFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.otherFee}" type="currency" /></td>
	   								<td style="vertical-align:middle;"><fmt:formatNumber value="${inFeeVer.totalFee}" type="currency" /></td>
   								</tr>
	   						</c:forEach>
   						</c:if>
   						
   						<%-- <c:if test="${inFeeMap[custom.id] == null && outFeeMap[custom.id] == null && outFeeVerMap[custom.id] == null && inFeeVerMap[custom.id] == null}">
 							<tr>
 								<td style="vertical-align:middle;">${custom.area.parentArea.areaName}</td>
   								<td style="vertical-align:middle;">${custom.area.areaName}</td>
 								<td style="vertical-align:middle;">${custom.customName}</td>
 								<td style="vertical-align:middle;">${custom.customCde}</td>
  								<td colspan="22"></td>
 							</tr>
   						</c:if> --%>
   				</c:forEach>
   			</tbody>
         </table>
         </div>
         </div>
</div><!--jt-container end-->
</body>
</html>