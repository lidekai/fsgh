<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>产品分类管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			form1.submit();					
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  var url="<c:url value='/info/product-type/list.jhtml'/>";
	  window.location.href=url;
	});
	
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
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" />
						<h5>
							<span id="formTitle">编辑产品分类</span>
						</h5>

						<c:if test="${vo.productType != null}">

							<div class="control-group">
								<label class="control-label">所属分类</label>
								<div class="controls">
									<input type="hidden" name="vo.productType.id"
										value="${vo.productType.id}" /> <input type="text"
										value="${vo.productType.productTypeName}" readonly="readonly" />
								</div>
							</div>
						</c:if>

						<div class="control-group">
							<label class="control-label">分类名称</label>
							<div class="controls">
								<input name="vo.productTypeName" id="vo_name"
									value="${vo.productTypeName }" type="text" maxLength="125"
									placeholder="请输入分类名称" dataType="Require" msg="请输入分类名称" /><span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">级别</label>
							<div class="controls">
								<input name="vo.lev"
									value="<c:out value='${vo.lev }' default='1' />" type="text"
									maxLength="50" placeholder="请输入级别 " dataType="Integer"
									msg="请输入级别" /><span>*</span>
							</div>
						</div>

						<div class="control-group mT30">
							<div class="controls">
								<button type="button" id="saveBtn" class="btn btn-primary">确
									定</button>
								<button type="button" id="cancelBtn" class="btn btn-primary">取
									消</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>