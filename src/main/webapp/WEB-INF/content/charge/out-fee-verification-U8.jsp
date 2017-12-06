<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.*"%>
<%@ page import="com.kington.fshg.webapp.security.*"%>
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
var act ='${act}';
$(document).ready(function(){
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
		 history.back();
	});
	
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			if(${vo.isCreateU8} && confirm("此条核销记录已经生成U8收款单，要再次生成，请在U8系统中将已经生成的相关信息删除，以免造成重复数据；如果已经删除，请点击确定按钮再次生成，如果未删除，请点击取消按钮"))
				form1.submit();
			if(!${vo.isCreateU8})
				form1.submit();
		}
	});
	
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled
		 $("select").attr("disabled","disabled");
		 $("#provisionBtn").hide();
	}
	
	if(act != 'ADD'){
		var projectType = $("#projectType").val();
		if(projectType == 'CXYGZL'){
			$("#CXYGZL").show();//促销员工资类
		}else if(projectType == 'CPMXL'){
			$("#CPMXL").show();//产品明细类
		}else if(projectType == 'QTFYL'){
			$("#QTFYL").show();//其他费用类
		}
	}
	
	$("#feeScale").blur(setTotalFee);
	$("#storeNum").blur(setTotalFee);
	$("#otherFee").blur(setTotalFee);
});

</script>
<style type="text/css">
.input120 {
	width: 120px;
}

.input60 {
	width: 60px;
}
</style>
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
							<span id="formTitle">合同外费用核销</span>
						</h5>

						<c:if test="${act != 'ADD' }">
							<div class="control-group">
								<label class="control-label">核销编码</label>
								<div class="controls">
									<input type="text" name="vo.verCode" value="${vo.verCode}"
										readonly="readonly" />
								</div>
							</div>
						</c:if>

						<div class="control-group">
							<label class="control-label">申请业务员</label>
							<div class="controls">
								<input value="${vo.salesman}" type="text" readonly="readonly" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">所属预提</label>
							<div class="controls">
								<input value="${vo.outFeeProvision.provisionCode}" type="text"
									readonly="readonly" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">项目名称</label>
							<div class="controls">
								<input name="" type="text"
									value="${vo.outFeeProvision.provisionProject.feeName }"
									readonly="readonly" id="projectName" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">所属客户</label>
							<div class="controls">
								<input type="text" value="${vo.custom.customName}" name="vo.custom.customName"
									readonly="readonly" id="customName" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">核销方向</label>
							<div class="controls">
								<input name="" type="text" value="${vo.verDirection.text}"
									readonly="readonly" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">核销类型</label>
							<div class="controls">
								<input name="" type="text" value="${vo.verType.dictName}"
									readonly="readonly" />
							</div>
						</div>

						<!-- 其他费用类 -->
						<div class="control-group" style="display: none;" id="QTFYL">
							<label class="control-label">其他费用</label>
							<div class="controls">
								<input type="text" value="${vo.totalFee}" id="otherFee" />
							</div>
						</div>


						<!-- 促销员工资类 -->
						<div class="control-group" style="display: none;" id="CXYGZL">
							<label class="control-label">门店数</label>
							<div class="controls">
								<input type="text" value="${storeNum}" id="storeNum"
									class="input120" />&nbsp;&nbsp;X&nbsp; <input type="text"
									value="${feeScale}" id="feeScale" class="input120" />%
							</div>
						</div>


						<div class="control-group">
							<label class="control-label">总费用</label>
							<div class="controls">
								<input name="" type="text" value="${vo.totalFee}"
									readonly="readonly" id="totalFee" />
							</div>
						</div>

						<div id="CPMXL" style="display: none;">
							<h5>
								<span>产品信息</span>
							</h5>
							<div class="row-fluid">
								<div class="span12">
									<table class="table table-bordered table-striped table-hover"
										id="product_table">
										<thead>
											<tr>
												<th style="width: 30px;">序号</th>
												<th style="width: 200px;">产品名称</th>
												<th style="width: 120px;">存货编码</th>
												<th style="white-space: nowrap">货号</th>
												<th style="white-space: nowrap">产品类别</th>
												<th style="width: 150px;">产品规格</th>
												<th style="white-space: nowrap">费用(元)</th>
												<th style="width: 100px;">操 作</th>
											</tr>
										</thead>
										<tbody id="tbody">
											<c:if test="${not empty vo.id }">
												<c:forEach items="${detailList}" var="detail" varStatus="i">
													<tr class="odd">
														<input id="product_${detail.product.id}" type="hidden"
															name="vpdList[${i.count-1}].product.id"
															value="${detail.product.id }" />
														<td>${i.count}</td>
														<td>${detail.product.productName}</td>
														<td>${detail.product.stockCde }</td>
														<td>${detail.product.number}</td>
														<td>${detail.product.productType.productTypeName}</td>
														<td>${detail.product.standard}</td>
														<td><input id="fee_${detail.product.id}" type="text"
															name="vpdList[${i.count-1 }].cost" class="input60"
															value="${detail.cost }" onblur="setTotalFee()" /></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
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
									<label class="control-label">收款银行科目</label>
									<div class="controls">
										<input name="vo.backItem" value="${vo.backItem}" type="text"
											placeholder="请输入收款银行科目" class="input" msg="请输入收款银行科目"
											dataType="Require" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">对方科目</label>
									<div class="controls">
										<input name="vo.customItem" value="${vo.customItem}"
											type="text" placeholder="请输入对方科目" class="input" msg="请输入对方科目"
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
