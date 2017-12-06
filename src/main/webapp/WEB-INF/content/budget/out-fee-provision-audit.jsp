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
	
<title>合同外费用预提审核</title>
<script type="text/javascript">
var btn;
$(document).ready(function(){
	//根据项目类型初始化编辑页面
	initEditPage();
	
	//确定按钮事件
	$("#auditBtn").click(function(){
		form1.submit(); 
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  var url="<c:url value='/budget/out-fee-provision/list.jhtml'/>";
	  window.location.href=url;
	});
	
	$('input').attr("readonly","readonly")//将input元素设置为disabled

});

//初始化
function initEditPage(){
	var projectType = '${vo.provisionProject.projectType.text}';//项目类型
	var customId = $("#custom_id").val(); //客户ID
	var provisionId = $("#vo_id").val();//预提ID
	
	if(projectType =='促销员工资类'){
		var storeScale = '${vo.storeScale}';//门店,比例保存字段
		if(storeScale != ''){
			$("#CXYGZL").show();
			$("#storeNum").val(storeScale.split(",")[0]);//填充门店数
			$("#scale").val(storeScale.split(",")[1]);//填充比例
		}
	}else if(projectType == '其他费用类'){
		$("#QTFYL").show();
	}else if(projectType == '产品明细类'){
		$("#CPMXL").show();
		showDetail();
	} 
}

//根据合同外费用预提，显示产品明细
function showDetail(){
	var provisionId = $("#vo_id").val();//预提ID
	var url="<c:url value='/budget/out-fee-provision/getDetail.jhtml'/>?vo.id="+ provisionId;
	$.post(url , null , function(data){
		var json = eval(data);
		var html="";
		$.each(json,function(i,item){
			html+="<tr class='odd'><input type='hidden' name='ppdList["+ i +"].storeProduct.id' value='"+item.storeProductId+"'/><td>"+ (i+1) +"</td><td>"+item.storeName+"</td><td>"+item.productName+"<input type='hidden' name='' value=''/></td><td>"+item.stockCde+"</td><td>"+item.number+"</td>";
			html+="<td>"+item.productType+"</td><td>"+item.standard+"</td><td><input type='text' value='"+item.cost+"' class='input_width60' name='ppdList["+i+"].cost'/></td><td><input id='remark_"+item.productId+"' type='text' value='"+item.remark+"' class='input_width120' name='ppdList["+i+"].remark' /></td></tr>";
		})
		$("#tbody").append(html);
	},"text"); 
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
					<form name="form1" id="form1" action="approve.jhtml" class="form-horizontal" method="post">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id}" />
						<input type="hidden" name="key" value="${key}"/>
						<input type="hidden" name="act" value="${act}"/>
						<input type="hidden" name="ids"  value="${vo.id}" />
						<input type="hidden" name="keys" value="${key}"/>
						<input type="hidden" name="vo.dateStart"  value="${vo.dateStart}" />
						<input type="hidden" name="vo.dateEnd" value="${vo.dateEnd}" />
						<input type="hidden" name="vo.startDate" value="${vo.startDate}" />
						<input type="hidden" name="vo.endDate" value="${vo.endDate}" />
						
						<h5>
							<span id="formTitle">${act.text}合同外费用预提</span>
						</h5>
								<div class="control-group">
									<label class="control-label">预提编码</label>
									<div class="controls">
										<input name="vo.provisionCode" value="${vo.provisionCode}" type="text" id="vo_code"
											placeholder="请输入预提编码" msg="请输入预提编码" dataType="Require" /><span>*</span>
											<span id="span_code"></span>
									</div>
								</div>
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
										<input name="vo.custom.customName" value="${vo.custom.customName}" type="text" readonly="readonly"/>
										<span>*</span>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">所属项目</label>
									<div class="controls">
										<input type="hidden" name="vo.provisionProject.id" value="${vo.provisionProject.id}" id="project_id" /> 
										<input name="vo.provisionProject.feeName" value="${vo.provisionProject.feeName}" type="text" readonly="readonly"/>
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
										<input name="vo.totalFee" type="text" value="${vo.totalFee}"  />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">总费用</label>
									<div class="controls">
										<input name="" type="text" value="${vo.totalFee}"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">预提时间</label>
									<div class="controls">
										<input name="vo.provisionTime"  value="<fmt:formatDate value='${vo.provisionTime}' pattern='yyyy-MM-dd'/>"  type="text" class="input-medium Wdate"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">所属区间</label>
									<div class="controls">
										<input type="text" class="input-medium Wdate" name="startTime" value="<fmt:formatDate value='${vo.startTime}' pattern='yyyy-MM-dd'/>"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />至
       									<input type="text" class="input-medium Wdate" name="endTime" value="<fmt:formatDate value='${vo.endTime}' pattern='yyyy-MM-dd'/>"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
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
								</h5>
							      <div class="row-fluid">
								   <div class="span12">
								   		<table class="table table-bordered table-striped table-hover">
								   			<thead>
								   				<tr>
								   					<th style="width:30px;">序号</th>
								   					<th style="white-space:nowrap">门店名称</th>
								   					<th style="width:200px;">产品名称</th>
													<th style="width:120px;">存货编码</th>
													<th style="white-space:nowrap">货号</th>
													<th style="white-space:nowrap">产品类别</th>
													<th style="width:150px;">产品规格</th>
													<th style="white-space:nowrap">费用(元)</th>
													<th style="white-space:nowrap">备注</th>
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
								<button type="button" id="auditBtn" class="btn btn-primary">审 批</button>
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
