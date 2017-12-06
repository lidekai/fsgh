<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="com.kington.fshg.common.*"%>
<%@ page import="com.kington.fshg.webapp.security.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
	<link href="<c:url value='/lib/weebox-0.4/src/weebox.css'/>" rel="stylesheet"  type="text/css" charset="utf-8" />
	<script type="text/javascript" src="<c:url value='/lib/jquery-1.4.4.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/weebox.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/weebox-0.4/src/bgiframe.js'/>"></script>
	
<title>合同外费用预提</title>
<script type="text/javascript">
var btn;
var act ='${act}';
$(document).ready(function(){
	//根据项目类型初始化编辑页面
	initEditPage();

	//确定按钮事件 
	$("#saveBtn").click(function(){
		
		if(Validator.Validate(form1,3,3)){
			var type = $("#project_type").val();
			/* if(type == 'CXYGZL'){
				var storeNum = $("#storeNum").val();
				var scale = $("#scale").val();
				if(storeNum != "" && isNaN(storeNum)){
					alert("门店数请输入正确的数字");
					return false;
				}else if(scale != "" && isNaN(scale)){
					alert("比例请输入正确的数字");
					return false;
				}
			}else  */
			if(type == 'QTFYL'){
				var otherFee = $("#vo_otherFee").val();
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
	 /*  var url="<c:url value='/budget/out-fee-provision/list.jhtml'/>";
	  window.location.href=url; */
	  history.back();
	});
	
	
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled
		 $("#customBtn").hide();//隐藏选择客户按钮
		 $("#projectBtn").hide();//隐藏选择项目类型按钮
	}
	
	
	//选择客户
	$("#customBtn").click(function(){
		var url = "<c:url value='/info/custom/selectCustom.jhtml'/>";
		top.showMyModal("选择客户",url,900,true);
		btn = "customBtn";
	});
	
	//选择项目类型
	$("#projectBtn").click(function(){
		
		if($("#custom_id").val() == ''){
			alert("请先选择所属客户");
			return;
		}
		var url = "<c:url value='/budget/provision-project/getProject.jhtml'/>";
		top.showMyModal("选择所属项目",url,900,true);
		btn = "projectBtn";
	});
	
	$("#scale").blur(setTotalFee);
	$("#storeNum").blur(setTotalFee);
	$("#vo_otherFee").blur(setTotalFee);
});

//删除单个产品明细
function deletes(obj){
	var id = $(obj).attr("att-id");
	$(obj).parent().parent().remove();
		var length = $("#product_table").find("tr").length;  //获取产品明细表格所有行
        for (i = 0; i < length; i++) {
            $("#product_table tr").eq(i).children("td").eq(0).html(i);  //给每行的第一列重写赋值
            $("#product_table tr").eq(i).children("input").attr("name","ppdList["+( i-1) +"].storeProduct.id");
            $("#product_table tr").eq(i).children("td").eq(7).children("input").attr("name","ppdList["+(i-1)+"].cost");
            $("#product_table tr").eq(i).children("td").eq(8).children("input").attr("name","ppdList["+(i-1)+"].remark");
        }
		setTimeout(setTotalFee,200);
	if(id == '')
		return false;
	var provisionId = $("#vo_id").val();
	var url ="<c:url value='/budget/out-fee-provision/deleteDetail.text'/>?detailId="+id +"&vo.id=" + provisionId;
	$.post(url , null , function(data){
		if(data != '-1'){
			showDetail('0');
		}else{
			alert("删除失败！");				
		}
	}, "text"); 
}

function setReturnValue(obj){
	if(obj == "") return;
	if(btn == 'customBtn'){
		
		$("#custom_id").val(obj.id);
		$("input[name='vo.custom.customName']").val(obj.name);
		
	}else if(btn == 'projectBtn'){
		
		$("#project_id").val(obj.id);
		$("input[name='vo.provisionProject.feeName']").val(obj.name);
		
		//隐藏
		$("#CXYGZL").hide();
		$("#QTFYL").hide();
		$("#CPMXL").hide();
		$("#storeNum").val('');
		$("#tbody").empty();
		
		var customId = $("#custom_id").val();//客户ID
		
		if(obj.type == '促销员工资类'){
			$("#project_type").val("CXYGZL");
			/* var url="<c:url value='/info/store/getNumByCustom.jhtml'/>?vo.custom.id="+ customId;
			$.post(url , null , function(data){
				this;
				$("#CXYGZL").show();
				$("#storeNum").val(data);
			}); */
		}else if(obj.type == '其他费用类'){
			$("#project_type").val("QTFYL");
			$("#QTFYL").show();
			
		}else if(obj.type == '产品明细类'){
			$("#project_type").val("CPMXL");
			$("#CPMXL").show();
			showDetail('1');
		}
	}else if(btn = "addProduct"){
		var idLen = obj.id.split(",").length
		for(var i = 0; i< idLen -1; i++){
			var storeProductId = $("#product_"+ obj.id.split(",")[i]);
			if(storeProductId.length > 0 ){
				continue;
			}
			var len = $("table tbody tr").length;
			var html = "";
			html+="<tr class='odd'><input id='product_"+obj.id.split(",")[i]+"' type='hidden' name='ppdList["+ len +"].storeProduct.id' value='"+obj.id.split(",")[i]+"'/><td>"+ (len+1) +"</td><td>"+ obj.storeName.split(",")[i] +"</td><td>"+obj.name.split(",")[i]+"<input type='hidden' name='' value=''/></td><td>"+obj.stockCde.split(",")[i]+"</td><td>"+obj.number.split(",")[i]+"</td>";
			html+="<td>"+obj.productType.split(",")[i]+"</td><td>"+obj.standard.split(",")[i]+"</td><td><input id='fee_"+obj.id.split(",")[i]+"' type='text' value='' class='input_width60' name='ppdList["+len+"].cost' onblur='setTotalFee()'/></td><td><input id='remark_"+obj.id.split(",")[i]+"' type='text' value='' class='input_width120' name='ppdList["+len+"].remark' /></td><td><a class='btn btn-danger' att-id='' onclick='deletes(this)'>删除</a></td></tr>";
			$("#tbody").append(html);
		}
	}
	
	setTotalFee();
}

//初始化
function initEditPage(){
	var projectType = '${vo.provisionProject.projectType.text}';//项目类型
	var customId = $("#custom_id").val(); //客户ID
	var provisionId = $("#vo_id").val();//预提ID
	
	/* if(projectType =='促销员工资类'){
		var storeScale = '${vo.storeScale}';//门店,比例保存字段
		if(storeScale != ''){
			$("#CXYGZL").show();
			$("#storeNum").val(storeScale.split(",")[0]);//填充门店数
			$("#scale").val(storeScale.split(",")[1]);//填充比例
		}
	}else  */
	if(projectType == '其他费用类'){
		$("#QTFYL").show();
	}else if(projectType == '产品明细类'){
		$("#CPMXL").show();
		showDetail();
	} 
}

//根据合同外费用预提，显示产品明细
function showDetail(type){
	var provisionId = $("#vo_id").val();//预提ID
	var url="<c:url value='/budget/out-fee-provision/getDetail.jhtml'/>?vo.id="+ provisionId;
	$.post(url , null , function(data){
		var json = eval(data);
		var html="";
		$.each(json,function(i,item){
			var productId = $("#product_"+ item.storeProductId);
			
			if(type == '0'){
				if(productId < 0){
					html+="<tr class='odd'><input id='product_"+item.storeProductId+"' type='hidden' name='ppdList["+ i +"].storeProduct.id' value='"+item.storeProductId+"'/><td>"+ (i+1) +"</td><td>"+item.storeName+"</td><td>"+item.productName+"<input type='hidden' name='' value=''/></td><td>"+item.stockCde+"</td><td>"+item.number+"</td>";
					html+="<td>"+item.productType+"</td><td>"+item.standard+"</td><td><input id='fee_"+item.storeProductId+"' type='text' value='"+item.cost+"' class='input_width60' name='ppdList["+i+"].cost' onblur='setTotalFee()'/></td><td><input id='remark_"+item.storeProductId+"' type='text' value='"+item.remark+"' class='input_width120' name='ppdList["+i+"].remark' /></td><td>";
					if(act != 'VIEW'){
						html+="<a class='btn btn-danger' att-id='"+item.id+"' onclick='deletes(this)'>删除</a></td></tr>";
					}else{
						html+="</td></tr>";
					}
				}
			}else{
				html+="<tr class='odd'><input id='product_"+item.storeProductId+"' type='hidden' name='ppdList["+ i +"].storeProduct.id' value='"+item.storeProductId+"'/><td>"+ (i+1) +"</td><td>"+item.storeName+"</td><td>"+item.productName+"<input type='hidden' name='' value=''/></td><td>"+item.stockCde+"</td><td>"+item.number+"</td>";
				html+="<td>"+item.productType+"</td><td>"+item.standard+"</td><td><input id='fee_"+item.storeProductId+"' type='text' value='"+item.cost+"' class='input_width60' name='ppdList["+i+"].cost' onblur='setTotalFee()'/></td><td><input id='remark_"+item.storeProductId+"' type='text' value='"+item.remark+"' class='input_width120' name='ppdList["+i+"].remark' /></td><td>";
				if(act != 'VIEW'){
					html+="<a class='btn btn-danger' att-id='"+item.id+"' onclick='deletes(this)'>删除</a></td></tr>";
				}else{
					html+="</td></tr>";
				}
			}
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

function setTotalFee(){
	var projectType = $("#project_type").val();
	if(projectType == 'CXYGZL'){
		var storeNum = $("#storeNum").val();
		var scale = $("#scale").val();
		
		var total = 0;
		if(storeNum != "" && scale != "" && !isNaN(storeNum) && !isNaN(scale))
			total = parseFloat(storeNum) * parseFloat(scale)/100;
		$("#vo_totalFee").val(total);
	}else if(projectType == 'QTFYL'){
		var totalFee = 0;
		var otherFee = $("#vo_otherFee").val();
		if(!isNaN(otherFee) && otherFee != "")
			totalFee = otherFee;
		$("#vo_totalFee").val(totalFee);
	}else if(projectType == 'CPMXL'){
		var totalFee = 0;
		$("input[id*='fee_']").each(function(){
			if(!isNaN($(this).val()))
				totalFee = parseFloat(totalFee) + parseFloat($(this).val());
		});
		$("#vo_totalFee").val(totalFee);
	}
	$("#vo_totalFee").val($("#vo_totalFee").val());
}
</script>
<style type="text/css">
.input_width120{width: 120px;}
.input_width60{width: 60px;}
</style>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="update.jhtml" class="form-horizontal" method="post">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id}" />
						<input type="hidden" name="key" value="${key }" />
						<input type="hidden" name="productIds" id="productIds" value="" />
						<input type="hidden" name="vo.dateStart"  value="${vo.dateStart}" />
						<input type="hidden" name="vo.dateEnd" value="${vo.dateEnd}" />
						<input type="hidden" name="vo.startDate" value="${vo.startDate}" />
						<input type="hidden" name="vo.endDate" value="${vo.endDate}" />
						<h5>
							<span id="formTitle">${act.text}合同外费用预提</span>
						</h5>
								<div class="control-group">
									<label class="control-label">申请业务员</label>
									<div class="controls">
										<input name="vo.salesman" value="<%=UserContext.get().getUserName() %>" type="text"  readonly="readonly"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">所属客户</label>
									<div class="controls">
										<input type="hidden" name="vo.custom.id" value="${vo.custom.id}" id="custom_id" /> 
										<input name="vo.custom.customName" value="${vo.custom.customName}" type="text" readonly="readonly"
											msg="请选择所属客户" dataType="Require" />
										<button class="btn btn-mini" type="button" id="customBtn">选择客户</button>
										<span>*</span>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">所属项目</label>
									<div class="controls">
										<input type="hidden" name="vo.provisionProject.id" value="${vo.provisionProject.id}" id="project_id" />
										<input type="hidden" name="vo.provisionProject.projectType" value="${vo.provisionProject.projectType}" id="project_type" /> 
										<input name="vo.provisionProject.feeName" value="${vo.provisionProject.feeName}" type="text" readonly="readonly"
											msg="请选择所属项目" dataType="Require" />
										<button class="btn btn-mini" type="button" id="projectBtn">选择项目</button>
										<span>*</span>
									</div>
								</div>
								
								<div class="control-group" style="display: none;" id="CXYGZL">
									<label class="control-label">门店数</label>
									<div class="controls">
										<input name="storeNum" type="text" value="" id="storeNum" class="input_width120"/>&nbsp;&nbsp;X&nbsp;
										<input name="feeScale" type="text" value="" id="scale" class="input_width120"/>%
									</div>
								</div>
								
								<div class="control-group" style="display: none;" id="QTFYL">
									<label class="control-label">其他费用</label>
									<div class="controls">
										<input name="" type="text" value="${vo.totalFee}"  id="vo_otherFee"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">总费用</label>
									<div class="controls">
										<input name="vo.totalFee" type="text" value="${vo.totalFee}"  id="vo_totalFee" msg="请输入总费用" dataType="Require"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">制单时间</label>
									<div class="controls">
										<input name="vo.provisionTime"  value="<fmt:formatDate value='${vo.provisionTime}' pattern='yyyy-MM-dd'/>"  type="text" class="input-medium Wdate"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">预提所属区间</label>
									<div class="controls">
										<input type="text" class="input-medium Wdate" name="vo.startTime" value="<fmt:formatDate value='${vo.startTime}' pattern='yyyy-MM-dd'/>"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />至
       									<input type="text" class="input-medium Wdate" name="vo.endTime" value="<fmt:formatDate value='${vo.endTime}' pattern='yyyy-MM-dd'/>"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">备注</label>
									<div class="controls">
										<textarea style="width: 500px;height: 100px;" name="vo.remark">${vo.remark}</textarea>
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
							   					<th style="white-space:nowrap">门店名称</th>
							   					<th style="width:150px;">产品名称</th>
												<th style="width:100px;">存货编码</th>
												<th style="white-space:nowrap">货号</th>
												<th style="white-space:nowrap">产品类别</th>
												<th style="width:100px;">产品规格</th>
												<th style="white-space:nowrap">费用(元)</th>
												<th style="white-space:nowrap">备注</th>
												<th style="width:100px;">操 作</th>
							   				</tr>
							   			</thead>
							   			<tbody id="tbody">
							   			</tbody>
							   		</table>
							   </div>
							  </div>
							  </div>
								
						<div class="control-group mT30">
							<div class="controls" style="margin:0 auto;text-align: center;">
								<c:if test="${act != 'VIEW' }">
									<button type="button" id="saveBtn" class="btn btn-primary">确 定</button>
								</c:if>
								<button type="button" id="cancelBtn" class="btn btn-primary">取 消</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
