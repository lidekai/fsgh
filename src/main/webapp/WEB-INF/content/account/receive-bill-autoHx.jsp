<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待核单自动核销</title>

<script type="text/javascript" src="<c:url value='/lib/fixedHeader/demo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/jquery.fixedheadertable.js'/>"></script>
<link href="<c:url value='/css/fixedHeader/960.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/defaultTheme.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/myTheme.css'/>" rel="stylesheet" type="text/css" />
<script>
$(document).ready(function(){
 	
	$('#result,#result1').fixedHeaderTable({
		altClass: 'odd'
	}); 
	
	//全部自动核销
	/* $("#autoHXBtn").click(function(){
		if(confirm("确定按当前待核单和收款单的搜索条件进行自动核销？")){
			$("#act").val("AUTOHX");
			$("#form1").submit();
		}
	}); */
	
	//选择自动核销
	/* $("#xzHXBtn").click(function(){
		if(confirm("确定按当前勾选待核单和收款单进行自动核销？")){
			var receiveIds="",receiptIds="";
			var obj = document.getElementsByName('cid');
			for ( var i = 0; i < obj.length; i++) {
				if (obj[i].checked == true) {
					var ss = obj[i].value.split(",");
					if(ss[0] == "0"){
						if(receiveIds != "") receiveIds += ',';
						receiveIds += ss[1];
					}
					if(ss[0] == "1"){
						if(receiptIds != "") receiptIds += ',';
						receiptIds += ss[1];
					}
				}
			}
			$("#receiveIds").val(receiveIds);
			$("#receiptIds").val(receiptIds);
			
			$("#act").val("XZHX");
			$("#form1").submit();
		}
	}); */
	
	$("#xzHXBtn").click(function(){
		if(confirm("确定按当前勾选待核单和收款单进行自动核销？")){
			var checkbillIds="",receiptIds="";
			var obj = document.getElementsByName('cid');
			for ( var i = 0; i < obj.length; i++) {
				if (obj[i].checked == true) {
					var ss = obj[i].value.split(",");
					if(ss[0] == "0"){
						if(checkbillIds != "") checkbillIds += ',';
						checkbillIds += ss[1];
					}
					if(ss[0] == "1"){
						if(receiptIds != "") receiptIds += ',';
						receiptIds += ss[1];
					}
				}
			}
			//alert(checkbillIds);
			var receiveIds="";
			var url="<c:url value='/account/receive-bill/getreceiveids.jhtml' />?checkbillIds="+checkbillIds;
			$.post(url,null,function(receiveIds){
				var obj = JSON.parse(receiveIds);
				var rids=obj.ids;				
				receiveIds=rids.substring(0,rids.length-1);
				//alert(receiveIds);
				 
				$("#receiveIds").val(receiveIds);
				$("#receiptIds").val(receiptIds);
				
				$("#act").val("XZHX");
				$("#form1").submit();			
			});
		}
	});
	
	$("#autoHXBtn").click(function(){
		if(confirm("确定按当前待核单和收款单的搜索条件进行自动核销？")){
			$("#act").val("AUTOHX");
			$("#form1").submit();
		}
	});
	
	
});
</script>

</head>
<body>
<div class="jt-container">
<form name="form1" action="autoHx.jhtml" class="form-inline" method="post" id="form1">
 <input type="hidden" id="act" value="" name="act"/>
  <input type="hidden" id="receiveIds" value="" name="receiveIds"/>
   <input type="hidden" id="receiptIds" value="" name="receiptIds"/>
 <div class="container-fluid">
  <div class="row-fluid">
   <div class="span12" style="min-width:1000px;">
      <h5><span>待核单明细</span></h5>
      <ul class="row-fluid"> 
      	<%-- <div class="span10">
 	        <li><label>销售发票号：</label><input id="csbvcode" class="input-medium" name="" value="${vo.csbvcode }" type="text" placeholder="输入发货单号" /></li>
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  id="customName" name="" value="${vo.customName }" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
 	        	<label>客户类型：</label>
 	        	<select name="" class="input-medium" id="customTypeId">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="${customsTypes}" var="type">
 	        			<option value="${type.id}" <c:if test="${vo.customTypeId == type.id }">selected="selected"</c:if>>${type.customTypeName}</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	        <li>
       			<label>发票日期：</label>
       			<input type="text" class="input-medium Wdate" name="cvo.beginTime" value="${cvo.beginTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
       			<input type="text" class="input-medium Wdate" name="cvo.endTime" value="${cvo.endTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
       		</li>
       		<li>
       			<button name="searchBtn" class="btn btn-primary" type="submit">搜 索</button>
       		</li>
        </div> --%>
        	<div class="span10">
 	        <li><label>单号：</label><input id="code" class="input-medium" name="cvo.code" value="${cvo.code}" type="text" placeholder="输入发货单号" /></li>
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  id="customName" name="cvo.customName" value="${cvo.customName}" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
       			<label>制单时间：</label>
       			<input type="text" class="input-medium Wdate" name="cvo.beginTime" value="${cvo.beginTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
       			<input type="text" class="input-medium Wdate" name="cvo.endTime" value="${cvo.endTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
       		</li>
       		
        	<li><label><button name="searchBtn" class="btn btn-primary" type="submit">搜 索</button></label></li>
        </div>
      </ul>
   </div><!-- / span12 end-->
  </div><!-- / row-fluid end-->
 </div><!--container-fluid end-->
<div class="container_12 divider">
 		<div class="grid_4 height400" id="page1">
	<%--  	<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
	    <thead>
			<tr> 
				<th style="min-width:20px"></th>
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
				<th style="min-width:50px">收款额</th>
				<th style="min-width:50px">待费用发票额</th>
				<th style="min-width:50px">待退货额</th>
				<th style="min-width:50px">暂扣额</th>
				<th style="min-width:50px">其他余额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageList.list}"  var="vo" varStatus="s">
			<tr>
				<td>
					<input class="chkBox" name="cid" value="0,${vo.id}" type="checkbox"/>
				</td>
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
				<td><fmt:formatNumber value="${vo.chargePrice}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${vo.returnPrice}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${vo.holdPrice}" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${vo.otherPrice}" pattern="#.##"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table> --%>
	 	<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
	    <thead>
			<tr> 
				<th style="min-width:20px"></th>
				<th style="min-width:20px">序号</th>
				<th style="min-width:20px">单号</th>
				<th style="min-width:30px">制单时间</th>
				<th style="min-width:40px">客户类型</th>
				<th style="min-width:40px">客户地区</th>
				<th style="min-width:40px">客户编号</th>
				<th style="min-width:140px">客户名称</th>
				
				<th style="min-width:50px">本币发票额</th>
				<th style="min-width:50px">已收款合计</th>
				<th style="min-width:50px">实际收款额</th>
				<th style="min-width:50px">待费用发票额</th>
				<th style="min-width:50px">待退货额</th>
				<th style="min-width:50px">暂扣额</th>
				<th style="min-width:50px">其他余额</th>
				<th style="min-width:30px">状态</th>
			<!-- 	<th style="min-width:50px">操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageList.list}"  var="vo" varStatus="s">
			<tr>
				<td>
					<input class="chkBox" name="cid" value="0,${vo.id}" type="checkbox"/>
				</td>
				<td>${s.index + 1}</td>
				<td>${vo.code}</td>
				<td><fmt:formatDate value="${vo.createDate}" pattern="yyyy-MM-dd"/></td>
				
				<td>${vo.customType}</td>
				<td>${vo.customArea}</td>
				<td>${vo.customCde}</td>
				<td>${vo.customName}</td>
				<td><fmt:formatNumber value="${vo.countPrice }" type="currency"/></td>
				<td><fmt:formatNumber value="${vo.receivePrice }" type="currency"/></td>
				<td><fmt:formatNumber value="${vo.actualPrice }" type="currency"/></td>
				<td><fmt:formatNumber value="${vo.chargePrice }" type="currency" /></td>
				<td><fmt:formatNumber value="${vo.returnPrice }" type="currency" /></td>
				<td><fmt:formatNumber value="${vo.holdPrice }" type="currency" /></td>
				<td><fmt:formatNumber value="${vo.otherPrice }" type="currency" /></td>
				<td>${vo.state.text}</td>
				<%-- <td>
					<c:if test="${vo.state != 'DHX' }">
						<button name="view" class="btn btn-small btn-info" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-eye-open"></i>查看</button>
					</c:if>
					<c:if test="${vo.state == 'DHX' }">
						<pt:checkFunc code="CHECK_BILL_HX">
							<button name="hx" class="btn btn-small btn-success" att-id="${vo.id}" att-key="${vo.key}"><i class="icon-edit"></i>核销</button>
						</pt:checkFunc>
					</c:if>
				</td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
   <div class="span12" style="min-width:1000px;">
       <h5><span>收款单管理</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
 	        <li><label>收款单号：</label><input class="input-medium" name="receiptVO.cvouchId" value="${receiptVO.cvouchId}" type="text" placeholder="输入发货单号" /></li>
 	        <li>
 	        	<label>客户类型：</label>
 	        	<select name="receiptVO.customTypeId" class="input-medium" id="customTypeId">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="${customsTypes}" var="type">
 	        			<option value="${type.id}" <c:if test="${vo.customTypeId == type.id }">selected="selected"</c:if>>${type.customTypeName}</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  name="receiptVO.customerName" value="${receiptVO.customerName }" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
       			<label>收款日期：</label>
       			<input type="text" class="input-medium Wdate" name="receiptVO.beginTime" value="${receiptVO.beginTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
       			<input type="text" class="input-medium Wdate" name="receiptVO.endTime" value="${receiptVO.endTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
       		</li>
       		<li>
       			<label>是否先核销收款金额与应收单待核销价税合计相等的单据：</label>
       			<select name="isType" class="span120">
       				<c:forEach items="<%=PublicType.IsType.values() %>" var="type">
 	        			<option value="${type.name}" <c:if test="${isType  == type.name}">selected</c:if>>${type.text }</option>
 	        		</c:forEach>
 	        	</select>
       		</li>
        	<li><label><button name="searchBtn" class="btn btn-primary" type="submit">搜 索</button>
        		<button id="autoHXBtn" class="btn btn-success" type="submit">全部自动核销</button>
        		<button id="xzHXBtn" class="btn btn-success" type="button">选择自动核销</button>
        	</label></li>
        </div>
      </ul>
   </div><!-- / span12 end-->
  </div><!-- / row-fluid end-->
 </div><!--container-fluid end-->
<div class="container_12 divider">
 		<div class="grid_4 height400" >
	 	<table class="fancyTable" id="result1" cellpadding="0" cellspacing="0">
	    <thead>
			<tr> 
				<th style="min-width:20px"></th>
				<th style="min-width:20px">序号</th>
				<th style="min-width:20px">收款单号</th>
				<th style="min-width:30px">收款日期</th>
				<th style="min-width:140px">客户名称</th>
				<th style="min-width:50px">客户编号</th>				
				
				<th style="min-width:30px">类别</th>
				<th style="min-width:50px">结算编号</th>
				<th style="min-width:50px">结算名称</th>
				<th style="min-width:70px">本币收款金额</th>
				<th style="min-width:70px">本币实际收款金额</th>
				<th style="min-width:70px">本币未核余额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageList1.list}"  var="vo" varStatus="s">
			<tr>
				<td>
					<input class="chkBox" name="cid" value="1,${vo.id}" type="checkbox"/>
				</td>
				<td>${s.index + 1}</td>
				<td>${vo.cvouchId}</td>
				<td><fmt:formatDate value="${vo.receiptDate }" pattern="yyyy-MM-dd"/></td>
				<td>${vo.customerName}</td>
				<td>${vo.customerCde}</td>
				<td>${vo.cvouchType == 48 ? '收': (vo.cvouchType == 49 ? '付' : '')}</td>
				<td>${vo.cssCode}</td>
				<td>${vo.cssName }</td>
				<td><fmt:formatNumber value="${vo.receiptCount}" type="currency" /></td>
				<td><fmt:formatNumber value="${vo.receiptCount1}" type="currency" /></td>
				<td><fmt:formatNumber value="${vo.receiveCount}" type="currency" /></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<p style="margin-top:1px"></p>
</div>
</div>
</form>
</div>
</body>
</html>