<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>字典管理</title>
<script type="text/javascript">

$(document).ready(function(){
	$("#addBtn").click(function(){
		location.href="edit.jhtml";	
	});
	
	$("#delBtn").click(function(){
		dels();	
	});
	
	$("button[name='edit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.funcCde="+id+"&key="+key;
	});
	$("#permBtn").click(function(){
		
	 location.href="functionTree.jhtml";
	});
	
});
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<form name="form1" action="list.jhtml" class="form-inline" id="form1"
						method="post">
						<h5>
							<span>菜单管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>菜单代码：</label><input class="input-small"
									name="vo.funcCde" value="${vo.funcCde }" type="text"
									placeholder="搜索菜单代码" /></li>
								<li><label>菜单名称：</label><input class="input-small"
									name="vo.funcName" value="${vo.funcName }" type="text"
									placeholder="搜索菜单名称" /></li>
								<li><label>功能类型：</label> <select name="vo.menuType"
									class="span80">
										<option value="">请选择</option>
										<option value="<%=PublicType.FuncType.MENU %>"
											<c:if test="${vo.menuType=='MENU'}" >selected="selected"</c:if>><%=PublicType.FuncType.MENU.getText() %></option>
										<option value="<%=PublicType.FuncType.BUTTON %>"
											<c:if test="${vo.menuType=='BUTTON'}" >selected="selected"</c:if>><%=PublicType.FuncType.BUTTON.getText() %></option>
								</select></li>
								<li><label>状态：</label> <select class="span80"
									name="vo.state">
										<option value="">请选择</option>
										<option value="<%=PublicType.StateType.USE %>"
											<c:if test="${vo.state=='USE'}" >selected="selected"</c:if>><%=PublicType.StateType.USE.getText() %></option>
										<option value="<%=PublicType.StateType.STOP %>"
											<c:if test="${vo.state=='STOP'}" >selected="selected"</c:if>><%=PublicType.StateType.STOP.getText() %></option>
								</select></li>
							</div>
							<div class="span2">
								<li><label><button name="searchBtn"
											class="btn btn-primary" type="submit">搜 索</button></label></li>
							</div>
						</ul>
					</form>
				</div>
				<!-- / span12 end-->
			</div>
			<!-- / row-fluid end-->
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<div class="pull-right  mB10">
						<button class="btn" id="permBtn">
							<i class="icon-refresh"></i>菜单权限目录查看
						</button>
						<pt:checkFunc code="SYS_FUNC_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus "></i>添 加
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="SYS_FUNC_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>

					</div>

					<display:table name="funcList" id="vo"
						class="table table-bordered table-striped table-hover">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.funcCde},${vo.key}" />
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${vo_rowNum}</display:column>
						<display:column title="功能代码"
							style="white-space:nowrap;text-align:left;">${vo.funcCde}</display:column>
						<display:column title="功能名称">${vo.funcName}</display:column>
						<display:column title="所属功能">${vo.parent.funcName}</display:column>
						<display:column title="级别"
							style="white-space:nowrap;text-align:left;">${vo.clevel}</display:column>
						<display:column title="状态" style="white-space:nowrap">${vo.state.text}</display:column>
						<display:column title="类别" style="white-space:nowrap">${vo.menuType.text}</display:column>
						<display:column title="链接" maxLength="20">${vo.link}</display:column>
						<display:column title="权限" maxLength="20">${vo.perLink}</display:column>
						<display:column title="操  作" style="white-space:nowrap">
							<pt:checkFunc code="SYS_FUNC_EDIT">
								<button name="edit" class="btn btn-small btn-success"
									att-id="${vo.funcCde }" att-key="${vo.key }">
									<i class="icon-edit"></i>编辑
								</button>
							</pt:checkFunc>
						</display:column>
						<display:setProperty name="paging.banner.item_name">菜单</display:setProperty>
						<display:setProperty name="paging.banner.items_name">菜单</display:setProperty>
					</display:table>

				</div>
				<!-- / span12 end -->
			</div>
			<!-- / row-fluid end -->

		</div>
		<!--container-fluid end-->
	</div>
	<!--jt-container end-->
</body>
</html>