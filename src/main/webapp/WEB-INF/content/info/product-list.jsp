<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品管理</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#addBtn").click(function(){
		location.href="edit.jhtml";	
	});
	
	$("#delBtn").click(function(){
		getCheckBox();
		if (ids == '') {
			alert('请选择要删除的信息');
			return;
		}
		if (confirm('确定要删除您勾选的记录？')){
			$("#ids").val(ids);
			$("#keys").val(keys);
			$("#form1").attr("action","delete.jhtml");
			$("#form1").submit();
		}
		
	});
	
	$("button[name='edit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.id="+id+"&key="+key;
	});
	
	$("button[name='view']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?act=VIEW&vo.id="+id+"&key="+key;
	});
	
	  $("#imp").bind("click", function(){
	    var url="<c:url value='/info/product/imp.jhtml'/>";
		window.location.href =url;  
	  });
	  
		//新品 
		$("#new").click(function(){
			getCheckBox();
			if(ids == ''){
				alert('请选择要更新为新品的信息');
				return;
			}
			if(confirm('确定要更新你勾选的记录？')){
				$("#ids").val(ids);
				$("#keys").val(keys);
				$("#isType").val(true);
				$("#form1").attr("action","modifyNewProduct.jhtml");
				$("#form1").submit();
			}
		});
		
		//非新品 
		$("#notNew").click(function(){
			getCheckBox();
			if(ids == ''){
				alert('请选择要更新为非新品的信息');
				return;
			}
			if(confirm('确定要更新你勾选的记录？')){
				$("#ids").val(ids);
				$("#keys").val(keys);
				$("#isType").val(false);
				$("#form1").attr("action","modifyNewProduct.jhtml");
				$("#form1").submit();
			}
		});
	  
		
		//绑定导出按钮点击事件
		$("#exp").click(function(){
			var productName=$("#productName").val();
			var stockCde=$("#stockCde").val();
			var productCde=$("#productCde").val();
			var number=$("#number").val();
			var productTypeID = $("#productTypeID").val();
			var chargeType=$("#chargeType").val();
			var newProduct = $("#newProduct").val();
			
			var url="<c:url value='/info/product/exportProduct.jhtml'/>";
			url+="?vo.productName=" + productName +"&vo.stockCde=" + stockCde + "&vo.productCde="+ productCde;
			url+="&vo.number=" + number +"&vo.productType.id="+ productTypeID+ "&vo.chargeType="+ chargeType +"&vo.newProduct="+ newProduct;
			
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
    				<input type="hidden" name="isType" value="" id="isType"/>
						<h5>
							<span>产品管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>产品名称：</label><input class="input-medium"  id="productName"
									name="vo.productName" value="${vo.productName}" type="text"
									placeholder="输入产品名称" /></li>
								<li><label>存货编码：</label><input class="input-medium"  id="stockCde"
									name="vo.stockCde" value="${vo.stockCde}" type="text"
									placeholder="输入存货编码" /></li>
								<li><label>存货代码：</label><input class="input-medium"  id="productCde"
									name="vo.productCde" value="${vo.productCde}" type="text"
									placeholder="输入存货代码" /></li>
								<li><label>货号：</label><input class="input-medium"  id="number"
									name="vo.number" value="${vo.number}" type="text"
									placeholder="输入货号" /></li>
								<li><label>产品类型：</label> <select class="span150"
									name="vo.productType.id" id="productTypeID">
										<option value="">请选择</option>
										<c:forEach items="${productTypeList}" var="productType">
											<option value="${productType.id}"
												<c:if test="${vo.productType.id==productType.id}" >selected="selected"</c:if>>${productType.productTypeName}</option>
										</c:forEach>
								</select></li>
								<li><label>计费形式：</label> <select class="span80"
									name="vo.chargeType" id="chargeType">
										<option value="">请选择</option>
										<option value="WEIGHT"
											<c:if test="${vo.chargeType=='WEIGHT'}" >selected="selected"</c:if>>重货</option>
										<option value="VOLUME"
											<c:if test="${vo.chargeType=='VOLUME'}" >selected="selected"</c:if>>泡货</option>
								</select></li>

								<li><label>是否新品：</label> <select class="span80"
									name="vo.newProduct" id="newProduct">
										<option value="">请选择</option>
										<option value="true"
											<c:if test="${vo.newProduct == true }">selected="selected"</c:if>>是</option>
										<option value="false"
											<c:if test="${vo.newProduct == false }">selected="selected"</c:if>>否</option>
								</select></li>

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
						<pt:checkFunc code="INFO_PRODUCT_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus "></i>添 加
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="INFO_PRODUCT_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="INFO_PRODUCT_DEL">
							<button class="btn" id="new">
								<i class="icon-ok-circle"></i>新品
							</button>
							<button class="btn" id="notNew">
								<i class="icon-remove-circle"></i>非新品
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="INFO_PRODUCT_IMPORT">
							<button class="btn" id="imp">
								<i class="icon-list-alt"></i>导入
							</button>
						</pt:checkFunc>
						<button class="btn" id="exp">
								<i class="icon-list-alt"></i>导出
						</button>
					</div>

					<display:table name="pageList" id="vo"
						class="table table-bordered table-striped table-hover">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
						</display:column>
						<display:column title="序号" style="white-space:nowrap;width:30px">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="存货编码" style="white-space:nowrap;width:50px">${vo.stockCde}</display:column>
						<display:column title="存货代码" style="white-space:nowrap;width:30px">${vo.productCde}</display:column>
						<display:column title="货号" style="white-space:nowrap;width:30px">${vo.number}</display:column>
						<display:column title="产品名称" style="white-space:nowrap;">${vo.productName}</display:column>
						<display:column title="产品类型" style="white-space:nowrap;">${vo.productType.productTypeName}</display:column>
						<display:column title="产品规格" style="white-space:nowrap">${vo.standard}</display:column>
						<display:column title="每箱毛重（公斤）"
							style="white-space:nowrap;width:50px;">${vo.boxWeight}</display:column>
						<display:column title="每箱体积（立方米）"
							style="white-space:nowrap;width:65px">${vo.volume}</display:column>
						<display:column title="每箱立方米重量（公斤）"
							style="white-space:nowrap;width:85px">${vo.meterWeight}</display:column>
						<display:column title="计费形式" style="white-space:nowrap;width:60px">${vo.chargeType.text}</display:column>
						<display:column title="是否新品" style="white-space:nowrap">
							<c:if test="${vo.newProduct==true}">是</c:if>
							<c:if test="${vo.newProduct==false}">否</c:if>					
						</display:column>
						<display:column title="操  作" style="white-space:nowrap">
							<button name="view" class="btn btn-small btn-info"
								att-id="${vo.id }" att-key="${vo.key }">
								<i class="icon-eye-open"></i>查看
							</button>
							<pt:checkFunc code="INFO_PRODUCT_EDIT">
								<button name="edit" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-edit"></i>编辑
								</button>
							</pt:checkFunc>
						</display:column>
						<display:setProperty name="paging.banner.item_name">产品</display:setProperty>
						<display:setProperty name="paging.banner.items_name">产品</display:setProperty>
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