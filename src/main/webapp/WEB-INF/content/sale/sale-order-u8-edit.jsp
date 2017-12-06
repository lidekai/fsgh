<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>销售订单管理</title>
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
	
	//选择客户
	$("#customBtn").click(function(){
		var url = "<c:url value='/info/custom/selectCustom.jhtml'/>";
		top.showMyModal("选择客户",url,900,true);
		btn = "customBtn";
	});
	
	//选择币种
	$("#cexchBtn").click(function(){
		var url = "<c:url value='/sale/sale-order-u8/selectCexch.jhtml'/>";
		top.showMyModal("选择币种",url,900,true);
		btn = "cexchBtn";
	});
	
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			
			var number = $("#tbody").find("tr").length;
			if(number < 1){
				alert("至少需要填写一项订单详情");
				return false;
			}
			//信用额判断
			var ddxye = $("#ddxye").val();
			var xye = $("#xye").val();
			var xyye = $("#xyye").val();
			if(ddxye == 1 && parseFloat(xyye) > parseFloat(xye)){
				alert("信用余额超过该客户信用额，信用额为：" + xye + ",信用余额为：" + xyye );
				return false;
			}
			form1.submit();					
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  	history.back();
	});
	
	//遍历所有需要实现全选的复选框
	$(".allchkBox").each(function(index){   //全选

		//捕获当前点击复选框的单击事件
		$(this).click(function() {   //勾选、取消后的行背景色

			var flag = this.checked; //判断当前复选框是否选中, true - 选中 , false - 取消选中

			//遍历当前选中复选框的table
			$($("#result").get(index)).find('.chkBox').each(function(chkindex) {   //勾选、取消后的行背景色
				
				this.checked = flag;
			});
		});
	});
	
});

function addOrderDetail(){
	var url="<c:url value='/sale/sale-order-u8/detail.jhtml'/>?detailVO.tax=" + $("#iTaxRate").val();
	top.showMyModal("订单详情",url,950,true);
	btn = "order";
}

function delOrderDetail(){
	ids="",keys="";
	var fIndex="";
	var obj = document.getElementsByName('cid');
	for ( var i = 0; i < obj.length; i++) {
		if (obj[i].checked == true) {
			var ss = obj[i].value.split(",");
			ids += ss[0] + ',';
			keys += ss[1] + ',';
			fIndex += ss[2] + ',';
		}
	}
	if (ids == '') {
		alert('请选择要删除的信息');
		return;
	}
	if ($("#tbody").find("tr").length == 1){
		alert('至少需要保留一条订单详情');
		return;
	}
	if (!confirm('确定要删除您勾选的记录？'))
		return;
	
	var idArray = ids.split(",");
	var keyArray = keys.split(",");
	var fIndexArray = fIndex.split(",");
	for(var i = 0; i < idArray.length; i++){
		//处理信用余额
		var xyye = $("#xyye").val();
		var jshj = 0;
		var s = 0;
		$("#" + idArray[i]).find("td").each(function(){
			var value = $(this).text();
			if(s == 17 && value != null && value != "")
				jshj = parseFloat(value);
			s++;
		})
		$("#xyye").val((parseFloat(xyye) - jshj).toFixed(2));
		
		$("#" + idArray[i]).remove();
		$("#g" + idArray[i]).remove();
		if(keyArray[i] != null && keyArray[i] != ""){
			//后台删除U8数据
			$.ajax({
				type:"post",
				url:"<c:url value='/sale/sale-order-u8/deleteDetail.jhtml'/>",
				data:{"id":idArray[i],"customCde":"${vo.customCode}","ddxye":${ddxye}},
				dataType:"text",
				success:function(data){
					if(data == '1')
						alert("删除成功");
					else
						alert("删除失败");
				}
			});
			
		}
		if(fIndexArray[i] != null && fIndexArray[i] != ""){
			//删除提交表单
			$("input[name='vo.detailList[" + fIndexArray[i] + "].cInvCode']").val("del");
		}
	}
	
	//列表重新排序
	var index = 1;
	$("td[id^='td']").each(function(){
		$("#" + this.id).html(index);
		index ++;
	});
	
	//合计计算
	sum(); 
}

function editOrderDetail(){
	btn = "order";
	getCheckBox();
	if (ids == '') {
		alert('请选择修改的信息');
		return;
	}else if(ids.split(",").length>2){
		alert('只能选择一条数据');
		return;
	}
	
	var index = 0;
	var tid = ids.split(",")[0];
	var url="<c:url value='/sale/sale-order-u8/detail.jhtml'/>?detailVO.id=" + tid;
	
	var productId = $("#pro_" + tid).val();
	if(productId != "" && productId != null)
		url += "&detailVO.productId=" + productId; 
	
	var value = "";
	$("tr[id='" + tid + "'] td").each(function(){
		value = $(this).text();
		if(index == 2){
			url += "&detailVO.hh=" + value;
		}else if(index == 3){
			url += "&detailVO.cInvCode=" + value;
		}else if(index == 5){
			url += "&detailVO.xjpfl=" + value;
		}else if(index == 6){
			url += "&detailVO.khsph=" + value;
		}else if(index == 7){
			url += "&detailVO.dfm=" + value;
		}else if(index == 8){
			url += "&detailVO.ggxh=" + value;
		}else if(index == 9){
			url += "&detailVO.zjl=" + value;
		}else if(index == 10){
			url += "&detailVO.js=" + value;
		}else if(index == 11){
			url += "&detailVO.sl=" + value;
		}else if(index == 12){
			url += "&detailVO.bj=" + value;
		}else if(index == 13){
			url += "&detailVO.zqwshj=" + value;
		}else if(index == 14){
			url += "&detailVO.hsdj=" + value;
		}else if(index == 15){
			url += "&detailVO.tax=" + value;
		}else if(index == 16){
			url += "&detailVO.wsdj=" + value;
		}else if(index == 17){
			url += "&detailVO.jshj=" + value;
			var xyye = $("#xyye").val();
			if(value != "" && value != null)
				$("#xyye").val((parseFloat(xyye) - parseFloat(value)).toFixed(2));
		}else if(index == 18){
			url += "&detailVO.wsje=" + value;
		}else if(index == 19){
			url += "&detailVO.zke=" + value;
		}else if(index == 20){
			url += "&detailVO.kl=" + value;
		}else if(index == 21){
			url += "&detailVO.kl2=" + value;
		}else if(index == 22){
			url += "&detailVO.chyt=" + value;
		}else if(index == 23){
			url += "&detailVO.scrq=" + value;
		}else if(index == 24){
			url += "&detailVO.bz=" + value;
		}
		index ++;
	})
	
	top.showMyModal("订单详情",url,950,true);
}

var chkId = 0;
function setReturnValue(obj){
	if(obj == "") return;
	
	if(btn == "order"){
		var trId = chkId;
		var formIndexId;
		if(obj.id != null && obj.id != ""){//修改
			var index = 0;
			$("#pro_" + obj.id).val(obj.productId);
			$("tr[id='" + obj.id + "'] td").each(function(){
				if(index == 0){
					var inputValue = $(this).find("input :first").val();
					formIndexId = inputValue.split(",")[2];
					if(formIndexId == ""){
						$(this).find("input :first").val(inputValue + formIndex)
					}
				}if(index == 2){
					var i = 0;
					/* $("tr[id='g" + obj.id + "'] td").each(function(){
						if(i == 2)
							$(this).text(obj.hh);
						i ++;
					});	 */
					$(this).text(obj.hh);
				}else if(index == 3){
					$(this).text(obj.chbm);
				}else if(index == 4){
					$(this).text(obj.chmc.substring(0,9) + "...");
					$(this).attr("title",obj.chmc);
				}else if(index == 5){
					$(this).text(obj.xjpfl);
				}else if(index == 6){
					$(this).text(obj.khsph);
				}else if(index == 7){
					$(this).text(obj.dfm);
				}else if(index == 8){
					$(this).text(obj.ggxh);
				}else if(index == 9){
					$(this).text(obj.zjl);
				}else if(index == 10){
					$(this).text(obj.js);
				}else if(index == 11){
					$(this).text(obj.sl);
				}else if(index == 12){
					$(this).text(obj.bj);
				}else if(index == 13){
					$(this).text(obj.zqwshj);
				}else if(index == 14){
					$(this).text(obj.hsdj);
				}else if(index == 15){
					$(this).text(obj.shl);
				}else if(index == 16){
					$(this).text(obj.wsdj);
				}else if(index == 17){
					$(this).text(obj.jshj);
				}else if(index == 18){
					$(this).text(obj.wsje);
				}else if(index == 19){
					$(this).text(obj.zke);
				}else if(index == 20){
					$(this).text(obj.kl);
				}else if(index == 21){
					$(this).text(obj.kl2);
				}else if(index == 22){
					$(this).text(obj.chyt);
				}else if(index == 23){
					$(this).text(obj.scrq);
				}else if(index == 24){
					$(this).text(obj.bz);
				}
				index ++;
			}); 
			
			//处理提交表单
			initForm(obj,formIndexId);
			
		}else{//新增
			var number = $("#tbody").find("tr").length + 1;
			var fxk ="<td style='width:20px;'><input type='checkbox' class='chkBox' name='cid' value='" + trId + "," + obj.key + "," + formIndex + "'/><input type='hidden' id='pro_" + trId + "' value='" + obj.productId + "'/></td>";//复选框
			var xh = "<td style='width:50px;' id='td" + number + "'>" + number + "</td>";//序号
			var hh = "<td style='width:50px;'>" + obj.hh + "</td>";//货号
			
			//固定列处理
			/* $(".fht-table.fancyTable tbody:first").append(
				"<tr id='g" + trId + "'>" + fxk + "<td style='width:50px;' id='td" + trId + "'>" + number + "</td>" + hh + "</tr>"
			);	 */
			
			var html ="<tr id='" + trId + "'><input type='hidden' value='" + obj.productId + "'/>";
			html += fxk + xh + hh + "<td>" + obj.chbm + "</td>"
				 + "<td title='" + obj.chmc + "'>" + obj.chmc.substring(0,9) + "...</td>"
				 + "<td>" + obj.xjpfl + "</td>"
				 + "<td>" + obj.khsph + "</td>"
				 + "<td>" + obj.dfm + "</td>"
				 + "<td>" + obj.ggxh + "</td>"
				 + "<td>" + obj.zjl + "</td>"
				 + "<td>" + obj.js + "</td>"
				 + "<td>" + obj.sl + "</td>"
				 + "<td>" + obj.bj + "</td>"
				 + "<td>" + obj.zqwshj + "</td>"
				 + "<td>" + obj.hsdj + "</td>"
				 + "<td>" + obj.shl + "</td>"
				 + "<td>" + obj.wsdj + "</td>"
				 + "<td>" + obj.jshj + "</td>"
				 + "<td>" + obj.wsje + "</td>"
				 + "<td>" + obj.zke + "</td>"
				 + "<td>" + obj.kl + "</td>"
				 + "<td>" + obj.kl2 + "</td>"
				 + "<td>" + obj.chyt + "</td>"
				 + "<td>" + obj.scrq + "</td>"
				 + "<td>" + obj.bz + "</td>"
				 + "</tr>" ;
			$("#tbody").append(html);
			
			chkId ++;
			
			//处理提交表单
			obj.id = trId;
			initForm(obj,"");
		}
		
		//处理合计
		sum();
		
		//处理信用余额
		var xyye = $("#xyye").val();
		if(obj.jshj != "" && obj.jshj != null)
			$("#xyye").val((parseFloat(xyye) + parseFloat(obj.jshj)).toFixed(2));
	}else if(btn == "customBtn"){
		$("#custom_id").val(obj.customCde);
		$("#customName").val(obj.name);
		$("#areaName").val(obj.areaName);
		
		//根据客户查询信用额
		if(${ddxye} == 1 ){
			$.ajax({
				type:"post",
				url:"<c:url value='/sale/sale-order-u8/getXyeJson.jhtml'/>",
				data:{"customCde":obj.customCde},
				dataType:"text",
				success:function(data){
					var date = eval(data);
					$("#xye").val(date[0].xye);
					$("#xyye").val(date[0].xyye);
				}
			});
		}
	}else if(btn == "cexchBtn"){
		$("#cexchName").val(obj.cexch);
		$("#iExchRate").val(obj.nflat);
	}
	
}

var formIndex = 0;
function initForm(obj,fIndex){
	if(fIndex != ""){
		$("input[name^='vo.detailList[" + fIndex + "].cInvCode']").val(obj.chbm);
		$("input[name^='vo.detailList[" + fIndex + "].xjpfl']").val(obj.xjpfl);
		$("input[name^='vo.detailList[" + fIndex + "].khsph']").val(obj.khsph);
		$("input[name^='vo.detailList[" + fIndex + "].dfm']").val(obj.dfm);
		$("input[name^='vo.detailList[" + fIndex + "].js']").val(obj.js);
		$("input[name^='vo.detailList[" + fIndex + "].sl']").val(obj.sl);
		$("input[name^='vo.detailList[" + fIndex + "].bj']").val(obj.bj);
		$("input[name^='vo.detailList[" + fIndex + "].zqwshj']").val(obj.zqwshj);
		$("input[name^='vo.detailList[" + fIndex + "].hsdj']").val(obj.hsdj);
		$("input[name^='vo.detailList[" + fIndex + "].tax']").val(obj.shl);
		$("input[name^='vo.detailList[" + fIndex + "].wsdj']").val(obj.wsdj);
		$("input[name^='vo.detailList[" + fIndex + "].jshj']").val(obj.jshj);
		$("input[name^='vo.detailList[" + fIndex + "].wsje']").val(obj.wsje);
		$("input[name^='vo.detailList[" + fIndex + "].zke']").val(obj.zke);
		$("input[name^='vo.detailList[" + fIndex + "].kl']").val(obj.kl);
		$("input[name^='vo.detailList[" + fIndex + "].kl2']").val(obj.kl2);
		$("input[name^='vo.detailList[" + fIndex + "].chyt']").val(obj.chyt);
		$("input[name^='vo.detailList[" + fIndex + "].scrq']").val(obj.scrq);
		$("input[name^='vo.detailList[" + fIndex + "].bz']").val(obj.bz);
	}else{
		var html = "<input type='hidden' name='vo.detailList[" + formIndex + "].id' value='" + obj.id + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].cInvCode' value='" + obj.chbm + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].xjpfl' value='" + obj.xjpfl + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].khsph' value='" + obj.khsph + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].dfm' value='" + obj.dfm + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].sl' value='" + obj.sl + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].js' value='" + obj.js + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].bj' value='" + obj.bj + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].zqwshj' value='" + obj.zqwshj + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].hsdj' value='" + obj.hsdj + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].tax' value='" + obj.shl + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].wsdj' value='" + obj.wsdj + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].jshj' value='" + obj.jshj + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].wsje' value='" + obj.wsje + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].zke' value='" + obj.zke + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].kl' value='" + obj.kl + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].kl2' value='" + obj.kl2 + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].chyt' value='" + obj.chyt + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].scrq' value='" + obj.scrq + "'/>"
			+ "<input type='hidden' name='vo.detailList[" + formIndex + "].bz' value='" + obj.bz + "'/>";
			
		$("form[name='form1']").append(html);
		
		formIndex ++;
	}
}

function getPersonList(){
	var departmentCode = $("#departmentCode").val();
	if(departmentCode != "" || departmentCode != null){
		$.ajax({
			type:"post",
			url:"<c:url value='/sale/sale-order-u8/getPersonJson.jhtml'/>",
			data:{"departmentCode":departmentCode},
			dataType:"text",
			success:function(data){
				var date = eval(data);
				var result = "<option value=\"\">请选择</option>";;
				for(var i=0;i<date.length;i++){
					 result += "<option value='" + date[i].code + "'>" + date[i].name + "</option>";
				}
				$("#personCode").html(result);  
			}
		});
	}
}

function sum(){
	var js=0,sl=0,zqwshj=0,jshj=0,wsje=0,zke=0;
	$("#tbody").find("tr").each(function(){
		var index=0;
		$(this).find("td").each(function(){
			var value = $(this).text();
			if( value != null && value != ''){
				value = parseFloat(value);
				if(index == 10 ) js = parseFloat(js + value);
				if(index == 11 ) sl = parseFloat(sl + value);
				if(index == 13 ) zqwshj = parseFloat(zqwshj + value);
				if(index == 17 ) jshj = parseFloat(jshj + value);
				if(index == 18 ) wsje = parseFloat(wsje + value);
				if(index == 19 ) zke = parseFloat(zke + value);
			}
			index ++;
		})
	});
	
	$("#ts").html($("#tbody").find("tr").length);
	$("#tf_js").html(js.toFixed(2));
	$("#tf_sl").html(sl.toFixed(6));
	$("#tf_zqwshj").html(zqwshj.toFixed(6));
	$("#tf_jshj").html(jshj.toFixed(6));
	$("#tf_wsje").html(wsje.toFixed(2));
	$("#tf_zke").html(zke.toFixed(3));
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
					<form name="form1" action="update.jhtml" class="form-horizontal" method="post">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" />
						<input type="hidden" name="ddxye" value="${ddxye }" id="ddxye"/>
						<input type="hidden" name="xye" value="${xye}" id="xye"/>
						<input type="hidden" name="xyye" value="${xyye}" id="xyye"/>
						<h5>
							<span id="formTitle">编辑销售订单</span>
						</h5>

						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">订单号</label>
									<div class="myMargin">
										<input name="vo.orderCode" value="${vo.orderCode}" type="text" class="myInput" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">输单日期</label>
									<div class="myMargin">
										<input name="vo.orderDate" class="myInput Wdate" dataType="Require" msg="请输入" 
											value="<fmt:formatDate value="${vo.orderDate}" pattern="yyyy-MM-dd" />"
											type="text" readonly="readonly"
											onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/> <span>*</span>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">销售部门</label>
									<div class="myMargin">
										<select name="vo.departmentCode" dataType="Require"  class="myInput" msg="请选择" onchange="getPersonList()" id="departmentCode">
											<option value="">请选择</option>
											<c:forEach items="${departmentList}" var="d">
												<option value="${d.text}" ${d.text == vo.departmentCode ? 'selected' : ''}>${d.name}</option>
											</c:forEach>
										</select>
										<span>*</span>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">销售类型</label>
									<div class="myMargin">
										<select name="vo.saleTypeCode" dataType="Require"  class="myInput" msg="请选择" >
											<option value="">请选择</option>
											<c:forEach items="${saletypeList}" var="s">
												<option value="${s.text}" ${s.text == vo.saleTypeCode ? 'selected' : ''}>${s.name}</option>
											</c:forEach>
										</select>
										<span>*</span>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">客户名称</label>
									<div class="myMargin">
										<input type="hidden" name="vo.customCode"
											value="${vo.customCode}" id="custom_id" /> <input
											name="vo.customName" value="${vo.customName}"
											type="text" readonly="readonly" msg="请选择客户名称"
											dataType="Require" id="customName"  class="myInput"/>
										<button class="btn btn-mini" type="button" id="customBtn">选择客户</button>
										<span>*</span>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">客户地区</label>
									<div class="myMargin">
										<input name="vo.customArea" value="${vo.customArea}" type="text" class="myInput" id="areaName" />
										<span>*</span>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">税率</label>
									<div class="myMargin">
										<input name="vo.iTaxRate" value="${empty vo.ITaxRate ? '17' : vo.ITaxRate}" type="text" class="myInput" dataType="Require" msg="请输入" id="iTaxRate"/>%
										<span>*</span>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">币种</label>
									<div class="myMargin">
										<input name="vo.cexchName" value="${empty vo.cexchName ? '人民币' : vo.cexchName}" type="text" class="myInput" dataType="Require" msg="请输入" id="cexchName"/>
										<button class="btn btn-mini" type="button" id="cexchBtn">选择币种</button>
										<span>*</span>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">汇率</label>
									<div class="myMargin">
										<input name="vo.iExchRate" value="${empty vo.IExchRate ? '1' : vo.IExchRate}" type="text" class="myInput" dataType="Require" msg="请输入" id="iExchRate"/>
										<span>*</span>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">收货人电话</label>
									<div class="myMargin">
										<input name="vo.shrPhone" value="${vo.shrPhone}" type="text" class="myInput" dataType="Require" msg="请输入"/>
										<span>*</span>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">业务负责人</label>
									<div class="myMargin">
										<select name="vo.personCode" class="myInput" id="personCode" dataType="Require" msg="请选择">
											<option value="">请选择</option>
											<c:forEach items="${personList}" var="p">
												<option value="${p.text}" ${p.text == vo.personCode ? 'selected' : '' }>${p.name}</option>
											</c:forEach>
										</select>
										<span>*</span>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">收货日期</label>
									<div class="myMargin">
										<input name="vo.shDate" class="myInput Wdate"
											value="<fmt:formatDate value="${vo.shDate}" pattern="yyyy-MM-dd" />"
											type="text" readonly="readonly"
											onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">卖场订单号</label>
									<div class="myMargin">
										<input name="vo.storeOrderCode" value="${vo.storeOrderCode}" type="text" class="myInput"  />
									</div>
								</div>
							</div>
							<div class="span8">
								<div class="control-group">
									<label class="myWidth">备注(收货地址)</label>
									<div class="myMargin">
										<input name="vo.remark" value="${vo.remark}" type="text" style="width:500px;" dataType="Require" msg="请输入"/>
										<span>*</span>
									</div>
								</div>
							</div>
						</div>

						<h5><span>订单详情</span>
							<c:if test="${empty vo.id || vo.state == 0}">
								<a onclick="javascript:addOrderDetail();" class="btn btn-small btn-info" >添加</a>
								<a onclick="javascript:editOrderDetail();" class="btn btn-small btn-success" >修改</a>
								<a onclick="javascript:delOrderDetail();" class="btn btn-small btn-danger" >删除</a>
							</c:if>
						</h5>
						
						<div class="container_12 divider">
 							<div class="grid_4 height250" id="page1">
	 							<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
									<thead>
										<tr>
											<th style="min-width: 20px"><input type='checkbox' class='allchkBox' /></th>
											<th style="min-width: 50px">序号</th>
											<th style="min-width: 50px">货号</th>
											<th style="min-width: 80px">存货编码</th>
											<th style="min-width: 150px">存货名称</th>
											<th style="min-width: 80px">新旧品分类</th>
											<th style="min-width: 80px">客户商品号</th>
											<th style="min-width: 80px">对方名</th>
											<th style="min-width: 150px">规格型号</th>
											<th style="min-width: 50px">主计量</th>
											<th style="min-width: 80px">件数</th>
											<th style="min-width: 80px">数量</th>
											<th style="min-width: 50px">报价</th>
											<th style="min-width: 150px">折前无税合计</th>
											<th style="min-width: 50px">含税单价</th>
											<th style="min-width: 80px">税率（%）</th>
											<th style="min-width: 80px">无税单价</th>
											<th style="min-width: 100px">价税合计</th>
											<th style="min-width: 80px">无税金额</th>
											<th style="min-width: 80px">折扣额</th>
											<th style="min-width: 80px">扣率（%）</th>
											<th style="min-width: 80px">扣率2（%）</th>
											<th style="min-width: 100px">存货用途</th>
											<th style="min-width: 80px">生产日期</th>
											<th style="min-width: 150px">备注</th>
										</tr>
									</thead>
									<tfoot>
										<tr id="tfoot">
											<td colspan="10">合计，共<span id="ts" class="mySpan">${fn:length(vo.detailList)}</span>条记录</td>
											<td><span id="tf_js" class="mySpan">${vo.js }</span></td>
											<td><span id="tf_sl" class="mySpan">${vo.sl }</span></td>
											<td></td>
											<td><span id="tf_zqwshj" class="mySpan"><fmt:formatNumber value='${vo.zqwshj}' pattern='#.##'/></span></td>
											<td></td>
											<td></td>
											<td></td>
											<td><span id="tf_jshj" class="mySpan"><fmt:formatNumber value='${vo.jshj}' pattern='#.##'/></span></td>
											<td><span id="tf_wsje" class="mySpan"><fmt:formatNumber value='${vo.wsje}' pattern='#.##'/></span></td>
											<td><span id="tf_zke" class="mySpan"> <fmt:formatNumber value='${vo.zke}' pattern='#.##'/></span></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</tfoot>
									<tbody id="tbody">
										<c:forEach items="${vo.detailList }" var="detail" varStatus="s">
											<tr id="${detail.id}">
												<td>
													<input type='checkbox' class='chkBox' name='cid' value='${detail.id},${detail.key},'/>
													<input type='hidden'  value='${detail.productId}' id="pro_${detail.id}"/>
												</td>
												<td id="td${s.index + 1}">${s.index + 1}</td>
												<td>${detail.hh}</td>
												<td>${detail.CInvCode }</td>
												<td title="${detail.chmc}">${detail.chmc}</td>
												<td>${detail.xjpfl}</td>
												<td>${detail.khsph }</td>
												<td>${detail.dfm}</td>
												<td>${detail.ggxh }</td>
												<td>${detail.zjl }</td>
												<td>${detail.js }</td>
												<td>${detail.sl }</td>
												<td>${detail.bj }</td>
												<td>${detail.zqwshj }</td>
												<td><fmt:formatNumber value="${detail.hsdj }" type="number" maxFractionDigits="3"/></td>
												<td>${detail.tax }</td>
												<td>${detail.wsdj }</td>
												<td>${detail.jshj }</td>
												<td>${detail.wsje }</td>
												<td>${detail.zke }</td>
												<td>${detail.kl }</td>
												<td>${detail.kl2 }</td>
												<td>${detail.chyt }</td>
												<td><fmt:formatDate value="${detail.scrq}" pattern="yyyy-MM-dd" /></td>
												<td>${detail.bz }</td>
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
										<c:if test="${empty vo.id || vo.state == 0}">
											<button type="button" id="saveBtn" class="btn btn-primary">保存</button>
										</c:if>
										<button type="button" id="cancelBtn" class="btn btn-primary">取消</button>
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