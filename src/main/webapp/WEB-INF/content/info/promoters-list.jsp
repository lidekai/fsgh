<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>促销员管理</title>
<script type="text/javascript">
$(document).ready(function(){
	
	//添加
	$("#addBtn").click(function(){
		location.href="edit.jhtml";	
	});
	
	//删除
	$("#delBtn").click(function(){
		dels();	
	});
	
	//导入
	$("#impBtn").click(function(){
		location.href="imp.jhtml";
	});
	
	
	//编辑
	$("button[name='edit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.id="+id+"&key="+key;
	});
	
	//查看
	$("button[name='view']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.id="+id+"&key="+key +"&act=VIEW";
	});
	
	
	//导出按钮事件
	 $("#expBtn").click(function(){
		var promotersName=$("#promotersName").val();
		var IDCare=$("#IDCare").val();
		var customName=$("#customName").val();
	
		var url="<c:url value='/info/promoters/exportPromoters.jhtml'/>";
		url+="?vo.promotersName=" + promotersName +"&vo.IDCare=" + IDCare +"&vo.custom.customName=" + customName;
		
		
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
							<span>促销员管理</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>所属客户：</label> <input class="input-medium" id="customName"
									name="vo.custom.customName" value="${vo.custom.customName}"
									type="text" placeholder="输入需要搜索的名称" /></li>
								<li><label>促销员名称：</label> <input class="input-medium" id="promotersName"
									name="vo.promotersName" value="${vo.promotersName}" type="text"
									placeholder="输入需要搜索的名称" /></li>
								<li><label>身份证号：</label> <input class="input-medium" id="IDCare"
									name="vo.IDCare" value="${vo.IDCare}" type="text"
									placeholder="输入需要搜索的身份证号" /></li>
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
						<pt:checkFunc code="INFO_PROMOTERS_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus "></i>添 加
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="INFO_PROMOTERS_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="INFO_PROMOTERS_IMP">
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
						<display:column title="促销员名称" style="white-space:nowrap">${vo.promotersName}</display:column>
						<display:column title="身份证号" style="white-space:nowrap">${vo.IDCare}</display:column>
						<display:column title="联系电话" style="white-space:nowrap">${vo.contactsInfo}</display:column>
						<display:column title="所在门店" style="white-space:nowrap">${vo.store.storeName}</display:column>
						<display:column title="所属客户" style="white-space:nowrap">${vo.custom.customName}</display:column>
						<display:column title="操  作" style="white-space:nowrap">
							<button name="view" class="btn btn-small btn-info"
								att-id="${vo.id }" att-key="${vo.key }">
								<i class="icon-eye-open"></i>查看
							</button>
							<pt:checkFunc code="INFO_PROMOTERS_EDIT">
								<button name="edit" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-edit"></i>编辑
								</button>
							</pt:checkFunc>
						</display:column>
						<display:setProperty name="paging.banner.item_name">促销员</display:setProperty>
						<display:setProperty name="paging.banner.items_name">促销员</display:setProperty>
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