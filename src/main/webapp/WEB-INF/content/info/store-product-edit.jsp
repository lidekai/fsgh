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
	
<title>门店存货管理</title>
<script type="text/javascript">
var screenwidth = $(window).width();
var screenheight = $(window).height();
var btn;
$(document).ready(function(){
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			form1.submit();					
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  history.back();
	});
	
	var act ='${act}';
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled 
		 $("#customBtn").hide();
	}
	
	
	$("#storeBtn").click(function(){
		var url="<c:url value='/info/store/selectStore.jhtml'/>";
		top.showMyModal("请选择门店",url ,900,true);
		btn = "storeBtn";
	});
	
	$("#productBtn").click(function(){
		var url="<c:url value='/info/product/selectProduct.jhtml'/>";
		top.showMyModal("请选择产品",url ,900,true);
		btn = "productBtn";
	});
	
});

function setReturnValue(obj){
	if(obj == '')
		return;
	if(btn == "storeBtn"){
		$("#store_id").val(obj.id);
		$("input[name='vo.store.storeName']").val(obj.name.split(",")[0]);
	}else if(btn == "productBtn"){
		$("#product_id").val(obj.id);
		$("input[name='vo.product.productName']").val(obj.name);
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
        <label class="control-label">门店名称</label>
        <div class="controls">
        	<input type="hidden" name="vo.store.id" value="${vo.store.id}" id="store_id" /> 
			<input name="vo.store.storeName" value="${vo.store.storeName}" type="text" readonly="readonly"
						msg="请选择门店" dataType="Require" />
			<button class="btn btn-mini" type="button" id="storeBtn">选择门店</button>
			<span>*</span>
        </div>
       </div>
    
      <div class="control-group">
        <label class="control-label">产品名称</label>
        <div class="controls">
        	<input type="hidden" name="vo.product.id" value="${vo.product.id}" id="product_id" /> 
			<input name="vo.product.productName" value="${vo.product.productName}" type="text" readonly="readonly"
						msg="请选择产品" dataType="Require" />
			<button class="btn btn-mini" type="button" id="productBtn">选择产品</button>
			<span>*</span>
        </div>
       </div>
       
		<div class="control-group">
	        <label class="control-label">直销KA价</label>
	        <div class="controls"><input name="vo.kaPrice" value="${vo.kaPrice }" type="text" 
	        	placeholder="请输入直销KA价"  msg="请输入数字" dataType="Double" /></div>
       </div>  

		<div class="control-group">
	        <label class="control-label">终端零售价</label>
	        <div class="controls"><input name="vo.retailPrice" value="${vo.retailPrice }" type="text" 
	        	placeholder="请输入终端零售价"  msg="请输入数字" dataType="Double" /></div>
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