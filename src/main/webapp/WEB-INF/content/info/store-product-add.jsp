<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<title>客户产品管理</title>

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
		$("#form1").attr("action","productList.jhtml");
	    form1.submit();	
	});
})

function getProductList(){
	var number = $("#number").val();
	if(number != "" || number != null){
		$.ajax({
			type:"post",
			url:"<c:url value='/info/product/getProductJson.jhtml'/>",
			data:{"number":number},
			dataType:"text",
			success:function(data){
				var date = eval(data);
				var result = "<option value=\"\">请选择</option>";;
				for(var i=0;i<date.length;i++){
					 result += "<option value='" + date[i].id + "'>" + date[i].name + "</option>";
				}
				$("#productName").html(result);  
			}
		});
	}
}
</script>
</head>
<body>
<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="save.jhtml" class="form-horizontal" method="post" id="form1">
						<input type="hidden" name="vo.store.custom.id" value="${vo.store.custom.id}"/>
						<h5>
							<span id="formTitle">添加门店产品信息</span>
						</h5>
						<div class="control-group">
							<label class="control-label">所属客户</label>
							<div class="controls">
								<input value="${customName}" type="text" readonly="readonly"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">门店名称</label>
							<div class="controls">
								<input name="vo.store.storeName" value="${vo.store.storeName}" type="text"
									maxLength="20" placeholder="请输入名称" dataType="Require"
									msg="请输入名称" /> <span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">门店编码</label>
							<div class="controls">
								<input name="vo.store.storeCde" value="${vo.store.storeCde}" type="text"
									placeholder="请输入门店编码" msg="请输入门店编码" dataType="Require" /><span>*</span>
							</div>
						</div>
						
						<div class="control-group">
					        <label class="control-label">货号</label>
					        <div class="controls">
								<input name="vo.product.number" value="${vo.product.number}" type="text" 
											msg="请输入货号" dataType="Require" onblur="getProductList()" id="number"/><span>*</span>
					        </div>
				        </div>
				        
				        <div class="control-group">
					        <label class="control-label">产品名称</label>
					        <div class="controls">
					        	<select name="vo.product.id" id="productName" msg="请选择" dataType="Require">
					        	</select>
								<span>*</span>
					        </div>
				        </div>
						<div class="control-group mT30">
							<div class="controls" style="margin:0 auto;text-align: center;">
								<button type="button" id="saveBtn" class="btn btn-primary">确 定</button>
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