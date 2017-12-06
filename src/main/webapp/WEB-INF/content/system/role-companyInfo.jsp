<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>数据库备份</title>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<h5>
						<span>公司资质管理</span>
					</h5>
				</div>
				<!-- / span12 end-->
			</div>
			<div class="row-fluid">
					<label class="control-label">附件上传</label>
					<div class="controls">
					<%
	       					String Code = request.getAttribute("vo.id") == null ? "":((Long)request.getAttribute("vo.id")).toString();
	       			%>
						<jsp:include page="/WEB-INF/common/uploadify.jsp" >
		       					<jsp:param value="<%=com.kington.fshg.common.PublicType.AttachType.GSZZ %>" name="attachType"/>
		       					<jsp:param value="1" name="code"/>
		       					<jsp:param value="<%=com.kington.fshg.common.Common.getAttachMD5(Code, com.kington.fshg.common.PublicType.AttachType.GSZZ) %>" name="key"/>
		       			</jsp:include>
					</div>
			</div>

		</div>
		<!--container-fluid end-->
	</div>
</body>
</html>