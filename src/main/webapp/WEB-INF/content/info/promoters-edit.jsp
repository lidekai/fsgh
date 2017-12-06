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

<title>促销员管理</title>
<script type="text/javascript">
var screenwidth = $(window).width();
var screenheight = $(window).height();
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
	  /* var url="<c:url value='/info/promoters/list.jhtml'/>";
	  window.location.href=url; */
	  history.back();
	});
	
	var act ='${act}';
	if(act == 'VIEW'){
		 $('input').attr("disabled","disabled")//将input元素设置为disabled
		 $("#customBtn").hide();
		 $("#storeBtn").hide();
	}
	
	
	$("#customBtn").click(function(){
		var url="<c:url value='/info/custom/selectCustom.jhtml'/>";
		top.showMyModal("请选择所属客户",url,900,true);
		btn="customBtn";
	});
	
	$("#storeBtn").click(function(){
		var url="<c:url value='/info/store/selectStore.jhtml'/>";
		top.showMyModal("请选择所属门店",url ,900,true);
		btn="storeBtn";
	});
	
});

function setReturnValue(obj){
	if(obj == '')
		return;
	if(btn == "customBtn"){
		$("#custom_id").val(obj.id);
		$("input[name='vo.custom.customName']").val(obj.name);
	}else if(btn == "storeBtn"){
		$("#store_id").val(obj.id);
		$("input[name='vo.store.storeName']").val(obj.name);
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
							<span id="formTitle">${act.text}促销员信息</span>
						</h5>
						<div class="control-group">
							<label class="control-label">促销员名称</label>
							<div class="controls">
								<input name="vo.promotersName" id="vo_name"
									value="${vo.promotersName}" type="text" maxLength="15"
									placeholder="请输入名称" dataType="Require" msg="请输入促销员名称" /> <span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">身份证号</label>
							<div class="controls">
								<input name="vo.IDCare" value="${vo.IDCare}" type="text"
									placeholder="请输入身份证号" msg="请输入身份证号" dataType="IdCard" /><span>*</span>
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
							<label class="control-label">所属门店</label>
							<div class="controls">
								<input type="hidden" name="vo.store.id" value="${vo.store.id}"
									id="store_id" /> <input name="vo.store.storeName"
									value="${vo.store.storeName}" type="text" readonly="readonly"
									msg="请选择所属门店" dataType="Require" />
								<button class="btn btn-mini" type="button" id="storeBtn">选择门店</button>
								<span>*</span>
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
							<label class="control-label">银行卡号</label>
							<div class="controls">
								<input name="vo.bankCare" value="${vo.bankCare}" type="text"
									maxLength="20" placeholder="请输入银行卡号" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">开户地</label>
							<div class="controls">
								<input name="vo.bankLocal" value="${vo.bankLocal}" type="text"
									placeholder="请输入开户地" />
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