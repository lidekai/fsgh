<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>产品管理</title>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="update.jhtml" class="form-horizontal"
						method="post" enctype="multipart/form-data">
						<h5>
							<span id="formTitle">查看产品信息</span>
						</h5>
						<div class="control-group">
							<label class="control-label">条形码</label>
							<div class="controls">${product.barCode}</div>		
						</div>
						<div class="control-group">
							<label class="control-label">存货编码</label>
							<div class="controls">${product.stockCde}</div>
						</div>
						<div class="control-group">
							<label class="control-label">货号</label>
							<div class="controls">${product.number}</div>
						</div>
						<div class="control-group">
							<label class="control-label">产品名称</label>
							<div class="controls">${product.productName}</div>
						</div>
						<div class="control-group">
							<label class="control-label">产品规格</label>
							<div class="controls">${product.standard}</div>
						</div>
						<div class="control-group">
							<label class="control-label">是否新品</label>
							<div class="controls">${product.newProduct ? '是' : '否'}</div>
						</div>
						<div class="control-group">
					        <div class="controls"><img src="<c:url value='../../${product.path}' />"></div>
					      </div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>