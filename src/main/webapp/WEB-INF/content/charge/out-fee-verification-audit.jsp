<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.*" %>
<%@ page import="com.kington.fshg.webapp.security.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
	<link href="<c:url value='/lib/weebox-0.4/src/weebox.css'/>" rel="stylesheet"  type="text/css" charset="utf-8" />
	<script type="text/javascript" src="<c:url value='/lib/jquery-1.4.4.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/weebox.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/bgiframe.js'/>"></script>
	
<title>合同内费用核销</title>
<script type="text/javascript">
var btn;
$(document).ready(function(){
	//审批按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			form1.submit();	
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  history.back();
	});
	
		 $('input').attr("readyonly","readonly")//将input元素设置为disabled
		 $("select").attr("disabled","disabled");
	
		var projectType = $("#projectType").val();
		if(projectType == 'CXYGZL'){
			$("#CXYGZL").show();//促销员工资类
		}else if(projectType == 'CPMXL'){
			$("#CPMXL").show();//产品明细类
		}else if(projectType == 'QTFYL'){
			$("#QTFYL").show();//其他费用类
		}
	
});
</script>
<style type="text/css">
.input120{width: 120px;}
.input60{width:60px;}
</style>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="approve.jhtml" class="form-horizontal" method="post">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key}" /> 
						<input type="hidden" name="ids" value="${vo.id}" /> 
						<input type="hidden" name="act" value="${act}" />
						<input type="hidden" name="vo.beginTime" value="${vo.beginTime}" />
						<input type="hidden" name="vo.endTime" value="${vo.endTime}" />
						<h5>
							<span id="formTitle">${act.text}合同外费用核销</span>
						</h5>
						
							<div class="control-group">
									<label class="control-label">核销编码</label>
									<div class="controls">
										<input name="" type="text" value="${vo.verCode}" readonly="readonly"/>
									</div>
								</div>
						
								<div class="control-group">
									<label class="control-label">申请业务员</label>
									<div class="controls">
										<input name="vo.salesman" value="${vo.salesman}" type="text" readonly="readonly"/>
									</div>
								</div>
						
								<div class="control-group">
									<label class="control-label">所属预提</label>
									<div class="controls">
										<input type="hidden" name="vo.outFeeProvision.id" value="${vo.outFeeProvision.id }" id="provision_id"/>
										<input type="hidden" name="vo.outFeeProvision.provisionProject.projectType" 
											value="${vo.outFeeProvision.provisionProject.projectType }" id="projectType"/>
										<input type="hidden" value="${vo.outFeeProvision.custom.id}" id="custom_id"/>
										<input name="vo.outFeeProvision.provisionCode" value="${vo.outFeeProvision.provisionCode}" type="text" readonly="readonly"
											msg="请选择所属预提" dataType="Require"/>
										<span>*</span>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">项目名称</label>
									<div class="controls">
										<input type="hidden" name="vo.outFeeProvision.provisionProject.projectType" 
											value="${vo.outFeeProvision.provisionProject.projectType }" id="projectType"/>
										<input name="" type="text" value="${vo.outFeeProvision.provisionProject.feeName }" readonly="readonly" id="projectName"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">所属客户</label>
									<div class="controls">
										<input name="vo.custom.customName" value="${vo.custom.customName}" type="text" readonly="readonly"/>
									</div>
								</div>
						
								<div class="control-group">
									<label class="control-label">核销方向</label>
									<div class="controls">
										<select name="vo.verDirection" dataType="Require" msg="请选择核销方向">
											<option value="">请选择</option>
											<c:forEach items="<%=PublicType.VerDirection.values() %>" var="direction">
												<option value="${direction.name}" <c:if test="${direction.name == vo.verDirection }">selected</c:if>>${direction.text}</option>
											</c:forEach>
										</select><span>*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">核销类型</label>
									<div class="controls">
										<select name="vo.verType.id" dataType="Require" msg="请选择核销类型">
						 	        		<option value="${vo.verType.id}"  selected>${vo.verType.dictName}</option>
						 	        	</select><span>*</span>
									</div>
								</div>
								
								<!-- 其他费用类 -->
								<div class="control-group" style="display: none;" id="QTFYL">
									<label class="control-label">其他费用</label>
									<div class="controls">
										<input name="vo.totalFee" type="text" value="${vo.totalFee}" readonly="readonly"/>
									</div>
								</div>
								
								
								<!-- 促销员工资类 -->
								<div class="control-group" style="display:none;" id="CXYGZL">
									<label class="control-label">门店数</label>
									<div class="controls">
										<input name="storeNum" type="text" value="${storeNum}" readonly="readonly" class="input120"/>&nbsp;&nbsp;X&nbsp;
										<input name="feeScale" type="text" value="${feeScale}" readonly="readonly" class="input120"/>%
									</div>
								</div>
								
								
								<div class="control-group">
									<label class="control-label">总费用</label>
									<div class="controls">
										<input name="" type="text" value="${vo.totalFee}" readonly="readonly" id="totalFee"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">核销时间</label>
									<div class="controls">
										<input name="vo.verTime"  value="<fmt:formatDate value='${vo.verTime}' pattern='yyyy-MM-dd' />" type="text" class="input-medium Wdate"  readonly="readonly" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">所属区间</label>
									<div class="controls">
										<input type="text" class="input-medium Wdate" name="vo.timeStart" value="<fmt:formatDate value='${vo.timeStart}' pattern='yyyy-MM-dd' />"  readonly="readonly"  />至
       									<input type="text" class="input-medium Wdate" name="vo.timeEnd" value="<fmt:formatDate value='${vo.timeEnd}' pattern='yyyy-MM-dd' />"  readonly="readonly"  />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">备注</label>
									<div class="controls">
										<textarea style="width: 500px;height: 100px;" name="vo.remark">${vo.remark }</textarea>
									</div>
								</div>
								
							<div id="CPMXL" style="display:none;">						
							<h5><span>产品信息</span>
							</h5>
						      <div class="row-fluid">
							   <div class="span12">
							   		<table class="table table-bordered table-striped table-hover" id="product_table">
							   			<thead>
							   				<tr>
							   					<th style="width:30px;">序号</th>
							   					<th style="white-space:nowrap">门店名称</th>
							   					<th style="width:200px;">产品名称</th>
												<th style="width:120px;">存货编码</th>
												<th style="white-space:nowrap">货号</th>
												<th style="white-space:nowrap">产品类别</th>
												<th style="width:150px;">产品规格</th>
												<th style="white-space:nowrap">费用(元)</th>
												<th style="width:100px;">操 作</th>
							   				</tr>
							   			</thead>
							   			<tbody id="tbody">
							   				<c:if test="${not empty vo.id }">
							   					<c:forEach items="${detailList}" var="detail" varStatus="i">
							   						<tr class="odd">
							   							<input id="product_${detail.storeProduct.product.id}" type="hidden" name="vpdList[${i.count-1}].product.id" value="${detail.storeProduct.product.id }"/>
							   							<td>${i.count}</td>
							   							<td>${detail.storeProduct.store.storeName }</td>
							   							<td>${detail.storeProduct.product.productName}</td>
							   							<td>${detail.storeProduct.product.stockCde }</td>
							   							<td>${detail.storeProduct.product.number}</td>
							   							<td>${detail.storeProduct.product.productType.productTypeName}</td>
							   							<td>${detail.storeProduct.product.standard}</td>
							   							<td><input id="fee_${detail.storeProduct.product.id}" type="text" name="vpdList[${i.count-1 }].cost" class="input60" value="${detail.cost }" /></td>
							   							<td><input id="remark_${detail.storeProduct.product.id}" type="text" name="vpdList[${i.count-1 }].remark" class="input120" value="${detail.remark }" /></td>
							   							<td>
							   							</td>		   							
							   						</tr>
							   					</c:forEach>
							   				</c:if>
							   			</tbody>
							   		</table>
							   </div>
							  </div>
							  </div>

						
						<div class="row-fluid">
						<div class="span12">
						<div class="control-group mT30">
							<div class="controls" style="margin:0 auto;text-align: center;">
								<button type="button" id="saveBtn" class="btn btn-primary">
									<c:if test="${act== 'AUDIT' }">
										审 批
									</c:if>
									<c:if test="${act == 'REAUDIT'}">
										反审批
									</c:if>
								</button>
								<button type="button" id="cancelBtn" class="btn btn-primary">取 消</button>
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
