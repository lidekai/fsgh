<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售订单管理</title>

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
	
	$("#addBtn").click(function(){
		location.href="edit.jhtml";	
	});
	
	//编辑按钮事件
	$("#editBtn").click(function(){
		getCheckBox();
		if (ids == '') {
			alert('请选择要编辑的信息');
			return;
		}
		if(ids.split(",").length > 2){
			alert('只能选择一条数据');
			return;
		}
		location.href = 'edit.jhtml?vo.beginTime=${vo.beginTime}&vo.endTime=${vo.endTime}&vo.id=' + ids.split(",")[0] + "&key="+keys.split(",")[0];
	});
	
	//删除按钮事件
	$("#delBtn").click(function(){
		getCheckBox();
		if (ids == '') {
			alert('请选择要删除的信息');
			return;
		}
		if(ids.split(",").length > 2){
			alert('只能选择一条数据');
			return;
		}
		if (!confirm('请注意系统将删除整个订单，确定要删除您勾选的订单？'))
			return;
		location.href = 'delete.jhtml?vo.beginTime=${vo.beginTime}&vo.endTime=${vo.endTime}&vo.id=' + ids.split(",")[0] + "&key="+keys.split(",")[0];
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
      <h5><span>销售订单管理</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
 	        <li><label>订单号：</label><input  class="input-medium" name="vo.orderCode" value="${vo.orderCode }" type="text" placeholder="输入订单号" /></li>
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  name="vo.customName" value="${vo.customName }" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
       			<label>输单日期：</label>
       			<input type="text" class="input-medium Wdate" name="vo.beginTime" value="${vo.beginTime}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
       			<input type="text" class="input-medium Wdate" name="vo.endTime" value="${vo.endTime}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
       		</li>
       		<li>
       			<label>审核状态：</label> 
      			<select name="vo.state" class="span80">
					<option value="">请选择</option>
					<option value="0" <c:if test="${vo.state  == 0}">selected</c:if>>未审核</option>
					<option value="1" <c:if test="${vo.state  == 1}">selected</c:if>>已审核</option>
				</select>
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
         	<pt:checkFunc code="SALE_BILL_U8_EDIT">
				<button class="btn" id="editBtn"><i class="icon-wrench"></i>编辑</button>
			</pt:checkFunc>     
           	<pt:checkFunc code="SALE_BILL_U8_DEL">
	           <button class="btn" id="delBtn"><i class="icon-trash"></i>删 除</button>
            </pt:checkFunc>  
            <pt:checkFunc code="SALE_BILL_U8_Add">
				<button class="btn" id="addBtn"><i class="icon-plus "></i>添 加</button>
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
				<th style="min-width: 20px"></th>
				<th style="min-width: 20px">序号</th>
				<th style="min-width: 80px">订单号</th>
				<th style="min-width: 80px">输单日期</th>
				<th style="min-width: 120px">销售部门</th>
				<th style="min-width: 50px">销售类型</th>
				<th style="min-width: 150px">客户名称</th>
				<th style="min-width: 50px">客户地区</th>	
				<th style="min-width: 50px">币种</th>	
				<th style="min-width: 50px">汇率</th>	
				<th style="min-width: 120px">收货人电话</th>	
				<th style="min-width: 50px">业务负责人</th>	
				<th style="min-width: 80px">收货日期</th>	
				<th style="min-width: 80px">卖场订单号</th>	
				
				<th style="min-width: 50px">存货编码</th>			
				<th style="min-width: 50px">货号</th>
				<th style="min-width: 150px">存货名称</th>	
				<th style="min-width: 80px">新旧品分类</th>	
				<th style="min-width: 80px">客户商品号</th>	
				<th style="min-width: 50px">对方名</th>	
				<th style="min-width: 80px">规格型号</th>	
				<th style="min-width: 50px">数量</th>	
				<th style="min-width: 50px">折前无税合计</th>	
				<th style="min-width: 50px">含税单价</th>	
				<th style="min-width: 50px">价税合计</th>	
				<th style="min-width: 80px">存货用途</th>	
				<th style="min-width: 80px">生产日期</th>	
				<th style="min-width: 120px">备注</th>	
				<th style="min-width: 50px">审核状态</th>	
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${detailList}" var="detailVO" varStatus="s">
				<tr>
					<td><input class="chkBox" name="cid" value="${detailVO.vo.id},${detailVO.vo.key}" type="checkbox"></td>
					<td>${s.index + 1}</td>
					<td>${detailVO.vo.orderCode}</td>
					<td><fmt:formatDate value="${detailVO.vo.orderDate}" pattern="yyyy-MM-dd" /></td>
					<td>${detailVO.vo.departmentCode}</td>
					<td>${detailVO.vo.saleTypeCode}</td>
					<td>${detailVO.vo.customName}</td>
					<td>${detailVO.vo.customArea}</td>
					<td>${detailVO.vo.cexchName}</td>
					<td>${detailVO.vo.IExchRate}</td>
					<td>${detailVO.vo.shrPhone}</td>
					<td>${detailVO.vo.presonName}</td>
					<td><fmt:formatDate value="${detailVO.vo.shDate}" pattern="yyyy-MM-dd" /></td>
					<td>${detailVO.vo.storeOrderCode}</td>
					
					<td>${detailVO.CInvCode}</td>
					<td>${detailVO.hh}</td>
					<td>${detailVO.chmc}</td>
					<td>${detailVO.xjpfl}</td>
					<td>${detailVO.khsph}</td>
					<td>${detailVO.dfm}</td>
					<td>${detailVO.ggxh}</td>
					<td><fmt:formatNumber value='${detailVO.sl}' pattern='#.##'/></td>
					<td><fmt:formatNumber value='${detailVO.zqwshj}' pattern='#.##'/></td>
					<td><fmt:formatNumber value='${detailVO.hsdj}' pattern='#.##'/></td>
					<td><fmt:formatNumber value='${detailVO.jshj}' pattern='#.##'/></td>
					<td>${detailVO.chyt}</td>
					<td><fmt:formatDate value="${detailVO.scrq}" pattern="yyyy-MM-dd" /></td>
					<td>${detailVO.bz}</td>
					<td>${detailVO.vo.state == 1 ? '已审核': '未审核'}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</div>
</div><!--jt-container end-->

</body>
</html>