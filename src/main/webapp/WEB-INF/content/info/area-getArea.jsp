<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>地区管理</title>

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
	return myobj;
}
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="getArea.jhtml" class="form-inline"
						method="post">
						<ul class="row-fluid">
							<div class="span12">
								<li><label>地区名称：</label> <input name="vo.areaName"
									value="${vo.areaName }" type="text" placeholder="地区名称" /></li>
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
					<display:table name="areaList" id="vo"
						class="table table-bordered table-striped table-hover"
						requestURI="getArea.jhtml">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
							<div style="display: none">
								<span id="s${vo.id }" att-name="${vo.areaName}" /></span>
							</div>
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="所属大区" style="white-space:nowrap">${vo.parentArea.areaName}</display:column>
						<display:column title="地区名称" style="white-space:nowrap">${vo.areaName}</display:column>
						<display:column title="级别" style="white-space:nowrap">${vo.lev}</display:column>
						<display:setProperty name="paging.banner.item_name">地区</display:setProperty>
						<display:setProperty name="paging.banner.items_name">地区</display:setProperty>
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