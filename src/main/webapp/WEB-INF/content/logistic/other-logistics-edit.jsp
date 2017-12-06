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

<title>其他物流费用</title>
<script type="text/javascript">
$(document).ready(function(){
	if("${act}" == 'VIEW' || "${act}" == 'AUDIT' || "${act}" == 'REAUDIT'){
		 $("#customBtn").hide();//隐藏选择客户按钮
	}
	
	//选择客户
	$("#customBtn").click(function(){
		var url = "<c:url value='/info/custom/selectCustom.jhtml'/>";
		top.showMyModal("选择客户",url,900,true);
		btn = "customBtn";
	});
	
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			//判断客户当月其他物流费用是否已存在
			var url = "<c:url value='/logistic/other-logistics/isExist.jhtml'/>?vo.id=${vo.id}&customId=" + $("#custom_id").val();
			$.post(url , null , function(data){
				if(data == "1"){
					alert("此客户本月其他物流费用已存在，不可重复添加");
					return false;
				}else
					form1.submit();
			});
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  history.back();
	});

	//审核
	$("#auditBtn").click(function(){
		$("#act").val("AUDIT");
		$("#form1").attr("action","approve.jhtml");
		$("#form1").submit();
	});
	
	//审核
	$("#reAuditBtn").click(function(){
		$("#act").val("REAUDIT");
		$("#form1").attr("action","approve.jhtml");
		$("#form1").submit();
	});
	
	//计算总费用
	$("input.input").change(function(){
		var sum = 0;
		$("input.input").each(function(){
			if(!isNaN(this.value) && this.value != "")
				sum += parseFloat(this.value);
		})
		$("#totalFee").val(sum.toFixed(2));
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
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" id="key" />
						<input type="hidden" name="ids" value="${vo.id }" id="ids"/>
						<input type="hidden" name="keys" value="${key }" id="keys"/>
						<input type="hidden" name="act" value="" id="act"/>
						<input type="hidden" name="vo.createStartTime" value="${vo.createStartTime }" />
						<input type="hidden" name="vo.createEndTime" value="${vo.createEndTime }" />
						<h5>
							<span id="formTitle">其他物流费用</span>
						</h5>
						<div class="control-group">
							<label class="control-label">所属客户</label>
							<div class="controls">
								<input type="hidden" name="vo.custom.id" value="${vo.custom.id}"
									id="custom_id" /> <input name="vo.custom.customName"
									value="${vo.custom.customName}" type="text" readonly="readonly"
									msg="请选择所属客户" dataType="Require" />
								<button class="btn btn-mini" type="button" id="customBtn">选择客户</button>
								<span>*</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">促销道具</label>
							<div class="controls">
								<input name="vo.cost" type="text" value="${vo.cost}"
									dataType="Double" msg="请输入正确的数字" class="input" /> <span>*</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">退货</label>
							<div class="controls">
								<input name="vo.returnGoods" type="text"
									value="${vo.returnGoods}" dataType="Double" msg="请输入正确的数字"
									class="input" /> <span>*</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">销售额</label>
							<div class="controls">
								<input name="vo.salesamount" type="text"
									value="${vo.salesamount}" dataType="Double" msg="请输入正确的数字" /> <span>*</span>
							</div>
						</div>
						<c:if test="${not empty vo.id}">
							<div class="control-group">
								<label class="control-label">工资分摊</label>
								<div class="controls">
									<input name="vo.wagesShare" type="text"
										value="${vo.wagesShare}" readonly="readonly" class="input" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">仓储分摊</label>
								<div class="controls">
									<input name="vo.storageShare" type="text"
										value="${vo.storageShare}" readonly="readonly" class="input" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">调拨费分摊</label>
								<div class="controls">
									<input name="vo.transferCost" type="text"
										value="${vo.transferCost}" readonly="readonly" class="input" />
								</div>
							</div>
						</c:if>
						<div class="control-group">
							<label class="control-label">运费合计</label>
							<div class="controls">
								<input name="vo.otherLogisticsCost" type="text"
									value="${vo.otherLogisticsCost}" readonly="readonly"
									id="totalFee" /> <span>*</span>
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
