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

<title>合同内费用核销</title>
<script type="text/javascript">
var btn;
$(document).ready(function(){
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			if(${vo.isCreateU8} && confirm("此条核销记录已经生成U8收款单，要再次生成，请在U8系统中将已经生成的相关信息删除，以免造成重复数据；如果已经删除，请点击确定按钮再次生成，如果未删除，请点击取消按钮"))
				form1.submit();
			if(!${vo.isCreateU8})
				form1.submit();
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  history.back();
	});
	
});

</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="createOrderU8.jhtml"
						class="form-horizontal" method="post">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" />
						<input type="hidden" name="vo.beginTime" value="${vo.beginTime}" />
						<input type="hidden" name="vo.endTime" value="${vo.endTime}" />
						<h5>
							<span id="formTitle">合同内费用核销</span>
						</h5>
						<div class="row-fluid">
							<div class="span12">
								<div class="control-group">
									<label class="control-label">核销编码</label>
									<div class="controls">
										<input value="${vo.verCode}" type="text" readonly="readonly" name="vo.verCode" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">所属预提</label>
									<div class="controls">
										<input value="${vo.inFeeProvision.code}" type="text"
											readonly="readonly" msg="请选择所属预提" dataType="Require" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">所属客户</label>
									<div class="controls">
										<input value="${vo.custom.customName}" type="text"
											readonly="readonly" id="customName" name="vo.custom.customName"/> <span>*</span>
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">核销方向</label>
									<div class="controls">
										<input value="${vo.verDirection.text}" type="text"
											readonly="readonly" id="customName" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">核销类型</label>
									<div class="controls">
										<input value="${vo.verType.dictName}" type="text"
											readonly="readonly" id="customName" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">进场费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.enterFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">固定费用</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.fixedFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">年返金</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.yearReturnFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">月返金</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.monthReturnFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">网络信息费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.netInfoFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">配送服务费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.deliveryFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">海报费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.posterFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">促销陈列费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.promotionFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">赞助费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.sponsorFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">损耗费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.lossFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">固定折扣</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.fixedDiscount}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">堆头费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.pilesoilFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">市场费</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.marketFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">现款现货返利</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.caseReturnFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">其他费用</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.otherFee}' pattern='#.##'/>"
											type="text" class="input" readonly="readonly" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">费用总和</label>
									<div class="controls">
										<input
											value="<fmt:formatNumber value='${vo.totalFee}' pattern='#.##'/>"
											type="text" id="totalFee" readonly="readonly" />
									</div>
								</div>
							</div>		
						</div>
						
						<h5>
							<span id="formTitle">U8收款单信息</span>
						</h5>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">制单人</label>
									<div class="controls">
										<input name="vo.maker" value="${vo.maker}" type="text"
											placeholder="请输入制单人" class="input" msg="请输入制单人"
											dataType="Require" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">摘要</label>
									<div class="controls">
										<input name="vo.summary" value="${vo.summary}" type="text"
											placeholder="请输入摘要" class="input" msg="请输入摘要"
											dataType="Require" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">借方科目</label>
									<div class="controls">
										<input name="vo.backItem" value="${vo.backItem}" type="text"
											placeholder="请输入借方科目" class="input" msg="请输入借方科目"
											dataType="Require" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">贷方科目</label>
									<div class="controls">
										<input name="vo.customItem" value="${vo.customItem}"
											type="text" placeholder="请输入贷方科目" class="input" msg="请输入贷方科目"
											dataType="Require" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">结算方式编码</label>
									<div class="controls">
										<input name="vo.ssCode" value="${vo.ssCode}" type="text"
											placeholder="请输入结算方式编码" class="input" msg="请输入结算方式编码"
											dataType="Require" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span12">
								<div class="control-group mT30">
									<div class="controls"
										style="margin: 0 auto; text-align: center;">
										<button type="button" id="saveBtn" class="btn btn-primary">生成U8收款单</button>
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
