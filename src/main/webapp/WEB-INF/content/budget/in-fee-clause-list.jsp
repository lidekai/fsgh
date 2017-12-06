<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>合同内条款设置</title>
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
	
	//绑定导出按钮点击事件
	$("#exp").click(function(){
		var customName=$("#customName").val();
		var year=$("#year").val();
				
		var url="<c:url value='/budget/in-fee-clause/exportInFeeClause.jhtml'/>";
		url+="?vo.custom.customName=" + customName +"&vo.year=" + year; 
		top.showView("请选择导出字段", url , 900);
	});
		
	//导入按钮事件
	  $("#imp").bind("click", function(){
		    var url="<c:url value='/budget/in-fee-clause/imp.jhtml'/>";
			window.location.href =url;  
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
							<span>合同内条款设置</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>所属客户：</label> <input class="input-medium" id="customName"
									name="vo.custom.customName" value="${vo.custom.customName}"
									type="text" placeholder="输入需要搜索的名称" /></li>
								<li><label>年度：</label> <input type="text" id="year"
									class="input-medium Wdate" name="vo.year" value="${vo.year}"
									readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy'})"
									id="year" /></li>
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
						<pt:checkFunc code="BUD_INClA_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus "></i>添 加
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="BUD_INClA_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>
						<button class="btn" id="exp">
								<i class="icon-list-alt"></i>导出
						</button>
						<pt:checkFunc code="BUD_INClA_ADD">
						<button class="btn" id="imp">
								<i class="icon-list-alt"></i>导入
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
						<display:column title="年度" style="white-space:nowrap">${vo.year}</display:column>
						<display:column title="所属客户" style="white-space:nowrap">${vo.custom.customName}</display:column>
						<display:column title="客户编码" style="white-space:nowrap">${vo.custom.customCde}</display:column>
						<display:column title="固定费用" style="white-space:nowrap">${vo.fixedFee}</display:column>
						<display:column title="网络信息费" style="white-space:nowrap">${vo.netInfoFee}</display:column>
						<display:column title="配送服务费(百分比)" style="white-space:nowrap">${vo.deliveryFee}</display:column>
						<display:column title="年返金(百分比)" style="white-space:nowrap">${vo.yearReturnFee}</display:column>
						<display:column title="月返金(百分比)" style="white-space:nowrap">${vo.monthReturnFee}</display:column>
						<display:column title="操  作" style="width:150px;">
							<button name="view" class="btn btn-small btn-info"
								att-id="${vo.id }" att-key="${vo.key }">
								<i class="icon-eye-open"></i>查看
							</button>
							<pt:checkFunc code="BUD_INClA_EDIT">
								<button name="edit" class="btn btn-small btn-success"
									att-id="${vo.id }" att-key="${vo.key }">
									<i class="icon-edit"></i>编辑
								</button>
							</pt:checkFunc>
						</display:column>
						<display:setProperty name="paging.banner.item_name">条款</display:setProperty>
						<display:setProperty name="paging.banner.items_name">条款</display:setProperty>
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