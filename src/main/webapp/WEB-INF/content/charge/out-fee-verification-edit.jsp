<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.*" %>
<%@ page import="com.kington.fshg.webapp.security.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
	<link href="<c:url value='/lib/weebox-0.4/src/weebox.css'/>" rel="stylesheet"  type="text/css" charset="utf-8" />
	<script type="text/javascript" src="<c:url value='/lib/jquery-1.4.4.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/weebox.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/bgiframe.js'/>"></script>
	
<title>合同外费用核销</title>
<script type="text/javascript">
var btn;
var act ='${act}';
$(document).ready(function(){
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			var type = $("#projectType").val();
			/* if(type == 'CXYGZL'){
				var storeNum = $("#storeNum").val();
				var scale = $("#feeScale").val();
				if(storeNum == "" ){
					alert("门店数不能为空");
					return false;
				}else if(isNaN(storeNum)){
					alert("门店数请输入正确的数字");
					return false;
				}else if(scale == ""){
					alert("比例不能为空");
					return false;
				}else if(isNaN(scale)){
					alert("比例请输入正确的数字");
					return false;
				}
			}else */ 
			if(type == 'QTFYL'){
				var otherFee = $("#otherFee").val();
				if(otherFee == ""){
					alert("其他费用不能为空");
					return false;
				}else if(isNaN(otherFee)){
					alert("其他费用请输入正确的数字");
					return false;
				}
					
			}else if(type == 'CPMXL'){
				if($("input[id^='product_']").length < 1){
					alert("请添加产品明细");
					return false;
				}else{
					var i = 1;
					
					var flag = true;
					$("input[id^='fee_']").each(function(){
						if(this.value == ""){
							flag = false;
							alert("第" + i + "行产品明细费用不能为空");
							return false;
						}else if(isNaN(this.value)){
							flag = false;
							alert("第" + i + "行产品明细费用格式不正确，请输入正确的数字");
							return false;
						}
						i++;
					});
					
					if(!flag)
						return false;
				}
			} 
			form1.submit();		
		}
	
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	 	history.back();
	});
	
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled
		 $("select").attr("disabled","disabled");
		 $("#provisionBtn").hide();
	}
	
	if(act != 'ADD'){
		var projectType = $("#projectType").val();
		if(projectType == 'CXYGZL'){
			$("#CXYGZL").show();//促销员工资类
		}else if(projectType == 'CPMXL'){
			$("#CPMXL").show();//产品明细类
		}else if(projectType == 'QTFYL'){
			$("#QTFYL").show();//其他费用类
		}
	}
	
	$("#provisionBtn").click(function(){
		var url="<c:url value='/budget/out-fee-provision/getProvision.jhtml'/>";
		top.showMyModal("选择合同外费用预提",url ,900,true);
		btn = "provisionBtn";
	});
	
	
	$("#feeScale").blur(setTotalFee);
	$("#storeNum").blur(setTotalFee);
	$("#otherFee").blur(setTotalFee);
});


function setReturnValue(obj){
	if(obj == "") 
		return;
	if(btn == "provisionBtn"){
		$("#provision_id").val(obj.id);
		$("input[name='vo.outFeeProvision.provisionCode']").val(obj.provisionCode);
		$("#projectType").val(obj.projectType);
		$("#custom_id").val(obj.customId);
		$("#customName").val(obj.customName);
		$("#projectName").val(obj.projectName);
		setVerificationType();
	}else if(btn == "addProduct"){
		var lenght = obj.id.split(",").length
		for(var i =0 ; i< lenght -1 ; i++){
			var productId = $("#product_" + obj.id.split(",")[i]);
			if(productId.length > 0){
				continue;
			}
			
			var len = $("table tbody tr").length;
			var html="";
			html+="<tr class='odd'><input id='product_"+obj.id.split(",")[i]+"' type='hidden' name='vpdList["+ len +"].storeProduct.id' value='"+obj.id.split(",")[i]+"'/><td>"+ (len+1) +"</td><td>"+ obj.storeName.split(",")[i]+"</td><td>"+obj.name.split(",")[i]+"<input type='hidden' name='' value=''/></td><td>"+obj.stockCde.split(",")[i]+"</td><td>"+obj.number.split(",")[i]+"</td>";
			html+="<td>"+obj.productType.split(",")[i]+"</td><td>"+obj.standard.split(",")[i]+"</td><td><input id='fee_"+obj.id.split(",")[i]+"' type='text' value='' class='input60' name='vpdList["+len+"].cost' onblur='setTotalFee()'/></td><td><input id='remark_"+obj.id.split(",")[i]+"' type='text' value='' class='input120' name='vpdList["+len+"].remark' /></td><td><a class='btn btn-danger' att-id='' onclick='deletes(this)'>删除</a></td></tr>";
			$("#tbody").append(html);
		}
	}
}


function setVerificationType(){
	var provisionId = $("#provision_id").val();
	var projectType = $("#projectType").val();
	
	$("#CXYGZL").hide();
	$("#CPMXL").hide();
	$("#QTFYL").hide();
	
	var url="<c:url value='/charge/out-fee-verification/getProvisionById.jhtml'/>?vo.outFeeProvision.id="+ provisionId;
	$.post(url, null ,function(data){
		var json = eval(data);
		var html="";
		$.each(json,function(i,item){
			/* if(projectType == 'CXYGZL'){
				$("#CXYGZL").show();
				$("#storeNum").val(item.storeNum);
				$("#feeScale").val(item.feeScale);
			}else  */
			if(projectType == 'CPMXL'){
				$("#CPMXL").show();
				
				html+="<tr class='odd'><input id='product_"+item.storeProductId+"' type='hidden' name='vpdList["+ i +"].storeProduct.id' value='"+item.storeProductId+"'/><td>"+ (i+1) +"</td><td>"+ item.storeName +"</td><td>"+item.productName+"<input type='hidden' name='' value=''/></td><td>"+item.stockCde+"</td><td>"+item.number+"</td>";
				html+="<td>"+item.productType+"</td><td>"+item.standard+"</td><td><input id='fee_"+item.storeProductId+"' type='text' value='"+item.cost+"' class='input60' name='vpdList["+i+"].cost' onblur='setTotalFee()'/></td><td><input id='remark_"+item.storeProductId+"' type='text' value='"+item.remark+"' class='input120' name='vpdList["+i+"].remark' /></td><td>";
				if(act != 'VIEW'){
					html+="<a class='btn btn-danger' att-id='' onclick='deletes(this)'>删除</a></td></tr>";
				}else{
					html+="</td></tr>";
				}
				
			}else if(projectType == 'QTFYL'){
				$("#QTFYL").show();
				$("#otherFee").val(item.totalFee);
			}
			$("#totalFee").val(item.totalFee);
		});
		
		$("#tbody").append(html);
	},"text");
} 

//点击添加按钮，选择产品
function addProductDetail(){
	var customId = $("#custom_id").val();
	var url="<c:url value='/info/store-product/productList.jhtml'/>?vo.store.custom.id="+ customId;
	top.showMyModal("选择产品",url,900,true);
	btn="addProduct";
}

//删除单个产品明细
function deletes(obj){
	var id = $(obj).attr("att-id");//核销产品明细ID
	
	if(id == ''){
		$(obj).parent().parent().remove();
		var length = $("#product_table").find("tr").length;  //获取产品明细表格所有行
	      for (i = 0; i < length; i++) {
	           $("#product_table tr").eq(i).children("td").eq(0).html(i);  //给每行的第一列重写赋值
	           $("#product_table tr").eq(i).children("input").attr("name","vpdList["+( i-1) +"].storeProduct.id");
	           $("#product_table tr").eq(i).children("td").eq(7).children("input").attr("name","vpdList["+(i-1)+"].cost");
	           $("#product_table tr").eq(i).children("td").eq(8).children("input").attr("name","vpdList["+(i-1)+"].remark");
	       }
	      setTotalFee();
		return false;
	}
	
	var verId = $("#vo_id").val();//核销ID
	var url ="<c:url value='/charge/out-fee-verification/deleteDetail.text'/>?detailId="+id +"&vo.id=" + verId;
	$.post(url , null , function(data){
		if(data != '0'){
			$(obj).parent().parent().remove();
			var length = $("#product_table").find("tr").length;  //获取产品明细表格所有行
		      for (i = 0; i < length; i++) {
		           $("#product_table tr").eq(i).children("td").eq(0).html(i);  //给每行的第一列重写赋值
		           $("#product_table tr").eq(i).children("input").attr("name","vpdList["+( i-1) +"].storeProduct.id");
		           $("#product_table tr").eq(i).children("td").eq(7).children("input").attr("name","vpdList["+(i-1)+"].cost");
		           $("#product_table tr").eq(i).children("td").eq(8).children("input").attr("name","vpdList["+(i-1)+"].remark");
		       }
		      setTotalFee();
		}else{
			alert("删除失败！");				
		}
	}, "text"); 
}

function setTotalFee(){
	var projectType = $("#projectType").val();
	if(projectType == 'CXYGZL'){
		var storeNum = $("#storeNum").val();
		var scale = $("#feeScale").val();
		
		var total = 0;
		if(storeNum != "" && scale != "" && !isNaN(storeNum) && !isNaN(scale))
			total = parseFloat(storeNum) * parseFloat(scale)/100;
		$("#totalFee").val(total);
	}else if(projectType == 'QTFYL'){
		var totalFee = 0;
		var otherFee = $("#otherFee").val();
		if(otherFee != "" && !isNaN(otherFee))
			totalFee = otherFee;
		$("#totalFee").val(totalFee);
	}else if(projectType == 'CPMXL'){
		var totalFee = 0;
		$("input[id*='fee_']").each(function(){
			if(!isNaN($(this).val()))
				totalFee = parseFloat(totalFee) + parseFloat($(this).val());
		});
		$("#totalFee").val(totalFee);
	}
}
</script>
<style type="text/css">
.input120{width: 120px;}
.input60{width:60px;}
</style>
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
							<span id="formTitle">${act.text}合同外费用核销</span>
						</h5>
						
						<c:if test="${act != 'ADD' }">
							<div class="control-group">
									<label class="control-label">核销编码</label>
									<div class="controls">
										<input name="" type="text" value="${vo.verCode}" readonly="readonly"/>
									</div>
								</div>
						</c:if>
						
								<div class="control-group">
									<label class="control-label">申请业务员</label>
									<div class="controls">
										<c:if test="${empty vo.id}">
											<input name="vo.salesman" value="<%=UserContext.get().getUserName()%>" type="text" readonly="readonly"/>
										</c:if>
										<c:if test="${not empty vo.id}">
											<input name="vo.salesman" value="${vo.salesman}" type="text" readonly="readonly"/>
										</c:if>
									</div>
								</div>
						
								<div class="control-group">
									<label class="control-label">所属预提</label>
									<div class="controls">
										<input type="hidden" name="vo.outFeeProvision.id" value="${vo.outFeeProvision.id }" id="provision_id"/>
										<input name="vo.outFeeProvision.provisionCode" value="${vo.outFeeProvision.provisionCode}" type="text" readonly="readonly"
											msg="请选择所属预提" dataType="Require"/>
										<c:if test="${empty vo.id}">
											<button class="btn btn-mini" type="button" id="provisionBtn">选择预提</button>
										</c:if>
										<span>*</span>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">项目名称</label>
									<div class="controls">
										<input type="hidden" name="vo.outFeeProvision.provisionProject.projectType" 
											value="${vo.outFeeProvision.provisionProject.projectType }" id="projectType"/>
										<input name="" type="text" value="${vo.outFeeProvision.provisionProject.feeName }" readonly="readonly" id="projectName"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">所属客户</label>
									<div class="controls">
										<input type="hidden" name="vo.custom.id" value="${vo.custom.id}" id="custom_id"/>
										<input name="" type="text" value="${vo.custom.customName}" readonly="readonly" id="customName"/>
									</div>
								</div>
						
								<div class="control-group">
									<label class="control-label">核销方向</label>
									<div class="controls">
										<select name="vo.verDirection" dataType="Require" msg="请选择核销方向">
											<option value="">请选择</option>
											<c:forEach items="<%=PublicType.VerDirection.values() %>" var="direction">
												<option value="${direction.name}" <c:if test="${direction.name == vo.verDirection }">selected</c:if>>${direction.text}</option>
											</c:forEach>
										</select><span>*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">核销类型</label>
									<div class="controls">
										<select name="vo.verType.id" dataType="Require" msg="请选择核销类型">
						 	        		<option value="">请选择</option>
						 	        		<c:forEach items="${verTypeList }" var="type">
						 	        			<option value="${type.id}"  <c:if test="${type.id == vo.verType.id}">selected</c:if>>${type.dictName}</option>
						 	        		</c:forEach>
						 	        	</select><span>*</span>
									</div>
								</div>
								
								<!-- 其他费用类 -->
								<div class="control-group" style="display: none;" id="QTFYL">
									<label class="control-label">其他费用</label>
									<div class="controls">
										<input name="" type="text" value="${vo.totalFee}" id="otherFee"/>
									</div>
								</div>
								
								
								<!-- 促销员工资类 -->
								<div class="control-group" style="display:none;" id="CXYGZL">
									<label class="control-label">门店数</label>
									<div class="controls">
										<input name="storeNum" type="text" value="${storeNum}" id="storeNum" class="input120"/>&nbsp;&nbsp;X&nbsp;
										<input name="feeScale" type="text" value="${feeScale}" id="feeScale" class="input120"/>%
									</div>
								</div>
								
								
								<div class="control-group">
									<label class="control-label">总费用</label>
									<div class="controls">
										<input name="vo.totalFee" type="text" value="${vo.totalFee}"  id="totalFee"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">核销时间</label>
									<div class="controls">
										<input name="vo.verTime"  value="<fmt:formatDate value='${vo.verTime}' pattern='yyyy-MM-dd' />" type="text" class="input-medium Wdate"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">所属区间</label>
									<div class="controls">
										<input type="text" class="input-medium Wdate" name="vo.timeStart" value="<fmt:formatDate value='${vo.timeStart}' pattern='yyyy-MM-dd' />"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />至
       									<input type="text" class="input-medium Wdate" name="vo.timeEnd" value="<fmt:formatDate value='${vo.timeEnd}' pattern='yyyy-MM-dd' />"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">备注</label>
									<div class="controls">
										<textarea style="width: 500px;height: 100px;" name="vo.remark">${vo.remark }</textarea>
									</div>
								</div>
								
							<div id="CPMXL" style="display:none;">						
							<h5><span>产品信息</span>
								<c:if test="${act != 'VIEW' }">
									<a onclick="javascript:addProductDetail();" class="btn btn-small btn-success" >添加</a>
								</c:if>
							</h5>
						      <div class="row-fluid">
							   <div class="span12">
							   		<table class="table table-bordered table-striped table-hover" id="product_table">
							   			<thead>
							   				<tr>
							   					<th style="width:30px;">序号</th>
							   					<th style="white-space:nowarp">门店名称</th>
							   					<th style="width:150px;">产品名称</th>
												<th style="width:120px;">存货编码</th>
												<th style="white-space:nowrap">货号</th>
												<th style="white-space:nowrap">产品类别</th>
												<th style="width:150px;">产品规格</th>
												<th style="white-space:nowrap">费用(元)</th>
												<th style="white-space:nowrap">备注</th>
												<th style="width:100px;">操 作</th>
							   				</tr>
							   			</thead>
							   			<tbody id="tbody">
							   				<c:if test="${not empty vo.id }">
							   					<c:forEach items="${detailList}" var="detail" varStatus="i">
							   						<tr class="odd">
							   							<input id="product_${detail.storeProduct.id}" type="hidden" name="vpdList[${i.count-1}].storeProduct.id" value="${detail.storeProduct.id }"/>
							   							<td>${i.count}</td>
							   							<td>${detail.storeProduct.store.storeName}</td>
							   							<td>${detail.storeProduct.product.productName}</td>
							   							<td>${detail.storeProduct.product.stockCde }</td>
							   							<td>${detail.storeProduct.product.number}</td>
							   							<td>${detail.storeProduct.product.productType.productTypeName}</td>
							   							<td>${detail.storeProduct.product.standard}</td>
							   							<td><input id="fee_${detail.storeProduct.id}" type="text" name="vpdList[${i.count-1 }].cost" class="input60" value="${detail.cost }" onblur="setTotalFee()"/></td>
							   							<td><input id="remark_${detail.storeProduct.id}" type="text" name="vpdList[${i.count-1}].remark" class="input120" value="${detail.remark}"/></td>
							   							<td>
							   								<c:if test="${act != 'VIEW' }">
							   									<a class="btn btn-danger" att-id="${detail.id}" onclick="deletes(this)">删除</a>
							   								</c:if>
							   							</td>		   							
							   						</tr>
							   					</c:forEach>
							   				</c:if>
							   			</tbody>
							   		</table>
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
