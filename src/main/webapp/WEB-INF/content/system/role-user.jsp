<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>角色用户配置</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#addRoleUser").click(function(){
		var url ="<c:url value='/system/role/getUser.jhtml'/>?id=${vo.id}&key=${vo.key}";
	    location.href=url;
	});
	$("body").css({  "background-color": "#ebf4fb" });
	$("#delRoleUser").click(function(){
		getCheckBox();
		if(ids == ''){
			alert("请选择您需要删除的用户");
			return;
		}
		if(!confirm("确定要删除选中的用户吗？")){
			return;
		}
		location.href="<c:url value='/system/role/deleteRoleUser.jhtml'/>?vo.id=${vo.id}&key=${vo.key}&idss="+ids;	
	});
});
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="pull-right mB10">
					<button class="btn" id="addRoleUser">
						<i class="icon-plus "></i>添 加
					</button>
					<button class="btn" id="delRoleUser">
						<i class="icon-trash"></i>删 除
					</button>
				</div>
				<display:table name="userList" id="vo"
					class="table table-bordered table-striped table-hover">
					<display:column media="html"
						title="<input type='checkbox' class='allchkBox' />"
						style="width:20px">
						<input type="checkbox" class="chkBox" name="cid"
							value="${vo.id},${vo.key}" />
					</display:column>
					<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
					<display:column title="角色名称" style="white-space:nowrap">${vo.role.roleName}</display:column>
					<display:column title="用户帐号" style="white-space:nowrap">${vo.user.userCode}</display:column>
					<display:column title="用户姓名" style="white-space:nowrap">${vo.user.userName}</display:column>
					<display:setProperty name="paging.banner.item_name">用户</display:setProperty>
					<display:setProperty name="paging.banner.items_name">用户</display:setProperty>
				</display:table>
			</div>
		</div>
		<!-- / span12 end -->
	</div>
	<!-- / row-fluid end -->
</body>
</html>