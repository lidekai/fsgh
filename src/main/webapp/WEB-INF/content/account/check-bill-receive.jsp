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
$(document).ready(function(){
	
	$('#result').fixedHeaderTable({
		altClass: 'odd'
	});
	
	$('#result1').fixedHeaderTable({
		altClass: 'odd'
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
		$("#form1").attr("action","hx.jhtml");
		$("#form1").submit();
	});
	
	//取消核销记录
	$("button[name='cancelRecord']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");	
		$("#checkRecordVO_id").val(id);
		
		$("#form1").attr("action","qxRecord.jhtml");
		$("#form1").submit();
	});
	
	//确定按钮事件saveBtn
	$("#saveBtn").click(function(){
		//收款单id判断
		if($("#receiptBillVO_id").val() == ""){
			alert("请选择收款单");
			return false;
		}else{
			//判断本次核销收款额是否大于收款单未核余额
			var brp = $("#b_rp").val();
			var receiveCount = $("#receiveCount").val();
			if(parseFloat(brp) > parseFloat(receiveCount)){
				alert("本次核销收款额(" + brp + ")不可以大于收款单未核余额(" + receiveCount + ")");
				return false;
			}
 			//判断核销后收款额是否大于事件价税合计
			var hrp = $("#h_rp").val();
			var countPrice1 = $("#countPrice1").val();
			if(parseFloat(hrp) > parseFloat(countPrice1)){
				alert("核销后收款额(" + hrp + ")不可以大于实际价税合计(" + countPrice1 + ")");
				return false;
			}
			
			//判断核销结果变动是否符合实际情况
			var dcp = $("#d_cp").val();
			var drep = $("#d_rep").val();
			var dhp = $("#d_hp").val();
			var dop = $("#d_op").val();
			var dap = $("#d_ap").val();
			
			if(!(dcp == "" && drep == "" && dhp == "" && dop == "" && dap == "")){
				var sum = 0;
				
				if(dcp == "" || drep == "" || dhp == "" || dop == "" || dap == ""){
					alert("核销结果变动四项数据可以全部留空，但不可几项留空，即填写则需全部填写");
					return false;
				}
				
				var flage = true;				
				
				if(!isNaN(parseFloat(dcp))) sum += parseFloat(dcp);
				else	flage = false;
				
				if(!isNaN(parseFloat(drep))) sum += parseFloat(drep);
				else	flage = false;
				
				if(!isNaN(parseFloat(dhp))) sum += parseFloat(dhp);
				else	flage = false;
				
				if(!isNaN(parseFloat(dop))) sum += parseFloat(dop);
				else	flage = false;
				
				if(!isNaN(parseFloat(dap))) sum += parseFloat(dap);
				else	flage = false;
				
				if(!flage){
					alert("核销结果变动数据格式错误");
					return false;
				}else if(sum != (parseFloat(countPrice1) - parseFloat(hrp))){
					alert("数据不平衡，核销结果变动数据之和(" + sum + ")不等于价税合计(" + countPrice1 + ") - 收款数额(" + hrp + ")");
					return false;
				}
			} 
		}
		$("#form1").submit();
	});
});

function addReceipt(){
	var customCde = "${receiveBillVO.customCde}";
	var url = "<c:url value='/account/receipt-bill/selectReceiptBill.jhtml'/>?vo.customerCde=" + customCde;
	top.showMyModal("选择客户",url,900,true);
}

//var index = 0;
function setReturnValue(obj){
	if(obj == "") return;
	
	$("#receiptBillVO_id").val(obj.id);
	$("#cvouchId").val(obj.cvouchId);
	$("#receiptDate").val(obj.receiptDate);
	$("#customerName").val(obj.customerName);
	$("#cvouchType").val(obj.cvouchType);
	$("#cssName").val(obj.cssName);
	$("#receiptCount1").val(obj.receiptCount1);
	$("#receiveCount").val(obj.receiveCount);
	
	
	/* var number = $("#tbody").find("tr").length + 1;

	var html = "<tr id='tr" + index + "'><td>" + number + "</td>"
		+ "<td>" + obj.cvouchId + "</td>"
		+ "<td>" + obj.receiptDate + "</td>"
		+ "<td title='" + obj.customerName + "'>" + (obj.customerName.length > 12 ? obj.customerName.substring(0,12) : obj.customerName) 
		+ (obj.customerName.length > 12 ? "...</td>" : "</td>")
		+ "<td>" + obj.cvouchType + "</td>"
		+ "<td>" + obj.cssName + "</td>"
		+ "<td>" + obj.receiptCount1 + "</td>"
		+ "<td>" + obj.receiveCount + "</td>"
		+ "<td><input name='receiveBillVO.recordList[0].receivePrice' style='width: 45px;' readOnly='readOnly' id='brp'/></td>"
		+ "<td><input name='receiveBillVO.recordList[0].chargePrice' style='width: 45px;' id='bcp' onchange='changeV(\"bcp\")'/></td>"
		+ "<td><input name='receiveBillVO.recordList[0].returnPrice' style='width: 45px;' id='brep' onchange='changeV(\"brep\")'/></td>"
		+ "<td><input name='receiveBillVO.recordList[0].holdPrice' style='width: 45px;' id='bhp' onchange='changeV(\"bhp\")'/></td>"
		+ "<td><input name='receiveBillVO.recordList[0].otherPrice' style='width: 45px;' id='bop' onchange='changeV(\"bop\")'/></td>"
		+ "<td>--</td><td><button class='btn btn-small btn-danger' onclick='remove(" + index + ")'>删 除</button></td></tr>";
		
	$("#tbody").append(html);
	index ++; */
}

/* function remove(id){
	if(confirm("确定删除此次核销记录？")){
		$("#tr" + id).remove();
		index --;
	}
} */

function changeV(id){
	var bvalue = $("#b_" + id).val();
	var value = $("#"+id).val();
	
	if(bvalue != ""){
		if(isNaN(bvalue)){
			alert("请输入正确的数字");
			$("#b_" + id).val("0");
			$("#b_" + id).focus();
			bvalue = 0;
		}else{
			if(value == "" || parseFloat(bvalue) > parseFloat(value)){
				alert("本次核销金额不能超过核销余额");
				$("#b_" + id).val("0");
				$("#b_" + id).focus();
				bvalue = 0;
			}
		}
		$("#h_" + id).val(parseFloat(value) - parseFloat(bvalue));
		
	}
	sum("b"); 
	hSum();
}

function sum(type){
	var cp = $("#" + type + "_cp").val();
	var rep = $("#" + type + "_rep").val();
	var hp = $("#" + type + "_hp").val();
	var op = $("#" + type + "_op").val();
	var ap = $("#" + type + "_ap").val();
	
	var sum = 0;
	if(!isNaN(parseFloat(cp))) sum += parseFloat(cp);
	if(!isNaN(parseFloat(rep))) sum += parseFloat(rep);
	if(!isNaN(parseFloat(hp))) sum += parseFloat(hp);
	if(!isNaN(parseFloat(op))) sum += parseFloat(op);
	if(!isNaN(parseFloat(ap))) sum += parseFloat(ap);
	
	$("#" + type + "_rp").val(sum);
	
}

function hSum(){
	var brp = $("#b_rp").val();
	var rp = $("#rp").val();
	
	var sum = 0;
	if(!isNaN(parseFloat(brp))) sum += parseFloat(brp);
	if(!isNaN(parseFloat(rp))) sum += parseFloat(rp);
	
	$("#h_rp").val(sum);
	
}

</script>
<style type="text/css">
.myWidth{
	float: left;
	width: 110px;
	padding-top: 5px;
	text-align: right
}
.myMargin{
margin-left: 120px;
}
.myInput{
	width: 110px;
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
					<form name="form1" action="saveReceive.jhtml" class="form-horizontal" method="post" id="form1">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" />
						<input type="hidden" name="vo.beginTime" value="${vo.beginTime}"/>
						<input type="hidden" name="vo.endTime" value="${vo.endTime}"/>
						<input type="hidden" name="receiveBillVO.id" id="receiveBillVO_id" value="${receiveBillVO.id}" />
						<input type="hidden" name="receiptBillVOId" id="receiptBillVO_id" value="" />
						<input type="hidden" name="checkRecordVO.id" id="checkRecordVO_id" value="" />
						<h5>
							<span id="formTitle">核销待核单明细</span>
						</h5>

						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">销售发票号</label>
									<div class="myMargin">
										<input value="${receiveBillVO.csbvcode}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">凭证号</label>
									<div class="myMargin">
										<input value="${receiveBillVO.number}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">发票日期</label>
									<div class="myMargin">
										<input value="<fmt:formatDate value='${receiveBillVO.billDate}' pattern='yyyy-MM-dd'/>" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">到期日期</label>
									<div class="myMargin">
										<input value="<fmt:formatDate value='${receiveBillVO.maturityDate}' pattern='yyyy-MM-dd'/>" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">客户编码</label>
									<div class="myMargin">
										<input value="${receiveBillVO.customCde}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">客户名称</label>
									<div class="myMargin">
										<input value="${receiveBillVO.customName}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">本币发票额</label>
									<div class="myMargin">
										<input value="<fmt:formatNumber value='${receiveBillVO.countPrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">本币实际发票额</label>
									<div class="myMargin">
										<input value="<fmt:formatNumber value='${receiveBillVO.countPrice1}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="countPrice1"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">收款数额</label>
									<div class="myMargin">
										<input value="<fmt:formatNumber value='${receiveBillVO.receivePrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">状态</label>
									<div class="myMargin">
										<input value="${receiveBillVO.state.text}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<h5><span>收款单</span>
							<c:if test="${act != 'VIEW' && receiveBillVO.state == 'DHX'}">
								<a onclick="javascript:addReceipt();" class="btn btn-small btn-info" >选择</a>
							</c:if>
						</h5>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">收款单号</label>
									<div class="myMargin">
										<input  value="" type="text" class="myInput" id="cvouchId" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">收款日期</label>
									<div class="myMargin">
										<input  value="" type="text" class="myInput" id="receiptDate" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">客户名称</label>
									<div class="myMargin">
										<input  value="" type="text" class="myInput" id="customerName" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">类别</label>
									<div class="myMargin">
										<input  value="" type="text" class="myInput" id="cvouchType" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">结算名称</label>
									<div class="myMargin">
										<input  value="" type="text" class="myInput" id="cssName" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">本币收款额</label>
									<div class="myMargin">
										<input  value="" type="text" class="myInput" id="receiptCount1" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">本币未核余额</label>
									<div class="myMargin">
										<input  value="" type="text" class="myInput" id="receiveCount" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<h5><span>核销过程明细</span></h5>
						
						<div class="container_12 divider">
 							<div class="grid_4 height250" id="page1">
	 							<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
									<thead>
										<tr>
											<th style="min-width:100px">所属项目</th>
											<th style="min-width:120px">已收款合计</th>
											<th style="min-width:120px">实际收款额</th>
											<th style="min-width:120px">待费用发票额</th>
											<th style="min-width:120px">待退货额</th>
											<th style="min-width:120px">暂扣额</th>
											<th style="min-width:120px">其他余额</th>
										</tr>
									</thead>
									<tbody id="tbody">
										<tr>
											<td>核销余额</td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.receivePrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="rp"/></td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.actualPrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="ap"/></td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.chargePrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="cp"/></td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.returnPrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="rep"/></td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.holdPrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="hp"/></td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.otherPrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="op"/></td>
										</tr>
										<tr>
											<td>本次核销</td>
											<td><input name="checkRecordVO.receivePrice" value="0" type="text" class="myInput" readonly="readonly" id="b_rp"/></td>
											<td><input name="checkRecordVO.actualPrice" value="0" type="text" class="myInput" id="b_ap" onchange="changeV('ap')"/></td>
											<td><input name="checkRecordVO.chargePrice" value="0" type="text" class="myInput" id="b_cp" onchange="changeV('cp')"/></td>
											<td><input name="checkRecordVO.returnPrice" value="0" type="text" class="myInput" id="b_rep" onchange="changeV('rep')"/></td>
											<td><input name="checkRecordVO.holdPrice" value="0" type="text" class="myInput" id="b_hp" onchange="changeV('hp')"/></td>
											<td><input name="checkRecordVO.otherPrice" value="0" type="text" class="myInput" id="b_op" onchange="changeV('op')"/></td>
										</tr>
										<tr>
											<td>核销结果</td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.receivePrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="h_rp"/></td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.actualPrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="h_ap"/></td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.chargePrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="h_cp"/></td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.returnPrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="h_rep"/></td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.holdPrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="h_hp"/></td>
											<td><input value="<fmt:formatNumber value='${receiveBillVO.otherPrice}' pattern='#.##'/>" type="text" class="myInput" readonly="readonly" id="h_op"/></td>
										</tr>
										<tr>
											<td>核销结果变动</td>
											<td>--</td>
											<td><input name="receiveBillVO.actualPrice" value="" type="text" class="myInput" id="d_ap"/></td>
											<td><input name="receiveBillVO.chargePrice" value="" type="text" class="myInput" id="d_cp"/></td>
											<td><input name="receiveBillVO.returnPrice" value="" type="text" class="myInput" id="d_rep"/></td>
											<td><input name="receiveBillVO.holdPrice" value="" type="text" class="myInput" id="d_hp"/></td>
											<td><input name="receiveBillVO.otherPrice" value="" type="text" class="myInput" id="d_op"/></td>
										</tr>
									</tbody>
								</table>
							</div>
					    </div>
						
						<h5><span>核销记录表</span></h5>
						
						<div class="container_12 divider">
 							<div class="grid_4 height250" id="page1">
	 							<table class="fancyTable" id="result1" cellpadding="0" cellspacing="0">
									<thead>
										<tr> 
											<th style="min-width:20px" rowspan="2">序号</th>
											<th style="min-width:70px" rowspan="2">收款单号</th>
											<th style="min-width:70px" rowspan="2">收款日期</th>
											<th style="min-width:150px" rowspan="2">客户名称</th>
											<th style="min-width:30px" rowspan="2">类别</th>
											<th style="min-width:50px" rowspan="2">结算名称</th>
											<th style="min-width:50px" rowspan="2">本币收款额</th>
											<th style="min-width:50px">本币未核余额</th>
											<th style="min-width:50px">已收款合计</th>
											<th style="min-width:50px">实际收款额</th>
											<th style="min-width:50px">待费用发票额</th>
											<th style="min-width:50px">待退货额</th>
											<th style="min-width:50px">暂扣额</th>
											<th style="min-width:50px">其他余额</th>
											<th style="min-width:50px" rowspan="2">状态</th>
											<th style="min-width:80px" rowspan="2">操作</th>
										</tr>
										<tr>
											<th colspan="7">本次核销金额</th>
										</tr>
									</thead>
									<tbody id="tbody">
										<c:forEach items="${receiveBillVO.recordList}" var="re" varStatus="s">
											<tr>
												<td>${s.index + 1}</td>
												<td>${re.receiptBill.cvouchId}</td>
												<td><fmt:formatDate value="${re.receiptBill.receiptDate }" pattern="yyyy-MM-dd"/></td>
												<td>${re.receiptBill.customerName}</td>
												<td>${re.receiptBill.cvouchType == 48 ? '收': (vo.receiptBill.cvouchType == 49 ? '付' : '')}</td>
												<td>${re.receiptBill.cssName }</td>	
												<td><fmt:formatNumber value='${re.receiptBill.receiptCount1}' pattern='#.##'/></td>	
												
												<td><fmt:formatNumber value='${re.receiptCount}' pattern='#.##'/></td>
												<td><fmt:formatNumber value='${re.receivePrice}' pattern='#.##'/></td>		
												<td><fmt:formatNumber value='${re.actualPrice}' pattern='#.##'/></td>		
												<td><fmt:formatNumber value='${re.chargePrice}' pattern='#.##'/></td>		
												<td><fmt:formatNumber value='${re.returnPrice}' pattern='#.##'/></td>		
												<td><fmt:formatNumber value='${re.holdPrice}' pattern='#.##'/></td>		
												<td><fmt:formatNumber value='${re.otherPrice}' pattern='#.##'/></td>											
												<td>${re.state.text}</td>
												<td>
													<c:if test="${re.state == 'YHX' && s.index == 0 && receiveBillVO.state == 'DHX'}">
														<button name="cancelRecord" class="btn btn-small btn-success" att-id="${re.id}" att-key="${re.key}"><i class="icon-edit"></i>取消</button>
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
										<c:if test="${receiveBillVO.state == 'DHX'}">
											<button type="button" id="saveBtn" class="btn btn-primary">确定</button>
										</c:if>
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