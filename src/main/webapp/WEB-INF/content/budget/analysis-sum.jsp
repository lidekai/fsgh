<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>预提核销余额表</title>
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
	
	//导出按钮事件
	$("#expBtn").click(function(){
		var name=$("#name").val();
		var code = $("#code").val();
		var pareaId = $("#pareaId").val();
		var areaId = $("#areaId").val();
		var start = $("#start").val();
		var end = $("#end").val();
		var url="<c:url value='/budget/analysis/exportSum.jhtml'/>";
		url+="?vo.custom.customName=" + name +"&vo.custom.customCde=" + code + "&vo.custom.area.parentArea.id="+ pareaId + "&vo.custom.area.id=" + areaId;
		url+="&vo.dateStart="+ start+ "&vo.dateEnd="+ end;
		top.showView("请选择导出字段", url , 900);
	});
	
	$(".TD").each(function(){
		var num = $(this).text();
		if(num.indexOf("-") > -1){
			$(this).attr("style","vertical-align:middle;color:red;");
		}
	})
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
     <form name="form1" action="sum.jhtml" class="form-inline" method="post" id="form1">
      <h5><span>预提核销余额表</span></h5>
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
   					<th rowspan="2" style="vertical-align:middle;min-width:130px;">客户名称</th>
   					<th rowspan="2" style="vertical-align:middle;min-width:30px;">客户编码</th>
   					<th rowspan="2" style="vertical-align:middle;min-width:20px;">总预提</th>
   					<th rowspan="2" style="vertical-align:middle;min-width:20px;">总核销</th>
   					<th rowspan="2" style="vertical-align:middle;min-width:20px;">总余额</th>
   					<th rowspan="2" style="vertical-align:middle;min-width:20px;">发票额</th>
   					<th rowspan="2" style="vertical-align:middle;min-width:50px;">投入产出比（总核销/发票额）</th>
   					
   					<th style="vertical-align:middle;min-width: 50px;">预提</th>
   					<th style="vertical-align:middle;min-width: 50px;">核销</th>
   					<th style="vertical-align:middle;min-width: 50px;">余额</th>
   					
   					<th style="vertical-align:middle;min-width: 50px;">预提</th>
   					<th style="vertical-align:middle;min-width: 50px;">实际预提</th>
   					<th style="vertical-align:middle;min-width: 50px;">核销</th>
   					<th style="vertical-align:middle;min-width: 50px;">余额</th>
   				</tr>
   				<tr>
   					<th colspan="3" style="vertical-align:middle;">合同内</th>
   					<th colspan="4" style="vertical-align:middle;">合同外</th>
   				</tr>
   			</thead>
   			<tbody>
   				 <c:forEach items="${list}" var="object">
					<tr>
						<td style="vertical-align:middle;">${object[0]}</td>
						<td style="vertical-align:middle;">${object[1]}</td>
						<td title="${object[2]}" style="vertical-align:middle;">${fn:length(object[2]) > 10 ? fn:substring(object[2],0,10): object[2]}
							${fn:length(object[2]) > 10 ? '...': ''}
						</td>
						<td style="vertical-align:middle;">${object[3]}</td>
						
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber value="${((object[4] != null ? object[4] : 0) - (object[5] != null ? object[5] : 0)) + ((object[7] != null ? object[7] : 0) - (object[8] != null ? object[8] : 0)) }" type="currency" /></td>
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber value="${object[6] + object[9]}" type="currency" /></td>
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber value="${((object[4] != null ? object[4] : 0) - (object[5] != null ? object[5] : 0) - (object[6] != null ? object[6] : 0)) + ((object[7] != null ? object[7] : 0) - (object[8] != null ? object[8] : 0) - (object[9] != null ? object[9] : 0))}" type="currency" /></td>
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber value="${object[10] != null ? object[10] : 0}" type="currency" /></td>
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber pattern="#.##" value="${(object[10] != null && (object[6] + object[9]) != null) ? (object[6] + object[9]) / object[10] * 100 : 0}"/>%</td>
						
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber value="${(object[4] != null ? object[4] : 0) - (object[5] != null ? object[5] : 0)}" type="currency" /></td>
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber value="${(object[6] != null ? object[6] : 0)}" type="currency" /></td>
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber value="${(object[4] != null ? object[4] : 0) - (object[5] != null ? object[5] : 0) - (object[6] != null ? object[6] : 0) }" type="currency" /></td>
						
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber value="${(object[7] != null ? object[7] : 0) - (object[8] != null ? object[8] : 0)}" type="currency" /></td>
						<td style="vertical-align:middle;"><fmt:formatNumber value="${object[12] != null ? object[12] : 0}" type="currency" /></td>
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber value="${(object[9] != null ? object[9] : 0)}" type="currency" /></td>
						<td style="vertical-align:middle;" class="TD"><fmt:formatNumber value="${(object[7] != null ? object[7] : 0) - (object[8] != null ? object[8] : 0) - (object[9] != null ? object[9] : 0) }" type="currency" /></td>
					</tr>
   				</c:forEach>
   			</tbody>
         </table>
         </div>
         </div>
</div><!--jt-container end-->
</body>
</html>