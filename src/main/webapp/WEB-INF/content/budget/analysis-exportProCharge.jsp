<%@page import="com.kington.fshg.common.PublicType"%>
<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>产品费用预提核销明细表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
$(document).ready(function(){
	//确定按钮事件
	$("button").eq(0).click(function(){
		getHeader();
		form1.submit();
	});
	
	$(".jt-container").css({  "background-color": "#ebf4fb" });
	
	$(".label").each(function(i){
		$(this).addClass("label-success");
	});

	$(".label").click(function(){
		if($(this).hasClass("label-success")){
			$(this).removeClass("label-success");
		}else{
			$(this).addClass("label-success");
		}
	});
	
	
});
function getHeader(){
	var names="";
	var bhgxm="";
	$(".label-success").each(function(){
		names = names + $(this).attr("att-name")+ ",";
	});
	$("#header").val(names);
}
</script>
</head>
<body>
	<div class="jt-container" style="max-width: 900px;">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="doExportProCharge.jhtml"
						class="form-horizontal" method="post">
						<input type="hidden" name="header" id="header" /> <input
							type="hidden" name="vo.custom.customName"
							value="${vo.custom.customName }" /> <input type="hidden"
							name="vo.storeName" value="${vo.storeName}" />  <input
							type="hidden" name="vo.custom.area.parentArea.id"
							value="${vo.custom.area.parentArea.id}" /> <input type="hidden"
							name="vo.custom.area.id" value="${vo.custom.area.id }" /> <input
							type="hidden" name="vo.product.productName"
							value="${vo.product.productName}" /> <input type="hidden"
							name="vo.product.stockCde" value="${vo.product.stockCde}" /> <input
							type="hidden" name="vo.project.id" value="${vo.project.id}" /> <input
							type="hidden" name="vo.dateStart" value="${vo.dateStart}" /> <input
							type="hidden" name="vo.dateEnd" value="${vo.dateEnd}" />

						<h5>
							<span id="formTitle">导出产品费用预提核销明细表</span>
						</h5>

						<div class="row-fluid">
							<div class="span12">
								<div class="control-group">
									<label class="control-label">表头信息</label>
									<div class="controls">
										<c:forEach items="${proCharHeader}" var="hl">
											<span class="label" att-name="${hl}">${hl}</span>
										</c:forEach>
										<blockquote>
											<small>说明：标签显示为绿色表示已选中的标签</small>
										</blockquote>
									</div>
								</div>
							</div>
						</div>

						<div class="control-group" style="text-align: center;">
							<div class="controls">
								<button type="button" class="btn btn-primary">确 定</button>
							</div>
						</div>

					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>