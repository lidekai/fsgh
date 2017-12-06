<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>系统配置表</title>

<script type="text/javascript">

function myReturnValue(){
	getCheckBox();
	if (ids == '') {
		alert('请选择一条数据');
		return;
	}
	if(ids.split(",").length>2){
		alert('只能选择一条数据');
		return;
	}
	var obj = getValue(ids);
	return obj;
}
function getValue(id){
	id = id.split(",")[0];
	var myobj = {};
	var t = $("#s"+id);
	
	myobj.id = id;
	myobj.name = t.attr("att-name");
	myobj.dictType = t.attr("att-dictType");
	myobj.clevel = t.attr("att-clevel");
	myobj.state = t.attr("att-state");
	return myobj;
}
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="get4dict.jhtml" class="form-inline"
						method="post">
						<ul class="row-fluid">
							<div class="span12">
								<li><label>名称：</label><input class="input-small"
									name="vo.dictName" value="${vo.dictName}" type="text"
									placeholder="名称" /></li>
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
					<display:table name="dictList" id="vo"
						class="table table-bordered table-striped table-hover"
						requestURI="get4dict.jhtml">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
							<div style="display: none">
								<span id="s${vo.id }" att-name="${vo.dictName }"
									att-dictType="${vo.dictType }" att-clevel="${vo.clevel}"
									att-state="${vo.state}"></span>
							</div>
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="名称" style="white-space:nowrap">${vo.dictName}</display:column>
						<display:column title="配置类型" style="white-space:nowrap">${vo.dictType.getText()}</display:column>
						<display:column title="级别" style="white-space:nowrap">${vo.clevel}</display:column>
						<display:column title="状态" style="white-space:nowrap">${vo.state.getText()}</display:column>
						<display:setProperty name="paging.banner.item_name">系统配置表</display:setProperty>
						<display:setProperty name="paging.banner.items_name">系统配置表</display:setProperty>
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