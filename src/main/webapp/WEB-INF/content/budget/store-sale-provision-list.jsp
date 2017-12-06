<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>门店销售预提管理</title>
<script type="text/javascript">
$(document).ready(function(){
	
	//添加
	$("#addBtn").click(function(){
		location.href="edit.jhtml";	
	});
	
	//删除
	$("#delBtn").click(function(){
		dels();	
	});
	
	//导入
	$("#impBtn").click(function(){
		location.href="imp.jhtml";
	});
	
	
	//编辑
	$("button[name='edit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.dateStart=${vo.dateStart}&vo.dateEnd=${vo.dateEnd}&vo.id="+id+"&key="+key;
	});
	
	//查看
	$("button[name='view']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.id="+id+"&key="+key +"&act=VIEW";
	});
	
	//导出按钮事件
	 $("#exp").click(function(){
		var storeName=$("#storeName").val();
		var storeCde=$("#storeCde").val();
		var dateStart=$("#dateStart").val();
		var dateEnd = $("#dateEnd").val();
	
		var url = "<c:url value='/budget/store-sale-provision/export.jhtml'/>";
		url += "?vo.store.storeName=" + storeName +"&vo.store.storeCde=" + storeCde
			+ "&vo.dateStart="+ dateStart +"&vo.dateEnd="+ dateEnd;;
		
		
		top.showView("请选择导出字段", url , 900);
	}); 
	
	
});
	
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<form name="form1" action="list.jhtml" class="form-inline" method="post" id="form1">
					<input type="hidden" name="ids" value="" id="ids"/>
    				<input type="hidden" name="keys" value="" id="keys"/>
						<h5>
							<span>门店销售预提管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>门店名称：</label> <input class="input-medium" id="storeName"
									name="vo.store.storeName" value="${vo.store.storeName}" type="text"
									placeholder="输入需要搜索的名称" /></li>
								<li><label>门店编码：</label> <input class="input-medium" id="storeCde"
									name="vo.store.storeCde" value="${vo.store.storeCde}" type="text"
									placeholder="输入需要搜索的编码" /></li>
								<li>
					       			<label>预提时间：</label>
					       			<input type="text" id="dateStart" class="input-medium Wdate" name="vo.dateStart" value="${vo.dateStart}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" />至
					       			<input type="text" id="dateEnd" class="input-medium Wdate" name="vo.dateEnd" value="${vo.dateEnd}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/>
					       			
					       		</li>
							</div>
							<div class="span2">
								<li><label><button name="searchBtn"
											class="btn btn-primary" type="submit">搜 索</button></label></li>
							</div>
						</ul>
					</form>
				</div>
				<!-- / span12 end-->
			</div>
			<!-- / row-fluid end-->
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					 <div class="pull-right  mB10">
			         	<pt:checkFunc code="BUD_STOREPRO_ADD">
			           		<button class="btn" id="addBtn"><i class="icon-plus "></i>添 加</button>
			           	</pt:checkFunc>
			           	<pt:checkFunc code="BUD_STOREPRO_DEL">
				           <button class="btn" id="delBtn"><i class="icon-trash"></i>删 除</button>
			            </pt:checkFunc>
			            <pt:checkFunc code="BUD_STOREPRO_IMP">
							<button class="btn" id="impBtn"><i class="icon-list-alt"></i>导入</button>
						</pt:checkFunc>
			            <button class="btn" id="exp"><i class="icon-list-alt"></i>导出</button>
			         </div>
			       
			       	<display:table name="pageList" id="vo" class="table table-bordered table-striped table-hover">
			       		<display:column media="html" title="<input type='checkbox' class='allchkBox' />" style="width:20px">
							<input type="checkbox" class="chkBox" name="cid" value="${vo.id},${vo.key}"/>
						</display:column>
				        <display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
				        <display:column title="所属客户" style="white-space:nowrap">${vo.store.custom.customName}</display:column>
				        <display:column title="门店名称" style="white-space:nowrap">${vo.store.storeName}</display:column>
				        <display:column title="门店编码" style="white-space:nowrap">${vo.store.storeCde}</display:column>
				        <display:column title="提成比例" style="white-space:nowrap">${vo.store.propertion}${empty vo.store.propertion ? '' : '%'}</display:column>
				        <display:column title="销售额" style="white-space:nowrap">${vo.saleSum}</display:column>
				        <display:column title="固定金额" style="white-space:nowrap">${vo.fixSum}</display:column>
				        <display:column title="预提费用" style="white-space:nowrap">${vo.count}</display:column>
				        <display:column title="预提时间" style="white-space:nowrap"><fmt:formatDate value="${vo.provisionTime}" pattern="yyyy-MM-dd" /></display:column>
			            <display:column title="操  作" style="width:250px;">
			            	<button name="view" class="btn btn-small btn-info" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-eye-open"></i>查看</button>
			            	<pt:checkFunc code="BUD_STOREPRO_EDIT">
			               		<button name="edit" class="btn btn-small btn-success" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-edit"></i>编辑</button>
			               	</pt:checkFunc>
			            </display:column>
			            <display:setProperty name="paging.banner.item_name">门店销售预提</display:setProperty>
			            <display:setProperty name="paging.banner.items_name">门店销售预提</display:setProperty>
				    </display:table>
				</div>
				<!-- / span12 end -->
			</div>
			<!-- / row-fluid end -->

		</div>
		<!--container-fluid end-->
	</div>
	<!--jt-container end-->
</body>
</html>