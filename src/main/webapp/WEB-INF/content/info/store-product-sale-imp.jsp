<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<title>门店销售信息导入</title>
<script type="text/javascript">
$(document).ready(function(){
	$("span").first().html("门店销售信息导入");
	//确定按钮事件
	$("#confirmBtn").click(function(){
		checkTheForm();
	});
	
	//取消按钮事件
	$("#cancelBtn").click(function(){
	  var url="<c:url value='/info/store-product-sale/list.jhtml'/>";
	  window.location.href=url;
	});
});
//验证提交的表单
function checkTheForm(){
	var filePath = document.getElementById("xlsfileupload").value;
    var extension=new String (filePath.substring(filePath.lastIndexOf(".")+1,filePath.length));//文件扩展名
    
	if (filePath==""){
		alert("请选择要导入的路径！");
		document.getElementsByName("xlsfileupload")[0].focus();
	} else if (extension == "xls" || extension == "xlsx"){
		form1.submit();		
	} else {
		alert('只能导入后缀名为：.xls或.xlsx 的文件！');
		document.getElementById("xlsfileupload").value = "";
	}
	
}
</script>
</head>
<body>
<div class="jt-container" style="max-width:1000px;">
 <div class="container-fluid">
  <div class="row-fluid">
   <div class="span12">
   	 <form name="form1" action="doImp.jhtml" class="form-horizontal"  method="post" enctype="multipart/form-data">
	   <h5><span id="formTitle"></span></h5>
       
       <div class="control-group">
        <label class="control-label">模板下载</label>
        <div class="controls">
         	<a href="<c:url value='/resources/exceltemplate/storeProductSaletemplate.xls'/>">下载模板</a>
        </div>
       </div>


       <div class="control-group">
        <label class="control-label">选择上传文件</label>
        <div class="controls">
         	<input type="file" id="xlsfileupload" name="xlsfileupload"  style="height:22px"/>
         	<blockquote><small>说明：上传的格式必须以模板为准，所有字段均为文本格式。</small></blockquote>
        </div>
       </div>
       
       <div class="control-group">
        <label class="control-label"><font size="4px" >导入结果</font></label>
        <div class="controls" >
         	<font size="4px" color="red">${info}</font>
        </div>
       </div>
       
       <div class="control-group">
        <div class="controls">
         <button type="button" id="confirmBtn" class="btn btn-primary">导入</button>
         <button type="button" id="cancelBtn" class="btn btn-primary">取 消</button>
        </div>
       </div>
     </form>
   </div>
  </div>
 </div>
</div>

</body>
</html>