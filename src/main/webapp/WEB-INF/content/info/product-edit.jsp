<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>产品管理</title>
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
	  	history.back();
	});
	
	$("#image").click(function(){
		$(this).prev().remove();
		$(this).remove()
		$("input[name='upload']").attr("style","inline");
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
						method="post" enctype="multipart/form-data">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" />
						<h5>
							<span id="formTitle">编辑产品信息</span>
						</h5>
						<div class="control-group">
							<label class="control-label">存货编码</label>
							<div class="controls">
								<input name="vo.stockCde" value="${vo.stockCde}" type="text"
									maxLength="15" placeholder="请输入存货编码" dataType="Require"
									msg="请输入存货编码" /><span>*</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">存货代码</label>
							<div class="controls">
								<input name="vo.productCde" value="${vo.productCde}" type="text"
									maxLength="15" placeholder="请输入存货代码" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">货号</label>
							<div class="controls">
								<input name="vo.number" value="${vo.number}" type="text"
									maxLength="15" placeholder="请输入货号" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">所属分类</label>
							<div class="controls">
								<select name="vo.productType.id" msg="请选择所属产品分类"
									dataType="Require">
									<option value="">请选择</option>
									<c:forEach items="${productTypeList }" var="productType">
										<option value="${productType.id}"
											<c:if test="${vo.productType.id == productType.id }">selected</c:if>>${productType.productTypeName}</option>
									</c:forEach>
								</select><span>*</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">产品名称</label>
							<div class="controls">
								<input name="vo.productName" value="${vo.productName}"
									type="text" maxLength="50" placeholder="请输入产品名称" msg="请输入产品名称"
									dataType="Require" /><span>*</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">产品规格</label>
							<div class="controls">
								<input name="vo.standard" value="${vo.standard}" type="text"
									maxLength="20" placeholder="请输入产品规格" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">每箱毛重(公斤)</label>
							<div class="controls">
								<input name="vo.boxWeight" value="${vo.boxWeight}" type="text"
									placeholder="请输入每箱毛重" msg="请输入数字" dataType="Double" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">纸箱长(厘米)</label>
							<div class="controls">
								<input name="vo.length" value="${vo.length}" type="text"
									placeholder="请输入纸箱长" msg="请输入数字" dataType="Double" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">纸箱宽(厘米)</label>
							<div class="controls">
								<input name="vo.width" value="${vo.width}" type="text"
									placeholder="请输入纸箱宽" msg="请输入数字" dataType="Double" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">纸箱高(厘米)</label>
							<div class="controls">
								<input name="vo.height" value="${vo.height}" type="text"
									placeholder="请输入纸箱高" msg="请输入数字" dataType="Double" />
							</div>
						</div>
						<c:if test="${act == 'VIEW' }">
							<div class="control-group">
								<label class="control-label">每箱体积(立方米)</label>
								<div class="controls">
									<input name="vo.volume" value="${vo.volume}" type="text" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">计费形式</label>
								<div class="controls">
									<input name="vo.chargeType" value="${vo.chargeType.text}"
										type="text" />
								</div>
							</div>
						</c:if>
						<div class="control-group">
							<label class="control-label">每立方米重量(公斤)</label>
							<div class="controls">
								<input name="vo.meterWeight" value="${vo.meterWeight}"
									type="text" placeholder="请输入每立方米重量" msg="请输入数字"
									dataType="Double" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">标准价</label>
							<div class="controls">
								<input name="vo.standardPrice" value="${vo.standardPrice }"
									type="text" placeholder="请输入标准价" msg="请输入数字" dataType="Double" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">报价</label>
							<div class="controls">
								<input name="vo.quote" value="${vo.quote }"
									type="text" placeholder="请输入报价" msg="请输入数字" dataType="Double" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">是否新品</label>
							<div class="controls">
								<select name="vo.newProduct" msg="请选择是否新品" dataType="Require">
									<option disabled="disabled">请选择</option>
									<option value=true
										<c:if test="${vo.newProduct == true }">selected</c:if>>是</option>
									<option value=false
										<c:if test="${vo.newProduct == false }">selected</c:if>>否</option>
								</select>
							</div>
						</div>
												
						<div class="control-group">
							<label class="control-label">启用时间</label>
							<div class="controls">
								<input name="vo.startTime" class="Wdate"
									value="<fmt:formatDate value="${vo.startTime}" pattern="yyyy-MM-dd" />"
									type="text" readonly="readonly"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">单位</label>
							<div class="controls">
								<input name="vo.unit" value="${vo.unit}"
									type="text" maxLength="20" placeholder="请输入单位"  />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">条形码</label>
							<div class="controls">
								<input name="vo.barCode" value="${vo.barCode}"
									type="text" maxLength="20" placeholder="请输入条形码"  />
							</div>		
						</div>
						
						 <div class="control-group">
					        <label class="control-label">上传图片</label>
					        <div class="controls">
					        	<c:if test="${not empty vo.path}"><img src="<c:url value='../../${vo.path}' />"><c:if test="${act != 'VIEW' }"><input type="button" class="btn btn-danger" id="image" value="替换图片" /></c:if></c:if>
					        	<input type="file" name="upload" <c:if test="${not empty vo.path}">style="display: none;"</c:if> />
					        </div>
					      </div>
						
						<div class="control-group" style="width: 200px">
							<label class="control-label">备注</label>
							<div class="controls">
								<textarea style="width: 500px" rows="5" cols="50"
									name="vo.remark">${vo.remark }</textarea>
							</div>
						</div>
						
						<c:if test="${not empty vo.id }">
							<div class="control-group" style="width: 800px">
								<label class="control-label">附件上传</label>
								<div class="controls">
									<%
					       					String Code = request.getAttribute("vo.id") == null ? "":((Long)request.getAttribute("vo.id")).toString();
					       			%>
									<jsp:include page="/WEB-INF/common/uploadify.jsp" >
					       					<jsp:param value="<%=com.kington.fshg.common.PublicType.AttachType.CPXX %>" name="attachType"/>
					       					<jsp:param value="${vo.id }" name="code"/>
					       					<jsp:param value="<%=com.kington.fshg.common.Common.getAttachMD5(Code, com.kington.fshg.common.PublicType.AttachType.CPXX) %>" name="key"/>
				       						<jsp:param value="${act == 'VIEW' ? 'true' : '' }" name="readonly"/>
					       			</jsp:include>
								</div>
							</div>
						</c:if>

						<div class="control-group mT30">
							<div class="controls">
								<c:if test="${act != 'VIEW' }">
									<button type="button" id="saveBtn" class="btn btn-primary">确
										定</button>
								</c:if>
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