<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/common/meta.jsp" %>
<%@ page import="com.kington.fshg.common.PublicType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>合同内费用核销管理</title>
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
	//审批
	$("button[name='audit']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		
		$("#id").val(id);
		$("#key").val(key);
		$("#act").val("AUDIT");
		$("#form1").attr("action","audit.jhtml");
		$("#form1").submit();
		
	});
	
	//反审
	$("button[name='reAudit']").click(function(){
	 	var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	 
		
		$("#id").val(id);
		$("#key").val(key);
		$("#act").val("REAUDIT");
		$("#form1").attr("action","audit.jhtml");
		$("#form1").submit();
	});
	
	//生成U8收款单
	$("button[name='createU8']").click(function(){
		var obj = $(this);
		var id = obj.attr("att-id");		
		var key = obj.attr("att-key");	
		
		$("#id").val(id);
		$("#key").val(key);
		$("#form1").attr("action","editOrderU8.jhtml");
		$("#form1").submit();
	})
	
	//绑定导出按钮点击事件
	$("#exp").click(function(){
		var verCode=$("#verCode").val();
		var code=$("#code").val();
		var verDirection=$("#verDirection").val();
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		var dictName=$("#dictName").val();
		var approveState=$("#approveState").val();
		var pareaId  = $("#pareaId").val();
		var areaId = $("#areaId").val();
		var customTypeId = $("#customTypeId").val();
		var customName = $("#customName").val();
		
		
		var url="<c:url value='/charge/in-fee-verification/exportInFeeVerification.jhtml'/>";
		url+="?vo.verCode=" + verCode +"&vo.inFeeProvision.code=" + code+"&vo.verDirection="+verDirection; 
		url+="&vo.beginTime="+beginTime+"&vo.endTime="+endTime+"&vo.verType.dictName="+dictName+"&vo.approveState="+approveState
			+ "&vo.custom.area.parentArea.id=" + pareaId + "&vo.custom.area.id=" + areaId + "&vo.custom.customType.id=" + customTypeId + "&vo.custom.customName=" + customName;
		top.showView("请选择导出字段", url , 900);
	});
	
	
});

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
   <div class="span12" style="min-width:1000px;">
     <form name="form1" action="list.jhtml" class="form-inline" method="post" id="form1">
     <input type="hidden" name="ids" value="" id="ids"/>
	<input type="hidden" name="keys" value="" id="keys"/>
	<input type="hidden" name="vo.id" value="" id="id"/>
	<input type="hidden" name="key" value="" id="key"/>
	<input type="hidden" name="act" value="" id="act"/>
      <h5><span>合同内费用核销</span></h5>
      <ul class="row-fluid"> 
      	<div class="span10">
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
 	        <li>
 	        	<label>核销编码：</label>
 	        	<input class="input-medium" id="verCode" name="vo.verCode" value="${vo.verCode}" type="text" placeholder="输入需要搜索的编码" />
 	        </li>
 	        <li>
 	        	<label>所属预提：</label>
 	        	<input class="input-medium" id="code" name="vo.inFeeProvision.code" value="${vo.inFeeProvision.code}" type="text" placeholder="输入需要搜索的预提编码" />
 	        </li>
 	        <li>
 	        	<label>核销方向：</label>
 	        	<select name="vo.verDirection" class="span150" id="verDirection">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="<%=PublicType.VerDirection.values() %>" var="direction">
 	        			<option value="${direction.name}"  <c:if test="${direction.name == vo.verDirection}">selected</c:if>>${direction.text}</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	        <li>
       			<label>核销日期：</label>
       			<input type="text" id="beginTime" class="input-medium Wdate" name="vo.beginTime" value="${vo.beginTime}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />至
       			<input type="text" id="endTime" class="input-medium Wdate" name="vo.endTime" value="${vo.endTime}"  readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
       			
       		</li>
 	        <li>
 	        	<label>核销类型：</label>
 	        	<select name="vo.verType.dictName" class="span120" id="dictName">
 	        		<option value="">请选择</option>
 	        		<c:forEach items="${verTypeList}" var="type">
 	        			<option value="${type.dictName}"  <c:if test="${type.dictName == vo.verType.dictName}">selected</c:if>>${type.dictName}</option>
 	        		</c:forEach>
 	        	</select>
 	        </li>
 	        <li>
 	        	<label>审批状态：</label>
 	        	<select name="vo.approveState" class="span120" id="approveState">
 	        		<option value="">请选择</option>
 	        		<option value="DSP"  <c:if test="${vo.approveState == 'DSP'}">selected</c:if>>待审批</option>
 	        		<option value="SPJS"  <c:if test="${vo.approveState == 'SPJS'}">selected</c:if>>审批结束</option>
 	        	</select>
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
   <div class="span12" style="min-width:1400px;">
         <div class="pull-right  mB10">
        	 <pt:checkFunc code="CHAR_INFEEVER_BAUDTI">
           		<button class="btn" id="allAuditBtn"><i class="icon-ok "></i>批量审核</button>
           	</pt:checkFunc>
         	<pt:checkFunc code="CHAR_INFEEVER_ADD">
           		<button class="btn" id="addBtn"><i class="icon-plus "></i>添 加</button>
           	</pt:checkFunc>
           	<pt:checkFunc code="CHAR_INFEEVER_DEL">
	           <button class="btn" id="delBtn"><i class="icon-trash"></i>删 除</button>
            </pt:checkFunc>
            <button class="btn" id="exp">
								<i class="icon-list-alt"></i>导出
			</button>
           <%--  <pt:checkFunc code="CHAR_INFEEVER_U8">
	           <button class="btn" id="createU8Btn"><i class="icon-list-alt"></i>生成U8收款单</button>
            </pt:checkFunc> --%>
         </div>
       
       	<display:table name="pageList" id="vo" class="table table-bordered table-striped table-hover">
       		<display:column media="html" title="<input type='checkbox' class='allchkBox' />" style="width:20px">
       			<c:if test="${vo.approveState != 'SPJS' }">
					<input type="checkbox" class="chkBox" name="cid" value="${vo.id},${vo.key}"/>
       			</c:if>
			</display:column>
	        <display:column title="序号" style="white-space:nowrap">${pageList.startIndex + vo_rowNum}</display:column>
	        <display:column title="核销编号" style="white-space:nowrap">${vo.verCode}</display:column>
	        <display:column title="所属预提" style="white-space:nowrap">${vo.inFeeProvision.code}</display:column>
	        <display:column title="所属大区" style="white-space:nowrap">${vo.custom.area.parentArea.areaName}</display:column>
			<display:column title="所属地区" style="white-space:nowrap">${vo.custom.area.areaName}</display:column>
			<display:column title="所属分类" style="white-space:nowrap">${vo.custom.customType.customTypeName}</display:column>
	        <display:column title="所属客户" style="white-space:nowrap">${vo.custom.customName}</display:column>
	        <display:column title="费用总和(元)" style="white-space:nowrap"><fmt:formatNumber value="${vo.totalFee}" type="currency" /></display:column>
	        <display:column title="核销方向" style="white-space:nowrap">${vo.verDirection.text}</display:column>
	        <display:column title="核销类型" style="white-space:nowrap">${vo.verType.dictName}</display:column>
	        <display:column title="核销时间" style="white-space:nowrap"><fmt:formatDate value="${vo.verDate}" pattern="yyyy-MM-dd" /></display:column>
	        <display:column title="审批状态" style="white-space:nowrap">${vo.approveState.text}</display:column>
	        <display:column title="是否已生成收款单" style="white-space:nowrap">${vo.isCreateU8 ? '是' : '否'}</display:column>
            <display:column title="操  作" >
            	<button name="view" class="btn btn-small btn-info" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-eye-open"></i>查看</button>
            	<c:if test="${vo.approveState =='DSP' }">
	            	<pt:checkFunc code="CHAR_INFEEVER_EDIT">
        	       		<button name="edit" class="btn btn-small btn-success" att-id="${vo.id }" att-key="${vo.key }"><i class="icon-edit"></i>编辑</button>
    	           	</pt:checkFunc>
    	           	<pt:checkFunc code="CHAR_INFEEVER_AUDIT">
    	           		 <button name="audit" class="btn btn-small btn-warning" att-id="${vo.id}" att-key="${vo.key}"><i class="icon-pencil"></i>审批</button>
    	           	</pt:checkFunc>
    	           	<pt:checkFunc code="CHAR_INFEEVER_U8">
            			<c:if test="${vo.isCreateU8}">
            				<button name="createU8" class="btn btn-small btn-primary" att-id="${vo.id}" att-key="${vo.key}"><i class="icon-home"></i>收款单</button>
            			</c:if>
		            </pt:checkFunc>
            	</c:if>
            	<c:if test="${vo.approveState == 'SPJS' }">
            		<pt:checkFunc code="CHAR_INFEEVER_REA">
            			<button name="reAudit" class="btn btn-small btn-danger" att-id="${vo.id}" att-key="${vo.key}"><i class="icon-repeat"></i>反审</button>
            		</pt:checkFunc>
            		<pt:checkFunc code="CHAR_INFEEVER_U8">
            			<c:if test="${!vo.isCreateU8}">
            				<button name="createU8" class="btn btn-small btn-primary" att-id="${vo.id}" att-key="${vo.key}"><i class="icon-home"></i>收款单</button>
            			</c:if>
		            </pt:checkFunc>
            	</c:if>
            </display:column>
            <display:setProperty name="paging.banner.item_name">核销</display:setProperty>
            <display:setProperty name="paging.banner.items_name">核销</display:setProperty>
	    </display:table>
       
   </div><!-- / span12 end -->
  </div><!-- / row-fluid end -->

 </div><!--container-fluid end-->
</div><!--jt-container end-->
</body>
</html>