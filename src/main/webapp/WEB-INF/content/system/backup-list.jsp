<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>数据库备份</title>
<script type="text/javascript">
$(document).ready(function(){
  $("#add").click(function(){
    var url="<c:url value='/system/backup/doBackup.jhtml'/>";
    if(window.confirm('确定要执行数据库备份操作?')){
	  location.href=url;
    }
  });
  
  $("#upload").click(function(){
	  var url="<c:url value='/system/backup/upfile.jhtml'/>";
	  location.href=url;
  });
});
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<form>
						<h5>
							<span>数据库备份</span>
						</h5>

					</form>
				</div>
				<!-- / span12 end-->
			</div>
			<!-- / row-fluid end-->


			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<div class="pull-right  mB10">
						<button class="btn" id="add">
							<i class="icon-plus "></i>添 加备份
						</button>
						<button class="btn" id="upload">
							<i class="icon-plus "></i>上传备份
						</button>
					</div>
					<display:table name="list" id="vo"
						class="table table-bordered table-striped table-hover"
						requestURI="list.jhtml">
						<display:column title="序号" style="white-space:nowrap">${vo.name}</display:column>
						<display:column title="备份" style="white-space:nowrap"></display:column>
						<display:column title="操  作" style="white-space:nowrap">
							<a
								onclick="return window.confirm('你确认要使用本备份还原数据库?\n提示：现有的数据即将丢失, 建议先备份后操作.')"
								href="<c:url value='/system/backup/doImport.jhtml?dateStr=${vo.name}'/>">恢复</a>&nbsp;&nbsp;
		       <a onclick="return window.confirm('你确认删除该备份, 该备份数据将无法还原?')"
								href="<c:url value='/system/backup/deleteBackup.jhtml?dateStr=${vo.name}'/>">删除</a>&nbsp;&nbsp;
		       <a target="hiddenFrame"
								href="<c:url value='/system/backup/download.jhtml?dateStr=${vo.name}'/>">下载</a>&nbsp;
            </display:column>
						<display:setProperty name="paging.banner.item_name">数据库备份</display:setProperty>
						<display:setProperty name="paging.banner.items_name">数据库备份</display:setProperty>
					</display:table>

				</div>
				<!-- / span12 end -->
			</div>
			<!-- / row-fluid end -->



		</div>
		<!--container-fluid end-->
	</div>
	<!--jt-container end-->
	<iframe name="hiddenFrame" id="hiddenFrame" width="0" height="0"
		style="display: none"></iframe>
</body>
</html>