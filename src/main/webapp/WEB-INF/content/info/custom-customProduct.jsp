<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户存货管理</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#addBtn").click(function(){
		location.href="editCuspro.jhtml";	
	});
	
	$("#delBtn").click(function(){
		getCheckBox();
		if (ids == '') {
			alert('请选择要删除的信息');
			return;
		}
		if (!confirm('确定要删除您勾选的记录？'))
			return;
		location.href = 'deleteCuspro.jhtml?ids=' + ids + "&keys="+keys;
	});
	
	$("button[name='edit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="editCuspro.jhtml?vo.id="+id+"&key="+key;
	});
	$("button[name='view']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="editCuspro.jhtml?act=VIEW&vo.id="+id+"&key="+key;
	});
	
	$("#impBtn").bind("click", function(){
	    var url="<c:url value='/info/custom/impCuspro.jhtml'/>";
		window.location.href =url;  
	  });
	
	//导出按钮事件
	 $("#expBtn").click(function(){
		var customName=$("#customName").val();
		var customCde=$("#customCde").val();
		var stockCde=$("#stockCde").val();
		var productName=$("#productName").val();
		
		var url="<c:url value='/info/custom/exportCustom.jhtml'/>";
		url+="?vo.customName=" + customName +"&vo.customCde=" + customCde + "&vo.stockCde="+ stockCde;	
		url+="&vo.productName=" + productName+"&act=CUSPRO";	
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
					<form name="form1" action="list.jhtml" class="form-inline"
						method="post">
						<input type="hidden" name="act" value="CUSPRO" />
						<h5>
							<span>客户存货管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>客户名称：</label> <input class="input-medium" id="customName"
									name="vo.customName" value="${vo.customName}" type="text"
									placeholder="输入需要搜索的客户名称" /></li>
								<li><label>客户编码：</label> <input class="input-medium" id="customCde"
									name="vo.customCde" value="${vo.customCde}" type="text"
									placeholder="输入需要搜索的客户编码" /></li>
								<li><label>存货编码：</label> <input class="input-medium" id="stockCde"
									name="vo.stockCde" value="${vo.stockCde}" type="text"
									placeholder="输入需要搜索的存货编码" /></li>
								<li><label>产品名称：</label> <input class="input-medium" id="productName"
									name="vo.productName" value="${vo.productName}" type="text"
									placeholder="输入需要搜索的产品名称" /></li>
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
						<pt:checkFunc code="INFO_CUSPRO_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus "></i>添 加
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="INFO_CUSPRO_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="INFO_CUSPRO_IMP">
							<button class="btn" id="impBtn">
								<i class="icon-list-alt"></i>导入
							</button>
						</pt:checkFunc>
						
						<button class="btn" id="expBtn">
								<i class="icon-list-alt"></i>导出
						</button>
					</div>
					<display:table name="pageList" id="vo"
						class="table table-bordered table-striped table-hover">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px;vertical-align:middle">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
						</display:column>
						<display:column title="序号"
							style="white-space:nowrap;vertical-align:middle">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="客户编码"
							style="white-space:nowrap;vertical-align:middle">${vo.customCde}</display:column>
						<display:column title="客户名称"
							style="white-space:nowrap;vertical-align:middle">${vo.customName}</display:column>
						<display:column title="产品信息 ( 产品存货编码 | 产品名称 | 产品规格 | 产品所属分类)"
							style="white-space:nowrap">
							<table class="table table-bordered table-striped table-hover">
								<thead>
									<c:forEach items="${vo.products}" var="product" varStatus="i">
										<tr>
											<td>${product.stockCde}</td>
											<td>${product.productName}</td>
											<td>${product.standard}</td>
											<td>${product.productType.productTypeName}</td>
										</tr>
									</c:forEach>
								</thead>
							</table>
						</display:column>
						<display:column title="操  作"
							style="white-space:nowrap;vertical-align:middle">
							<button name="view" class="btn btn-small btn-info"
								att-id="${vo.id }" att-key="${vo.key }">
								<i class="icon-eye-open"></i>查看
							</button>
							<pt:checkFunc code="INFO_CUSPRO_EDIT">
								<button name="edit" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-edit"></i>编辑
								</button>
							</pt:checkFunc>
						</display:column>
						<display:setProperty name="paging.banner.item_name">客户存货</display:setProperty>
						<display:setProperty name="paging.banner.items_name">客户存货</display:setProperty>
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