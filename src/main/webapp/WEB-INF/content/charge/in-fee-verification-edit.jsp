<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
	<link href="<c:url value='/lib/weebox-0.4/src/weebox.css'/>" rel="stylesheet"  type="text/css" charset="utf-8" />
	<script type="text/javascript" src="<c:url value='/lib/jquery-1.4.4.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/weebox.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/bgiframe.js'/>"></script>
	
<title>合同内费用核销</title>
<script type="text/javascript">
var btn;
$(document).ready(function(){
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			form1.submit();
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
		 history.back();
	});
	
	var act ='${act}';
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled
		 $("select").attr("disabled","disabled");
		 $("#customBtn").hide();
		 $("#provisionBtn").hide();
	}
	
	
	$("#provisionBtn").click(function(){
		var url="<c:url value='/budget/in-fee-provision/getProvision.jhtml'/>";
		top.showMyModal("选择预提",url ,900,true);
		btn = "provisionBtn";
	});
	
	//计算总费用
	$("input.input").change(function(){
		var sum = 0;
		$("input.input").each(function(){
			if(!isNaN(this.value) && this.value != "")
				sum += parseFloat(this.value);
		})
		$("#totalFee").val(sum.toFixed(2));
	});
	
});


function setReturnValue(obj){
	if(obj == "") 
		return;
	if(btn == "provisionBtn"){
		$("#provision_id").val(obj.id);
		$("input[name='vo.inFeeProvision.code']").val(obj.name);
		$("#custom_id").val(obj.customId);
		$("#customName").val(obj.customName);
		var customType = obj.customType;
		if(customType.indexOf("DKA") > -1){
			$("#dka").removeAttr("style");
			$("#jxs").attr("style","display:none");
		}else{
			$("#jxs").removeAttr("style");
			$("#dka").attr("style","display:none");
		}
		setAllValue();
	}
}

//根据所选预提，设置各个费用
function setAllValue(){
	var provisionId = $("#provision_id").val();
	var url = "<c:url value='/budget/in-fee-provision/getById.jhtml'/>?vo.id=" + provisionId;
	$.post(url , null , function(data){
		var json = eval(data);
		$.each(json, function(i,item){
			$("input[name = 'vo.enterFee']").val(item.enterFee);
			$("input[name = 'vo.fixedFee']").val(item.fixedFee);
			$("input[name = 'vo.yearReturnFee']").val(item.yearReturnFee);
			$("input[name = 'vo.monthReturnFee']").val(item.monthReturnFee);
			$("input[name = 'vo.netInfoFee']").val(item.netInfoFee);
			$("input[name = 'vo.deliveryFee']").val(item.deliveryFee);
			$("input[name = 'vo.posterFee']").val(item.posterFee);
			$("input[name = 'vo.promotionFee']").val(item.promotionFee);
			$("input[name = 'vo.sponsorFee']").val(item.sponsorFee);
			$("input[name = 'vo.lossFee']").val(item.lossFee);
			$("input[name = 'vo.fixedDiscount']").val(item.fixedDiscount);
			$("input[name = 'vo.pilesoilFee']").val(item.pilesoilFee);
			$("input[name = 'vo.marketFee']").val(item.marketFee);
			$("input[name = 'vo.caseReturnFee']").val(item.caseReturnFee);
			$("input[name = 'vo.otherFee']").val(item.otherFee);
			$("input[name = 'vo.totalFee']").val(item.inFeeCount);
			
		});
	},"text");
}

</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="update.jhtml" class="form-horizontal" method="post">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" />
						<input type="hidden" name="vo.beginTime" value="${vo.beginTime}" />
						<input type="hidden" name="vo.endTime" value="${vo.endTime}" />
						<h5>
							<span id="formTitle">${act.text}合同内费用核销</span>
						</h5>
						<c:if test="${act != 'ADD' }">
						<div class="row-fluid">
							<div class="span12">
								<div class="control-group">
									<label class="control-label">核销编码</label>
									<div class="controls">
										<input name="vo.verCode" value="${vo.verCode}" type="text"  readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						</c:if>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">所属预提</label>
									<div class="controls">
										<input type="hidden" name="vo.inFeeProvision.id" value="${vo.inFeeProvision.id }" id="provision_id"/>
										<input name="vo.inFeeProvision.code" value="${vo.inFeeProvision.code}" type="text" readonly="readonly"
											msg="请选择所属预提" dataType="Require"/>
										<c:if test="${act =='ADD' }">
											<button class="btn btn-mini" type="button" id="provisionBtn">选择预提</button>
										</c:if>
										<span>*</span>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">所属客户</label>
									<div class="controls">
										<input type="hidden" name="vo.custom.id" value="${vo.custom.id}" id="custom_id" /> 
										<input name="vo.custom.customName" value="${vo.custom.customName}" type="text" 
												  readonly="readonly"  id="customName"/>
										<span>*</span>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">核销方向</label>
									<div class="controls">
										<select name="vo.verDirection" dataType="Require" msg="请选择核销方向">
											<option value="">请选择</option>
											<c:forEach items="<%=PublicType.VerDirection.values() %>" var="direction">
												<option value="${direction.name}" <c:if test="${direction.name == vo.verDirection }">selected</c:if>>${direction.text}</option>
											</c:forEach>
										</select>
										<span>*</span>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">核销类型</label>
									<div class="controls">
										<select name="vo.verType.id" dataType="Require" msg="请选择核销类型">
						 	        		<option value="">请选择</option>
						 	        		<c:forEach items="${verTypeList }" var="type">
						 	        			<option value="${type.id}"  <c:if test="${type.dictName == vo.verType.dictName}">selected</c:if>>${type.dictName}</option>
						 	        		</c:forEach>
						 	        	</select>
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
										<input name="vo.enterFee"
											value="<fmt:formatNumber value='${vo.enterFee}' pattern='#.##'/>"
											type="text" placeholder="请输入进场费" class="input"
											dataType="Double" msg="请输入正确的数字" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">固定费用</label>
									<div class="controls">
										<input name="vo.fixedFee"
											value="<fmt:formatNumber value='${vo.fixedFee}' pattern='#.##'/>"
											type="text" placeholder="请输入固定费用" class="input"
											dataType="Double" msg="请输入正确的数字" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">年返金</label>
									<div class="controls">
										<input name="vo.yearReturnFee"
											value="<fmt:formatNumber value='${vo.yearReturnFee}' pattern='#.##'/>"
											type="text" placeholder="请输入年返金比例" class="input"
											dataType="Double" msg="请输入正确的数字" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">其他费用</label>
									<div class="controls">
										<input name="vo.otherFee"
											value="<fmt:formatNumber value='${vo.otherFee}' pattern='#.##'/>"
											type="text" placeholder="请输入其他费用" class="input"
											dataType="Double" msg="请输入正确的数字" />
									</div>
								</div>
							</div>
						</div>
						<div id="dka" style="display: ${empty vo.id ? 'none' : (fn:contains(vo.custom.customType.customTypeName,'DKA') ? '' : 'none')};">
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">网络信息费</label>
										<div class="controls">
											<input name="vo.netInfoFee"
												value="<fmt:formatNumber value='${vo.netInfoFee}' pattern='#.##'/>"
												type="text" placeholder="请输入网络信息费" class="input"
												dataType="Double" msg="请输入正确的数字" />
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">配送服务费</label>
										<div class="controls">
											<input name="vo.deliveryFee"
												value="<fmt:formatNumber value='${vo.deliveryFee}' pattern='#.##'/>"
												type="text" placeholder="请输入配送服务费比例" class="input"
												dataType="Double" msg="请输入正确的数字" />
										</div>
									</div>
								</div>
							</div>
	
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">海报费</label>
										<div class="controls">
											<input name="vo.posterFee"
												value="<fmt:formatNumber value='${vo.posterFee}' pattern='#.##'/>"
												type="text" placeholder="请输入海报费" class="input"
												dataType="Double" msg="请输入正确的数字" />
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">促销陈列费</label>
										<div class="controls">
											<input name="vo.promotionFee"
												value="<fmt:formatNumber value='${vo.promotionFee}' pattern='#.##'/>"
												type="text" placeholder="请输入促销陈列费" class="input"
												dataType="Double" msg="请输入正确的数字" />
										</div>
									</div>
								</div>
							</div>
	
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">赞助费</label>
										<div class="controls">
											<input name="vo.sponsorFee"
												value="<fmt:formatNumber value='${vo.sponsorFee}' pattern='#.##'/>"
												type="text" placeholder="请输入赞助比例" class="input"
												dataType="Double" msg="请输入正确的数字" />
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">损耗费</label>
										<div class="controls">
											<input name="vo.lossFee"
												value="<fmt:formatNumber value='${vo.lossFee}' pattern='#.##'/>"
												type="text" placeholder="请输入损耗比例" class="input"
												dataType="Double" msg="请输入正确的数字" /> 
										</div>
									</div>
								</div>
							</div>
	
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">固定折扣</label>
										<div class="controls">
											<input name="vo.fixedDiscount"
												value="<fmt:formatNumber value='${vo.fixedDiscount}' pattern='#.##'/>"
												type="text" placeholder="请输入固定折扣比例" class="input"
												dataType="Double" msg="请输入正确的数字" />
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">堆头费</label>
										<div class="controls">
											<input name="vo.pilesoilFee"
												value="<fmt:formatNumber value='${vo.pilesoilFee}' pattern='#.##'/>"
												type="text" placeholder="请输入堆头费" class="input"
												dataType="Double" msg="请输入正确的数字" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">月返金</label>
										<div class="controls">
											<input name="vo.monthReturnFee"
												value="<fmt:formatNumber value='${vo.monthReturnFee}' pattern='#.##'/>"
												type="text" placeholder="请输入月返金比例" class="input"
												dataType="Double" msg="请输入正确的数字" />
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
											<input name="vo.marketFee"
												value="<fmt:formatNumber value='${vo.marketFee}' pattern='#.##'/>"
												type="text" placeholder="请输入市场费比例" class="input"
												dataType="Double" msg="请输入正确的数字" />
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">现款现货返利</label>
										<div class="controls">
											<input name="vo.caseReturnFee"
												value="<fmt:formatNumber value='${vo.caseReturnFee}' pattern='#.##'/>"
												type="text" placeholder="请输入现款现货返利比例" class="input"
												dataType="Double" msg="请输入正确的数字" />
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">费用总和</label>
									<div class="controls">
										<input name="vo.totalFee"
											value="<fmt:formatNumber value='${vo.totalFee}' pattern='#.##'/>"
											type="text" readonly="readonly" id="totalFee" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">核销日期</label>
									<div class="controls">
										<input name="vo.verDate" value="<fmt:formatDate value="${vo.verDate }" pattern="yyyy-MM-dd"/>"
											readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" type="text" placeholder="请输入核销日期"  dataType="Date" msg="请输入正确日期格式"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
						<div class="span12">
						<div class="control-group mT30">
							<div class="controls" style="margin:0 auto;text-align: center;">
								<c:if test="${act != 'VIEW' }">
									<button type="button" id="saveBtn" class="btn btn-primary">确 定</button>
								</c:if>
								<button type="button" id="cancelBtn" class="btn btn-primary">取 消</button>
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
