<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>客户信息管理</title>

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
	var obj = getValue(ids,keys);
	return obj;
}
function getValue(ids,keys){
	cexch = ids.split(",")[0];
	nflat = keys.split(",")[0];
	var myobj = {};
	
	myobj.cexch = cexch;
	myobj.nflat = nflat;
	return myobj;
}
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<display:table name="cexchList" id="c" class="table table-bordered table-striped table-hover">
						<display:column media="html" title="<input type='checkbox' class='allchkBox' />" style="width:20px">
							<input type="checkbox" class="chkBox" name="cid" value="${c[0]},${c[1]}" />
						</display:column>
						<display:column title="币别名称" style="white-space:nowrap">${c[0]}</display:column>
						<display:column title="汇率" style="white-space:nowrap">${c[1]}</display:column>
						<display:setProperty name="paging.banner.item_name">币种</display:setProperty>
						<display:setProperty name="paging.banner.items_name">币种</display:setProperty>
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