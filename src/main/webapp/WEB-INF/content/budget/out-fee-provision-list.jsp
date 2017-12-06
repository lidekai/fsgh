<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>合同外费用预提管理</title>
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
		
		$("#id").val(id);
		$("#key").val(key);
		$("#form1").attr("action","edit.jhtml");
		$("#form1").submit();
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
		$("#form1").attr("action","audit.jhtml");
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
		$("#form1").attr("action","audit.jhtml");
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
		
		$("#form1").attr("action","approve.jhtml");
		$("#form1").submit();
	});
	
	

	//绑定导出按钮点击事件
	$("#exp").click(function(){
		var provisionCode=$("#provisionCode").val();
		var customName=$("#customName").val();
		var feeName=$("#feeName").val();
		var projectType=$("#projectType").val();
		
		var approveState = $("#approveState").val();
		var dateStart=$("#dateStart").val();
		var dateEnd = $("#dateEnd").val();
		
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		
		var url="<c:url value='/budget/out-fee-provision/exportOutFeeProvision.jhtml'/>";
		url+="?vo.provisionCode=" + provisionCode +"&vo.custom.customName=" + customName + "&vo.provisionProject.feeName="+ feeName;
		url+="&vo.provisionProject.projectType=" + projectType +"&vo.approveState="+ approveState+ "&vo.dateStart="+ dateStart +"&vo.dateEnd="+ dateEnd
			+ "&vo.startDate=" + startDate + "&vo.endDate=" + endDate;
		
		top.showView("请选择导出字段", url , 900);
	});
	
	//生成促销员工资预提
	$("#salePro").click(function(){
		if(confirm("确定生成该制单时间内的促销员工资预提吗？")){
			$("#form1").attr("action","createSalePro.jhtml");
			$("#form1").submit();
		}
	});
});
	
</script>
</head>
<body>
<div class="jt-container">
 <div class="container-fluid">
  <div class="row-fluid">
   <div class="span12" style="min-width:1000px;">
     <form name="form1" action="list.jhtml" class="form-inline" method="post" id="form1">
     <input type="hidden" name="ids" value="" id="ids"/>
	<input type="hidden" name="keys" value="" id="keys"/>
	<input type="hidden" name="act" value="" id="act"/>
	<input type="hidden" name="vo.id" value="" id="id"/>
	<input type="hidden" name="key" value="" id="key"/>
      <h5><span>合同外费用预提</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
      		<li>
 	        	<label>预提编码：</label>
 	        	<input class="input-medium" id="provisionCode" name="vo.provisionCode" value="${vo.provisionCode}" type="text" placeholder="输入需要搜索的编码" />
 	        </li>
 	        <li>
 	        	<label>所属客户：</label>
 	        	<input class="input-medium" id="customName" name="vo.custom.customName" value="${vo.custom.customName}" type="text" placeholder="输入需要搜索的名称" />
 	        </li>
 	        <li>
 	        	<label>所属项目：</label>
 	        	<input class="input-medium" id="feeName" name="vo.provisionProject.feeName" value="${vo.provisionProject.feeName}" type="text" placeholder="输入需要搜索的项目名称" />
 	        </li>
 	        <li>
 	        	<label>项目类型：</label>
 	        	<select name="vo.provisionProject.projectType" class="span150" id="projectType">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="<%=PublicType.ProjectType.values() %>" var="type">
 	        			<option value="${type.name }" <c:if test="${vo.provisionProject.projectType  == type.name}">selected</c:if>>${type.text }</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	        <li>
 	        	<label>审核状态：</label>
 	        	<select name="vo.approveState" class="span150" id="approveState">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="<%=PublicType.ApproveState.values()%>" var="state">
 	        			<option value="${state.name }" <c:if test="${vo.approveState  == state.name}">selected</c:if>>${state.text }</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	        <li>
       			<label>制单时间：</label>
       			<input type="text" id="dateStart" class="input-medium Wdate" name="vo.dateStart" value="${vo.dateStart}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />至
       			<input type="text" id="dateEnd" class="input-medium Wdate" name="vo.dateEnd" value="${vo.dateEnd}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
       		</li>
       		<li>
       			<label>预提所属区间：</label>
       			<input type="text" id="startDate" class="input-medium Wdate" name="vo.startDate" value="${vo.startDate}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />至
       			<input type="text" id="endDate" class="input-medium Wdate" name="vo.endDate" value="${vo.endDate}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
       		</li>
        </div>
        <div class="span2">
        	<li><label><button name="searchBtn" class="btn btn-primary" type="submit">搜 索</button></label></li>
        </div>
      </ul>
     </form>
   </div><!-- / span12 end-->
  </div><!-- / row-fluid end-->
  <div class="row-fluid">
   <div class="span12" style="min-width:1000px;">
         <div class="pull-right  mB10">
            <pt:checkFunc code="BUD_OUTPRO_SALEPRO">
           		<button class="btn" id="salePro"><i class="icon-plus "></i>生成促销员工资预提</button>
           	</pt:checkFunc>
         	<pt:checkFunc code="BUD_OUTPRO_BATAUDIT">
           		<button class="btn" id="allAuditBtn"><i class="icon-ok "></i>批量审核</button>
           	</pt:checkFunc>
         	<pt:checkFunc code="BUD_OUTPRO_ADD">
           		<button class="btn" id="addBtn"><i class="icon-plus "></i>添 加</button>
           	</pt:checkFunc>
           	<pt:checkFunc code="BUD_OUTPRO_DEL">
	           <button class="btn" id="delBtn"><i class="icon-trash"></i>删 除</button>
            </pt:checkFunc>
            <button class="btn" id="exp"><i class="icon-list-alt"></i>导出</button>
         </div>
       
       	<display:table name="pageList" id="vo" class="table table-bordered table-striped table-hover">
       		<display:column media="html" title="<input type='checkbox' class='allchkBox' />" style="width:20px">
				<c:if test="${vo.approveState != 'SPJS' }">
					<input type="checkbox" class="chkBox" name="cid" value="${vo.id},${vo.key}"/>
				</c:if>
			</display:column>
	        <display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
	        <display:column title="预提编码" style="white-space:nowrap">${vo.provisionCode}</display:column>
	        <display:column title="所属客户" style="white-space:nowrap">${vo.custom.customName}</display:column>
	        <display:column title="申请业务员" style="white-space:nowrap">${vo.salesman}</display:column>
	        <display:column title="项目名称" style="white-space:nowrap">${vo.provisionProject.feeName}</display:column>
	        <display:column title="所属项目类型" style="white-space:nowrap">${vo.provisionProject.projectType.text}</display:column>
	        <display:column title="总费用(元)" style="white-space:nowrap">${vo.totalFee}</display:column>
	        <display:column title="制单时间" style="white-space:nowrap"><fmt:formatDate value="${vo.provisionTime}" pattern="yyyy-MM-dd" /></display:column>
	        <display:column title="预提所属区间" style="white-space:nowrap"><fmt:formatDate value="${vo.startTime}" pattern="yyyy-MM-dd" />
	        	--- <fmt:formatDate value="${vo.endTime}" pattern="yyyy-MM-dd" />
	        </display:column>
	        <display:column title="审核状态" style="white-space:nowrap">${vo.approveState.text}</display:column>
            <display:column title="操  作" style="min-width:250px;">
            	<button name="view" class="btn btn-small btn-info" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-eye-open"></i>查看</button>
            	<c:if test="${vo.approveState == 'DSP' }">
	            	<pt:checkFunc code="BUD_OUTPRO_EDIT">
	               		<button name="edit" class="btn btn-small btn-success" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-edit"></i>编辑</button>
	               	</pt:checkFunc>
	               	<pt:checkFunc code="BUD_OUTPRO_ONEAUDIT">
	               		<button name="audit" class="btn btn-small btn-warning" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-pencil"></i>一审</button>
	               	</pt:checkFunc>
            	</c:if>
            	<c:if test="${vo.approveState == 'ESZ' }">
            		<pt:checkFunc code="BUD_OUTPRO_TWOAUDIT">
	               		<button name="audit" class="btn btn-small btn-warning" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-pencil"></i>二审</button>
	               	</pt:checkFunc>
            		<pt:checkFunc code="BUD_OUTPRO_RETONE">
	               		<button name="reAudit" class="btn btn-small btn-danger" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-repeat"></i>反一审</button>
	               	</pt:checkFunc>
            	</c:if>
            	<c:if test="${vo.approveState == 'ZSZ' }">
	               	<pt:checkFunc code="BUD_OUTPRO_ENDAUDIT">
	               		<button name="audit" class="btn btn-small btn-warning" att-id="${vo.id }" att-key="${vo.key }" ><i class="icon-pencil"></i>终审</button>
	               	</pt:checkFunc>
            		<pt:checkFunc code="BUD_OUTPRO_RETTWO">
	               		<button name="reAudit" class="btn btn-small btn-danger" att-id="${vo.id }" att-key="${vo.key }" ><i class="icon-repeat"></i>反二审</button>
	               	</pt:checkFunc>
            	</c:if>
            	<c:if test="${vo.approveState == 'SPJS' }">
            		<pt:checkFunc code="BUD_OUTPRO_RETEND">
	               		<button name="reAudit" class="btn btn-small btn-danger" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-repeat"></i>反终审</button>
	               	</pt:checkFunc>
            	</c:if>
            </display:column>
            <display:setProperty name="paging.banner.item_name">预提</display:setProperty>
            <display:setProperty name="paging.banner.items_name">预提</display:setProperty>
	    </display:table>
       
   </div><!-- / span12 end -->
  </div><!-- / row-fluid end -->

 </div><!--container-fluid end-->
</div><!--jt-container end-->
</body>
</html>