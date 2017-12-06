<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户管理</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#addBtn").click(function(){
		location.href="edit.jhtml";	
	});
	
	$("#delBtn").click(function(){
		dels();	
	});
	
	$("#impBtn").click(function(){
		location.href="imp.jhtml";
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
		location.href="edit.jhtml?vo.id="+id+"&key="+key + "&act=VIEW";
	});
	
	//导出按钮事件
	 $("#expBtn").click(function(){
		var customName=$("#customName").val();
		var customCde=$("#customCde").val();
		var state=$("#state").val();
		var parentAreaID=$("#area-parentArea-id").val();
		var areaID = $("#area-id").val();
		var userID=$("#user-id").val();		
		var url="<c:url value='/info/custom/exportCustom.jhtml'/>";
		url+="?vo.customName=" + customName +"&vo.customCde=" + customCde + "&vo.state="+ state;
		if(${roleName != '业务员' && roleName != '大区经理' && roleName != '地区经理' }){
			url+="&vo.area.parentArea.id=" + parentAreaID +"&vo.area.id="+ areaID+ "&vo.user.id="+ userID;
		}
		
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
						<h5>
							<span>客户管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>客户名称：</label> <input class="input-medium" id="customName"
									name="vo.customName" value="${vo.customName}" type="text"
									placeholder="输入需要搜索的客户名称" /></li>
								<li><label>客户编码：</label> <input class="input-medium" id="customCde"
									name="vo.customCde" value="${vo.customCde}" type="text"
									placeholder="输入需要搜索的客户编码" /></li>
								<li><label>客户状态：</label> <select name="vo.state"   id="state"
									class="input-medium">
										<option value="">请选择</option>
										<c:forEach items="<%= PublicType.CustomerState.values()%>"
											var="s">
											<option value="${s.name}"
												<c:if test="${vo.state == s.name }">selected="selected"</c:if>>${s.text}</option>
										</c:forEach>
								</select></li>
								<c:if
									test="${roleName != '业务员' && roleName != '大区经理' && roleName != '地区经理' }">
									<li><label>所属大区：</label> <select  id="area-parentArea-id"
										name="vo.area.parentArea.id" class="input-medium">
											<option value="">请选择</option>
											<c:forEach items="${parentAreas }" var="area">
												<option value="${area.id}"
													<c:if test="${vo.area.parentArea.id == area.id }">selected="selected"</c:if>>${area.areaName}</option>
											</c:forEach>
									</select></li>
									<li><label>所属地区：</label> <select name="vo.area.id"
										class="input-medium" id="area-id" >
											<option value="">请选择</option>
											<c:forEach items="${areas }" var="area">
												<option value="${area.id}"
													<c:if test="${vo.area.id == area.id }">selected="selected"</c:if>>${area.areaName}</option>
											</c:forEach>
									</select></li>
									<li><label>所属业务员：</label> <select name="vo.user.id"
										class="input-medium" id="user-id">
											<option value="">请选择</option>
											<c:forEach items="${users}" var="user">
												<option value="${user.id}"
													<c:if test="${vo.user.id == user.id }">selected="selected"</c:if>>${user.userName}</option>
											</c:forEach>
									</select></li>
								</c:if>
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
						<pt:checkFunc code="INFO_CUSTOM_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus "></i>添 加
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="INFO_CUSTOM_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="INFO_CUSTOM_IMP">
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
							style="width:20px">
							<input type="checkbox" class="chkBox" name="cid"
								value="${vo.id},${vo.key}" />
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="客户编码" style="white-space:nowrap">${vo.customCde}</display:column>
						<display:column title="客户名称" style="white-space:nowrap">${vo.customName}</display:column>
						<display:column title="所属大区" style="white-space:nowrap">${vo.area.parentArea.areaName}</display:column>
						<display:column title="所属地区" style="white-space:nowrap">${vo.area.areaName}</display:column>
						<display:column title="所属业务员" style="white-space:nowrap">${vo.user.userName}</display:column>
						<display:column title="客户类型" style="white-space:nowrap">${vo.customType.customTypeName}</display:column>
						<display:column title="联系人" style="white-space:nowrap">${vo.contacts}</display:column>
						<display:column title="客户状态" style="white-space:nowrap">${vo.state.text}</display:column>
						<display:column title="操  作" style="white-space:nowrap">
							<button name="view" class="btn btn-small btn-info"
								att-id="${vo.id }" att-key="${vo.key }">
								<i class="icon-eye-open"></i>查看
							</button>
							<pt:checkFunc code="INFO_CUSTOM_EDIT">
								<button name="edit" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-edit"></i>编辑
								</button>
							</pt:checkFunc>
						</display:column>
						<display:setProperty name="paging.banner.item_name">客户</display:setProperty>
						<display:setProperty name="paging.banner.items_name">客户</display:setProperty>
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