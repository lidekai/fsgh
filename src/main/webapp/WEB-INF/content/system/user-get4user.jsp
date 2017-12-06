<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>用户</title>

<script type="text/javascript">

function myReturnValue(){
	getCheckBox();
	if (ids == '') {
		alert('请选择一条或多条数据');
		return;
	}
	var obj = getValue(ids);
	return obj;
}
function getValue(id){
	var ids = id.split(",");
	var myobj = new Array();
	for(var i = 0 ; i < ids.length-1 ; i++){
		var t = $("#s"+ids[i]);
		myobj[i] = ""+ids[i] + "," +t.attr("att-name");
	}
	return myobj;
}
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid" style="background-color: #ebf4fb;">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="get4user.jhtml" class="form-inline"
						method="post">
						<ul class="row-fluid">
							<div class="span12">
								<li><label>用户名称：</label><input class="input-small"
									name="vo.userName" value="${vo.userName}" type="text"
									placeholder="用户名称" /></li>
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
					<display:table name="userList" id="vo"
						class="table table-bordered table-striped table-hover"
						requestURI="get4user.jhtml">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
							<div style="display: none">
								<span id="s${vo.id }" att-name="${vo.userName}" /></span>
							</div>
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="用户帐号" style="white-space:nowrap">${vo.userCode}</display:column>
						<display:column title="用户名称" style="white-space:nowrap">${vo.userName}</display:column>
						<display:setProperty name="paging.banner.item_name">管理员</display:setProperty>
						<display:setProperty name="paging.banner.items_name">管理员</display:setProperty>
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