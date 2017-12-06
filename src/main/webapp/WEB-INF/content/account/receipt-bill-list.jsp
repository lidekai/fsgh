<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>应收单管理</title>

<script type="text/javascript" src="<c:url value='/lib/fixedHeader/demo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/lib/fixedHeader/jquery.fixedheadertable.js'/>"></script>
<link href="<c:url value='/css/fixedHeader/960.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/defaultTheme.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/fixedHeader/myTheme.css'/>" rel="stylesheet" type="text/css" />
<script>
$(document).ready(function(){
	
	$('#result').fixedHeaderTable({
		altClass: 'odd',
		footer: true
	});
	
	//导入U8收款单
	$("#impBtn").click(function(){
		if(confirm("确定导入U8收款单？")){
			$("#impBtn").attr("disabled",true);
			$("#form1").attr("action","imp.jhtml");
			$("#form1").submit();
		}
	});
	
	//删除按钮事件
	$("#delBtn").click(function(){
		getCheckBox();
		if (ids == '') {
			alert('请选择要删除的信息');
			return;
		}
		if (!confirm('确定要删除您勾选的记录？'))
			return;
		location.href = 'delete.jhtml?vo.beginTime=${vo.beginTime}&vo.endTime=${vo.endTime}&ids=' + ids + "&keys="+keys;
	});
	
	
	$("button[name='view']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.id="+id+"&key="+key + "&act=VIEW";
	});
	

	$("button[name='edit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.beginTime=${vo.beginTime}&vo.endTime=${vo.endTime}&vo.id="+id+"&key="+key + "&act=EDIT";
	});
	
	//导出按钮事件
	$("#expBtn").click(function(){
		var cvouchId=$("#cvouchId").val();
		var customerName=$("#customerName").val();
		var start = $("#orderDateStart").val();
		var end = $("#orderDateEnd").val();
		var url="<c:url value='/account/receipt-bill/exportReceiptBill.jhtml'/>";
		url+="?vo.cvouchId=" + cvouchId +"&vo.customerName=" + customerName 
				+"&vo.beginTime="+ start+ "&vo.endTime="+ end;
		
		top.showView("请选择导出字段", url , 900);
	});
	
});
	
function checkAll(flag){
	//遍历当前选中复选框的table
	$('.chkBox').each(function() {   //勾选、取消后的行背景色
		
		this.checked = flag;

		if($(this).is(":checked")){
			$(this).parents("tr").addClass("tr-checked");
		}else{
			$(this).parents("tr").removeClass("tr-checked");
		}
	});
}

</script>

</head>
<body>
<div class="jt-container">
 <div class="container-fluid">
  <div class="row-fluid">
   <div class="span12" style="min-width:1000px;">
     <form name="form1" action="list.jhtml" class="form-inline" method="post" id="form1">
      <h5><span>收款单管理</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
 	        <li><label>收款单号：</label><input id="cvouchId" class="input-medium" name="vo.cvouchId" value="${vo.cvouchId}" type="text" placeholder="输入发货单号" /></li>
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  id="customerName" name="vo.customerName" value="${vo.customerName }" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
       			<label>收款日期：</label>
       			<input type="text" class="input-medium Wdate" name="vo.beginTime" value="${vo.beginTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
       			<input type="text" class="input-medium Wdate" name="vo.endTime" value="${vo.endTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
       		</li>
        	<li><label><button name="searchBtn" class="btn btn-primary" type="submit">搜 索</button></label></li>
        </div>
      </ul>
     </form>
   </div><!-- / span12 end-->
  </div><!-- / row-fluid end-->
  <div class="row-fluid">
   <div class="span12" style="min-width:1000px;">
         <div class="pull-right  mB10">
         	<button class="btn" onclick="checkAll(true)"><i class="icon-ok"></i>全选</button>
         	<button class="btn" onclick="checkAll(false)"><i class="icon-remove"></i>全不选</button>
           	<pt:checkFunc code="RECEIPT_BILL_DEL">
	           <button class="btn" id="delBtn"><i class="icon-trash"></i>删 除</button>
            </pt:checkFunc>           
	        <button class="btn" id="expBtn"><i class="icon-list-alt"></i>导出</button>
            <pt:checkFunc code="RECEIPT_BILL_IMP">
	           <button class="btn" id="impBtn"><i class="icon-list-alt"></i>导入U8收款单</button>
            </pt:checkFunc>
         </div>
   </div><!-- / span12 end -->
  </div><!-- / row-fluid end -->
 </div><!--container-fluid end-->
<div class="container_12 divider">
 		<div class="grid_4 height400" id="page1">
	 	<table class="fancyTable" id="result" cellpadding="0" cellspacing="0">
	    <thead>
			<tr> 
				<th style="min-width:20px"></th>
				<th style="min-width:20px">序号</th>
				<th style="min-width:20px">收款单号</th>
				<th style="min-width:30px">收款日期</th>
				<th style="min-width:140px">客户名称</th>
				<th style="min-width:50px">客户编号</th>				
				
				<th style="min-width:30px">类别</th>
				<th style="min-width:50px">结算编号</th>
				<th style="min-width:50px">结算名称</th>
				<th style="min-width:70px">本币收款金额</th>
				<th style="min-width:70px">本币实际收款金额</th>
				<th style="min-width:70px">本币未核余额</th>
				<th style="min-width:80px">操作</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td colspan="${empty sumList ? '12': '9'}">合计,共${fn:length(pageList.list)}条记录</td>
				<c:forEach items="${sumList }" var="s">
					<td><fmt:formatNumber value="${s[0]}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${s[1]}" pattern="#.##" /></td>
					<td><fmt:formatNumber value="${s[2]}" pattern="#.##" /></td>
				</c:forEach> 
				<td></td>
			</tr>
		</tfoot>
		<tbody>
		<c:forEach items="${pageList.list}"  var="vo" varStatus="s">
			<tr>
				<td><input class="chkBox" name="cid" value="${vo.id},${vo.key}" type="checkbox"></td>
				<td>${s.index + 1}</td>
				<td>${vo.cvouchId}</td>
				<td><fmt:formatDate value="${vo.receiptDate }" pattern="yyyy-MM-dd"/></td>
				<td>${vo.customerName}</td>
				<td>${vo.customerCde}</td>
				<td>${vo.cvouchType == 48 ? '收': (vo.cvouchType == 49 ? '付' : '')}</td>
				<td>${vo.cssCode}</td>
				<td>${vo.cssName }</td>
				<td><fmt:formatNumber value="${vo.receiptCount}" pattern="#.##" /></td>
				<td><fmt:formatNumber value="${vo.receiptCount1}" pattern="#.##" /></td>
				<td><fmt:formatNumber value="${vo.receiveCount}" pattern="#.##" /></td>
				<td>
					<button name="view" class="btn btn-small btn-info" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-eye-open"></i>查看</button>
					<pt:checkFunc code="RECEIPT_BILL_EDIT">
						<button name="edit" class="btn btn-small btn-success" att-id="${vo.id}" att-key="${vo.key}"><i class="icon-edit"></i>编辑</button>
					</pt:checkFunc>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</div><!--jt-container end-->

</body>
</html>