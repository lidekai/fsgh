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
<script type="text/javascript">
<!--
var setting = {
	check: {
		enable: true,
		chkStyle: "checkbox",
		chkboxType: { "Y": "ps", "N": "s" }
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
		onCheck: zTreeOnCheck
	}
};

var zNodes = ${treeJSON};

$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	zTreeOnCheck(null,null,null);
});

function zTreeOnCheck(event, treeId, treeNode){
	var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
    var nodes=treeObj.getCheckedNodes(true);
    var val="";
    for(var i=0;i<nodes.length;i++){
    	val += nodes[i].id + ",";
    }
    $("#idss").val(val);
}

//-->
</script>
</head>
<body>
	<div class="ifrm-cont">
		<div class="pos clear">
			<h2 class="title01-cont floatL">菜单权限目录查看</h2>
		</div>
		<form name="form1" action="" method="post">
			<input type="hidden" name="vo.id" value="${vo.id }" /> <input
				type="hidden" name="key" value="${vo.key }" /> <input type="hidden"
				id="idss" name="idss" value="" />
			<div class="content_wrap" style="width: 350px; height: 580px;">
				<div class="zTreeDemoBackground left"
					style="width: 350px; height: 580px;">
					<ul id="treeDemo" class="ztree"
						style="width: 350px; height: 550px;"></ul>
				</div>
			</div>
			<div class="mT20">
				<span class="btn05 mL10"><input type="button" value="返 回"
					onclick="javascript:history.back(-1);" /></span>
			</div>
		</form>
	</div>
</body>
</html>