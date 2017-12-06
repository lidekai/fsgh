<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>字典</title>
<script type="text/javascript">
$(document).ready(function(){
	var act = '${act}';
	if(act=="ADD") act = "添加";
	else if(act=="EDIT") act = "编辑";
	else if(act=="VIEW"){
		act = "查看";
	}  
	$("span").first().html(act + "菜单信息");
	$("#span_userCde").hide();
	//确定按钮事件
	$("button").eq(0).click(function(){
		if(Validator.Validate(form1,3,3)){
			if('${act}' == "ADD"){
				var funcCde = $("#vo_funcCde").val();
				var span_funcCde = $("#span_funcCde");
				url = "<c:url value='/system/function/checkFunc.text'/>?vo.funcCde="+funcCde;
				$.post(url, null, function (rt){
					this;
					if(rt == '1'){
						span_funcCde.text('');
						span_funcCde.css("color", "red");
						span_funcCde.text('编码已存在,请重新输入');
						$("#span_funcCde").show();
					}else{
						form1.submit();					
					}
					},"text"
				);
			}else{
				form1.submit();
			}
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
						<input type="hidden" name="vo.id" value="${vo.funcCde }" /> <input
							type="hidden" name="key" value="${key }" />
						<h5>
							<span id="formTitle"></span>
						</h5>

						<div class="control-group mT30">
							<label class="control-label">功能代码</label>
							<div class="controls">
								<input name="vo.funcCde" id="vo_funcCde" value="${vo.funcCde }"
									type="text" <c:if test="${act!='ADD' }">readonly</c:if>
									maxLength="50" placeholder="请输入功能代码" dataType="Require"
									msg="请输入功能代码" /><span>*</span><span id="span_funcCde"></span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">功能名称</label>
							<div class="controls">
								<input name="vo.funcName" value="${vo.funcName }" type="text"
									maxLength="50" placeholder="请输入功能名称" dataType="Require"
									msg="请输入功能名称" /><span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">所属菜单</label>
							<div class="controls">
								<select name="vo.parent.funcCde">
									<option value="">请选择</option>
									<c:forEach items="${funcList }" var="func">
										<option value="${func.funcCde}"
											<c:if test="${vo.parent.funcCde == func.funcCde }">selected</c:if>>${func.funcCde}
											-- ${func.funcName}</option>
									</c:forEach>
								</select> <span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">排序</label>
							<div class="controls">
								<input type="text" name="vo.clevel" value="${vo.clevel }"
									maxlength="6" placeholder="请输入排序" dataType="Integer"
									msg="请输入正确的整数" /><span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">菜单类型</label>
							<div class="controls">
								<select name="vo.menuType" dataType="Require" msg="请选择菜单类型">
									<option value="<%=PublicType.FuncType.MENU %>"
										<c:if test="${vo.menuType=='MENU'}" >selected="selected"</c:if>><%=PublicType.FuncType.MENU.getText() %></option>
									<option value="<%=PublicType.FuncType.BUTTON %>"
										<c:if test="${vo.menuType=='BUTTON'}" >selected="selected"</c:if>><%=PublicType.FuncType.BUTTON.getText() %></option>
								</select> <span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">菜单级别</label>
							<div class="controls">
								<select name="vo.sort">
									<option value="1"
										<c:if test="${vo.sort=='1'}" >selected="selected"</c:if>>一级菜单</option>
									<option value="2"
										<c:if test="${vo.sort=='2'}" >selected="selected"</c:if>>二级菜单</option>
									<option value="3"
										<c:if test="${vo.sort=='3'}" >selected="selected"</c:if>>三级菜单</option>
								</select> <span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">状态</label>
							<div class="controls">
								<select class="select1" name="vo.state" dataType="Require"
									msg="请选择状态">
									<option value="USE"
										<c:if test="${vo.state=='USE'}" >selected="selected"</c:if>>启用</option>
									<option value="STOP"
										<c:if test="${vo.state=='STOP'}" >selected="selected"</c:if>>停用</option>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">链接</label>
							<div class="controls">
								<textarea name="vo.link" rows="2" class="span6">${vo.link }</textarea>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">权限</label>
							<div class="controls">
								<textarea name="vo.perLink" rows="5" class="span6">${vo.perLink }</textarea>
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