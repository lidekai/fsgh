package com.kington.fshg.webapp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.kington.fshg.common.MyApplicationContextUtil;
import com.kington.fshg.model.system.UserVO;
import com.kington.fshg.service.system.RoleService;

public class PermissionTag extends TagSupport{
	private static final long serialVersionUID = 1L;
	private RoleService roleService;
	
	// 判断权限的代码
	private String code;
	private boolean isShow = false;

	@Override
	public int doStartTag() throws JspException {

		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			HttpSession session = request.getSession();
			UserVO userVO = (UserVO) session.getAttribute(SecurityFilter.SESSION_KEY);
			if(roleService == null){
				roleService = (RoleService)(MyApplicationContextUtil.getBean("roleService"));
			}
			isShow = roleService.checkPermissionByUserCde(userVO.getId(), code);
		} catch (Exception e) {
			e.printStackTrace();
			isShow = false;
		}
		
		if (isShow) {
			return Tag.EVAL_BODY_INCLUDE;
		} else {
			return Tag.SKIP_BODY;
		}
	}
		
	public void setCode(String code) {
		this.code = code;
	}
}
