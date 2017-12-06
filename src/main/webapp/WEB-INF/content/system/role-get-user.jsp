<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>用户选择</title>
<script type="text/javascript">
$(document).ready(function(){
	$("button[name='searchBtn']").click(function(){
		form1.submit();
	});
	$("button[name='submitBtn']").click(function(){
		getCheckBox();
		if(ids == ''){
			alert("请选择您需要添加的用户");
			return;
		}
		$("#idss").val(ids);
		form2.submit();
	});
});
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12 mT_30">
					<form name="form1" action="getUser.jhtml" class="form-inline"
						method="post">
						<input type="hidden" name="vo.id" value="${id }" /> <input
							type="hidden" name="key" value="${key }" />
						<h5>
							<span>选择需要添加的用户，并点击提交按钮提交数据</span>
						</h5>
						<ul class="row-fluid">
							<li><label>用户帐号：</label><input class="input-small"
								name="userVO.userCode" value="${userVO.userCode }" type="text"
								placeholder="用户帐号" /></li>
							<li><label>用户名称：</label><input class="input-small"
								name="userVO.userName" value="${userVO.userName }" type="text"
								placeholder="用户名称" /></li>
							<li><label><button name="searchBtn" class="btn"
										type="button">搜 索</button></label>
								<li><label><button name="submitBtn"
											class="btn btn-warning" type="button">提 交</button></label></li>
						</ul>
					</form>
				</div>
				<!-- / span12 end-->
			</div>
			<!-- / row-fluid end-->
			<div class="row-fluid">
				<div class="span12">
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
	<form name="form2" action="updateRoleUser.jhtml" class="form-inline"
		method="post">
		<input type="hidden" name="vo.id" value="${id }" /> <input
			type="hidden" name="key" value="${key }" /> <input type="hidden"
			id="idss" name="idss" value="" />
	</form>
</body>
</html>