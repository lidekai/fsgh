<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
	<link href="<c:url value='/lib/weebox-0.4/src/weebox.css'/>" rel="stylesheet"  type="text/css" charset="utf-8" />
	<script type="text/javascript" src="<c:url value='/lib/jquery-1.4.4.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/weebox.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/bgiframe.js'/>"></script>
	
<title>门店库存管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			form1.submit();					
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  /* var url="<c:url value='/info/store-product-stock/list.jhtml'/>";
	  window.location.href=url; */
	  history.back();
	});
	
	var act ='${act}';
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled 
		 $("#storeBtn").hide();
	}
	
	$("#storeBtn").click(function(){
		var url="<c:url value='/info/store-product/selectStoreProduct.jhtml'/>";
		top.showMyModal("请选择门店存货",url ,900,true);
	});
	
});

function setReturnValue(obj){
	if(obj == '')
		return;
	$("#store_id").val(obj.id);
	var array = obj.name.split(",");
	$("input[name='vo.storeProduct.store.storeName']").val(array[0]);
	$("#product").val(array[1]);
	$("#standard").val(array[2]);
	$("#unit").val(array[3]);
	$("#standardPrice").val(array[4]);
	countMoney();
}
function countMoney(){
	var standardPrice = $("#standardPrice").val();
	var count = $("#count").val();
	if(standardPrice != "" && count != ""){
		var money = parseInt(standardPrice) * parseInt(count);
		$("#money").val(money.toFixed(2));
	}
}

</script>
</head>
<body>
<div class="jt-container">
 <div class="container-fluid">
  <div class="row-fluid">
   <div class="span12">
     <form name="form1" action="update.jhtml" class="form-horizontal" method="post">
     <input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
	 <input type="hidden" name="key" value="${key }" />
       <h5><span id="formTitle">${act.text}门店存货信息</span></h5>        	
       
       <div class="control-group">
        <label class="control-label">门店信息</label>
        <div class="controls">
        	<input type="hidden" name="vo.storeProduct.id" value="${vo.storeProduct.id}" id="store_id" /> 
			<input name="vo.storeProduct.store.storeName" value="${vo.storeProduct.store.storeName}" type="text" readonly="readonly"
						msg="请选择门店存货" dataType="Require" />
			<button class="btn btn-mini" type="button" id="storeBtn">选择门店存货</button>
			<span>*</span>
        </div>
       </div>
       
       <div class="control-group">
	        <label class="control-label">产品名称</label>
	        <div class="controls"><input value="${vo.storeProduct != null ? vo.storeProduct.product.productName : ''}" type="text" id="product" readonly="readonly" /></div>
       </div>  
       
       <div class="control-group">
	        <label class="control-label">规格</label>
	        <div class="controls"><input value="${vo.storeProduct.product != null ? vo.storeProduct.product.standard : ''}" type="text" id="standard" readonly="readonly" /></div>
       </div>  
       
        <div class="control-group">
	        <label class="control-label">单位</label>
	        <div class="controls"><input value="${vo.storeProduct.product != null ? vo.storeProduct.product.unit : ''}" type="text" id="unit" readonly="readonly" /></div>
       </div>  
       
       <div class="control-group">
	        <label class="control-label">标准价</label>
	        <div class="controls"><input value="${vo.storeProduct.product != null ? vo.storeProduct.product.standardPrice : ''}" type="text" id="standardPrice" readonly="readonly" /></div>
       </div>  
    	
		<div class="control-group">
	        <label class="control-label">数量</label>
	        <div class="controls"><input name="vo.count" value="${vo.count}" type="text" id="count"
	        	placeholder="请输入数量"  msg="请输入数字" dataType="Double" onchange="countMoney()"/></div>
        </div>  
        
        <div class="control-group">
	        <label class="control-label">金额</label>
	        <div class="controls"><input name="vo.money" value="${vo.money}" type="text" id="money"
	        	placeholder="请输入数量"  msg="请输入数字" dataType="Double" /></div>
        </div>  

		<div class="control-group">
	        <label class="control-label">年月</label>
	        <div class="controls"><input class="input-medium Wdate" name="vo.month" value="${vo.month}" 
	        	type="text" readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyy-MM'})"  dataType="Require" msg="请选择年月"/></div>
       </div>    
       
       
       <div class="control-group mT30">
        <div class="controls">
        <c:if test="${act != 'VIEW' }">
	         <button type="button" id="saveBtn" class="btn btn-primary">确 定</button>
        </c:if>
         <button type="button" id="cancelBtn" class="btn btn-primary">取 消</button>
        </div>
       </div>
     </form>
   </div>
  </div>
 </div>
</div>
</body>
</html>