<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<link href="<c:url value='/lib/weebox-0.4/src/weebox.css'/>"
	rel="stylesheet" type="text/css" charset="utf-8" />
<script type="text/javascript"
	src="<c:url value='/lib/jquery-1.4.4.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/lib/weebox-0.4/src/weebox.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/lib/weebox-0.4/src/bgiframe.js'/>"></script>

<title>合同内条款设置</title>
<script type="text/javascript">
var btn;
$(document).ready(function(){
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			checkCustomYear('submit');
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  /* var url="<c:url value='/budget/in-fee-clause/list.jhtml'/>";
	  window.location.href=url; */
	  history.back();
	});
	
	var act ='${act}';
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled
		 $("#customBtn").hide();
	}
	
	
	//选择客户
	$("#customBtn").click(function(){
		var url = "<c:url value='/info/custom/selectCustom.jhtml'/>";
		top.showMyModal("选择客户",url,900,true);
		btn = "customBtn";
	});
	
});


function setReturnValue(obj){
	if(obj == "") return;
	$("#custom_id").val(obj.id);
	$("input[name='vo.custom.customName']").val(obj.name);
	
	var type = obj.type;
	if(type.indexOf("DKA") > -1){
		$("#dka").removeAttr("style");
		$("#jxs").attr("style","display:none");
	}else{
		$("#dka").attr("style","display:none");
		$("#jxs").removeAttr("style");
	}
		
	//getStoreCount();
}

function checkCustomYear(type){
	var year = $("#year").val();
	var customId = $("#custom_id").val();
	var id = $("#vo_id").val();
	if(year != null && customId != null){
		var url ="<c:url value='/budget/in-fee-clause/checkCustomYear.text'/>?vo.year="+ year + "&vo.custom.id=" + customId + "&vo.id=" + id;
		$.post(url , null , function(result){
			if(result == '1'){
				alert("此客户该年度已存在合同条款设置");
			}else if(type != ""){
				form1.submit();					
			}
		}, "text");
	}
}

function getStoreCount(){
	var customId = $("#custom_id").val();
	var url="<c:url value='/info/store/getNumByCustom.jhtml'/>?vo.custom.id="+ customId;
	$.post(url , null , function(data){
		$("#storeCount").val(data);
	});
	checkCustomYear("");
}

</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="update.jhtml" class="form-horizontal"
						method="post">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" />
						<h5>
							<span id="formTitle">${act.text}合同内条款设置</span>
						</h5>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">年份</label>
									<div class="controls">
										<input name="vo.year" value="${vo.year}" type="text"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy'})"
											msg="请选择年份" dataType="Require" id="year"
											onchange="checkCustomYear('')" /><span>*</span>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">所属客户</label>
									<div class="controls">
										<input type="hidden" name="vo.custom.id"
											value="${vo.custom.id}" id="custom_id" /> <input
											name="vo.custom.customName" value="${vo.custom.customName}"
											type="text" readonly="readonly" msg="请选择所属客户"
											dataType="Require" />
										<button class="btn btn-mini" type="button" id="customBtn">选择客户</button>
										<span>*</span>
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">进场费</label>
									<div class="controls">
										<input name="vo.enterFee" value="${vo.enterFee}" type="text"
											 dataType="Double" msg="格式错误"/><br />
									<input name="vo.enterStartDate" class="Wdate"
													value="<fmt:formatDate value="${vo.enterStartDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px" />
									-<input name="vo.enterEndDate" class="Wdate"
													value="<fmt:formatDate value="${vo.enterEndDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px"/>
									</div>
								</div>
							</div>
							
							
							<div class="span6">
								<div class="control-group">
									<label class="control-label">年返金</label>
									<div class="controls">
										<input name="vo.yearReturnFee" value="${vo.yearReturnFee}"
											type="text" placeholder="请输入年返金比例" dataType="Double"
											msg="格式错误" /> <b style="font-size: 16px;">%</b>
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">固定费用</label>
									<div class="controls">
										<input name="vo.fixedFee" value="${vo.fixedFee}" type="text"
											 dataType="Double" msg="格式错误" /><br />
										<input name="vo.fixedStartDate" class="Wdate"
													value="<fmt:formatDate value="${vo.fixedStartDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px" />
										-<input name="vo.fixedEndDate" class="Wdate"
													value="<fmt:formatDate value="${vo.fixedEndDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px"/>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">其他费用</label>
									<div class="controls">
										<input name="vo.otherFee" value="${vo.otherFee}" type="text"
											 dataType="Double" msg="格式错误" /><br />
										<input name="vo.otherStartDate" class="Wdate"
													value="<fmt:formatDate value="${vo.otherStartDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px" />
										-<input name="vo.otherEndDate" class="Wdate"
													value="<fmt:formatDate value="${vo.otherEndDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px"/>
									</div>
								</div>
							</div>
						</div>
						
						<div id="dka" style="display: ${empty vo.id ? 'none' : (fn:contains(vo.custom.customType.customTypeName,'DKA') ? '' : 'none')};">
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">月返金</label>
										<div class="controls">
											<input name="vo.monthReturnFee" value="${vo.monthReturnFee}"
												type="text" placeholder="请输入月返金比例" dataType="Double"
												msg="请输入正确的数字" /> <b style="font-size: 16px;">%</b>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">网络信息费</label>
										<div class="controls">
											<input name="vo.netInfoFee" value="${vo.netInfoFee}" type="text"
											 	dataType="Double" msg="格式错误" /><br />
											<input name="vo.netStartDate" class="Wdate"
													value="<fmt:formatDate value="${vo.netStartDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px" />
											-<input name="vo.netEndDate" class="Wdate"
													value="<fmt:formatDate value="${vo.netEndDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px"/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row-fluid" >
								<div class="span6">
									<div class="control-group">
										<label class="control-label">配送服务费</label>
										<div class="controls">
											<input name="vo.deliveryFee" value="${vo.deliveryFee}"
												type="text" placeholder="请输入配送服务费比例" dataType="Double"
												msg="请输入正确的数字" /> <b style="font-size: 16px;">%</b>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">海报费</label>
										<div class="controls">
											<input name="vo.posterFee" value="${vo.posterFee}" type="text"
											 	dataType="Double" msg="格式错误" /><br />
											<input name="vo.posterStartDate" class="Wdate"
													value="<fmt:formatDate value="${vo.posterStartDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px" />
											-<input name="vo.posterEndDate" class="Wdate"
													value="<fmt:formatDate value="${vo.posterEndDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px"/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">赞助费</label>
										<div class="controls">
											<input name="vo.sponsorFee" value="${vo.sponsorFee}"
												type="text" placeholder="请输入赞助比例" dataType="Double"
												msg="请输入正确的数字" /><b style="font-size: 16px;">%</b>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">促销陈列费</label>
										<div class="controls">
											<input name="vo.promotionFee" value="${vo.promotionFee}" type="text"
											 	dataType="Double" msg="格式错误" /><br />
											<input name="vo.promotionStartDate" class="Wdate"
													value="<fmt:formatDate value="${vo.promotionStartDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px" />
											-<input name="vo.promotionEndDate" class="Wdate"
													value="<fmt:formatDate value="${vo.promotionEndDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px"/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">固定折扣</label>
										<div class="controls">
											<input name="vo.fixedDiscount" value="${vo.fixedDiscount}"
												type="text" placeholder="请输入固定折扣比例" dataType="Double"
												msg="请输入正确的数字" /><b style="font-size: 16px;">%</b>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">堆头费</label>
										<div class="controls">
											<input name="vo.pilesoilFee" value="${vo.pilesoilFee}" type="text"
											 	dataType="Double" msg="格式错误" /><br />
											<input name="vo.pileStartDate" class="Wdate"
													value="<fmt:formatDate value="${vo.pileStartDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px" />
											-<input name="vo.pileEndDate" class="Wdate"
													value="<fmt:formatDate value="${vo.pileEndDate}" pattern="yyyy-MM-dd" />"
													type="text" readonly="readonly"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 80px"/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">损耗费</label>
										<div class="controls">
											<input name="vo.lossFee" value="${vo.lossFee}" type="text"
												placeholder="请输入损耗比例" dataType="Double" msg="请输入正确的数字" /><b
												style="font-size: 16px;">%</b>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div id="jxs" style="display: ${empty vo.id ? 'none' : (fn:contains(vo.custom.customType.customTypeName,'DKA') ? 'none' : '')};">
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">市场费</label>
										<div class="controls">
											<input name="vo.marketFee" value="${vo.marketFee}" type="text"
												placeholder="请输入市场费比例" dataType="Double" msg="请输入正确的数字" /><b
												style="font-size: 16px;">%</b>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">现款现货返利</label>
										<div class="controls">
											<input name="vo.caseReturnFee" value="${vo.caseReturnFee}"
												type="text" placeholder="请输入现款现货返利比例" dataType="Double"
												msg="请输入正确的数字" /><b style="font-size: 16px;">%</b>
										</div>
									</div>
								</div>
							</div>
						</div>


						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">开始日期</label>
									<div class="controls">
										<input name="vo.startDate" class="Wdate"
											value="<fmt:formatDate value="${vo.startDate}" pattern="yyyy-MM-dd" />"
											type="text" readonly="readonly"
											onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
									</div>
								</div>
							</div>
								<div class="span6">
									<div class="control-group">
									<label class="control-label">结束日期</label>
										<div class="controls">
										<input name="vo.endDate" class="Wdate"
											value="<fmt:formatDate value="${vo.endDate}" pattern="yyyy-MM-dd" />"
											type="text" readonly="readonly"
											onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
										</div>
									</div>
								</div>
						</div>
						
						
						<div class="control-group">
							<label class="control-label">备注</label>
							<div class="controls">
								<textarea style="width:500px;" rows="5" cols="50" name="vo.remark">${vo.remark }</textarea>
							</div>
							
								
						</div>
						<div class="row-fluid">
							<div class="span12">
								<div class="control-group mT30">
									<div class="controls"
										style="margin: 0 auto; text-align: center;">
										<c:if test="${act != 'VIEW' }">
											<button type="button" id="saveBtn" class="btn btn-primary">确
												定</button>
										</c:if>
										<button type="button" id="cancelBtn" class="btn btn-primary">取
											消</button>
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
