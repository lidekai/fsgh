<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="taglibs.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%
/*
例子：
<jsp:include page="/WEB-INF/common/uploadify.jsp">
	<jsp:param name="attachType" value="" />
	<jsp:param name="code" value="${vo.id}" />
	<jsp:param name="exts" value="*.jpg;*.png" />
	<jsp:param name="readonly" value="false" />
	<jsp:param name="maxSize" value="1KB" />
	<jsp:param name="maxNum" value="false" />
	<jsp:param name="key" value="${vo.key}" />
</jsp:include>
*/


	String attachType	= request.getParameter("attachType");
	String code			= request.getParameter("code");
	
	Boolean readonly		= false;	// 是否只读
	if (StringUtils.isNotBlank(request.getParameter("readonly")) && request.getParameter("readonly").equalsIgnoreCase("true")){
		readonly = true;
	}
	
	String exts 	= "*.*";		// 允许上传的图片扩展名，默认全部允许， 分号分割。如，*.gif; *.jpg; *.png
	if (StringUtils.isNotBlank(request.getParameter("exts"))){
		exts = request.getParameter("exts");
	}
	
	String maxSize 	= "50MB";		// 附件大小，默认10MB
	if (StringUtils.isNotBlank(request.getParameter("maxSize"))){
		maxSize = request.getParameter("maxSize");
	}
	
	String maxNum 	= "50";			// 附件数量，默认 20
	if (StringUtils.isNotBlank(request.getParameter("maxNum"))){
		maxNum = request.getParameter("maxNum");
	}
	
	String key 	= null;			
	if (StringUtils.isNotBlank(request.getParameter("key"))){
		key = request.getParameter("key");
	}
%>
<script
	src="<c:url value='/lib-ext/uploadify-3.2/jquery.uploadify.min.js' />"
	type="text/javascript"></script>
<link href="<c:url value='/lib-ext/uploadify-3.2/uploadify.css' />"
	type="text/css" rel="stylesheet"></link>
<style type="text/css">
.uploadify-queue-item {
	background-color: #FFFFFF;
	border: none;
	border-bottom: 1px solid #E5E5E5;
	font: 11px Verdana, Geneva, sans-serif;
	height: 26px;
	margin-top: 0;
	padding: 10px;
	/*width: 330px;*/
	text-align: left;
}

.uploadify-queue-item .cancel {
	float: right;
}

.uploadify-queue-item .cancel a {
	background: url('<c:url value="/lib-ext/uploadify-3.2/cancel.png" />') 0
		0 no-repeat;
	float: right;
	height: 16px;
	text-indent: -9999px;
	width: 16px;
}

.uploadify-queue-item .data {
	/*display:none;*/
	
}

#kt_uploadify_queue {
	border: 1px solid #E5E5E5;
	height: 188px;
	margin-bottom: 10px;
	/*width: 370px;*/
	overflow: auto;
}
</style>
<script type="text/javascript">
var kt_uploadify_formData = {
	"kt_session_id"		: "<%= request.getSession().getId() %>",
	"attachType"		: "<%= attachType %>",
	"code"				: "<%= code %>",
	"key"				: "<%= key %>"
};
function deleteRes(id, tagId, key){
	var url =  '<c:url value="/system/attach/delete.jhtml" />?id=' + id + "&key=" + key;
	$("#downfile").attr("src",url);
	
	var target = document.getElementById(tagId);
	target.parentNode.removeChild(target);
	
	var result = "";
	var multiFileIds	= document.getElementById('multiFileIds').value.split(",");
	for (var j=0; j<multiFileIds.length; j++){
		if (multiFileIds[j] == "") continue;
		if (id == multiFileIds[j]) continue;
		result += multiFileIds[j] + ",";
	}
	document.getElementById('multiFileIds').value = result;
	
	$('#kt_uploadify_message').html(getMsg());
}
function getItemHTML(attach) {	
	var tagId = "SWFUpload_" + attach.id;
	
	var html = '<div class="uploadify-queue-item" id="' + tagId + '">'
	
<c:if test="<%= !readonly %>">
	+ '	<div class="cancel">'
	+ "		<a href=\"#\" onclick=\"deleteRes('" + attach.id + "','" + tagId + "', '" + attach.key + "');return false;\">X</a>"
	+ "	</div>"
</c:if>
	
	+ '	<span class="fileName">'
	+ ' <a target="downfile" href="<c:url value="/system/attach/down.jhtml?id=" />' + attach.id + '&key=' + attach.key + '"> '
	+ attach.name
	+ '	</a></span><span class="data"></span>'
	+ '</div>';

	return html;
}
function getItemCount(){
	var count = $('#multiFileIds').val().split(',').length;
	return count == 0 ? 0 : count - 1;
}
function getMsg(err) {
	var count = getItemCount();
	var msg = "";
	if (count>0){
		msg = '已上传<font color="red"> ' +  getItemCount()  + ' </font>个文件 ';
	} else {
		msg = '请选择文件 ';
	}
	
	if (err !=null && err > 0) {
		msg += ', 发生  ' + err + ' 个 错误 ';
	}
	msg += '. ';
	
	return msg;
}
$(document).ready(function(){
    $("#kt_uploadify_file").uploadify({
    	formData		: kt_uploadify_formData,
        swf				: '<c:url value="/lib-ext/uploadify-3.2/uploadify.swf" />',
        uploader		: '<c:url value="/system/attach/up.jhtml" />',
        removeCompleted	: false,
        queueID			: 'kt_uploadify_queue',
        fileTypeExts	: '<%= exts %>',
        fileTypeDesc	: '文件 ',
        fileSizeLimit	: '<%= maxSize %>',
        fileQueueLimit	: '<%= maxNum %>',
        queueSizeLimit	: '<%= maxNum %>',
        uploadLimit		: '<%= maxNum %>',
        fileObjName		: 'res',
        multi			: true,
        auto			: true,
        buttonImage		: '<c:url value="/lib-ext/uploadify-3.2/upload.png" />',
        width			: 78,
        height			: 28,
        onInit			: onInit,
        onFallback		: onFallback,
        onUploadSuccess	: onUploadSuccess
    }); 
});
function onInit(){
	setTimeout(function(){
		if (kt_uploadify_formData["attachType"] == "") alert("出错! 必须指定   attachmenType.");
		if (kt_uploadify_formData["attachType"] != "" && kt_uploadify_formData["code"] != "" && kt_uploadify_formData["key"] != ""){
			var url = "<c:url value='/system/attach/list.jhtml'/>";
			$.post(url, kt_uploadify_formData, function(data){
				try {
					var attachList = eval(data);
					var obj = $('#kt_uploadify_queue');
					for (var i=0; i<attachList.length; i++){
						obj.append(getItemHTML(attachList[i]));
						$('#multiFileIds').val($('#multiFileIds').val() + attachList[i].id + ",");
					}
					
					$('#kt_uploadify_message').html(getMsg());
				} catch (ex){alert("uploadify 出错！\n\n" + data);}
			});
		}
	},200);
}
function onUploadSuccess(fileObj, response, data) {
	eval("var attach=" + response);
	
	// 文件名称
	var html = '<a target="downfile" href="<c:url value='/system/attach/down.jhtml' />?id='
			+ attach.id + "&key=" + attach.key + '">' + attach.name + '</a>';
	$("#" + fileObj.id + " .fileName").html(html);
	
	// 删除按钮
	html = "<a href=\"#\" onclick=\"deleteRes('" + attach.id + "','" + fileObj.id + "', '" + attach.key + "');return false;\">X</a>";
	$("#" + fileObj.id + " .cancel").html(html);
	
   	var multiFileIds	= $('#multiFileIds');
   	multiFileIds.val(multiFileIds.val() + attach.id + ",");
   	
   	$('#kt_uploadify_message').html(getMsg());
}
function onFallback(){ 
    if (confirm("您未安装FLASH控件，无法上传附件！是否现在安装 FLASH 插件？")){
    	var url = "";
		if (navigator.appName == "Microsoft Internet Explorer"){
			url = "<c:url value='/resources/flash/FlashPlayer_11.4-ie.exe' />";
		} else {
			url = "<c:url value='/resources/flash/FlashPlayer_11.4-no_ie.exe' />";
		}
		$("#downfile").attr("src",url);
    } 
}
</script>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td style="width: 100px; border-width: 0px;" align="left">
			<table cellpadding="0" cellspacing="0" border="0"
				<c:if test="<%=readonly %>">style="display:none"</c:if>>
				<tr>
					<td style="border: 0px;"><input type="file"
						id="kt_uploadify_file" name="file_upload" multiple="true" />
						<div id="kt_uploadify_message"></div></td>
				</tr>
			</table> <input type="hidden" id="multiFileIds" name="multiFileIds" /> <iframe
				id="downfile" name="downfile" style="display: none"></iframe>
		</td>
		<td style="width: auto; border-width: 0px;"><div
				id="kt_uploadify_queue"></div></td>
	</tr>
</table>