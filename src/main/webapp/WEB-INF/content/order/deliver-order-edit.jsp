<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>发货订单管理</title>
<script type="text/javascript">
$(document).ready(function(){
	
	
	//保存
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			var receiveNum = $("#vo_receiveNum").val();
			if(receiveNum == ''){
				alert("实收数不能为空！");
				return false;
			}else if(isNaN(receiveNum)){
				alert("请输入正确的数字");
				return false;
			}
			form1.submit();
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  	history.back();
	});
	
	$("input:not([id^='vo_receiveNum'])").attr("readonly","readonly");
	$("#vo_receiveNum").blur(setTotal);
});

function setTotal(){
	 var count = $("#vo_count").val();
	var countPrice = $("#vo_countPrice").val();
	var receiveNum =$("#vo_receiveNum").val();
	if(count != "" && countPrice != "" && receiveNum != ""){
		if(!isNaN(count) && !isNaN(countPrice) && !isNaN(receiveNum)){
			var total = (parseFloat(countPrice) / parseFloat(count)) *　parseFloat(receiveNum);
			$("#total").val(total);
		}
	}
}

</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="update.jhtml" class="form-horizontal"
						method="post">
						<input type="hidden" name="vo.id" value="${vo.id}" /> 
						<input type="hidden" name="key" value="${vo.key}" />
						<input type="hidden" name="vo.timeType" value="${vo.timeType}" />
						<input type="hidden" name="vo.beginTime" value="${vo.beginTime}" />
						<input type="hidden" name="vo.endTime" value="${vo.endTime}" />
						<h5>
							<span id="formTitle">查看销售发货单</span>
						</h5>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">发货单号</label>
									<div class="controls">
										<input name="vo.cdlCode" value="${vo.cdlCode}" type="text" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">发货日期</label>
									<div class="controls">
										<input type="text" class="Wdate" name="vo.deliverDate"
											value="<fmt:formatDate value="${vo.deliverDate }" pattern="yyyy-MM-dd"/>"
											readonly="readonly" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">销售订单号</label>
									<div class="controls">
										<input name="vo.csoCode" value="${vo.csoCode}" type="text" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">订单日期</label>
									<div class="controls">
										<input type="text" class="Wdate" name="vo.orderDate"
											value="<fmt:formatDate value="${vo.orderDate }" pattern="yyyy-MM-dd"/>"
											readonly="readonly" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">卖场订单号</label>
									<div class="controls">
										<input name="vo.storeCde" value="${vo.storeCde}" type="text" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">原始单据日期</label>
									<div class="controls">
										<input type="text" class="Wdate" name="vo.originalDate"
											value="<fmt:formatDate value="${vo.originalDate}" pattern="yyyy-MM-dd"/>"
											readonly="readonly" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">客户编码</label>
									<div class="controls">
										<input name="vo.customCde" value="${vo.customCde}" type="text" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">客户名称</label>
									<div class="controls">
										<input name="vo.customName" value="${vo.customName}"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">存货编码</label>
									<div class="controls">
										<input name="vo.stockCde" value="${vo.stockCde}" type="text" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">存货代码</label>
									<div class="controls">
										<input name="vo.productCde" value="${vo.productCde}"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">存货名称</label>
									<div class="controls">
										<input name="vo.stockName" value="${vo.stockName}" type="text" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">存货规格</label>
									<div class="controls">
										<input name="vo.standard" value="${vo.standard}" type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">数量</label>
									<div class="controls">
										<input name="vo.count" value="${vo.count}" type="text"
											id="vo_count" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">本币含税单价</label>
									<div class="controls">
										<input name="vo.LocalPrice" value="${vo.localPrice}"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">本币价税合计</label>
									<div class="controls">
										<input name="vo.countPrice" value="${vo.countPrice}"
											type="text" id="vo_countPrice" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">实收数</label>
									<div class="controls">
										<input name="vo.receiveNum" value="${vo.receiveNum}"
											type="text" id="vo_receiveNum"
											<c:if test="${act =='VIEW' }">readonly='readonly'</c:if>
											dataType="Double" msg="请输入正确的数字" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">实收本币价税合计</label>
									<div class="controls">
										<input name="" value="${vo.total}" type="text" id="total" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">件数</label>
									<div class="controls">
										<input name="" value="${vo.number}" type="text" id="number" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">本币无税金额</label>
									<div class="controls">
										<input name="" value="${vo.noTaxPrice}" type="text"
											id="noTaxPrice" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">销售类型</label>
									<div class="controls">
										<input name="" value="${vo.saleType}" type="text"
											id="saleType" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">报价</label>
									<div class="controls">
										<input name="" value="${vo.iQuotedPrice}" type="text"
											id="iQuotedPrice" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">扣率</label>
									<div class="controls">
										<input name="" value="${vo.kl}" type="text" id="kl" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">二次扣率</label>
									<div class="controls">
										<input name="" value="${vo.kl2}" type="text" id="kl2" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">票折</label>
									<div class="controls">
										<input name="" value="${vo.ticketDiscount}" type="text"
											id="ticketDiscount" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">费用折</label>
									<div class="controls">
										<input name="" value="${vo.costDiscount}" type="text"
											id="costDiscount" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span12">
								<div class="control-group mT30">
									<div class="controls"
										style="margin: 0 auto; text-align: center;">
										
										<c:if test="${act != 'VIEW' }">
											<button type="button" id="saveBtn" class="btn btn-primary">确
												定</button>
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