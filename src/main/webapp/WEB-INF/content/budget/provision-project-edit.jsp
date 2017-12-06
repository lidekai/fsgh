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

<title>合同外预提项目管理</title>
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
	  /* var url="<c:url value='/budget/provision-project/list.jhtml'/>";
	  window.location.href=url; */
	  history.back();
	});
	
	var act ='${act}';
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled
	}
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
							<span id="formTitle">${act.text}合同外预提项目</span>
						</h5>
						<div class="control-group">
							<label class="control-label">费用名称</label>
							<div class="controls">
								<input name="vo.feeName" value="${vo.feeName}" type="text"
									placeholder="请输入费用名称" msg="请输入费用名称" dataType="Require" /><span>*</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">项目类型</label>
							<div class="controls">
								<select name="vo.projectType" dataType="Require" msg="请选择项目类型">
									<option value="">请选择</option>
									<c:forEach items="<%=PublicType.ProjectType.values() %>"
										var="type">
										<option value="${type.name}"
											<c:if test="${type.name == vo.projectType }">selected</c:if>>${type.text}</option>
									</c:forEach>
								</select> <span>*</span>
							</div>
						</div>


						<div class="control-group mT30">
							<div class="controls" style="margin: 0 auto; text-align: center;">
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
