<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>合同内费用预提</title>

<script type="text/javascript">

function myReturnValue(){
	getCheckBox();
	if (ids == '') {
		alert('请选择至少一条数据');
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
	myobj.customId = t.attr("att-customId");
	myobj.customName = t.attr("att-customName");
	myobj.customType = t.attr("att-customType");
	return myobj;
}
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="getProvision.jhtml" class="form-inline"
						method="post">
						<ul class="row-fluid">
							<div class="span12">
								<li><label>预提编号：</label> <input name="vo.code"
									value="${vo.code }" type="text" placeholder="预提编号" /></li>
								<li><label>预提日期：</label> <input type="text"
									class="input-medium Wdate" name="vo.createTimeStart"
									value="${vo.createTimeStart}" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="createTimeStart" />至
									<input type="text" class="input-medium Wdate"
									name="vo.createTimeEnd" value="${vo.createTimeEnd}"
									readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
									id="createTimeEnd" />
								</li>
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
						requestURI="getProvision.jhtml">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
							<div style="display: none">
								<span id="s${vo.id }" att-name="${vo.code}"
									att-customId="${vo.custom.id}"
									att-customName="${vo.custom.customName }" att-customType="${vo.custom.customType.customTypeName}"/></span>
							</div>
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="预提编码" style="white-space:nowrap">${vo.code}</display:column>
						<display:column title="所属客户" style="white-space:nowrap">${vo.custom.customName}</display:column>
						<display:column title="客户类型" style="white-space:nowrap">${vo.custom.customType.customTypeName}</display:column>
						<display:column title="总费用" style="white-space:nowrap">${vo.inFeeCount}</display:column>
						<display:column title="预提日期" style="white-space:nowrap">
							<fmt:formatDate value="${vo.provisionTime}" pattern="yyyy-MM" />
						</display:column>
						<display:setProperty name="paging.banner.item_name">预提</display:setProperty>
						<display:setProperty name="paging.banner.items_name">预提</display:setProperty>
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