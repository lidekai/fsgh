<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>管理员</title>
<script type="text/javascript">
$(document).ready(function(){
	var act = '${act}';
	if(act=="ADD") act = "添加";
	else if(act=="EDIT") act = "编辑";
	else if(act=="VIEW"){
		act = "查看";
		lockPage();
	}  
	$("span").first().html(act + "用户信息");
	$("#span_userCde").hide();
	
	//判断是否存在所属区域
	showArea();
	
	//确定按钮事件
	$("button").eq(0).click(function(){
		if(Validator.Validate(form1,3,3)){
			var text = $("#roleSelect").find("option:selected").text();
			var val = $("#areaIds").val();
			if(text == '地区经理' || text == '大区经理'){
				if(val == ''){
					alert("用户角色为：地区经理或者大区经理，请选择所属区域");
					return;
				}
			}
			
			var userCode = $("#vo_userCode").val();
			var userId = $("#vo_id").val();
			var span_userCde = $("#span_userCde");
			url = "<c:url value='/system/user/checkUser.text'/>?vo.userCode="+userCode;
			$.post(url, null, function (rt){
				this;
				if(rt != '0' && rt != userId){
					span_userCde.text('');
					span_userCde.css("color", "red");
					span_userCde.text('帐号已存在,请重新输入');
					$("#span_userCde").show();
				}else{
					form1.submit();					
				}
				},"text"
			);
		}
		
	});
	
	//取消按钮事件
	$("button").eq(1).click(function(){
		history.back(-1);
	});
	
	
	$("#roleSelect").change(function(){
		var text = $("#roleSelect").find("option:selected").text();
		$("#area").hide();
		$("#areaId").empty();
		var roleName = "";
		if(text == '地区经理' || text == '大区经理'){
			if(text == '地区经理'){
				$("#label").text("所属地区");
				roleName = "smallArea";
			}
			if(text == '大区经理'){
				$("#label").text("所属大区");
				roleName = "bigArea";
			}
			var url="<c:url value='/system/user/getArea.text'/>?roleName=" +roleName;
			$.post(url ,null ,function(data){
				var json = eval(data);
				$("#area").show();
				$.each(json,function(i,item){
					$("#areaId").append("<input type='checkbox' name='areaIds' value='"+ item.id +"'>" + item.name + "</input>&nbsp&nbsp&nbsp");
				})
			},"text");
		}
	});
	
});

function showArea(){
	var text = $("#roleSelect").find("option:selected").text();
	if(text == '地区经理' || text == '大区经理'){
		var roleName = "";
			if(text == '地区经理'){
				$("#label").text("所属地区");
				roleName = "smallArea";
			}
			if(text == '大区经理'){
				$("#label").text("所属大区");
				roleName = "bigArea";
			}
			var url="<c:url value='/system/user/getArea.text'/>?roleName=" +roleName;
			$.post(url ,null ,function(data){
				var json = eval(data);
				$("#area").show();
				$.each(json,function(i,item){
					var html="";
					html+="<input type='checkbox' name='areaIds' value='"+ item.id +"'";
					
					var areaIds = "${vo.areaIds}";
					var a = areaIds.split(";");
					var flage = 0;
					for(var i=0; i<a.length; i++){
						if(parseInt(a[i]) == item.id){
							flage = 1;
							break;
						}
					}
					if(flage == 1){
						html+="checked >";
					}else{
						html+=">";
					}
					html+=item.name + "</input>&nbsp&nbsp&nbsp";
					$("#areaId").append(html);
				})
			},"text");
	}else{
		$("#area").hide();
	}
}
</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="update.jhtml" class="form-horizontal"
						method="post">
						<input type="hidden" name="vo.id" id="vo_id" value="${vo.id }" />
						<input type="hidden" name="key" value="${key }" /> 
						<h5>
							<span id="formTitle"></span>
						</h5>

						<div class="control-group mT30">
							<label class="control-label">用户帐号</label>
							<div class="controls">
								<input name="vo.userCode" id="vo_userCode"
									value="${vo.userCode }" type="text"
									<c:if test="${act!='ADD' }">readonly</c:if> maxLength="20"
									placeholder="请输入用户帐号" dataType="Require" msg="请输入用户帐号" /><span>*</span><span
									id="span_userCde"></span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">用户姓名</label>
							<div class="controls">
								<input name="vo.userName" value="${vo.userName }" type="text"
									maxLength="20" placeholder="请输入用户姓名" dataType="Require"
									msg="请输入用户姓名" /><span>*</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">手机号码</label>
							<div class="controls">
								<input name="vo.phone" value="${vo.phone }" type="text"
									placeholder="请输入手机号码" Require="false" dataType="Mobile"
									msg="请输入正确的手机号码" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">用户状态</label>
							<div class="controls">
								<select name="vo.state" dataType="Require" msg="请选择用户状态">
									<option value="USE"
										<c:if test="${vo.state == 'USE' }">selected</c:if>>启用</option>
									<option value="STOP"
										<c:if test="${vo.state == 'STOP' }">selected</c:if>>停用</option>
								</select>
							</div>
						</div>

						<c:if test="${vo.userCode != 'admin' }">
							<div class="control-group">
								<label class="control-label">用户角色</label>
								<div class="controls">
									<select name="roleId" class="input-small" style="width: 220px"
										id="roleSelect">
										<option value="">请选择</option>
										<c:forEach items="${roleList}" var="role">
											<c:if test="${role.roleName != '超级管理员' }">
												<option value="${role.id}"
													<c:if test="${role.id == vo.roles[0].id}">selected</c:if>>${role.roleName}</option>
											</c:if>
										</c:forEach>
									</select><span>*</span>
								</div>
							</div>
						</c:if>

						<c:if test="${vo.userCode == 'admin' }">
							<div class="control-group">
								<label class="control-label">用户角色</label>
								<div class="controls">
									<select name="" class="input-small" style="width: 220px"
										disabled="disabled">
										<option value="">超级管理员</option>
									</select><span>*</span>
								</div>
							</div>
						</c:if>

						<div class="control-group" style="display: none;" id="area">
							<label class="control-label" id="label">所属区域</label>
							<div class="controls" id="areaId">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
								<span>说明:新添加的用户密码默认为：${defPwd}</span>
							</div>
						</div>

						<div class="control-group">
							<div class="controls">
								<button type="button" class="btn btn-primary">确 定</button>
								<button type="button" class="btn btn-primary">取 消</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>