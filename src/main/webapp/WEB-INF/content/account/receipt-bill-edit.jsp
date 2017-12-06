<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<title>收款单管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  	history.back();
	});
	//保存
	$("#saveBtn").click(function(){
		var receiptCount1 = $("#receiptCount1").val();
		if(receiptCount1 == '' || receiptCount1 == null){
			alert("本币实际收款金额不能为空！");
			return false;
		}else if(isNaN(receiptCount1)){
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
			<span id="formTitle">查看收款单</span>
		</h5>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">类别</label>
					<div class="controls">
						<input name="vo.cvouchType" value="${vo.cvouchType == 48 ? '收': (vo.cvouchType == 49 ? '付' : '')}" type="text" readonly="readonly" />
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">收款单号</label>
					<div class="controls">
						<input name="vo.cvouchId" value="${vo.cvouchId}" type="text" readonly="readonly" />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">收款日期</label>
					<div class="controls">
						<input name="receiptDate" value="<fmt:formatDate value="${vo.receiptDate }" pattern="yyyy-MM-dd"/>" type="text" readonly="readonly"/>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">客户编号</label>
					<div class="controls">
						<input name="vo.customerCde" value="${vo.customerCde}" type="text" readonly="readonly"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">客户名称</label>
					<div class="controls">
						<input name="vo.customerName" value="${vo.customerName}" type="text"  readonly="readonly"/>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">结算编号</label>
					<div class="controls">
						<input name="vo.cssCode" value="${vo.cssCode}" type="text" readonly="readonly"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">结算名称</label>
					<div class="controls">
						<input name="vo.cssName" value="${vo.cssName}" type="text"  readonly="readonly"/>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label">本币收款金额 </label>
					<div class="controls">
						<input name="vo.receiptCount" value="<fmt:formatNumber value='${vo.receiptCount}' pattern='#.##'/>" type="text"  readonly="readonly"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label">本币实际收款金额 </label>
					<div class="controls">
						<input name="vo.receiptCount1" value="<fmt:formatNumber value='${vo.receiptCount1}' pattern='#.##'/>" type="text"  id="receiptCount1"/>
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