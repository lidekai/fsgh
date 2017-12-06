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
	//确定按钮事件
	$("button").eq(0).click(function(){
		if(Validator.Validate(form1,3,3)){
			form1.submit();					
		}
	});
	
});

</script>
</head>
<body>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="form1" action="updateMyPwd.jhtml"
						class="form-horizontal" method="post">
						<h5>
							<span id="formTitle">修改我的登录密码</span>
						</h5>

						<div class="control-group mT30">
							<label class="control-label">用户帐号</label>
							<div class="controls">
								<h5>${USER_INFO.userCode }</h5>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">用户姓名</label>
							<div class="controls">
								<h5>${USER_INFO.userName }</h5>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">新密码</label>
							<div class="controls">
								<input name="vo.pwd" type="password" placeholder="请输入新密码"
									dataType="Require" msg="请输入新密码" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">再次输入新密码</label>
							<div class="controls">
								<input name="newpwd" type="password" placeholder="请再次输入新密码"
									dataType="Repeat" to="vo.pwd" msg="两次输入的密码不一致" />
							</div>
						</div>

						<div class="control-group">
							<div class="controls">
								<button type="button" class="btn btn-primary">确 定</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>