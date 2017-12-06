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
	
<title>门店销售预提管理</title>
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
	   history.back();
	});
	
	var act ='${act}';
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled 
		 $("#storeBtn").hide();
	}
	
	$("#storeBtn").click(function(){
		var url="<c:url value='/info/store/selectStore.jhtml'/>";
		top.showMyModal("请选择门店",url ,900,true);
	});
	
	$("#saleSum,#fixSum").change(function(){
		countMoney();
	})
	
});

function setReturnValue(obj){
	if(obj == '')
		return;
	$("#store_id").val(obj.id);
	var array = obj.name.split(",");
	$("input[name='vo.store.storeName']").val(array[0]);
	$("#propertion").val(array[1]);
	countMoney();
}
function countMoney(){
	var propertion = $("#propertion").val();
	var saleSum = $("#saleSum").val();
	var fixSum = $("#fixSum").val();
	if(propertion != "" && saleSum != "" &&　fixSum != ""){
		var count = parseFloat(propertion) * parseFloat(saleSum) * 0.01 + parseFloat(fixSum);
		$("#count").val(count.toFixed(2));
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
	 <input type="hidden" name="vo.dateStart" value="${vo.dateStart}" />
	 <input type="hidden" name="vo.dateEnd" value="${vo.dateEnd}" />
       <h5><span id="formTitle">${act.text}门店销售预提</span></h5>        	
       
       <div class="control-group">
        <label class="control-label">门店信息</label>
        <div class="controls">
        	<input type="hidden" name="vo.store.id" value="${vo.store.id}" id="store_id" /> 
			<input name="vo.store.storeName" value="${vo.store.storeName}" type="text" readonly="readonly"
						msg="请选择门店" dataType="Require" />
			<button class="btn btn-mini" type="button" id="storeBtn">选择门店</button>
			<span>*</span>
        </div>
       </div>
       
       <div class="control-group">
	        <label class="control-label">提成比例</label>
	        <div class="controls"><input value="${vo.store.propertion}" type="text" id="propertion" readonly="readonly" />%</div>
       </div> 
       
       <div class="control-group">
	        <label class="control-label">销售额</label>
	        <div class="controls"><input name="vo.saleSum" value="${vo.saleSum}" type="text" id="saleSum"
	        	placeholder="请输入销售额"  msg="请输入数字" dataType="Double" /></div>
        </div>  
        
        <div class="control-group">
	        <label class="control-label">固定金额</label>
	        <div class="controls"><input name="vo.fixSum" value="${vo.fixSum}" type="text" id="fixSum"
	        	placeholder="请输入固定金额"  msg="请输入数字" dataType="Double" /></div>
        </div>  
       
       <div class="control-group">
			<label class="control-label">预提时间</label>
			<div class="controls">
				<input name="vo.provisionTime"  value="<fmt:formatDate value='${vo.provisionTime}' pattern='yyyy-MM-dd'/>"  type="text" class="input-medium Wdate"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			</div>
		</div>
       
       <div class="control-group">
	        <label class="control-label">预提费用</label>
	        <div class="controls"><input name="vo.count" value="${vo.count}" type="text" id="count"
	        	placeholder="请输入预提费用"  msg="请输入数字" dataType="Double" /></div>
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