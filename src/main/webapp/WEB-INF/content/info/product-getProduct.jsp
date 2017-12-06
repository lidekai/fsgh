<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>客户产品管理</title>

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
	myobj.stockCde= t.attr("att-stockCde");
	myobj.number = t.attr("att-number");
	myobj.productType = t.attr("att-productType");
	myobj.standard = t.attr("att-standard");
	return myobj;
}
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="" class="form-inline" method="post">
						<ul class="row-fluid">
							<div class="span12"></div>
						</ul>
					</form>
				</div>
				<!-- / span12 end-->
			</div>
			<!-- / row-fluid end-->
			<div class="row-fluid">
				<div class="span12">
					<display:table name="productList" id="vo"
						class="table table-bordered table-striped table-hover"
						requestURI="getProduct.jhtml">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
							<div style="display: none">
								<span id="s${vo.id }" att-name="${vo.productName}"
									att-stockCde="${vo.stockCde}" att-number="${vo.number}"
									att-productType="${vo.productType.productTypeName}"
									att-standard="${vo.standard}" /></span>
							</div>
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="产品名称" style="white-space:nowrap">${vo.productName}</display:column>
						<display:column title="产品编码" style="white-space:nowrap">${vo.stockCde}</display:column>
						<display:column title="货号" style="white-space:nowrap">${vo.number}</display:column>
						<display:column title="产品类型" style="white-space:nowrap">${vo.productType.productTypeName}</display:column>
						<display:setProperty name="paging.banner.item_name">客户产品</display:setProperty>
						<display:setProperty name="paging.banner.items_name">客户产品</display:setProperty>
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