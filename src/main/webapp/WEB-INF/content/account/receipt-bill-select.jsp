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
	var obj = getValue(ids);
	return obj;
}
function getValue(id){
	id = id.split(",")[0];
	var myobj = {};
	var t = $("#s"+id);
	
	myobj.id = id;
	myobj.cvouchId = t.attr("att-cvouchId");
	myobj.receiptDate = t.attr("att-receiptDate");
	myobj.customerName = t.attr("att-customerName");
	myobj.cvouchType = t.attr("att-cvouchType");
	myobj.cssName = t.attr("att-cssName");
	myobj.receiptCount1 = t.attr("att-receiptCount1");
	myobj.receiveCount = t.attr("att-receiveCount");
	return myobj;
}
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="selectReceiptBill.jhtml" class="form-inline" method="post">
					<input type="hidden" name="vo.customerCde" value="${vo.customerCde }"/>
						<ul class="row-fluid">
							<div class="span12">
								<li><label>收款单号：</label><input id="cvouchId" class="input-medium" name="vo.cvouchId" value="${vo.cvouchId}" type="text" placeholder="输入单号" /></li>
					 	        <li>
					       			<label>收款日期：</label>
					       			<input type="text" class="input-medium Wdate" name="vo.beginTime" value="${vo.beginTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
					       			<input type="text" class="input-medium Wdate" name="vo.endTime" value="${vo.endTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
					       		</li>
								<li><label><button name="searchBtn" class="btn btn-primary" type="submit">搜 索</button></label></li>
							</div>
						</ul>
					</form>
				</div>
				<!-- / span12 end-->
			</div>
			<!-- / row-fluid end-->
			<div class="row-fluid">
				<div class="span12">
					<display:table name="pageList" id="vo" class="table table-bordered table-striped table-hover" requestURI="selectReceiptBill.jhtml">
						<display:column media="html" title="<input type='checkbox' class='allchkBox' />" style="width:20px">
							<input type="checkbox" class="chkBox" name="cid" value="${vo.id},${vo.key}" />
							<div style="display: none">
								<span id="s${vo.id }" att-cvouchId="${vo.cvouchId}" att-receiptDate="<fmt:formatDate value='${vo.receiptDate }' pattern='yyyy-MM-dd'/>" 
								att-customerName="${vo.customerName}" att-cvouchType="${vo.cvouchType == 48 ? '收': (vo.cvouchType == 49 ? '付' : '')}"
								att-cssName="${vo.cssName }" att-receiptCount1="${vo.receiptCount1}" att-receiveCount="${vo.receiveCount}"/></span>
							</div>
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="收款单号" style="white-space:nowrap">${vo.cvouchId}</display:column>
						<display:column title="收款日期" style="white-space:nowrap"><fmt:formatDate value="${vo.receiptDate }" pattern="yyyy-MM-dd"/></display:column>
						<display:column title="客户名称" style="white-space:nowrap">${vo.customerName}</display:column>
						<display:column title="类别" style="white-space:nowrap">${vo.cvouchType == 48 ? '收': (vo.cvouchType == 49 ? '付' : '')}</display:column>
						<display:column title="结算名称" style="white-space:nowrap">${vo.cssName }</display:column>
						<display:column title="本币实际收款金额" style="white-space:nowrap"><fmt:formatNumber value="${vo.receiptCount1}" type="currency" /></display:column>
						<display:column title="本币未核余额" style="white-space:nowrap"><fmt:formatNumber value="${vo.receiveCount}" type="currency" /></display:column>
						<display:setProperty name="paging.banner.item_name">收款单</display:setProperty>
						<display:setProperty name="paging.banner.items_name">收款单</display:setProperty>
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