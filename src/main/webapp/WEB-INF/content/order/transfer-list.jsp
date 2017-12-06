<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>仓库调拨单管理</title>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/demo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/jquery.fixedheadertable.js'/>"></script>
<link href="<c:url value='/css/fixedHeader/960.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/defaultTheme.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/myTheme.css'/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function(){
	
	$('#result').fixedHeaderTable({
		altClass: 'odd',
		footer: true
	});
	
	$("#delBtn").click(function(){
		dels();	
	});
	
	$("#impBtn").click(function(){
		if(confirm("确定导入U8仓库调拨单？")){
			$("#impBtn").attr("disabled",true);
			$("#form1").attr("action","imp.jhtml");
			$("#form1").submit();
		}
	});
	
	$("button[name='view']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.id="+id+"&key="+key + "&act=VIEW";
	});
	

	//导出按钮事件
	$("#expBtn").click(function(){
		var transCode=$("#transCode").val();
		var stockName=$("#stockName").val();
		var inWhouseName=$("#inWhouseName").val();
		var outWhouseName=$("#outWhouseName").val();
		var start = $("#orderDateStart").val();
		var end = $("#orderDateEnd").val();
		var url="<c:url value='/order/transfer/exportTransfer.jhtml'/>";
		url+="?vo.transCode=" + transCode +"&vo.stockName=" + stockName + "&vo.inWhouseName="+ inWhouseName;	
		url+="&vo.outWhouseName="+ outWhouseName +"&vo.transBeginTime="+ start + "&vo.transEndTime="+ end;		
		top.showView("请选择导出字段", url , 900);
	});
	
	
	
});

function checkAll(flag){
	//遍历当前选中复选框的table
	$('.chkBox').each(function() {   //勾选、取消后的行背景色
		
		this.checked = flag;

		if($(this).is(":checked")){
			$(this).parents("tr").addClass("tr-checked");
		}else{
			$(this).parents("tr").removeClass("tr-checked");
		}
	});
}
	
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<form name="form1" action="list.jhtml" class="form-inline" method="post" id="form1">
					<input type="hidden" name="ids" value="" id="ids"/>
    				<input type="hidden" name="keys" value="" id="keys"/>
						<h5>
							<span>仓库调拨单管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>调拨单号：</label><input id="transCode"
									class="input-medium" name="vo.transCode"
									value="${vo.transCode}" type="text" placeholder="输入调拨单号" /></li>
								<li><label>存货名称：</label> <input id="stockName"
									class="input-medium" name="vo.stockName"
									value="${vo.stockName}" type="text" placeholder="输入需要搜索的存货名称" />
								</li>
								<li><label>调入仓库名称：</label><input id="inWhouseName"
									class="input-medium" name="vo.inWhouseName"
									value="${vo.inWhouseName}" type="text" placeholder="输入仓库名称" /></li>
								<li><label>调出仓库名称：</label><input id="outWhouseName"
									class="input-medium" name="vo.outWhouseName"
									value="${vo.outWhouseName}" type="text" placeholder="输入仓库名称" /></li>
								<li><label>调拨日期：</label> <input type="text"
									class="input-medium Wdate" name="vo.transBeginTime"
									value="${vo.transBeginTime}" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
									id="orderDateStart" />至 <input type="text"
									class="input-medium Wdate" name="vo.transEndTime"
									value="${vo.transEndTime}" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd" />

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
				<div class="span12" style="min-width: 1000px;">
					<div class="pull-right  mB10">
						<button class="btn" onclick="checkAll(true)">
							<i class="icon-ok"></i>全选
						</button>
						<button class="btn" onclick="checkAll(false)">
							<i class="icon-remove"></i>全不选
						</button>
						<button class="btn" id="expBtn">
							<i class="icon-list-alt"></i>导出
						</button>
						<pt:checkFunc code="ORDER_TRAN_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="ORDER_TRAN_IMP">
							<button class="btn" id="impBtn">
								<i class="icon-list-alt"></i>导入U8仓库调拨单
							</button>
						</pt:checkFunc>
					</div>
				</div>
				<!-- / span12 end -->
			</div>
			<!-- / row-fluid end -->
		</div>
		<!--container-fluid end-->
		<div class="container_12 divider">
 			<div class="grid_4 height400" id="page1">
	 			<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th style="width: 20px"></th>
								<th style="width: 20px">序号</th>
								<th style="width: 30px">调拨单号</th>
								<th style="width: 60px">调拨日期</th>
								<th style="width: 60px">调出仓库名称</th>
								<th style="width: 120px">调入仓库名称</th>
								<th style="width: 120px">存货名称</th>
								<th style="width: 30px">件数</th>
								<th style="width: 70px">运费</th>
								<th style="width: 70px">配送费</th>
								<th style="width: 70px">运费合计</th>
								<th style="width: 120px">操作</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<td colspan="10">合计,共${fn:length(pageList.list)}条记录</td>
								<td>${sum}</td>
								<td></td>
							</tr>
						</tfoot>
						<tbody>
							<c:forEach items="${pageList.list}" var="vo" varStatus="s">
								<tr>
									<td><input class="chkBox" name="cid"
										value="${vo.id},${vo.key}" type="checkbox"></td>
									<td>${s.index + 1}</td>
									<td>${vo.transCode}</td>
									<td><fmt:formatDate value="${vo.transDate }"
											pattern="yyyy-MM-dd" /></td>
									<td>${vo.outWhouseName}</td>
									<td>${vo.inWhouseName}</td>
									<td>${vo.stockName}</td>
									<td>${vo.count}</td>
									<td><fmt:formatNumber value="${vo.freight}" type="currency" /></td>
									<td><fmt:formatNumber value="${vo.logDeliverFee}" type="currency" /></td>
									<td><fmt:formatNumber value="${vo.freightTotal}" type="currency" /></td>
									<td>
										<button name="view" class="btn btn-small btn-info"
											att-id="${vo.id }" att-key="${vo.key }">
											<i class="icon-eye-open"></i>查看
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
					</div>
		
	</div>
	<!--jt-container end-->
</body>
</html>