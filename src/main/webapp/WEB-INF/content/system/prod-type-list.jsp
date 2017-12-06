<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<%@ include file="/WEB-INF/common/meta.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/lib-ext/zTree-3.5/css/demo.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/lib-ext/zTree-3.5/css/zTreeStyle/zTreeStyle.css'/>" />
<script type="text/javascript"
	src="<c:url value='/lib-ext/zTree-3.5/js/jquery-1.4.4.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/lib-ext/zTree-3.5/js/jquery.ztree.core-3.5.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/lib-ext/zTree-3.5/js/jquery.ztree.excheck-3.5.min.js'/>"></script>

<script type="text/javascript"
	src="<c:url value='/lib-ext/zTree-3.5/js/jquery.ztree.exedit-3.5.js'/>"></script>
<script type="text/javascript">
var setting = {
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all",
		chkboxType : {"Y":"s","N":"s"}
	},
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pid",
			rootPId: 0
		}
	},
	callback: {
		onCheck: zTreeOnCheck,
		beforeClick: beforeClick
	}
};



var zNodes = ${treeJSON};

$(document).ready(function(){
  $.fn.zTree.init($("#treeDemo"), setting, zNodes);
  
  $("#expand").bind("click", function(){
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	if($("#flag").val() == 1){
	  treeObj.expandAll(false); 
	  $("#flag").val(0);
	  $("#expand").val("展开");
	}else{
	  treeObj.expandAll(true); 
	  $("#flag").val(1);
	  $("#expand").val("收缩");
	}
		 
  });
  
  $("#adds").bind("click", function(){
    var selects=$("#typeIds").val();
    var prodsType=$("#prodsType").val();
    
    if(selects == "" || selects == null){
      var url="<c:url value='/system/prod-type/first.jhtml'/>";
  	  window.location.href=url;
	}else{
	  if(prodsType=="CP4"){
	    alert("最多可以添加4层产品类别");
	  }else{
		var url="<c:url value='/system/prod-type/add.jhtml'/>?parentVO.id="+selects;
		window.location.href=url; 
	  }
	  
    }
  });
  
  $("#updates").bind("click", function(){
	var parentId=$("#parentIds").val();
    var selects = $("#typeIds").val();
    var key=$("#key").val();
	if(selects == "" || selects == null){
	  alert("请选择指定的产品类别进行编辑操作");
	}else{
	  var url="<c:url value='/system/prod-type/edit.jhtml'/>?vo.id="+selects+"&key="+key+"&parentVO.id="+parentId;
	  window.location.href=url;
	}
  });
  
  $("#delete").bind("click", function(){
	var selects = $("#typeIds").val();
    var key=$("#key").val();
	if(selects == "" || selects == null){
	  alert("请选择指定的产品类别");
	}else{
	  if(confirm("确认删除该产品类别吗？")){
		var url="<c:url value='/system/prod-type/delete.jhtml'/>?id="+selects+"&keys="+key;
	    window.location.href =url;
	  }
	}
    
  });
  
  $("#imp").bind("click", function(){
    var url="<c:url value='/system/prod-type/imp.jhtml'/>";
	window.location.href =url;  
  });
 
});

function zTreeOnCheck(event, treeId, treeNode){
  $("#parentIds").val(treeNode.pid);
  $("#typeIds").val(treeNode.id);
  $("#key").val(treeNode.key);
  $("#prodsType").val(treeNode.prodsType);
}

//节点单击事件
function beforeClick(treeId,treeNode){
  var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
  treeObj.checkNode(treeNode, !treeNode.checked, null, true);
  return false;
}


//搜索树
function searchTree(){
	var treeNode;
	var searchName=$("#searchName").val();
	if(searchName == ""){
		$("#treeDemo").html("加载中，请稍后...");
	}
	$.post(
		"searchTree.jhtml",
		{
			"vo.name"	:searchName
		},
		function(data){
			treeNode = eval(data);
  			$.fn.zTree.init($("#treeDemo"), setting, treeNode);
		}
	);
}


</script>
<style type="text/css">
.search {
	position: absolute;
	top: 30px;
	left: 400px;
}
</style>
</head>
<body>
	<div class="ifrm-cont">
		<div class="pos clear">
			<h2 class="title01-cont floatL">产品类别管理</h2>
		</div>
		<div>
			<input type="hidden" value="1" id="flag" /> <input type="hidden"
				id="parentIds" /> <input type="hidden" id="typeIds" /> <input
				type="hidden" id="key" /> <input type="hidden" id="prodsType" /> <input
				type="button" class="btn btn-small btn-success" value="收缩"
				id="expand" />
			<pt:checkFunc code="SYS_PROD_ADD">
				<input type="button" class="btn btn-small btn-success" value="添加"
					id="adds" />
			</pt:checkFunc>
			<pt:checkFunc code="SYS_PROD_EDIT">
				<input type="button" class="btn btn-small btn-success" value="编辑"
					id="updates" />
			</pt:checkFunc>
			<pt:checkFunc code="SYS_PROD_DEL">
				<input type="button" class="btn btn-small btn-success" value="删除"
					id="delete" />
			</pt:checkFunc>
			<pt:checkFunc code="SYS_PROD_IMP">
				<input type="button" class="btn btn-small btn-success" value="导入"
					id="imp" />
			</pt:checkFunc>
			<input type="text" id="searchName" placeholder="请输入关键字"
				style="width: 150px; margin-top: 10px; margin-left: 100px;" /> <input
				type="button" class="btn btn-small btn-success" value="搜索"
				id="search" onclick="searchTree();" />
			<blockquote style="color: red;">说明：[]的数字代表产品编号;()中的数字，1代表许可产品分类，2代表非许可产品分类，12代表既是许可产品分类又是非许可产品分类.</blockquote>
		</div>

		<form>
			<input type="hidden" name="vo.id" value="${vo.id}" /> <input
				type="hidden" name="key" value="${vo.key}" />
			<div class="content_wrap" style="width: 600px; height: 580px;">
				<div class="zTreeDemoBackground left"
					style="width: 600px; height: 580px;">
					<ul id="treeDemo" class="ztree"
						style="width: 600px; height: 550px;"></ul>
				</div>
			</div>

		</form>

		<div class="mT20"></div>
	</div>
</body>
</html>