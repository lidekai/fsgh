<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>用户管理</title>
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
		location.href="edit.jhtml?vo.id="+id+"&key="+key;
	});
	
	$("button[name='editPwd']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="editPwd.jhtml?vo.id="+id+"&key="+key;
	});
	$("#expBtn").click(function(){
		var url = "<c:url value='/system/user/export.jhtml' />?";
		top.showView("选择导出字段",url,1000);
	});
});
	
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<form name="form1" action="list.jhtml" class="form-inline"
						method="post" id="form1">
						<h5>
							<span>用户管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>用户帐号：</label><input class="input-medium"
									name="vo.userCode" value="${vo.userCode }" type="text"
									placeholder="输入需要搜索的用户帐号" /></li>
								<li><label>用户名称：</label><input class="input-medium"
									name="vo.userName" value="${vo.userName }" type="text"
									placeholder="输入需要搜索的用户名称" /></li>
								<li><label>用户状态：</label> <select class="span80"
									name="vo.state">
										<option value="">请选择</option>
										<option value="USE"
											<c:if test="${vo.state=='USE'}" >selected="selected"</c:if>>启用</option>
										<option value="STOP"
											<c:if test="${vo.state=='STOP'}" >selected="selected"</c:if>>停用</option>
								</select></li>
								<li><label>所属区域：</label> <select class="span80"
									name="vo.areaId">
										<option value="">请选择</option>
										<c:forEach items="${areaList }" var="area">
											<option value="${area.id}"
												<c:if test="${vo.areaId == area.id }">"selected"</c:if>>${area.areaName}</option>
										</c:forEach>
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
						<pt:checkFunc code="SYS_USER_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus "></i>添 加
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="SYS_USER_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>
						<button class="btn" id="expBtn">
							<i class="icon-list-alt"></i>导 出
						</button>
					</div>

					<display:table name="pageList" id="vo"
						class="table table-bordered table-striped table-hover">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="用户帐号" style="white-space:nowrap">${vo.userCode}</display:column>
						<display:column title="用户姓名" style="white-space:nowrap">${vo.userName}</display:column>
						<display:column title="手机号码" style="white-space:nowrap">${vo.phone}</display:column>
						<display:column title="使用状态" style="white-space:nowrap">${vo.state.text}</display:column>
						<display:column title="用户角色" style="white-space:nowrap">
							<c:forEach var="var" items="${vo.roles}">
								<p style="text-align: center;">${var.roleName}</p>
							</c:forEach>
						</display:column>
						<display:column title="所属区域" style="white-space:nowrap">
							<c:forEach var="a" items="${vo.areas}">
								<p style="text-align: center;">${a.areaName}</p>
							</c:forEach>
						</display:column>
						<display:column title="操  作" style="white-space:nowrap">
							<pt:checkFunc code="SYS_USER_EDIT">
								<button name="edit" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-edit"></i>编辑
								</button>
								<button name="editPwd" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-edit"></i>修改密码
								</button>
							</pt:checkFunc>
						</display:column>
						<display:setProperty name="paging.banner.item_name">用户</display:setProperty>
						<display:setProperty name="paging.banner.items_name">用户</display:setProperty>
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