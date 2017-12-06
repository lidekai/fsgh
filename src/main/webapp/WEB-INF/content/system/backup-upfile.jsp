<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>监督抽查</title>
<script type="text/javascript">
		$(document).ready(function(){
			//确定按钮事件
			$("#confirmBtn").click(function(){
				$("#importForm").submit();
			});
			//取消按钮事件
			$("#cancelBtn").click(function(){
				var url="<c:url value='/system/backup/list.jhtml'/>";
				  location.href=url;
			});
		});
</script>
</head>
<body>
	<div class="jt-container" style="max-width: 1000px;">
		<div class="container-fluid ">
			<div class="row-fluid">
				<div class="span12">
					<form class="form-horizontal" id="importForm" name="importForm"
						action="upload.jhtml" method="post"
						onsubmit="return Validator.Validate(this,3);"
						enctype="multipart/form-data">
						<h5>
							<span id="formTitle">导入备份文件</span>
						</h5>


						<div class="control-group mT30">
							<label class="control-label">选择上传文件</label>
							<div class="controls">
								<input type="file" name="pack" dataType="Custom"
									regexp="^.+\.dat$" msg="请选择 dat 数据包" />
							</div>
						</div>

						<div class="control-group mT30">
							<label class="control-label">导入结果</label>
							<div class="controls">
								<font size="4px" color="red">${result}</font>

							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<button type="button" id="confirmBtn" class="btn btn-primary">导入</button>
								<button type="button" id="cancelBtn" class="btn btn-primary">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>