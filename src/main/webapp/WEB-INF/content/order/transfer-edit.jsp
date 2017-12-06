<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>销售订单管理</title>
<script type="text/javascript">
$(document).ready(function(){
	$('input').attr("readonly","readonly");
	
});

</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="update.jhtml" class="form-horizontal"
						method="post">
						<h5>
							<span id="formTitle">查看仓库调拨单</span>
						</h5>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">调拨单号</label>
									<div class="controls">
										<input name="vo.transCode" value="${vo.transCode}" type="text" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">调拨日期</label>
									<div class="controls">
										<input name="vo.transDate"
											value="<fmt:formatDate value='${vo.transDate}' pattern='yyyy-MM-dd'/>"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">调出仓库编码</label>
									<div class="controls">
										<input name="vo.outWhouseCode" value="${vo.outWhouseCode}"
											type="text" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">调出仓库名称</label>
									<div class="controls">
										<input name="vo.outWhouseName" value="${vo.outWhouseName}"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">调入仓库编码</label>
									<div class="controls">
										<input name="vo.inWhouseCode" value="${vo.inWhouseCode}"
											type="text" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">调入仓库名称</label>
									<div class="controls">
										<input name="vo.inWhouseName" value="${vo.inWhouseName}"
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
									<label class="control-label">件数</label>
									<div class="controls">
										<input name="vo.count" value="${vo.count}" type="text" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">单价</label>
									<div class="controls">
										<input name="vo.price"
											value="<fmt:formatNumber value='${vo.price}' pattern='#.##'/>"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">金额</label>
									<div class="controls">
										<input name="vo.money"
											value="<fmt:formatNumber value='${vo.money}' pattern='#.##'/>"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">运费</label>
									<div class="controls">
										<input name="vo.money"
											value="<fmt:formatNumber value='${vo.freight}' pattern='#.##'/>"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">配送费</label>
									<div class="controls">
										<input name="vo.money"
											value="<fmt:formatNumber value='${vo.logDeliverFee}' pattern='#.##'/>"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">运费合计</label>
									<div class="controls">
										<input name="vo.money"
											value="<fmt:formatNumber value='${vo.freightTotal}' pattern='#.##'/>"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span12">
								<div class="control-group mT30">
									<div class="controls"
										style="margin: 0 auto; text-align: center;">
										<button type="button" id="cancelBtn" class="btn btn-primary"
											onclick="history.back();">取 消</button>
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