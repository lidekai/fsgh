<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待核单管理</title>

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
	
	//删除按钮事件
	$("#delBtn").click(function(){
		getCheckBox();
		if (ids == '') {
			alert('请选择要删除的信息');
			return;
		}
		if (!confirm('确定要删除您勾选的记录？'))
			return;
		
		$("#ids").val(ids);
		$("#keys").val(keys);
		$("#form1").attr("action","delete.jhtml");
		$("#form1").submit();
	});
	
	$("button[name='view']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="hx.jhtml?vo.beginTime=${vo.beginTime}&vo.endTime=${vo.endTime}&vo.id="+id+"&key="+key + "&act=VIEW";
	});
	
	$("button[name='hx']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="hx.jhtml?vo.beginTime=${vo.beginTime}&vo.endTime=${vo.endTime}&vo.id="+id+"&key="+key + "&act=EDIT";
	});
	
	//导出按钮事件
	$("#expBtn").click(function(){
		var code=$("#code").val();
		var customName=$("#customName").val();
		var start = $("#orderDateStart").val();
		var end = $("#orderDateEnd").val();
		var state = $("#state").val();
		var url="<c:url value='/account/check-bill/exportCheckBill.jhtml'/>";
		url+="?vo.code=" + code +"&vo.customName=" + customName 
			+"&vo.beginTime="+ start+ "&vo.endTime="+ end + "&vo.state=" + state;
		
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
     <input type="hidden" name="ids" value="" id="ids"/>
     <input type="hidden" name="keys" value="" id="keys"/>
      <h5><span>待核单管理</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
 	        <li><label>单号：</label><input id="code" class="input-medium" name="vo.code" value="${vo.code}" type="text" placeholder="输入发货单号" /></li>
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  id="customName" name="vo.customName" value="${vo.customName}" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
       			<label>制单时间：</label>
       			<input type="text" class="input-medium Wdate" name="vo.beginTime" value="${vo.beginTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
       			<input type="text" class="input-medium Wdate" name="vo.endTime" value="${vo.endTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
       		</li>
       		<li>
       			<label>状态：</label> 
       			<select name="vo.state"  id="state" class="input-medium">
						<option value="">请选择</option>
						<option value="DHX" <c:if test="${vo.state == 'DHX' }">selected="selected"</c:if>>待核销</option>
						<option value="YHX" <c:if test="${vo.state == 'YHX' }">selected="selected"</c:if>>已核销</option>
				</select>
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
           	 <pt:checkFunc code="RECEIVE_BILL_DEL">
	           <button class="btn" id="delBtn"><i class="icon-trash"></i>删 除</button>
            </pt:checkFunc>
	        <button class="btn" id="expBtn"><i class="icon-list-alt"></i>导出</button>
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
				<th style="min-width:20px">单号</th>
				<th style="min-width:30px">制单时间</th>
				<th style="min-width:40px">客户类型</th>
				<th style="min-width:40px">客户地区</th>
				<th style="min-width:40px">客户编号</th>
				<th style="min-width:140px">客户名称</th>
				
				<th style="min-width:50px">本币发票额</th>
				<th style="min-width:50px">已收款合计</th>
				<th style="min-width:50px">实际收款额</th>
				<th style="min-width:50px">待费用发票额</th>
				<th style="min-width:50px">待退货额</th>
				<th style="min-width:50px">暂扣额</th>
				<th style="min-width:50px">其他余额</th>
				<th style="min-width:30px">状态</th>
				<th style="min-width:50px">操作</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td colspan="8">合计,共${fn:length(pageList.list)}条记录</td>
				<c:forEach items="${sumList }" var="s">
					<td><fmt:formatNumber value="${s[0]}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${s[1]}" pattern="#.##" /></td>
					<td><fmt:formatNumber value="${s[2]}" pattern="#.##" /></td>
					<td><fmt:formatNumber value="${s[3]}" pattern="#.##" /></td>
					<td><fmt:formatNumber value="${s[4]}" pattern="#.##" /></td>
					<td><fmt:formatNumber value="${s[5]}" pattern="#.##" /></td>
					<td><fmt:formatNumber value="${s[6]}" pattern="#.##" /></td>
				</c:forEach> 
				<td></td>
				<td></td>
			</tr>
		</tfoot>
		<tbody>
		<c:forEach items="${pageList.list}"  var="vo" varStatus="s">
			<tr>
				<td>
					<input class="chkBox" name="cid" value="${vo.id},${vo.key},${vo.customCde}" type="checkbox"/>
				</td>
				<td>${s.index + 1}</td>
				<td>${vo.code}</td>
				<td><fmt:formatDate value="${vo.createDate}" pattern="yyyy-MM-dd"/></td>
				
				<td>${vo.customType}</td>
				<td>${vo.customArea}</td>
				<td>${vo.customCde}</td>
				<td>${vo.customName}</td>
				<td><fmt:formatNumber value="${vo.countPrice }" type="currency"/></td>
				<td><fmt:formatNumber value="${vo.receivePrice }" type="currency"/></td>
				<td><fmt:formatNumber value="${vo.actualPrice }" type="currency"/></td>
				<td><fmt:formatNumber value="${vo.chargePrice }" type="currency" /></td>
				<td><fmt:formatNumber value="${vo.returnPrice }" type="currency" /></td>
				<td><fmt:formatNumber value="${vo.holdPrice }" type="currency" /></td>
				<td><fmt:formatNumber value="${vo.otherPrice }" type="currency" /></td>
				<td>${vo.state.text}</td>
				<td>
					<c:if test="${vo.state != 'DHX' }">
						<button name="view" class="btn btn-small btn-info" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-eye-open"></i>查看</button>
					</c:if>
					<c:if test="${vo.state == 'DHX' }">
						<pt:checkFunc code="CHECK_BILL_HX">
							<button name="hx" class="btn btn-small btn-success" att-id="${vo.id}" att-key="${vo.key}"><i class="icon-edit"></i>核销</button>
						</pt:checkFunc>
					</c:if>
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