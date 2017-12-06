<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<link href="<c:url value='/lib/weebox-0.4/src/weebox.css'/>"
	rel="stylesheet" type="text/css" charset="utf-8" />
<script type="text/javascript"
	src="<c:url value='/lib/jquery-1.4.4.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/lib/weebox-0.4/src/weebox.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/lib/weebox-0.4/src/bgiframe.js'/>"></script>

<title>物流费用</title>
<script type="text/javascript">
var btn;
$(document).ready(function(){
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  history.back();
	});

	//审核
	$("#auditBtn").click(function(){
		$("#act").val("AUDIT");
		$("#form1").submit();
	});
	
	//审核
	$("#reAuditBtn").click(function(){
		$("#act").val("REAUDIT");
		$("#form1").submit();
	});
	
});

</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="approve.jhtml" class="form-horizontal" method="post" id="form1">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" id="key" />
						<input type="hidden" name="ids" value="${vo.id }" id="ids"/>
						<input type="hidden" name="keys" value="${key }" id="keys"/>
						<input type="hidden" name="act" value="" id="act"/>
						<input type="hidden" name="vo.orderStartTime" value="${vo.orderStartTime }" />
						<input type="hidden" name="vo.orderEndTime" value="${vo.orderEndTime }" />
						<h5>
							<span id="formTitle">物流费用</span>
						</h5>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">发货单号</label>
									<div class="controls">
										<input value="${vo.deliverOrder.cdlCode}" type="text" name="vo.deliverOrder.cdlCode" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">发货日期</label>
									<div class="controls">
										<input
											value="<fmt:formatDate value='${vo.deliverOrder.deliverDate}'  pattern='yyyy-MM-dd'/>"
											type="text" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">客户编码</label>
									<div class="controls">
										<input value="${vo.custom.customCde}" type="text"
											readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">客户名称</label>
									<div class="controls">
										<input value="${vo.custom.customName}" type="text" name="vo.custom.customName"
											readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">存货编码</label>
									<div class="controls">
										<input value="${vo.product.stockCde}" type="text" name="vo.product.stockCde"
											readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">存货名称</label>
									<div class="controls">
										<input value="${vo.product.productName}" type="text" name="vo.product.productName"
											readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">规格型号</label>
									<div class="controls">
										<input value="${vo.product.standard}" type="text"
											readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">数量/包数</label>
									<div class="controls">
										<input value="${vo.deliverOrder.count}" type="text"
											readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<label class="control-label">货物单位重量</label>
								<div class="controls">
									<input value="${vo.product.boxWeight}" type="text"
										readonly="readonly" />
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">货物重量</label>
									<div class="controls">
										<input value="${vo.logWeight}" type="text" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">货物单位体积（立方米）</label>
									<div class="controls">
										<input value="${vo.product.volume}" type="text"
											readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">货物体积（立方米）</label>
									<div class="controls">
										<input value="${vo.logVolume}" type="text" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">计价方式</label>
									<div class="controls">
										<input value="${vo.chargeType.text}" type="text"
											readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">单价</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber type='currency' value='${vo.logPrice}'/>"
											type="text" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">运费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber type='currency' value='${vo.freight}'/>"
											type="text" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">配送费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber type='currency' value='${vo.logDeliverFee}'/>"
											type="text" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">本币金额</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber type='currency' value='${vo.deliverCost}'/>"
											type="text" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">保险金额</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber type='currency' value='${vo.insuranceFee}'/>"
											type="text" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">业务员</label>
									<div class="controls">
										<input value="${vo.custom.user.userName}" type="text"
											readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">运费合计</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber type='currency' value='${vo.freightTotal}'/>"
											type="text" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span12">
								<div class="control-group mT30">
									<div class="controls"
										style="margin: 0 auto; text-align: center;">
										<c:if test="${act == 'AUDIT'}">
											<button type="button" id="auditBtn" class="btn btn-primary">审核</button>
										</c:if>
										<c:if test="${act == 'REAUDIT' }">
											<button type="button" id="reAuditBtn" class="btn btn-primary">反审核</button>
										</c:if>
										<button type="button" id="cancelBtn" class="btn btn-primary">取
											消</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
