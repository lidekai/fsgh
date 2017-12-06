<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>字典管理</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#addBtn").click(function(){
		location.href="edit.jhtml";	
	});
	
	$("#delBtn").click(function(){
		dels();	
	});
	
	$("button[name='edit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.id="+id+"&key="+key;
	});
});
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12" style="min-width: 1000px;">
					<form name="form1" action="list.jhtml" class="form-inline" id="form1"
						method="post">
						<h5>
							<span>常量管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>常量名称：</label><input class="input-medium"
									name="vo.dictName" value="${vo.dictName }" type="text"
									placeholder="输入需要搜索的常量名称" /></li>
								<li><label>常量类型：</label> <select name="vo.dictType">
										<option value="">请选择</option>
										<c:forEach items="${list }" var="type">
											<option value="${type.name}"
												<c:if test="${vo.dictType == type.name }">selected</c:if>>${type.text}</option>
										</c:forEach>
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
						<pt:checkFunc code="SYS_DICT_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus "></i>添 加
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="SYS_DICT_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>
					</div>

					<display:table name="pageList" id="vo"
						class="table table-bordered table-striped table-hover">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="常量名称" style="white-space:nowrap">${vo.dictName}</display:column>
						<display:column title="常量类型" style="white-space:nowrap">${vo.dictType.text}</display:column>
						<display:column title="常量数值">${vo.value}</display:column>
						<display:column title="操  作" style="white-space:nowrap">
							<pt:checkFunc code="SYS_DICT_EDIT">
								<button name="edit" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-edit"></i>编辑
								</button>
							</pt:checkFunc>
						</display:column>
						<display:setProperty name="paging.banner.item_name">常量</display:setProperty>
						<display:setProperty name="paging.banner.items_name">常量</display:setProperty>
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