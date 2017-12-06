<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>角色管理</title>

<script type="text/javascript">
$(document).ready(function(){
	$("#addBtn").click(function(){
		location.href="edit.jhtml";	
	});
	
	$("#delBtn").click(function(){
		dels();	
	});
	
	$("#pubBtn").click(function(){
		location.href = "<c:url value='/system/role/reloadPubPerm.jhtml'/>";
	});

	$("#roleBtn").click(function(){
		location.href = "<c:url value='/system/role/reloadPerm.jhtml'/>";
	});
	
	$("button[name='edit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.id="+id+"&key="+key;
	});
	
	$("button[name='roleUser']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");
		var url ="<c:url value='/system/role/editUser.jhtml'/>?vo.id="+id+"&key="+key;
		top.showView("角色用户配置",url,900);
	});
	$("button[name='rolePerm']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");
		location.href="editPerm.jhtml?vo.id="+id+"&key="+key;
	});
});


</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="list.jhtml" class="form-inline"
						method="post" id="form1">
						<h5>
							<span>角色管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>角色名称：</label><input class="input-medium"
									name="vo.roleName" value="${vo.roleName }" type="text"
									placeholder="输入需要搜索的角色名称" /></li>
								<li><label>状态：</label> <select class="span80"
									name="vo.state">
										<option value="">请选择</option>
										<option value="USE"
											<c:if test="${vo.state=='USE'}" >selected="selected"</c:if>>启用</option>
										<option value="STOP"
											<c:if test="${vo.state=='STOP'}" >selected="selected"</c:if>>停用</option>
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
				<div class="span12">
					<div class="pull-right  mB10">
						<pt:checkFunc code="SYS_ROLE_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus "></i>添 加
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="SYS_ROLE_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="SYS_ROLE_UP">
							<button class="btn" id="pubBtn">
								<i class="icon-refresh"></i>生成公共角色权限
							</button>
							<button class="btn" id="roleBtn">
								<i class="icon-refresh"></i>生成所有角色权限
							</button>
						</pt:checkFunc>
					</div>

					<display:table name="pageList" id="vo"
						class="table table-bordered table-striped table-hover">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
						</display:column>
						<display:column title="角色ID" style="white-space:nowrap">${vo.id}</display:column>
						<display:column title="角色名称" style="white-space:nowrap">${vo.roleName}</display:column>
						<display:column title="备注" maxLength="15">${vo.remark}</display:column>
						<display:column title="使用状态" style="white-space:nowrap">${vo.state.text}</display:column>

						<display:column title="操  作" style="white-space:nowrap">
							<pt:checkFunc code="SYS_ROLE_EDIT">
								<button name="edit" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-edit"></i>编辑
								</button>
							</pt:checkFunc>
							<pt:checkFunc code="SYS_ROLE_USER">
								<button name="roleUser" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-user"></i>配置用户
								</button>
							</pt:checkFunc>
							<pt:checkFunc code="SYS_ROLE_QX">
								<button name="rolePerm" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-filter"></i>配置权限
								</button>
							</pt:checkFunc>
						</display:column>
						<display:setProperty name="paging.banner.item_name">角色</display:setProperty>
						<display:setProperty name="paging.banner.items_name">角色</display:setProperty>
					</display:table>

				</div>
				<!-- / span12 end -->
			</div>
			<!-- / row-fluid end -->

		</div>
	</div>
</body>
</html>