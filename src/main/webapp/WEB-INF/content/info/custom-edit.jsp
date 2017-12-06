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

<title>客户管理</title>
<script type="text/javascript">
var btn;
$(document).ready(function(){
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			var customCde = $("#vo_customCde").val();
			var customId = $("#vo_id").val();
			var span_customCde = $("#span_customCde");
			var url ="<c:url value='/info/custom/checkCustom.text'/>?vo.customCde="+ customCde ;
			$.post(url , null , function(result){
				this;
				if(result != '0' && result != customId){
					span_customCde.text('');
					span_customCde.css("color", "red");
					span_customCde.text('客户编码已存在,请重新输入');
					$("#span_customCde").show();
				}else{
					form1.submit();					
				}
			}, "text");
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  history.back();
	});
	
	var act ='${act}';
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled 
	}
	
	
	//选择地区
	$("#areaBtn").click(function(){
		var url = "<c:url value='/info/area/getArea.jhtml'/>";
		top.showMyModal("选择所属地区",url,900,true);
		btn = "areaBtn";
	});
	
	
	//选择客户类型
	$("#customTypeBtn").click(function(){
		var url ="<c:url value='/info/customs-type/getCustomType.jhtml'/>";
		top.showMyModal("选择客户类型",url,900,true);
		btn = "customTypeBtn";
	});
	
	
	//选择业务员
	$("#salesmanBtn").click(function(){
		var url ="<c:url value='/system/user/getSalesman.jhtml'/>";
		top.showMyModal("选择业务员",url ,900 ,true);
		btn = "salesmanBtn";
	});
	
});


function setReturnValue(obj){
	if(obj == "") return;
	if(btn == "areaBtn"){
		$("#area_id").val(obj.id);
		$("input[name='vo.area.areaName']").val(obj.name);
	}else if(btn == "customTypeBtn"){
		$("#customType_id").val(obj.id);
		$("input[name='vo.customType.customTypeName']").val(obj.name);
	}else if(btn == "salesmanBtn"){
		$("#user_id").val(obj.id);
		$("input[name='vo.user.userName']").val(obj.name);
	}
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
							<span id="formTitle">${act.text}客户信息</span> <span
								style="color: red;">温馨提示：如填写“件数单价”项目，此客户的物流费用计算将全部按照件数单价计算；如果按重泡货计算，“件数单价”填写0</span>
						</h5>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">客户编码</label>
									<div class="controls">
										<input name="vo.customCde" value="${vo.customCde}" type="text"
											id="vo_customCde" placeholder="请输入客户编码" msg="请输入客户编码"
											dataType="Require" /><span>*</span> <span
											id="span_customCde"></span>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">客户名称</label>
									<div class="controls">
										<input name="vo.customName" value="${vo.customName}"
											type="text" maxLength="20" placeholder="请输入名称"
											dataType="Require" msg="请输入名称" /> <span>*</span>
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">所属地区</label>
									<div class="controls">
										<input type="hidden" name="vo.area.id" value="${vo.area.id}"
											id="area_id" /> <input name="vo.area.areaName"
											value="${vo.area.areaName}" type="text" readonly="readonly"
											msg="请选择所属地区" dataType="Require" />
										<button class="btn btn-mini" type="button" id="areaBtn">选择地区</button>
										<span>*</span>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">所属业务员</label>
									<div class="controls">
										<input type="hidden" name="vo.user.id" value="${vo.user.id}"
											id="user_id" /> <input name="vo.user.userName"
											value="${vo.user.userName}" type="text" readonly="readonly"
											msg="请选择所属地区" dataType="Require" />
										<button class="btn btn-mini" type="button" id="salesmanBtn">选择业务员</button>
										<span>*</span>
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">客户类型</label>
									<div class="controls">
										<input type="hidden" name="vo.customType.id"
											value="${vo.customType.id}" id="customType_id" /> <input
											name="vo.customType.customTypeName"
											value="${vo.customType.customTypeName}" type="text"
											readonly="readonly" msg="请选择客户类型" dataType="Require" />
										<button class="btn btn-mini" type="button" id="customTypeBtn">选择客户类型</button>
										<span>*</span>
									</div>
								</div>
							</div>
							<div class="span6">
									<label class="control-label">省份</label>
									<div class="controls">
										<input name="vo.province" value="${vo.province}" type="text"
											maxLength="50" placeholder="请输入省份" />
									</div>
								<div class="control-group">
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">客户状态</label>
									<div class="controls">
										<select name="vo.state" dataType="Require" msg="请选择客户状态">
											<option value="">请选择</option>
											<c:forEach items="<%=PublicType.CustomerState.values()%>"
												var="state">
												<option value="${state.name}"
													<c:if test="${vo.state == state.name}">selected</c:if>>${state.text}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">联系人</label>
									<div class="controls">
										<input name="vo.contacts" value="${vo.contacts}" type="text"
											maxLength="50" placeholder="请输入联系人" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">联系电话</label>
									<div class="controls">
										<input name="vo.contactInfo" value="${vo.contactInfo}"
											type="text" maxLength="50" placeholder="请输入联系方式" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">地址</label>
									<div class="controls">
										<input name="vo.address" value="${vo.address}" type="text"
											placeholder="请输入地址" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">铺底额</label>
									<div class="controls">
										<input name="vo.amount" value="${vo.amount}" type="text"
											placeholder="请输入铺底额" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">账期</label>
									<div class="controls">
										<input name="vo.accountDay" value="${vo.accountDay}"
											type="text" placeholder="请输入账期" dataType="Integer"
											msg="请输入整数" />天
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">合作起始日期</label>
									<div class="controls">
										<input name="vo.beginTime" class="Wdate"
											value="<fmt:formatDate value="${vo.beginTime}" pattern="yyyy-MM-dd" />"
											type="text" readonly="readonly"
											onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">合作终止日期</label>
									<div class="controls">
										<input name="vo.endTime" class="Wdate"
											value="<fmt:formatDate value="${vo.endTime}" pattern="yyyy-MM-dd" />"
											type="text" readonly="readonly"
											onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">泡货单价</label>
									<div class="controls">
										<input name="vo.cargoPrice" value="${vo.cargoPrice}"
											type="text" placeholder="请输入泡货单价" dataType="Double"
											msg="请输入数字" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">重货单价</label>
									<div class="controls">
										<input name="vo.heavyPrice" value="${vo.heavyPrice}"
											type="text" placeholder="请输入重货单价" dataType="Double"
											msg="请输入数字" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">按件单价</label>
									<div class="controls">
										<input name="vo.unitPrice" value="${vo.unitPrice}" type="text"
											placeholder="请输入按件单价" dataType="Double" msg="请输入数字" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">配送费</label>
									<div class="controls">
										<input name="vo.deliverFee" value="${vo.deliverFee}"
											type="text" placeholder="请输入配送费" dataType="Double"
											msg="请输入数字" />
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">地区经理</label>
									<div class="controls">
										<input name="vo.areaManager" value="${vo.areaManager}"
											type="text" placeholder="请输入地区经理名称" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">大区经理</label>
									<div class="controls">
										<input name="vo.regManager" value="${vo.regManager}"
											type="text" placeholder="请输入大区经理名称" />
									</div>
								</div>
							</div>
						</div>
						<div class="control-group" style="width: 200px">
							<label class="control-label">备注</label>
							<div class="controls">
								<textarea style="width: 600px" rows="5" cols="50"
									name="vo.remark">${vo.remark }</textarea>
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
