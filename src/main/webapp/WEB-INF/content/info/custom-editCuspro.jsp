<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>客户存货管理</title>
<script type="text/javascript">
var screenwidth = $(window).width();
var screenheight = $(window).height();
var productList = <c:if test="${empty productIds}">""</c:if><c:if test="${not empty productIds}">${productIds}</c:if>;
$(document).ready(function(){
	
	if(productList != ""){
		for(var i=0;i<productList.length;i++){
			$("#product_" + productList[i]).addClass("label-success");
		}
	}
	
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			getProductIds();
			form1.submit();					
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  var url="<c:url value='/info/custom/list.jhtml?act=CUSPRO'/>";
	  window.location.href=url;
	});
	
	$(".label").click(function(){
		if($(this).hasClass("label-success")){
			$(this).removeClass("label-success");
		}else{
			$(this).addClass("label-success");
		}
	});
	
	$("#customBtn").click(function(){
		var url="<c:url value='/info/custom/selectCustom.jhtml'/>";
		top.showMyModal("请选择所属客户",url,900,true);
	});
});


function getProductIds(){
	var productIds = "";
	$(".label-success").each(function(){
		productIds = productIds + $(this).attr("att-name")+ ",";
	});
	$("#productIds").val(productIds);
}
function setReturnValue(obj){
	if(obj == '')
		return;
	$("#customId").val(obj.id);
	$("#customName").val(obj.name);
}

</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="updateCuspro.jhtml"
						class="form-horizontal" method="post">
						<input type="hidden" name="productIds" id="productIds" value="" />
						<input type="hidden" name="vo.id" value="${vo.id }" /> <input
							type="hidden" name="id" id="customId" value="" /> <input
							type="hidden" name="key" value="${key }" />
						<h5>
							<span id="formTitle">客户存货管理</span>
						</h5>
						<div class="row-fluid">
							<div class="span12">
								<div class="control-group">
									<label class="control-label">客户名称</label>
									<div class="controls">
										<input value="${vo.customName}" type="text"
											readonly="readonly" style="width: 250px" id="customName" />
										<c:if test="${empty vo.customName}">
											<button class="btn btn-mini" type="button" id="customBtn">选择客户</button>
											<span>*</span>
										</c:if>
									</div>
								</div>
							</div>
						</div>
						<h5>
							<span id="formTitle">产品信息</span>
						</h5>
						<c:forEach items="${productMap}" var="product">
							<div class="row-fluid">
								<div class="span12">
									<div class="control-group">
										<label class="control-label">${product.key}</label>
										<div class="controls">
											<c:forEach items="${product.value}" var="productVO">
												<span class="label" att-name="${productVO.id}"
													id="product_${productVO.id}">${productVO.productName}</span>
											</c:forEach>
											<blockquote>
												<small>说明：标签显示为绿色表示已选中的标签</small>
											</blockquote>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						<div class="control-group mT30">
							<div class="controls">
								<c:if test="${act != 'VIEW' }">
									<button type="button" id="saveBtn" class="btn btn-primary">确
										定</button>
								</c:if>
								<button type="button" id="cancelBtn" class="btn btn-primary">取
									消</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>