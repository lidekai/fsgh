package com.kington.fshg.webapp.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.jtframework.websupport.mvc.action.BaseActionSupport;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.webapp.security.UserContext;

public class BaseAction extends BaseActionSupport {
	private static final long serialVersionUID = -5647085048071846148L;
	
	/**
	 * ids用于删除时批量传递参数
	 */
	protected String ids;
	protected ActType act;//操作类型
	private String key;//用于校验ID是否合法
	private String keys;//用于校验ID是否合法

	public final static String CUR_URL = "CUR_URL";
	
	/**
	 * 获取异常并跳转到异常界面
	 * @param e
	 * @return
	 */
	public String doException(Exception e) {
		e.printStackTrace();
		return doError(e.getMessage(),StringUtils.EMPTY);
	}
	
	/**
	 * 自己定义异常信息并跳转
	 * @param errorInfo
	 * @return
	 */
	public String doException(String errorInfo) {
		return doError(errorInfo,StringUtils.EMPTY);
	}
	
	/**
	 * 进入错误提示界面
	 * @param errorInfo
	 * @param okURL  点击跳转后的链接
	 * @return
	 */
	public String doError(String mess,String okURL) {
		this.redirect("/error/info.jhtml?url="+okURL);
		this.addActionMessage(mess);
		return null;
	}
	public String doErrorBack(String mess) {
		this.redirect("/error/back.jhtml");
		this.addActionMessage(mess);
		return null;
	}
	public String doErrorClose(String mess) {
		this.redirect("/error/close.jhtml");
		this.addActionMessage(mess);
		return null;
	}
	
	/**
	 * 进入信息提示界面
	 * @param errorInfo
	 * @param okURL  点击跳转后的链接
	 * @return
	 */
	public String doMessage(String mess,String okURL) {
		this.redirect("/message/info.jhtml?url="+okURL);
		this.addActionMessage(mess);
		return null;
	}
	
	public String doMessage2(String mess,String okURL) {
		this.redirect("/message/info2.jhtml?url="+okURL);
		this.addActionMessage(mess);
		return null;
	}
	
	public String doMessageClose(String mess) {
		this.redirect("/message/close.jhtml");
		this.addActionMessage(mess);
		return null;
	}
	
	/**
	 * 获取Session中的userId
	 * @return
	 */
	public String getUserCde(){
		return UserContext.get().getUserCode();
	}

	
	/**
	 * 获取当前ACTION的request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	/**
	 * 获取当前ACTION的request对象
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/**
	 * 将值及属性设置到request中去，以便在前台JSP中使用
	 * @param name
	 * @param o
	 */
	public void setAttr(String name,Object o) {
		getRequest().setAttribute(name, o);
	}
	public Object getAttr(String name) {
		return getRequest().getAttribute(name);
	}
	
	public void setSessionAttr(String name,Object o) {
		getSession().setAttribute(name, o);
	}
	public Object getSessionAttr(String name) {
		return getSession().getAttribute(name);
	}
	
	public String getContextPath(){
		return ServletActionContext.getRequest().getContextPath();
	}
	
	public void setAct(ActType act) {
		this.act = act;
	}

	public ActType getAct() {
		return act;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getIds() {
		return ids;
	}
	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}
}
