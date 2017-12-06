<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
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

<title>门店管理</title>
<script type="text/javascript">
var screenwidth = $(window).width();
var screenheight = $(window).height();
$(document).ready(function(){
	//确定按钮事件
	$("#saveBtn").click(function(){
		if(Validator.Validate(form1,3,3)){
			form1.submit();					
		}
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	 /*  var url="<c:url value='/info/store/list.jhtml'/>";
	  window.location.href=url; */
	  history.back();
	});
	
	var act ='${act}';
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled 
		 $("#customBtn").hide();
	}
	
	
	$("#customBtn").click(function(){
		var url="<c:url value='/info/custom/selectCustom.jhtml'/>";
		top.showMyModal("请选择所属客户",url ,900,true);
	});
	
});

function setReturnValue(obj){
	if(obj == '')
		return;
	$("#custom_id").val(obj.id);
	$("input[name='vo.custom.customName']").val(obj.name);
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
							<span id="formTitle">${act.text}门店信息</span>
						</h5>
						<div class="control-group">
							<label class="control-label">门店名称</label>
							<div class="controls">
								<input name="vo.storeName" value="${vo.storeName}" type="text"
									maxLength="20" placeholder="请输入名称" dataType="Require"
									msg="请输入名称" /> <span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">门店编码</label>
							<div class="controls">
								<input name="vo.storeCde" value="${vo.storeCde}" type="text"
									placeholder="请输入门店编码" msg="请输入门店编码" dataType="Require" /><span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">所属客户</label>
							<div class="controls">
								<input type="hidden" name="vo.custom.id" value="${vo.custom.id}"
									id="custom_id" /> <input name="vo.custom.customName"
									value="${vo.custom.customName}" type="text" readonly="readonly"
									msg="请选择所属客户" dataType="Require" />
								<button class="btn btn-mini" type="button" id="customBtn">选择客户</button>
								<span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">所在城市</label>
							<div class="controls">
								<input name="vo.city" value="${vo.city}" type="text"
									maxLength="50" placeholder="请输入所在城市" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">提成比例</label>
							<div class="controls">
								<input name="vo.propertion" value="${vo.propertion}" type="text"
									placeholder="请输入销售额占比" msg="请输入数字" dataType="Double" />%
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">联系人</label>
							<div class="controls">
								<input name="vo.contacts" value="${vo.contacts}" type="text"
									maxLength="50" placeholder="请输入联系人" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">联系电话</label>
							<div class="controls">
								<input name="vo.contactsInfo" value="${vo.contactsInfo}"
									type="text" maxLength="50" placeholder="请输入联系方式" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">地址</label>
							<div class="controls">
								<input name="vo.address" value="${vo.address}" type="text"
									placeholder="请输入地址" />
							</div>
						</div>
						
					
						<div class="control-group" style="width: 200px">
							<label class="control-label">备注</label>
							<div class="controls">
								<textarea style="width: 500px" rows="5" cols="50"
									name="vo.remark">${vo.remark }</textarea>
							</div>
						</div>



						<div class="control-group mT30">
							<div class="controls">
								<c:if test="${act != 'VIEW' }">
									<button type="button" id="saveBtn" class="btn btn-primary">确
										定</button>
								</c:if>
								<button type="button" id="cancelBtn" class="btn btn-primary">取
									消</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>