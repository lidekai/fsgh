<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>待核单管理</title>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/demo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/jquery.fixedheadertable.js'/>"></script>
<link href="<c:url value='/css/fixedHeader/960.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/defaultTheme.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/myTheme.css'/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var btn;
$(document).ready(function(){
	
	$('#result').fixedHeaderTable({
		altClass: 'odd',
		//fixedColumns:3,
		footer: true
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
		$("#form1").attr("action","list.jhtml");
		$("#form1").submit();
	});
	
	$("button[name='hx'],button[name='view']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");	
		$("#receiveBillVO_id").val(id);
		
		$("#form1").attr("action","hxReceive.jhtml");
		$("#form1").submit();
	});
	
});

function checkAll(flag){
	//遍历当前选中复选框的table
	$('.chkBox').each(function() {   //勾选、取消后的行背景色		
		this.checked = flag;		
	});
	getCheckBox();
	//alert(ids);
	var customCde = "${vo.customCde}";
	var url = "<c:url value='/account/receipt-bill/selectReceiptBill.jhtml'/>?vo.customerCde=" + customCde;
	top.showMyModal("选择客户",url,900,true);
}

function setReturnValue(obj){
	if(obj == "") return;
	//alert(ids);
	//alert("收款单号："+obj.cvouchId+"   id："+obj.id);
	var idss=ids.substring(0,ids.length-1);
	location.href="<c:url value='/account/receive-bill/autoHx.jhtml'/>?receiveIds="+idss+"&receiptIds="+obj.id+"&act=XZHX";

}


</script>
<style type="text/css">
.myWidth{
	float: left;
	width: 100px;
	padding-top: 5px;
	text-align: right
}
.myMargin{
margin-left: 110px;
}
.myInput{
	width: 140px;
}
.mySpan{
	padding-left: 0px;
	font-size:12px;
	color:black;
}
</style>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="update.jhtml" class="form-horizontal" method="post" id="form1">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" />
						<input type="hidden" name="vo.beginTime" value="${vo.beginTime}"/>
						<input type="hidden" name="vo.endTime" value="${vo.endTime}"/>
						<input type="hidden" name="receiveBillVO.id" id="receiveBillVO_id" value="" />
						<h5>
							<span id="formTitle">核销待核单</span>
						</h5>

						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">单号</label>
									<div class="myMargin">
										<input value="${vo.code}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">制单日期</label>
									<div class="myMargin">
										<input class="myInput Wdate"  
											value="<fmt:formatDate value="${vo.createDate}" pattern="yyyy-MM-dd" />"
											type="text" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">客户类型</label>
									<div class="myMargin">
										<input value="${vo.customType}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">客户地区</label>
									<div class="myMargin">
										<input value="${vo.customArea}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">客户编号</label>
									<div class="myMargin">
										<input value="${vo.customCde}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">客户名称</label>
									<div class="myMargin">
										<input value="${vo.customName}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">本币发票额</label>
									<div class="myMargin">
										<input value="<fmt:formatNumber value='${vo.countPrice}' type='currency'/>" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">收款数额</label>
									<div class="myMargin">
										<input value="<fmt:formatNumber value='${vo.receivePrice}' type='currency'/>" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">待费用发票额</label>
									<div class="myMargin">
										<input value="<fmt:formatNumber value='${vo.chargePrice}' type='currency'/>" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">待退货额</label>
									<div class="myMargin">
										<input value="<fmt:formatNumber value='${vo.returnPrice}' type='currency'/>" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">暂扣额</label>
									<div class="myMargin">
										<input value="<fmt:formatNumber value='${vo.holdPrice}' type='currency'/>" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">其他余额</label>
									<div class="myMargin">
										<input  value="<fmt:formatNumber value='${vo.otherPrice}' type='currency'/>" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">状态</label>
									<div class="myMargin">
										<input value="${vo.state.text}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<h5><span>待核单明细</span><button style="margin-left: 30px" id="HxAll" onclick="checkAll(true)" class="btn btn-success" type="button">自动核销</button></h5>
						
					
						<div class="container_12 divider">
 							<div class="grid_4 height400" id="page1">
	 							<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
									<thead>
										<tr> 
											<th style="min-width:20px"></th>
											<th style="min-width:20px">序号</th>
											<th style="min-width:20px">销售发票号</th>
											<th style="min-width:30px">凭证号</th>
											<th style="min-width:30px">发票日期</th>
											<th style="min-width:30px">到期日期</th>
											<th style="min-width:140px">客户名称</th>
											
											<th style="min-width:50px">本币实际发票额</th>
											<th style="min-width:50px">已收款合计</th>
											<th style="min-width:50px">待核销额</th>
											<th style="min-width:50px">实际收款额</th>
											<th style="min-width:50px">待费用发票额</th>
											<th style="min-width:50px">待退货额</th>
											<th style="min-width:50px">暂扣额</th>
											<th style="min-width:50px">其他余额</th>
											<th style="min-width:30px">状态</th>
											<th style="min-width:80px">操作</th>
										</tr>
									</thead>
									<tfoot>
										<tr id="tfoot">
											<td colspan="7">合计，共${fn:length(vo.receiveList)}条记录</td>
											<td><fmt:formatNumber value='${vo.countPrice}' pattern='#.##'/></td>
											<td><fmt:formatNumber value='${vo.receivePrice}' pattern='#.##'/></td>
											<td><fmt:formatNumber value='${vo.countPrice-vo.receivePrice}' pattern='#.##'/></td>
											<td>${vo.actualPrice}</td>
											<td>${vo.chargePrice}</td>
											<td>${vo.returnPrice}</td>
											<td>${vo.holdPrice}</td>
											<td>${vo.otherPrice}</td>
											<td></td>
											<td></td>
										</tr>
									</tfoot>
									<tbody id="tbody">
										<c:forEach items="${vo.receiveList }" var="re" varStatus="s">
											<tr>
												<td>
													<input class="chkBox" name="cid" value="${re.id},${re.key}" type="checkbox"/>
												</td>
												<td>${s.index + 1}</td>
												<td>${re.csbvcode}</td>
												<td>${re.number}</td>
												<td><fmt:formatDate value="${re.billDate }" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${re.maturityDate }" pattern="yyyy-MM-dd"/></td>
												<td>${re.customName}</td>
												
												<td><fmt:formatNumber value="${re.countPrice1 }" type="currency"/></td>
												<td><fmt:formatNumber value="${re.receivePrice }" type="currency"/></td>
												<td><fmt:formatNumber value="${re.countPrice1-re.receivePrice }" type="currency"/></td>
												<td><fmt:formatNumber value="${re.actualPrice }" type="currency"/></td>
												<td><fmt:formatNumber value="${re.chargePrice }" type="currency"/></td>
												<td><fmt:formatNumber value="${re.returnPrice }" type="currency"/></td>
												<td><fmt:formatNumber value="${re.holdPrice }" type="currency"/></td>
												<td><fmt:formatNumber value="${re.otherPrice }" type="currency"/></td>
												<td>${re.state.text}</td>
												<td>
													<c:if test="${re.state != 'DHX' }">
														<button name="view" class="btn btn-small btn-info" att-id="${re.id }" att-key="${re.key }"><i class="icon-eye-open"></i>查看</button>
													</c:if>
													<c:if test="${re.state == 'DHX' }">
														<button name="hx" class="btn btn-small btn-success" att-id="${re.id}" att-key="${re.key}"><i class="icon-edit"></i>核销</button>
														<%-- <button name="edit" class="btn btn-small btn-success" att-id="${re.id}" att-key="${re.key}"><i class="icon-edit"></i>编辑</button> --%>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
					    </div>
							
						<div class="row-fluid">
							<div class="span12">
								<div class="control-group mT30">
									<div class="controls" style="margin: 0 auto; text-align: center;">
										<button type="button" id="cancelBtn" class="btn btn-primary">返回</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>