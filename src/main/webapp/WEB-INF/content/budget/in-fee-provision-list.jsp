<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<%@ page import="com.kington.fshg.common.PublicType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>合同内预提</title>
<script type="text/javascript">
$(document).ready(function(){
	
	//添加
	$("#addBtn").click(function(){
		var url = "<c:url value='/budget/in-fee-clause/selectCustom.jhtml'/>";
		top.showMyModal("选择客户",url,900,true);
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
		location.href="edit.jhtml?vo.createTimeStart=${vo.createTimeStart}&vo.createTimeEnd=${vo.createTimeEnd}&vo.id="+id+"&key="+key;
	});
	
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
	
	//批量核销
	$("#allVerBtn").click(function(){
		getCheckBox();	
		if (ids == '') {
			alert('请选择要批量核销的信息');
			return;
		}
		if (!confirm('确定要批量核销您勾选的记录？'))
			return;
		
		$("#ids").val(ids);
		$("#keys").val(keys);
		
		$("#form1").attr("action","ver.jhtml");
		$("#form1").submit();
	});
	
	//导出按钮事件
	$("#expBtn").click(function(){
		var code=$("#code").val();
		var customName=$("#customName").val();
		var start = $("#createTimeStart").val();
		var end = $("#createTimeEnd").val();
		var	approveState=$("#approveState").val();
		var parentAreaId = $("#pareaId").val();
		var areaId = $("#areaId").val();
		var customTypeId = $("#customTypeId").val();
		
		var url="<c:url value='/budget/in-fee-provision/exportInFeeProvision.jhtml'/>";
		url+="?vo.code=" + code +"&vo.custom.customName=" + customName + "&vo.createTimeStart="+ start;	
		url+="&vo.createTimeEnd=" + end + "&vo.approveState=" + approveState;
		url+="&vo.custom.area.parentArea.id=" + parentAreaId + "&vo.custom.area.id=" + areaId + "&vo.custom.customType.id=" + customTypeId;
		top.showView("请选择导出字段", url , 900);
	});
	
	
	
	
});

function setReturnValue(obj){
	if(obj == "") return;
	var createTimeStart = $("#createTimeStart").val();
	var createTimeEnd = $("#createTimeEnd").val();
	
	if(createTimeStart == "" || createTimeEnd == ""){
		alert("请选择预提日期，系统将更加预提日期选择的时间区间去统计发票额或者发货数");
	}else{
		if(confirm("确定要根据" + createTimeStart + "到" + createTimeEnd + "时间内的发票额或发货数统计生成预提吗？"))
			location.href="<c:url value='/budget/in-fee-provision/autoCreate.jhtml'/>?ids=" + obj.id 
					+ "&vo.createTimeStart=" + createTimeStart + "&vo.createTimeEnd=" + createTimeEnd;
	}
}

function getChildren(){
	var parentId = $("#pareaId").val();
	var html = "<option value=\"\">请选择</option>";
	if(parentId == "" || parentId == null)
		$("#areaId").html(html);
	else{
		$.ajax({
			type:"post",
			url:"<c:url value='/info/area/getChildrenJson.jhtml'/>",
			data:{"areaId":parentId},
			dataType:"text",
			success:function(data){
				var date = eval(data);
				var result = "<option value=\"\">请选择</option>";;
				for(var i=0;i<date.length;i++){
					 result += "<option value='" + date[i].id + "'>" + date[i].name + "</option>";
				}
				$("#areaId").html(result);  
			}
		});
	}; 
}
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
					<input type="hidden" name="act" value="" id="act"/>
					<input type="hidden" name="vo.id" value="" id="id"/>
					<input type="hidden" name="key" value="" id="key"/>
					
						<h5>
							<span>合同内预提</span>
						</h5>
						<ul class="row-fluid">
							<div class="span10">
								<li><label>预提编码：</label> <input id="code"
									class="input-medium" name="vo.code" value="${vo.code}"
									type="text" placeholder="输入需要搜索的编码" /></li>
								<li><label>所属大区：</label>
					 	        	<select name="vo.custom.area.parentArea.id" class="input-medium" id="pareaId" onchange="getChildren()">
					 	        		<option value="">请选择</option>
					 	        		<c:forEach items="${parentAreas }" var="area">
					 	        			<option value="${area.id}" <c:if test="${vo.custom.area.parentArea.id == area.id }">selected="selected"</c:if>>${area.areaName}</option>
					 	        		</c:forEach>
					 	        	</select>
					 	        </li>
					 	        <li>
					 	        	<label>所属地区：</label>
					 	        	<select name="vo.custom.area.id" class="input-medium" id="areaId">
					 	        		<option value="">请选择</option>
					 	        		<c:forEach items="${areas}" var="area">
					 	        			<option value="${area.id}" <c:if test="${vo.custom.area.id == area.id }">selected="selected"</c:if>>${area.areaName}</option>
					 	        		</c:forEach>
					 	        	</select>
					 	        </li>
					 	        <li>
					 	        	<label>所属分类：</label>
					 	        	<select name="vo.custom.customType.id" class="input-medium" id="customTypeId">
					 	        		<option value="">请选择</option>
					 	        		<c:forEach items="${customsTypes}" var="type">
					 	        			<option value="${type.id}" <c:if test="${vo.custom.customType.id == type.id }">selected="selected"</c:if>>${type.customTypeName}</option>
					 	        		</c:forEach>
					 	        	</select>
					 	        </li>
								<li><label>所属客户：</label> <input id="customName"
									class="input-medium" name="vo.custom.customName"
									value="${vo.custom.customName}" type="text"
									placeholder="输入需要搜索的名称" /></li>
								<li><label>预提日期：</label> <input type="text"
									class="input-medium Wdate" name="vo.createTimeStart"
									value="${vo.createTimeStart}" readonly="readonly"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="createTimeStart" />至
									<input type="text" class="input-medium Wdate"
									name="vo.createTimeEnd" value="${vo.createTimeEnd}"
									readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
									id="createTimeEnd" /></li>
								<li><label>审核状态：</label> <select id="approveState"
									name="vo.approveState" class="span150">
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
				<div class="span12" style="min-width: 1000px;">
					<div class="pull-right  mB10">
					   <pt:checkFunc code="BUD_INPRO_VER">
							<button class="btn" id="allVerBtn">
								<i class="icon-home "></i>批量核销
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="BUD_INPRO_AUDIT">
							<button class="btn" id="allAuditBtn">
								<i class="icon-ok "></i>批量审核
							</button>
						</pt:checkFunc>
						<button class="btn" id="expBtn">
							<i class="icon-list-alt"></i>导出
						</button>
						<pt:checkFunc code="BUD_INPRO_ADD">
							<button class="btn" id="addBtn">
								<i class="icon-plus "></i>生成预提
							</button>
						</pt:checkFunc>
						<pt:checkFunc code="BUD_INPRO_DEL">
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
							<input type="checkbox" class="chkBox" name="cid" value="${vo.id},${vo.key}" />
						</display:column>
						<display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
						<display:column title="预提编码" style="white-space:nowrap">${vo.code}</display:column>
						<display:column title="所属大区" style="white-space:nowrap">${vo.custom.area.parentArea.areaName}</display:column>
						<display:column title="所属地区" style="white-space:nowrap">${vo.custom.area.areaName}</display:column>
						<display:column title="所属分类" style="white-space:nowrap">${vo.custom.customType.customTypeName}</display:column>
						<display:column title="所属客户" style="white-space:nowrap">${vo.custom.customName}</display:column>
						<display:column title="预提日期" style="white-space:nowrap">
							<fmt:formatDate value="${vo.provisionTime}" pattern="yyyy-MM" />
						</display:column>
						<display:column title="费用总和(元)" style="white-space:nowrap">
							<fmt:formatNumber value="${vo.inFeeCount}" type="currency" />
						</display:column>
						<display:column title="审核状态" style="white-space:nowrap">${vo.approveState.text}</display:column>
						<display:column title="操  作" style="width:250px;">
							<button name="view" class="btn btn-small btn-info"
								att-id="${vo.id }" att-key="${vo.key }">
								<i class="icon-eye-open"></i>查看
							</button>
							<c:if test="${vo.approveState == 'DSP' }">
								<pt:checkFunc code="BUD_INPRO_EDIT">
									<button name="edit" class="btn btn-small btn-success"
										att-id="${vo.id }" att-key="${vo.key }">
										<i class="icon-edit"></i>编辑
									</button>
								</pt:checkFunc>
								<pt:checkFunc code="BUD_INPRO_ONEAUDIT">
									<button name="audit" class="btn btn-small btn-warning"
										att-id="${vo.id }" att-key="${vo.key }">
										<i class="icon-pencil"></i>一审
									</button>
								</pt:checkFunc>
							</c:if>
							<c:if test="${vo.approveState == 'ESZ' }">
								<pt:checkFunc code="BUD_INPRO_TWOAUDIT">
									<button name="audit" class="btn btn-small btn-warning"
										att-id="${vo.id }" att-key="${vo.key }">
										<i class="icon-pencil"></i>二审
									</button>
								</pt:checkFunc>
								<pt:checkFunc code="BUD_INPRO_RETONE">
									<button name="reAudit" class="btn btn-small btn-danger"
										att-id="${vo.id }" att-key="${vo.key }">
										<i class="icon-repeat"></i>反一审
									</button>
								</pt:checkFunc>
							</c:if>
							<c:if test="${vo.approveState == 'ZSZ' }">
								<pt:checkFunc code="BUD_INPRO_ENDAUDIT">
									<button name="audit" class="btn btn-small btn-warning"
										att-id="${vo.id }" att-key="${vo.key }">
										<i class="icon-pencil"></i>终审
									</button>
								</pt:checkFunc>
								<pt:checkFunc code="BUD_INPRO_RETTWO">
									<button name="reAudit" class="btn btn-small btn-danger"
										att-id="${vo.id }" att-key="${vo.key }">
										<i class="icon-repeat"></i>反二审
									</button>
								</pt:checkFunc>
							</c:if>
							<c:if test="${vo.approveState == 'SPJS' }">
								<pt:checkFunc code="BUD_INPRO_RETEND">
									<button name="reAudit" class="btn btn-small btn-danger"
										att-id="${vo.id }" att-key="${vo.key }">
										<i class="icon-repeat"></i>反终审
									</button>
								</pt:checkFunc>
							</c:if>
						</display:column>
						<display:setProperty name="paging.banner.item_name">预提</display:setProperty>
						<display:setProperty name="paging.banner.items_name">预提</display:setProperty>
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