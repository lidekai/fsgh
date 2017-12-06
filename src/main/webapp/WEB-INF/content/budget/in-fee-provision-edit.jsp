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

<title>客户管理</title>
<script type="text/javascript">
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
	
	
	//选择客户
	$("#customBtn").click(function(){
		var url = "<c:url value='/info/custom/selectCustom.jhtml'/>";
		top.showMyModal("选择客户",url,900,true);
		btn = "customBtn";
	});
	
	$("input.input").change(function(){
		var sum = 0;
		$("input.input").each(function(){
			if(!isNaN(this.value) && this.value != "")
				sum += parseFloat(this.value);
		})
		$("#inFeeCount").val(sum.toFixed(2));
	});
	
	//审核
	$("#auditBtn").click(function(){
		$("#act").val("AUDIT");
		$("#form1").attr("action","approve.jhtml");
		$("#form1").submit();
	});
	
	//反审核
	$("#reAuditBtn").click(function(){
		$("#act").val("REAUDIT");
		$("#form1").attr("action","approve.jhtml");
		$("#form1").submit();
	});
	
});


function setReturnValue(obj){
	if(obj == "") return;
		$("#custom_id").val(obj.id);
		$("input[name='vo.custom.customName']").val(obj.name);
}

</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="update.jhtml" class="form-horizontal" method="post" id="form1">
						<input type="hidden" name="ids" value="${vo.id }" />
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" id="key" />
						<input type="hidden" name="vo.createTimeStart" id="start" value="${vo.createTimeStart}" />
						<input type="hidden" name="vo.createTimeEnd" id="end" value="${vo.createTimeEnd}" />
						<input type="hidden" name="act" id="act" value="" />
						
						<h5>
							<span id="formTitle">${act.text}合同内预提</span> <span
								style="color: red;">温馨提示：每项填入框后显示对应合同内条款设置情况，以供参考</span>
						</h5>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">预提编码</label>
									<div class="controls">
										<input name="vo.code" value="${vo.code}" type="text"
											id="vo_code" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">所属客户</label>
									<div class="controls">
										<input type="hidden" name="vo.custom.id"
											value="${vo.custom.id}" id="custom_id" /> <input
											name="vo.custom.customName" value="${vo.custom.customName}"
											type="text" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">进场费</label>
									<div class="controls">
										<input name="vo.enterFee"
											value="<fmt:formatNumber value='${vo.enterFee}' pattern='#.##'/>"
											type="text" placeholder="请输入进场费" class="input"
											dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
										${vo.inFeeClause.enterFee}
										<c:if test="${not empty vo.inFeeClause.enterStartDate && not empty vo.inFeeClause.enterEndDate}">
											(<fmt:formatDate value="${vo.inFeeClause.enterStartDate}" pattern="yyyy-MM-dd"/>
											-<fmt:formatDate value="${vo.inFeeClause.enterEndDate}" pattern="yyyy-MM-dd"/>)										
										</c:if>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">固定费用</label>
									<div class="controls">
										<input name="vo.fixedFee"
											value="<fmt:formatNumber value='${vo.fixedFee}' pattern='#.##'/>"
											type="text" placeholder="请输入固定费用" class="input"
											dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
										${vo.inFeeClause.fixedFee}
										<c:if test="${not empty vo.inFeeClause.fixedStartDate && not empty vo.inFeeClause.fixedEndDate}">
											(<fmt:formatDate value="${vo.inFeeClause.fixedStartDate}" pattern="yyyy-MM-dd"/>
											-<fmt:formatDate value="${vo.inFeeClause.fixedEndDate}" pattern="yyyy-MM-dd"/>)										
										</c:if>
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">年返金</label>
									<div class="controls">
										<input name="vo.yearReturnFee"
											value="<fmt:formatNumber value='${vo.yearReturnFee}' pattern='#.##'/>"
											type="text" placeholder="请输入年返金比例" class="input"
											dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
										${vo.inFeeClause.yearReturnFee}% * ${vo.sum}
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">其他费用</label>
									<div class="controls">
										<input name="vo.otherFee"
											value="<fmt:formatNumber value='${vo.otherFee}' pattern='#.##'/>"
											type="text" placeholder="请输入其他费用" class="input"
											dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
										${vo.inFeeClause.otherFee}
										<c:if test="${not empty vo.inFeeClause.otherStartDate && not empty vo.inFeeClause.otherEndDate}">
											(<fmt:formatDate value="${vo.inFeeClause.otherStartDate}" pattern="yyyy-MM-dd"/>
											-<fmt:formatDate value="${vo.inFeeClause.otherEndDate}" pattern="yyyy-MM-dd"/>)										
										</c:if>
									</div>
								</div>
							</div>
						</div>
						<div id="dka" style="display: ${empty vo.id ? 'none' : (fn:contains(vo.custom.customType.customTypeName,'DKA') ? '' : 'none')};">
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">网络信息费</label>
										<div class="controls">
											<input name="vo.netInfoFee"
												value="<fmt:formatNumber value='${vo.netInfoFee}' pattern='#.##'/>"
												type="text" placeholder="请输入网络信息费" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
											${vo.inFeeClause.netInfoFee}
											<c:if test="${not empty vo.inFeeClause.netStartDate && not empty vo.inFeeClause.netEndDate}">
												(<fmt:formatDate value="${vo.inFeeClause.netStartDate}" pattern="yyyy-MM-dd"/>
												-<fmt:formatDate value="${vo.inFeeClause.netEndDate}" pattern="yyyy-MM-dd"/>)										
											</c:if>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">配送服务费</label>
										<div class="controls">
											<input name="vo.deliveryFee"
												value="<fmt:formatNumber value='${vo.deliveryFee}' pattern='#.##'/>"
												type="text" placeholder="请输入配送服务费比例" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
											${vo.inFeeClause.deliveryFee}% * ${vo.sum}
										</div>
									</div>
								</div>
							</div>
	
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">海报费</label>
										<div class="controls">
											<input name="vo.posterFee"
												value="<fmt:formatNumber value='${vo.posterFee}' pattern='#.##'/>"
												type="text" placeholder="请输入海报费" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
											${vo.inFeeClause.posterFee}
											<c:if test="${not empty vo.inFeeClause.posterStartDate && not empty vo.inFeeClause.posterEndDate}">
												(<fmt:formatDate value="${vo.inFeeClause.posterStartDate}" pattern="yyyy-MM-dd"/>
												-<fmt:formatDate value="${vo.inFeeClause.posterEndDate}" pattern="yyyy-MM-dd"/>)										
											</c:if>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">促销陈列费</label>
										<div class="controls">
											<input name="vo.promotionFee"
												value="<fmt:formatNumber value='${vo.promotionFee}' pattern='#.##'/>"
												type="text" placeholder="请输入促销陈列费" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
											${vo.inFeeClause.promotionFee}
											<c:if test="${not empty vo.inFeeClause.promotionStartDate && not empty vo.inFeeClause.promotionEndDate}">
												(<fmt:formatDate value="${vo.inFeeClause.promotionStartDate}" pattern="yyyy-MM-dd"/>
												-<fmt:formatDate value="${vo.inFeeClause.promotionEndDate}" pattern="yyyy-MM-dd"/>)										
											</c:if>
										</div>
									</div>
								</div>
							</div>
	
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">赞助费</label>
										<div class="controls">
											<input name="vo.sponsorFee"
												value="<fmt:formatNumber value='${vo.sponsorFee}' pattern='#.##'/>"
												type="text" placeholder="请输入赞助比例" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
											${vo.inFeeClause.sponsorFee}% * ${vo.sum}
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">损耗费</label>
										<div class="controls">
											<input name="vo.lossFee"
												value="<fmt:formatNumber value='${vo.lossFee}' pattern='#.##'/>"
												type="text" placeholder="请输入损耗比例" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/> 
											${vo.inFeeClause.lossFee}% * ${vo.sum}
										</div>
									</div>
								</div>
							</div>
	
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">固定折扣</label>
										<div class="controls">
											<input name="vo.fixedDiscount"
												value="<fmt:formatNumber value='${vo.fixedDiscount}' pattern='#.##'/>"
												type="text" placeholder="请输入固定折扣比例" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
											${vo.inFeeClause.fixedDiscount}% * ${vo.sum}
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">堆头费</label>
										<div class="controls">
											<input name="vo.pilesoilFee"
												value="<fmt:formatNumber value='${vo.pilesoilFee}' pattern='#.##'/>"
												type="text" placeholder="请输入堆头费" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
											${vo.inFeeClause.pilesoilFee}
											<c:if test="${not empty vo.inFeeClause.pileStartDate && not empty vo.inFeeClause.pileEndDate}">
												(<fmt:formatDate value="${vo.inFeeClause.pileStartDate}" pattern="yyyy-MM-dd"/>
												-<fmt:formatDate value="${vo.inFeeClause.pileEndDate}" pattern="yyyy-MM-dd"/>)										
											</c:if>
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">月返金</label>
										<div class="controls">
											<input name="vo.monthReturnFee"
												value="<fmt:formatNumber value='${vo.monthReturnFee}' pattern='#.##'/>"
												type="text" placeholder="请输入月返金比例" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
											${vo.inFeeClause.monthReturnFee}% * ${vo.sum}
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="jxs" style="display: ${empty vo.id ? 'none' : (fn:contains(vo.custom.customType.customTypeName,'DKA') ? 'none' : '')};">
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">市场费</label>
										<div class="controls">
											<input name="vo.marketFee"
												value="<fmt:formatNumber value='${vo.marketFee}' pattern='#.##'/>"
												type="text" placeholder="请输入市场费比例" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
											${vo.inFeeClause.marketFee}% * ${vo.sum}
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">现款现货返利</label>
										<div class="controls">
											<input name="vo.caseReturnFee"
												value="<fmt:formatNumber value='${vo.caseReturnFee}' pattern='#.##'/>"
												type="text" placeholder="请输入现款现货返利比例" class="input"
												dataType="Double" msg="请输入正确的数字" style="width: 90px;"/>
											${vo.inFeeClause.caseReturnFee}% * ${vo.sum}
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">费用总和</label>
									<div class="controls">
										<input name="vo.inFeeCount"
											value="<fmt:formatNumber value='${vo.inFeeCount}' pattern='#.##'/>"
											type="text" readonly="readonly" id="inFeeCount" style="width: 90px;"/>
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span12">
								<div class="control-group mT30">
									<div class="controls"
										style="margin: 0 auto; text-align: center;">
										<c:if
											test="${act != 'VIEW' && act != 'AUDIT' && act != 'REAUDIT'}">
											<button type="button" id="saveBtn" class="btn btn-primary">确
												定</button>
										</c:if>
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
