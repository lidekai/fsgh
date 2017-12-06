package com.kington.fshg.webapp.security;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kington.fshg.model.system.UserVO;

/**
 * 当前登录用户的上下文信息
 * 
 * @author lijialin
 * 
 */
public class UserContext implements Serializable {
	private static final long serialVersionUID = 2982640924670218283L;
	private static ThreadLocal<UserContext> tl = new ThreadLocal<UserContext>();
	private HttpServletRequest request;
	private HttpServletResponse response;

	private UserVO user;

	/**
	 * 获取当前登录用户的上下文信息。
	 * 
	 * @return
	 */
	public static UserContext get() {
		UserContext ctx = tl.get();
		if (ctx == null) {
			ctx = new UserContext();
			tl.set(ctx);
		}
		return ctx;
	}

	/**
	 * 获取用户对象，如果返回Null则表明没有登录。一般在受保护的访问路径中总是不会 返回Null的。
	 * 
	 * @return
	 */
	public UserVO getUser() {
		return this.user;
	}

	/**
	 * 获取当前用户帐号
	 * 
	 * @return
	 */
	public String getUserCode() {
		if (this.user != null) {
			return this.user.getUserCode();
		} else {
			return null;
		}
	}

	/**
	 * 获取当前用户名称
	 * 
	 * @return
	 */
	public String getUserName() {
		if (this.user != null) {
			return this.user.getUserName();
		} else {
			return null;
		}
	}

	/**
	 * 由安全框架设置当前用户的信息。
	 * 
	 * @param user
	 */
	public void setUser(UserVO user) {
		this.user = user;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}
