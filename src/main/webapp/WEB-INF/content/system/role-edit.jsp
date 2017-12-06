<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>角色</title>
<script type="text/javascript">
$(document).ready(function(){
	var act = '${act}';
	if(act=="ADD") act = "添加";
	else if(act=="EDIT") act = "编辑";
	else if(act=="VIEW"){
		act = "查看";
	}  
	$("span").first().html(act + "角色信息");
	//确定按钮事件
	$("button").eq(0).click(function(){
		if(Validator.Validate(form1,3,3)){
			form1.submit();					
		}
	});
	//取消按钮事件
	$("button").eq(1).click(function(){
		history.back(-1);
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
							<span id="formTitle"></span>
						</h5>
						<div class="control-group mT30">
							<label class="control-label">角色名称</label>
							<div class="controls">
								<input name="vo.roleName" value="${vo.roleName }" type="text"
									maxLength="50" placeholder="请输入角色名称" dataType="Require"
									msg="请输入角色名称" /><span>*</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">状态</label>
							<div class="controls">
								<select name="vo.state" dataType="Require" msg="请选择状态">
									<option value="USE"
										<c:if test="${vo.state=='USE'}" >selected="selected"</c:if>>启用</option>
									<option value="STOP"
										<c:if test="${vo.state=='STOP'}" >selected="selected"</c:if>>停用</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">备注</label>
							<div class="controls">
								<textarea name="vo.remark" rows="5" class="span6">${vo.remark }</textarea>
							</div>
						</div>

						<div class="control-group">
							<div class="controls">
								<button type="button" class="btn btn-primary">确 定</button>
								<button type="button" class="btn btn-primary">取 消</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>