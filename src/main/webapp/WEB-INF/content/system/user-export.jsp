<%@page import="com.kington.fshg.common.PublicType"%>
<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>用户管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//确定按钮事件
	$("button").eq(0).click(function(){
		getHeader();
		form1.submit();
	});
	$(".jt-container").css({  "background-color": "#ebf4fb" });
	//进放添加页面时提交资料清单中的数据全部先中
	$(".label").each(function(i){
		$(this).addClass("label-success");
	});
	
	$(".label").click(function(){
		if($(this).hasClass("label-success")){
			$(this).removeClass("label-success");
		}else{
			$(this).addClass("label-success");
		}
	});
	
	
});
function getHeader(){
	var names="";
	$(".label-success").each(function(){
		names = names + $(this).attr("att-name")+ ",";
	});
	$("#header").val(names);
}
</script>
</head>
<body>
	<div class="jt-container" style="max-width: 1000px;">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="exportExcel.jhtml"
						class="form-horizontal" method="post">
						<input type="hidden" name="header" id="header" />
						<h5>
							<span id="formTitle">表头信息</span>
						</h5>

						<div class="row-fluid">
							<div class="span12">
								<div class="control-group">
									<label class="control-label">用户部分</label>
									<div class="controls">
										<c:forEach items="${userHeader}" var="hl">
											<span class="label" att-name="${hl}">${hl}</span>
										</c:forEach>
										<blockquote>
											<small>说明：标签显示为绿色表示已选中的标签</small>
										</blockquote>
									</div>
								</div>
							</div>
						</div>

						<div class="control-group" style="text-align: center;">
							<div class="controls">
								<button type="button" class="btn btn-primary">确 定</button>
							</div>
						</div>

					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>