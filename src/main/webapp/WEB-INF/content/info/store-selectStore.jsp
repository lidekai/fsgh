<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<title>门店信息管理</title>

<script type="text/javascript">

function myReturnValue(){
	getCheckBox();
	if (ids == '') {
		alert('请选择一条数据');
		return;
	}
	if(ids.split(",").length>2){
		alert('只能选择一条数据');
		return;
	}
	var obj = getValue(ids);
	return obj;
}
function getValue(id){
	id = id.split(",")[0];
	var myobj = {};
	var t = $("#s"+id);
	
	myobj.id = id;
	myobj.name = t.attr("att-name");
	return myobj;
}
</script>
</head>
<body>
<div class="jt-container">
 <div class="container-fluid" >
  <div class="row-fluid">
   <div class="span12">
     <form name="form1" action="selectStore.jhtml" class="form-inline" method="post">
      <ul class="row-fluid"> 
      	<div class="span12">
      		<li>
	        	<label>客户名称：</label>
	        	<input  name="vo.custom.customName" value="${vo.custom.customName}" type="text" placeholder="客户名称" />
	        </li>
	        <li>
	        	<label>门店名称：</label>
	        	<input  name="vo.storeName" value="${vo.storeName }" type="text" placeholder="门店名称" />
	        </li>
	        <li>
	        	<label>门店编码：</label>
	        	<input  name="vo.storeCde" value="${vo.storeCde }" type="text" placeholder="门店编码" />
	        </li>
        	<li>
        		<label><button name="searchBtn" class="btn btn-primary" type="submit">搜 索</button></label>
        	</li>
        </div>
      </ul>
     </form>
   </div><!-- / span12 end-->
  </div><!-- / row-fluid end-->
  <div class="row-fluid">
   <div class="span12">
      	<display:table name="pageList" id="vo" class="table table-bordered table-striped table-hover" requestURI="selectStore.jhtml">
       		<display:column media="html" title="<input type='checkbox' class='allchkBox' />" style="width:20px">
				<input type="checkbox" class="chkBox" name="cid" value="${vo.id},${vo.key}"/>
				<div style="display:none">
					<span id="s${vo.id }" att-name="${vo.storeName},${vo.propertion}"/></span>
				</div>
			</display:column>
	        <display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
	         <display:column title="客户名称" style="white-space:nowrap">${vo.custom.customName}</display:column>
	         <display:column title="门店名称" style="white-space:nowrap">${vo.storeName}</display:column>
	         <display:column title="门店编码" style="white-space:nowrap">${vo.storeCde}</display:column>
	         <display:column title="销售占比" style="white-space:nowrap">${vo.propertion}${empty vo.propertion ? '' : '%'}</display:column>
            <display:setProperty name="paging.banner.item_name">门店</display:setProperty>
            <display:setProperty name="paging.banner.items_name">门店</display:setProperty>
	    </display:table>
       
   </div><!-- / span12 end -->
  </div><!-- / row-fluid end -->

 </div><!--container-fluid end-->
</div><!--jt-container end-->
</body>
</html>