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
	
	//导入U8发票
	$("#impBtn").click(function(){
		if(confirm("确定导入U8应收单？")){
			$("#impBtn").attr("disabled",true);
			$("#form1").attr("action","imp.jhtml");
			$("#form1").submit();
		}
	});
	
	//删除按钮事件
	$("#delBtn").click(function(){
		getCheckBox1();
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
	
	//生成待核单按钮事件
	$("#checkBtn").click(function(){
		getCheckBox1();
		if (ids == '') {
			alert('请选择应收单信息');
			return;
		}
		
		var cdes = cusCdes.split(",");
		
		if(cdes.length > 2){
			var customCde = cdes[0];
			for(var i = 1; i < cdes.length; i++){
				if(customCde != cdes[i] && cdes[i] != ""){
					alert("请选择同一客户的应收单进行待核单的生成");
					return;
				}
			}
		}
		
		if (!confirm('确定要将选择的应收单生成待核单吗？'))
			return;
		$("#ids").val(ids);
		$("#keys").val(keys);
		$("#form1").attr("action","createCheck.jhtml");
		$("#form1").submit();
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
		location.href="edit.jhtml?vo.beginTime=${vo.beginTime}&vo.endTime=${vo.endTime}&vo.timeType=${vo.timeType}&vo.id="+id+"&key="+key + "&act=EDIT";
	});
	
	//导出按钮事件
	$("#expBtn").click(function(){
		var csbvcode=$("#csbvcode").val();
		var customName=$("#customName").val();
		var start = $("#orderDateStart").val();
		var end = $("#orderDateEnd").val();
		var state = $("#state").val();
		var areaName = $("#areaName").val();
		var url="<c:url value='/account/receive-bill/exportReceiveBill.jhtml'/>";
		url+="?vo.csbvcode=" + csbvcode +"&vo.customName=" + customName ;
		url+="&vo.beginTime="+ start+ "&vo.endTime="+ end + "&vo.state=" + state
		   + "&vo.areaName=" + areaName;
		
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
	sumCount();
}

var cusCdes="";
function getCheckBox1() {
	ids="",keys="",cusCdes="";
	var obj = document.getElementsByName('cid');
	for ( var i = 0; i < obj.length; i++) {
		if (obj[i].checked == true) {
			var ss = obj[i].value.split(",");
			ids += ss[0] + ',';
			keys += ss[1] + ',';
			cusCdes += ss[2] + ',';
		}
	}
}

function sumCount(){
	var count = 0;
	var obj = document.getElementsByName('cid');
	for ( var i = 0; i < obj.length; i++) {
		if (obj[i].checked == true) {
			var ss = obj[i].value.split(",");
			count = (parseFloat(count) + parseFloat(ss[3])).toFixed(2);
		}
	}
	$("#count").val(count);
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
      <h5><span>应收单管理</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
 	        <li><label>销售发票号：</label><input id="csbvcode" class="input-medium" name="vo.csbvcode" value="${vo.csbvcode }" type="text" placeholder="输入发货单号" /></li>
 	        <li>
 	        	<label>所属地区：</label>
 	        	<select name="vo.areaName" class="input-medium" id="areaName">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="${areas}" var="area">
 	        			<option value="${area.areaName}" <c:if test="${vo.areaName == area.areaName}">selected="selected"</c:if>>${area.areaName}</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	        <li>
 	        	<label>客户名称：</label>
 	        	<input class="input-medium"  id="customName" name="vo.customName" value="${vo.customName }" type="text" placeholder="输入需要搜索的客户名称" />
 	        </li>
 	        <li>
       			<label>发票日期：</label>
       			<input type="text" class="input-medium Wdate" name="vo.beginTime" value="${vo.beginTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateStart"/>至
       			<input type="text" class="input-medium Wdate" name="vo.endTime" value="${vo.endTime }"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderDateEnd"/>
       		</li>
       		<li>
       			<label>状态：</label> 
       			<select name="vo.state"  id="state" class="input-medium">
						<option value="">请选择</option>
						<option value="WCL" <c:if test="${vo.state == 'WCL' }">selected="selected"</c:if>>未处理</option>
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
         	已勾本币实际发票额合计  <input id="count" value="0" style="width: 80px;"/>
         	<button class="btn" onclick="checkAll(true)"><i class="icon-ok"></i>全选</button>
         	<button class="btn" onclick="checkAll(false)"><i class="icon-remove"></i>全不选</button>
           	<pt:checkFunc code="RECEIVE_BILL_DEL">
	           <button class="btn" id="delBtn"><i class="icon-trash"></i>删 除</button>
            </pt:checkFunc>           
	        <button class="btn" id="expBtn"><i class="icon-list-alt"></i>导出</button>
            <pt:checkFunc code="RECEIVE_BILL_IMP">
	           <button class="btn" id="impBtn"><i class="icon-list-alt"></i>导入U8应收单</button>
            </pt:checkFunc>
            <pt:checkFunc code="RECEIVE_BILL_CREATE">
	           <button class="btn" id="checkBtn"><i class="icon-file"></i>生成待核单</button>
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
				<th style="min-width:20px">销售发票号</th>
				<th style="min-width:20px">凭证号</th>
				<th style="min-width:30px">发票日期</th>
				<th style="min-width:30px">到期日期</th>
				
				<th style="min-width:80px">客户类型</th>				
				<th style="min-width:80px">客户地区</th>
				<th style="min-width:80px">客户编码</th>				
				<th style="min-width:140px">客户名称</th>
				
				<th style="min-width:50px">本币发票额</th>
				<th style="min-width:50px">本币实际发票额</th>
				<th style="min-width:30px">状态</th>
				<th style="min-width:80px">操作</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td colspan="10">合计,共${fn:length(pageList.list)}条记录</td>
				<c:forEach items="${sumList }" var="s">
					<td><fmt:formatNumber value="${s[1]}" pattern="#.##" /></td>
					<td><fmt:formatNumber value="${s[2]}" pattern="#.##" /></td>
				</c:forEach> 
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tfoot>
		<tbody>
		<c:forEach items="${pageList.list}"  var="vo" varStatus="s">
			<tr>
				<td>
					<c:if test="${vo.state == 'WCL' }">
						<input class="chkBox" name="cid" value="${vo.id},${vo.key},${vo.customCde},${vo.countPrice1}" type="checkbox" onclick="sumCount()"/>
					</c:if>
				</td>
				<td>${s.index + 1}</td>
				<td>${vo.csbvcode}</td>
				<td>${vo.number}</td>
				<td><fmt:formatDate value="${vo.billDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${vo.maturityDate }" pattern="yyyy-MM-dd"/></td>
				<td>${vo.customType}</td>
				<td>${vo.customArea}</td>
				<td>${vo.customCde}</td>
				<td>${vo.customName}</td>
				<td><fmt:formatNumber value="${vo.countPrice }" pattern="#.##"/></td>
				<td><fmt:formatNumber value="${vo.countPrice1 }" pattern="#.##"/></td>
				<td>${vo.state.text}</td>
				<td>
					<button name="view" class="btn btn-small btn-info" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-eye-open"></i>查看</button>
					<c:if test="${vo.state == 'WCL' }">
						<pt:checkFunc code="RECEIVE_BILL_EDIT">
							<button name="edit" class="btn btn-small btn-success" att-id="${vo.id}" att-key="${vo.key}"><i class="icon-edit"></i>编辑</button>
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