<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>物流费用</title>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#addBtn").click(function(){
		if(confirm("确定生成物流费用？")){
			$("#form1").attr("action","add.jhtml");
			$("#form1").submit();
		}
	});
	
	//删除
	$("#delBtn").click(function(){
		dels();	
	});
	
	$("#delAllBtn").click(function(){
		if(confirm("确定要全部删除吗？")){
			$("#form1").attr("action","deleteAll.jhtml");
			$("#form1").submit();
		}
	})
	
	//查看
	$("button[name='view']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		location.href="edit.jhtml?vo.id="+id+"&key="+key +"&act=VIEW";
	});
	
	//审核
	$("button[name = 'audit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		
		$("#id").val(id);
		$("#key").val(key);
		$("#act").val("AUDIT");
		$("#form1").attr("action","edit.jhtml");
		$("#form1").submit();
	});
	
	//反审核
	$("button[name = 'reAudit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");
		var key = obj.attr("att-key");
		
		$("#id").val(id);
		$("#key").val(key);
		$("#act").val("REAUDIT");
		$("#form1").attr("action","edit.jhtml");
		$("#form1").submit();
	});
	
	//批量审核
	$("#allAuditBtn").click(function(){
		 getCheckBox();	 
		if (ids == '') {
			alert('请选择要批量审核的信息');
			return;
		}
		if (!confirm('确定要批量审核您勾选的记录？'))
			return;
		
		$("#ids").val(ids);
		$("#keys").val(keys);
		$("#act").val("AUDIT");
		$("#form1").attr("action","approve.jhtml");
		$("#form1").submit();
	});
	
	//导出按钮事件
	$("#expBtn").click(function(){
		var cdlCode=$("#cdlCode").val();
		var customName=$("#customName").val();
		var productName=$("#productName").val();
		var stockCde = $("#stockCde").val();
		var orderStartTime = $("#orderStartTime").val();
		var orderEndTime = $("#orderEndTime").val();
		var approveState = $("#approveState").val();
		var url="<c:url value='/logistic/logistics/export.jhtml'/>";
		url+="?vo.deliverOrder.cdlCode=" + cdlCode + "&vo.custom.customName="+ customName
				+ "&vo.product.productName=" + productName+ "&vo.product.stockCde=" + stockCde
				+ "&vo.orderStartTime=" + orderStartTime+ "&vo.orderEndTime=" + orderEndTime 
				+ "&vo.approveState=" + approveState;
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
						<input type="hidden" name="vo.id" value="" id="id"/>
						<input type="hidden" name="key" value="" id="key"/>
						<input type="hidden" name="act" value="" id="act"/>
						<h5>
							<span>物流费用</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>发货单号：</label> <input class="input-medium"
									name="vo.deliverOrder.cdlCode"
									value="${vo.deliverOrder.cdlCode}" type="text"
									placeholder="输入需要搜索的发货单号" id="cdlCode"/></li>
								<li><label>所属客户：</label> <input class="input-medium"
									name="vo.custom.customName" value="${vo.custom.customName}"
									type="text" placeholder="输入需要搜索的名称" id="customName"/></li>
								<li><label>存货名称：</label> <input class="input-medium"
									name="vo.product.productName" value="${vo.product.productName}"
									type="text" placeholder="输入需要搜索的存货名称" id="productName"/></li>
								<li><label>存货编码：</label> <input class="input-medium"
									name="vo.product.stockCde" value="${vo.product.stockCde}"
									type="text" placeholder="输入需要搜索的存货编码" id="stockCde"/></li>
								<li><label>发货日期：</label> <input type="text"
									class="input-medium Wdate" name="vo.orderStartTime"
									value="${vo.orderStartTime}" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderStartTime"/>至 <input
									type="text" class="input-medium Wdate" name="vo.orderEndTime"
									value="${vo.orderEndTime}" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="orderEndTime"/></li>
								<li><label>审核状态：</label> <select name="vo.approveState"
									class="span150" id="approveState">
										<option value="">请选择</option>
										<c:forEach items="<%=PublicType.ApproveState.values()%>"
											var="state">
											<option value="${state.name }"
												<c:if test="${vo.approveState  == state.name}">selected</c:if>>${state.text }</option>
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
				<div class="span12" style="min-width: 1400px;">
					<div class="pull-right  mB10">
						<pt:checkFunc code="LOG_LOGISTICS_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
							<button class="btn" id="delAllBtn">
								<i class="icon-trash"></i>全部删除
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="LOG_LOGISTICS_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-list-alt"></i>生成物流费用
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="LOG_LOGISTICS_AUDIT">
			           		<button class="btn" id="allAuditBtn"><i class="icon-ok "></i>批量审核</button>
			           	</pt:checkFunc>
						<button class="btn" id="expBtn"><i class="icon-list-alt"></i>导出</button>
					</div>

					<display:table name="pageList" id="vo"
						class="table table-bordered table-striped table-hover">
						<display:column media="html"
							title="<input type='checkbox' class='allchkBox' />"
							style="width:20px">
							<c:if test="${vo.approveState == 'DSP' }">
								<input type="checkbox" class="chkBox" name="cid"
									value="${vo.id},${vo.key}" />
							</c:if>
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="发货日期" style="white-space:nowrap">
							<fmt:formatDate value="${vo.deliverOrder.deliverDate}"
								pattern="yyyy-MM-dd" />
						</display:column>
						<display:column title="发货单号" style="white-space:nowrap">${vo.deliverOrder.cdlCode}</display:column>
						<display:column title="所属客户" style="white-space:nowrap">${vo.custom.customName}</display:column>
						<display:column title="存货编码" style="white-space:nowrap">${vo.product.stockCde}</display:column>
						<display:column title="存货名称" style="white-space:nowrap">${vo.product.productName}</display:column>
						<display:column title="运费合计(元)" style="white-space:nowrap">
							<fmt:formatNumber value="${vo.freightTotal}" type="currency" />
						</display:column>
						<display:column title="审核状态" style="white-space:nowrap">${vo.approveState.text}</display:column>
						<display:column title="操  作" style="width:250px;">
							<button name="view" class="btn btn-small btn-info"
								att-id="${vo.id }" att-key="${vo.key }">
								<i class="icon-eye-open"></i>查看
							</button>
							<c:if test="${vo.approveState =='DSP' }">
								<pt:checkFunc code="LOG_LOGISTICS_AUDIT">
									<button name="audit" class="btn btn-small btn-warning"
										att-id="${vo.id}" att-key="${vo.key}">
										<i class="icon-pencil"></i>审核
									</button>
								</pt:checkFunc>
							</c:if>
							<c:if test="${vo.approveState == 'SPJS' }">
								<pt:checkFunc code="LOG_LOGISTICS_REA">
									<button name="reAudit" class="btn btn-small btn-danger"
										att-id="${vo.id}" att-key="${vo.key}">
										<i class="icon-repeat"></i>反审
									</button>
								</pt:checkFunc>
							</c:if>
						</display:column>
						<display:setProperty name="paging.banner.item_name">物流费用</display:setProperty>
						<display:setProperty name="paging.banner.items_name">物流费用</display:setProperty>
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