<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<title>客户产品管理</title>

<script type="text/javascript">

$(document).ready(function(){
	//查看
	$("button[name='addBtn']").click(function(){
		 $("#form1").attr("action","addStoreProduct.jhtml");
		 form1.submit();	
	});
})

function myReturnValue(){
	getCheckBox();
	if (ids == '') {
		alert('请选择一条数据');
		return;
	}

	var obj = getValue(ids);
	return obj;
}
function getValue(id){
	//id = id.split(",")[0];
	var len = id.split(",").length;
	var nameStr= "";
	var stockStr= "";
	var numberStr= "";
	var productTypeStr= "";
	var standardStr= "";
	var storeNameStr= "";
	
	for(var i = 0; i< len -1; i++){
		var t = $("#s"+id.split(",")[i]);
		nameStr+=t.attr("att-name") + ",";
		stockStr+=t.attr("att-stockCde") + ",";
		numberStr+=t.attr("att-number") + ",";
		productTypeStr+=t.attr("att-productType") + ",";
		standardStr+=t.attr("att-standard") + ",";
		storeNameStr+=t.attr("att-storeName") + ",";
	}
	var myobj = {};
	
	myobj.id = id;
	myobj.name = nameStr;
	myobj.stockCde=stockStr;
	myobj.number =numberStr;
	myobj.productType = productTypeStr;
	myobj.standard = standardStr;
	myobj.storeName = storeNameStr;
	
	return myobj;
}
</script>
</head>
<body>
<div class="jt-container">
 <div class="container-fluid" >
  <div class="row-fluid">
   <div class="span12">
     <form name="form1" action="productList.jhtml" class="form-inline" method="post" id="form1">
     	  <input type="hidden" name="vo.store.custom.id" value="${vo.store.custom.id}"/>
	      <ul class="row-fluid"> 
	      	<div class="span10">
	      		<li>
	 	        	<label>门店名称：</label>
	 	        	<input class="input-medium" name="vo.store.storeName" value="${vo.store.storeName}" type="text" placeholder="输入需要搜索的名称" />
 	        	</li>
	 	        <li>
	 	        	<label>产品名称：</label>
	 	        	<input class="input-medium" name="vo.product.productName" value="${vo.product.productName}" type="text"  />
	 	        </li>
	 	        <li>
	 	        	<label>货号：</label>
	 	        	<input class="input-medium" name="vo.product.number" value="${vo.product.number}" type="text"  />
	 	        </li>
	        </div>
	        <div class="span12">
	        	<li><label><button name="searchBtn" class="btn btn-primary" type="submit">搜 索</button></label></li>
	        	<li><label><button name="addBtn" class="btn btn-primary" type="button">添加</button></label></li>
	        </div>
	      </ul>
     </form>
   </div><!-- / span12 end-->
  </div><!-- / row-fluid end-->
  <div class="row-fluid">
   <div class="span12">
      	<display:table name="pageList" id="vo" class="table table-bordered table-striped table-hover" requestURI="productList.jhtml">
       		<display:column media="html" title="<input type='checkbox' class='allchkBox' />" style="width:20px">
				<input type="checkbox" class="chkBox" name="cid" value="${vo.id},${vo.key}"/>
				<div style="display:none">
					<span id="s${vo.id }" att-name="${vo.product.productName}" att-stockCde="${vo.product.stockCde}" att-number="${vo.product.number}"
					att-productType="${vo.product.productType.productTypeName}" att-standard="${vo.product.standard}" att-storeName="${vo.store.storeName}"/></span>
				</div>
			</display:column>
	        <display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
	        <display:column title="所属门店" style="white-space:nowrap">${vo.store.storeName}</display:column>
	         <display:column title="产品名称" style="white-space:nowrap">${vo.product.productName}</display:column>
	         <display:column title="产品编码" style="white-space:nowrap">${vo.product.stockCde}</display:column>
	         <display:column title="货号" style="white-space:nowrap">${vo.product.number}</display:column>
	         <display:column title="产品类型" style="white-space:nowrap">${vo.product.productType.productTypeName}</display:column>
            <display:setProperty name="paging.banner.item_name">门店产品</display:setProperty>
            <display:setProperty name="paging.banner.items_name">门店产品</display:setProperty>
	    </display:table>
       
   </div><!-- / span12 end -->
  </div><!-- / row-fluid end -->

 </div><!--container-fluid end-->
</div><!--jt-container end-->
</body>
</html>