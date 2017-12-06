<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发票管理</title>

<script type="text/javascript" src="<c:url value='/lib/fixedHeader/demo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/jquery.fixedheadertable.js'/>"></script>
<link href="<c:url value='/css/fixedHeader/960.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/defaultTheme.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/myTheme.css'/>" rel="stylesheet" type="text/css" />
<script>
$(document).ready(function(){
	
	$('#result').fixedHeaderTable({
		altClass: 'odd',
		footer: true
	});
	
	//导入U8发票
	$("#impBtn").click(function(){
		if(confirm("确定导入U8发票额？")){
			$("#impBtn").attr("disabled",true);
			$("#form1").attr("action","imp.jhtml");
			$("#form1").submit();
		}
	});
	
	//删除按钮事件
	$("#delBtn").click(function(){
		dels();	
	});
	
	
	$("button[name='view']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.id="+id+"&key="+key + "&act=VIEW";
	});
	
	//导出按钮事件
	$("#expBtn").click(function(){
		var csbvcode=$("#csbvcode").val();
		var customName=$("#customName").val();
		var productName=$("#productName").val();
		var timeType=$("#timeType").val();
		var start = $("#orderDateStart").val();
		var end = $("#orderDateEnd").val();
		var isRebate = $("#isRebate").val();
		
		var url="<c:url value='/order/sale-bill/exportSaleBill.jhtml'/>";
		url+="?vo.csbvcode=" + csbvcode +"&vo.customName=" + customName + "&vo.productName="+ productName;
		url+="&vo.timeType=" + timeType +"&vo.beginTime="+ start+ "&vo.endTime="+ end + "&vo.isRebate="+ isRebate ;
		
		top.showView("请选择导出字段", url , 900);
	});
	
	//返利
	$("#isRebBtn").click(function(){
		getCheckBox();
		if(ids == ''){
			alert('请选择要更新为返利的信息');
			return;
		}
		if(!confirm('确定要更新你勾选的记录？'))
			return;
		$("#ids").val(ids);
		$("#keys").val(keys);
		$("#isType").val("Y");
		$("#form1").attr("action","isRebate.jhtml");
		$("#form1").submit();
	});
	
	//非返利
	$("#noRebBtn").click(function(){
		getCheckBox();
		if(ids == ''){
			alert('请选择要更新为返利的信息');
			return;
		}
		if(!confirm('确定要更新你勾选的记录？'))
			return;
		$("#ids").val(ids);
		$("#keys").val(keys);
		$("#isType").val("N");
		$("#form1").attr("action","isRebate.jhtml");
		$("#form1").submit();
	});
	
	
});
	
function checkAll(flag){
	//遍历当前选中复选框的table
	$('.chkBox').each(function() {   //勾选、取消后的行背景色
		
		this.checked = flag;

		if($(this).is(":checked")){
			$(this).parents("tr").addClass("tr-checked");
		}else{
			$(this).parents("tr").removeClass("tr-checked");
		}
	});
}

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
	<input type="hidden" name="isType" value="" id="isType"/>
      <h5><span>发票额管理</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
 	        <li><label>销售发票号：</label><input id="csbvcode" class="input-medium" name="vo.csbvcode" value="${vo.csbvcode }" type="text" placeholder="输入发货单号" /></li>
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  id="customName" name="vo.customName" value="${vo.customName }" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li><label>存货名称：</label><input id="productName" class="input-medium" name="vo.productName" value="${vo.productName }" type="text" placeholder="输入产品名称" /></li>
 	        <li>
       			<label>日期条件：</label>
       			<select name="vo.timeType" class="span120" id="timeType">
 	        		<option value="1" <c:if test="${vo.timeType  == '1'}">selected</c:if>>订单日期</option>
 	        		<option value="2" <c:if test="${vo.timeType  == '2'}">selected</c:if>>发货日期</option>
 	        		<option value="3" <c:if test="${vo.timeType  == '3'}">selected</c:if>>制单时间</option>
 	        	</select>
       			<input type="text" class="input-medium Wdate" name="vo.beginTime" value="${vo.beginTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
       			<input type="text" class="input-medium Wdate" name="vo.endTime" value="${vo.endTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
       		</li>
       		<li><label>是否返利：</label> <select name="vo.isRebate"
				class="span150" id="isRebate">
					<option value="">请选择</option>
					<c:forEach items="<%=PublicType.IsType.values()%>"
						var="rebate">
						<option value="${rebate.name }"
							<c:if test="${vo.isRebate  == rebate.name}">selected</c:if>>${rebate.text }</option>
					</c:forEach>
			</select></li>
        	<li><label><button name="searchBtn" class="btn btn-primary" type="submit">搜 索</button></label></li>
        </div>
      </ul>
     </form>
   </div><!-- / span12 end-->
  </div><!-- / row-fluid end-->
  <div class="row-fluid">
   <div class="span12" style="min-width:1000px;">
         <div class="pull-right  mB10">
         	<button class="btn" onclick="checkAll(true)"><i class="icon-ok"></i>全选</button>
         	<button class="btn" onclick="checkAll(false)"><i class="icon-remove"></i>全不选</button>
           	<pt:checkFunc code="SALE_BILL_DEL">
	           <button class="btn" id="delBtn"><i class="icon-trash"></i>删 除</button>
            </pt:checkFunc>           
            <pt:checkFunc code="SALE_BILL_ISREB">
				<button class="btn" id="isRebBtn">
					<i class="icon-ok-circle"></i>返利
				</button>
				<button class="btn" id="noRebBtn">
					<i class="icon-remove-circle"></i>非返利
				</button>
			</pt:checkFunc>
	        <button class="btn" id="expBtn"><i class="icon-list-alt"></i>导出</button>
            <pt:checkFunc code="SALE_BILL_IMP">
	           <button class="btn" id="impBtn"><i class="icon-list-alt"></i>导入U8发票额</button>
            </pt:checkFunc>
         </div>
   </div><!-- / span12 end -->
  </div><!-- / row-fluid end -->
 </div><!--container-fluid end-->
<div class="container_12 divider">
 		<div class="grid_4 height400" id="page1">
	 	<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
	    <thead>
			<tr> 
				<th style="min-width:20px"></th>
				<th style="min-width:20px">序号</th>
				<th style="min-width:20px">销售发票号</th>
				<th style="min-width:30px">制单时间</th>
				<th style="min-width:30px">订单日期</th>
				<th style="min-width:30px">发货日期</th>
				<th style="min-width:30px">销售订单号</th>
				<th style="min-width:140px">客户名称</th>
				<th style="min-width:140px">存货名称</th>				
				
				<th style="min-width:30px">数量</th>
				<th style="min-width:50px">本币含税单价</th>
				<th style="min-width:50px">本币价税合计</th>
				<th style="min-width:70px">本币无税金额</th>
				<th style="min-width:50px">是否返利</th>
				<th style="min-width:80px">操作</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td colspan="${empty sumList ? '12': '9'}">合计,共${fn:length(pageList.list)}条记录</td>
				<c:forEach items="${sumList }" var="s">
					<td><fmt:formatNumber value="${s[0]}" pattern="#.##"/></td>
					<td></td>
					<td><fmt:formatNumber value="${s[1]}" type="currency" /></td>
				</c:forEach> 
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tfoot>
		<tbody>
		<c:forEach items="${pageList.list}"  var="vo" varStatus="s">
			<tr>
				<td><input class="chkBox" name="cid" value="${vo.id},${vo.key}" type="checkbox"></td>
				<td>${s.index + 1}</td>
				<td>${vo.csbvcode}</td>
				<td><fmt:formatDate value="${vo.createDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${vo.saleDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${vo.deliverDate }" pattern="yyyy-MM-dd"/></td>
				<td>${vo.csoCode }</td>
				<td title="${vo.customName }">${fn:length(vo.customName) > 12 ? fn:substring(vo.customName,0,12): vo.customName}
					${fn:length(vo.customName) > 12 ? '...': ''}
				</td>
				<td title="${vo.productName }">
					${fn:length(vo.productName) > 12 ? fn:substring(vo.productName,0,12): vo.productName}
					${fn:length(vo.productName) > 12 ? '...': ''}
				</td>
				<td>${vo.count }</td>
				<td>${vo.localPrice }</td>
				<td><fmt:formatNumber value="${vo.countPrice }" type="currency"/></td>
				<td><fmt:formatNumber value="${vo.noTaxPrice }" type="currency" /></td>
				<td>${vo.isRebate.text}</td>
				<td>
					<button name="view" class="btn btn-small btn-info" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-eye-open"></i>查看</button>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</div><!--jt-container end-->

</body>
</html>