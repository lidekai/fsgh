<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>其他物流费用</title>
<script type="text/javascript">
$(document).ready(function(){
	
	//添加
	$("#addBtn").click(function(){
		location.href="edit.jhtml";	
	});
	//编辑
	$("button[name='edit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		
		$("#id").val(id);
		$("#key").val(key);
		$("#form1").attr("action","edit.jhtml");
		$("#form1").submit();
	});
	
	//删除
	$("#delBtn").click(function(){
		dels();	
	});
	
	//分摊
	$("#ftBtn").click(function(){
		if(confirm("确定要分摊费用吗？")){
			$("#form1").attr("action","ftCost.jhtml");
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
		var customName=$("#customName").val();
		var createStartTime = $("#createStartTime").val();
		var createEndTime = $("#createEndTime").val();
		var approveState = $("#approveState").val();
		var url="<c:url value='/logistic/other-logistics/export.jhtml'/>";
		url+="?vo.custom.customName="+ customName
				+ "&vo.createStartTime=" + createStartTime+ "&vo.createEndTime=" + createEndTime
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
					<form name="form1" action="list.jhtml" class="form-inline"	method="post" id="form1">
						<input type="hidden" name="ids" value="" id="ids"/>
						<input type="hidden" name="keys" value="" id="keys"/>
						<input type="hidden" name="vo.id" value="" id="id"/>
						<input type="hidden" name="key" value="" id="key"/>
						<input type="hidden" name="act" value="" id="act"/>
						<h5>
							<span>其他物流费用</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>所属客户：</label> <input class="input-medium"
									name="vo.custom.customName" value="${vo.custom.customName}"
									type="text" placeholder="输入需要搜索的名称" id="customName"/></li>
								<li>
									<li><label>提交日期：</label> <input type="text"
										class="input-medium Wdate" name="vo.createStartTime"
										value="${vo.createStartTime}" readonly="readonly"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="createStartTime"/>至 <input
										type="text" class="input-medium Wdate" name="vo.createEndTime"
										value="${vo.createEndTime}" readonly="readonly"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="createEndTime"/></li>
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
						<pt:checkFunc code="LOG_OTHERLOG_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus"></i>添加
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="LOG_OTHERLOG_DEL">
							<button class="btn" id="delBtn">
								<i class="icon-trash"></i>删 除
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="LOG_OTHERLOG_FT">
							<button class="btn" id="ftBtn">
								<i class="icon-list-alt"></i>分摊
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="LOG_OTHERLOG_AUDIT">
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
						<display:column title="所属客户" style="white-space:nowrap">${vo.custom.customName}</display:column>
						<display:column title="提交日期" style="white-space:nowrap">
							<fmt:formatDate value="${vo.createTime}" pattern="yyyy-MM-dd" />
						</display:column>
						<display:column title="促销道具" style="white-space:nowrap">
							<fmt:formatNumber value="${vo.cost}" type="currency" />
						</display:column>
						<display:column title="退货" style="white-space:nowrap">
							<fmt:formatNumber value="${vo.returnGoods}" type="currency" />
						</display:column>
						<display:column title="工资分摊" style="white-space:nowrap">
							<fmt:formatNumber value="${vo.wagesShare}" type="currency" />
						</display:column>
						<display:column title="仓储分摊" style="white-space:nowrap">
							<fmt:formatNumber value="${vo.storageShare}" type="currency" />
						</display:column>
						<display:column title="调拨分摊" style="white-space:nowrap">
							<fmt:formatNumber value="${vo.transferCost}" type="currency" />
						</display:column>
						<display:column title="运费合计" style="white-space:nowrap">
							<fmt:formatNumber value="${vo.otherLogisticsCost}" type="currency" />
						</display:column>
						<display:column title="审核状态" style="white-space:nowrap">${vo.approveState.text}</display:column>
						<display:column title="操  作" style="width:250px;">
							<button name="view" class="btn btn-small btn-info"
								att-id="${vo.id }" att-key="${vo.key }">
								<i class="icon-eye-open"></i>查看
							</button>
							<c:if test="${vo.approveState =='DSP' }">
								<pt:checkFunc code="LOG_OTHERLOG_EDIT">
									<button name="edit" class="btn btn-small btn-success"
										att-id="${vo.id }" att-key="${vo.key }">
										<i class="icon-edit"></i>编辑
									</button>
								</pt:checkFunc>
								<pt:checkFunc code="LOG_OTHERLOG_AUDIT">
									<button name="audit" class="btn btn-small btn-warning"
										att-id="${vo.id}" att-key="${vo.key}">
										<i class="icon-pencil"></i>审核
									</button>
								</pt:checkFunc>
							</c:if>
							<c:if test="${vo.approveState == 'SPJS' }">
								<pt:checkFunc code="LOG_OTHERLOG_REA">
									<button name="reAudit" class="btn btn-small btn-danger"
										att-id="${vo.id}" att-key="${vo.key}">
										<i class="icon-repeat"></i>反审
									</button>
								</pt:checkFunc>
							</c:if>
						</display:column>
						<display:setProperty name="paging.banner.item_name">其他物流费用</display:setProperty>
						<display:setProperty name="paging.banner.items_name">其他物流费用</display:setProperty>
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