<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<title>销售发票管理</title>
<script type="text/javascript">
$(document).ready(function(){
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
   <form name="form1" action="update.jhtml" class="form-horizontal" method="post">
		<h5>
			<span id="formTitle">查看销售发票</span>
		</h5>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">销售发票号</label>
					<div class="controls">
						<input name="vo.csbvcode" value="${vo.csbvcode}" type="text"  />
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">订单日期</label>
					<div class="controls">
						<input name="vo.saleDate" value="<fmt:formatDate value='${vo.saleDate}' pattern='yyyy-MM-dd'/>" type="text" />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">制单时间</label>
					<div class="controls">
						<input name="vo.createDate" value="<fmt:formatDate value='${vo.createDate}' pattern='yyyy-MM-dd'/>" type="text"  />
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">发货日期</label>
					<div class="controls">
						<input name="vo.deliverDate" value="<fmt:formatDate value='${vo.deliverDate}' pattern='yyyy-MM-dd'/>" type="text" />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">客户编码</label>
					<div class="controls">
						<input name="vo.customCde" value="${vo.customCde}" type="text"  />
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">客户名称</label>
					<div class="controls">
						<input name="vo.customName" value="${vo.customName}" type="text" />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">存货编号</label>
					<div class="controls">
						<input name="vo.stockCde" value="${vo.stockCde}" type="text"  />
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">存货代码</label>
					<div class="controls">
						<input name="vo.productCde" value="${vo.productCde}" type="text" />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">存货名称</label>
					<div class="controls">
						<input name="vo.productName" value="${vo.productName}" type="text"  />
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
						<input name="vo.count" value="${vo.count}" type="text"  />
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">本币含税单价</label>
					<div class="controls">
						<input name="vo.LocalPrice" value="<fmt:formatNumber value='${vo.localPrice}' pattern='#.##'/>" type="text" />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">本币价税合计</label>
					<div class="controls">
						<input name="vo.countPrice" value="<fmt:formatNumber value='${vo.countPrice}' pattern='#.##'/>" type="text"  />
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">本币无税金额</label>
					<div class="controls">
						<input name="vo.noTaxPrice" value="<fmt:formatNumber value='${vo.noTaxPrice}' pattern='#.##'/>" type="text" />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">销售订单号</label>
					<div class="controls">
						<input name="vo.csoCode" value="${vo.csoCode}" type="text"  />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
			<div class="control-group mT30">
				<div class="controls" style="margin:0 auto;text-align: center;">
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