<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.*"%>
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
	var obj = getValue(ids);
	return obj;
}
function getValue(id){
	id = id.split(",")[0];
	var myobj = {};
	var t = $("#s"+id);
	
	myobj.id = id;
	myobj.provisionCode = t.attr("att-name");
	myobj.projectType = t.attr("att-type");
	myobj.customId = t.attr("att-customId");
	myobj.customName = t.attr("att-customName");
	myobj.projectName = t.attr("att-projectName");
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
								<li><label>预提编码：</label> <input class="input-medium"
									name="vo.provisionCode" value="${vo.provisionCode}" type="text"
									placeholder="输入需要搜索的编码" /></li>
								<li><label>所属项目：</label> <input class="input-medium"
									name="vo.provisionProject.feeName"
									value="${vo.provisionProject.feeName}" type="text"
									placeholder="输入需要搜索的项目名称" /></li>
								<li><label>制单日期</label> <input type="text"
									class="input-medium Wdate" name="vo.dateStart"
									value="${vo.dateStart}" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 至
									<input type="text"
									class="input-medium Wdate" name="vo.dateEnd"
									value="${vo.dateEnd}" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></li>	
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
						requestURI="getProject.jhtml">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
							<div style="display: none">
								<span id="s${vo.id }" att-name="${vo.provisionCode}"
									att-type="${vo.provisionProject.projectType}"
									att-customId="${vo.custom.id}"
									att-customName="${vo.custom.customName }"
									att-projectName="${vo.provisionProject.feeName }" /></span>
							</div>
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="预提编码" style="white-space:nowrap">${vo.provisionCode}</display:column>
						<display:column title="项目名称" style="white-space:nowrap">${vo.provisionProject.feeName}</display:column>
						<display:column title="所属客户" style="white-space:nowrap">${vo.custom.customName}</display:column>
						<display:column title="总费用" style="white-space:nowrap">${vo.totalFee}</display:column>
						<display:column title="预提日期" style="white-space:nowrap">
							<fmt:formatDate value="${vo.createTime}" pattern="yyyy-MM" />
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