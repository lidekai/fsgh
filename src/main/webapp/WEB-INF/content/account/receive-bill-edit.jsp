<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<title>销售发票管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  	history.back();
	});
	//保存
	$("#saveBtn").click(function(){
		var countPrice1 = $("#countPrice1").val();
		if(countPrice1 == '' || countPrice1 == null){
			alert("本币实际价税合计不能为空！");
			return false;
		}else if(isNaN(countPrice1)){
			alert("请输入正确的数字");
			return false;
		}
		form1.submit();
	});
});



</script>
</head>
<body>
<div class="jt-container">
 <div class="container-fluid">
  <div class="row-fluid">
   <div class="span12">
   <form name="form1" action="update.jhtml" class="form-horizontal" method="post">
   <input name="vo.beginTime" value="${vo.beginTime}" type="hidden"/>
   <input name="vo.endTime" value="${vo.endTime}" type="hidden"/>
   <input name="vo.id" value="${vo.id}" type="hidden"/>
   <input name="key" value="${key}" type="hidden"/>
		<h5>
			<span id="formTitle">查看应收单</span>
		</h5>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">销售发票号</label>
					<div class="controls">
						<input name="vo.csbvcode" value="${vo.csbvcode}" type="text" readonly="readonly" />
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">凭证号</label>
					<div class="controls">
						<input name="vo.number" value="${vo.number}" type="text" readonly="readonly" />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">发票日期</label>
					<div class="controls">
						<input name="vo.billDate" value="<fmt:formatDate value='${vo.billDate}' pattern='yyyy-MM-dd'/>" type="text" readonly="readonly"/>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">到期日期</label>
					<div class="controls">
						<input name="vo.maturityDate" value="<fmt:formatDate value='${vo.maturityDate}' pattern='yyyy-MM-dd'/>" type="text" readonly="readonly"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">客户类型</label>
					<div class="controls">
						<input name="vo.customType" value="${vo.customType}" type="text"  readonly="readonly"/>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">客户地区</label>
					<div class="controls">
						<input name="vo.customArea" value="${vo.customArea}" type="text" readonly="readonly"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">客户编码</label>
					<div class="controls">
						<input name="vo.customCde" value="${vo.customCde}" type="text"  readonly="readonly"/>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">客户名称</label>
					<div class="controls">
						<input name="vo.customName" value="${vo.customName}" type="text" readonly="readonly"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">本币发票额</label>
					<div class="controls">
						<input name="vo.countPrice" value="<fmt:formatNumber value='${vo.countPrice}' pattern='#.##'/>" type="text"  readonly="readonly"/>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">本币实际发票额</label>
					<div class="controls">
						<input name="vo.countPrice1" value="<fmt:formatNumber value='${vo.countPrice1}' pattern='#.##'/>" type="text"  id="countPrice1"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
			<div class="control-group mT30">
				<div class="controls" style="margin:0 auto;text-align: center;">
					<c:if test="${act != 'VIEW' }">
						<button type="button" id="saveBtn" class="btn btn-primary">确定</button>
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