<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>日志管理</title>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="<c:url value='list.jhtml'/>"
						class="form-inline" method="post">
						<h5>
							<span>日志管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>用户帐号：</label><input class="input-medium"
									name="vo.userCde" value="${vo.userCde}" type="text"
									placeholder="输入需要搜索的用户帐号" /></li>
								<li><label>用户名称：</label> <input class="input-medium"
									name="vo.userName" value="${vo.userName}" type="text"
									placeholder="输入需要搜索的用户名称" /></li>
								<li><label>IP地址：</label> <input class="input-medium"
									name="vo.ipAddress" value="${vo.ipAddress}" type="text"
									placeholder="输入需要搜索的IP地址" /></li>
								<li><label>访问链接：</label> <input class="input-medium"
									name="vo.link" value="${vo.link}" type="text"
									placeholder="输入需要搜索的访问链接" /></li>
								<li><label>开始时间</label> <input type="text" class="input"
									name="vo.startTime"
									value="<fmt:formatDate value='${vo.startTime}' pattern='yyyy-MM-dd HH:mm:ss' />"
									style="width: 140px;" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									placeholder="请输入开始时间" /></li>
								<li><label>结束时间</label> <input type="text" class="input"
									name="vo.endTime"
									value="<fmt:formatDate value='${vo.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
									style="width: 140px;" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
									placeholder="请输入结束时间" /></li>

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
					<display:table name="pageList" id="vo"
						class="table table-bordered table-striped table-hover"
						requestURI="list.jhtml">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
						</display:column>
						<display:column title="用户帐号" style="white-space:nowrap">${vo.userCde}</display:column>
						<display:column title="用户名称" style="white-space:nowrap">${vo.userName}</display:column>
						<display:column title="IP地址" style="white-space:nowrap">${vo.ipAddress}</display:column>
						<display:column title="访问时间" style="white-space:nowrap">${vo.createTime}</display:column>
						<display:column title="访问链接" style="white-space:nowrap">${vo.link}</display:column>

						<display:setProperty name="paging.banner.item_name">日志管理</display:setProperty>
						<display:setProperty name="paging.banner.items_name">日志管理</display:setProperty>
					</display:table>

				</div>
				<!-- / span12 end -->
			</div>
			<!-- / row-fluid end -->

		</div>
	</div>
</body>
</html>