package com.kington.fshg.webapp.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.jtframework.websupport.mvc.action.BaseActionSupport;
import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType.FuncType;
import com.kington.fshg.model.system.Function;
import com.kington.fshg.model.system.UserVO;
import com.kington.fshg.service.system.DictService;
import com.kington.fshg.service.system.RoleService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.security.UserContext;

public class MainAction extends BaseActionSupport {
	private static final long serialVersionUID = -6799211165565544443L;
	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;
	@Resource
	private DictService dictService;

	private String initUrl;
	
	public String enter() {
		setAttr("SYSTEM_TYPE",Common.SYSTEM_TYPE);
		return "enter";
	}
	
	/**
	 * 进入index界面 需要查询出用户所属的菜单权限
	 */
	public String index() {
		String path = "index";
		try {
			UserContext uc = UserContext.get();
			
			UserVO userVO = userService.getVOByCde(uc.getUserCode());
			setAttr("USER_INFO", userVO);
			List funcList = null;
			// 获取权限信息
			List<Function> tmpList = roleService.getUserFunctionList(uc.getUserCode());
			funcList = new ArrayList<Function>();
			for (Function func : tmpList) {
				if (func.getMenuType() == FuncType.MENU) {
					funcList.add(func);
				}
			}
			setAttr("funcList",funcList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("loginTime", DateFormat.date2Str(new Date(),9));
		return path;
	}

	public String welcome() throws Exception {
		setAttr("dt",DateFormat.date2Str(new Date(), 8));
		setAttr("curDate",new Date());
		
		return "welcome";
	}

	public String pwdedit() {
		return "pwdedit";
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
	 * 
	 * @param name
	 * @param o
	 */
	public void setAttr(String name, Object o) {
		getRequest().setAttribute(name, o);
	}

	public Object getAttr(String name) {
		return getRequest().getAttribute(name);
	}

	/**
	 * 自己定义异常信息并跳转
	 * 
	 * @param errorInfo
	 * @return
	 */
	public String doException(String errorInfo) {
		this.redirectError("/error/info.jhtml", errorInfo);
		return null;
	}

	/**
	 * 获取异常并跳转到异常界面
	 * 
	 * @param e
	 * @return
	 */
	public String doException(Exception e) {
		e.printStackTrace();
		this.redirectError("/error/info.jhtml", e.getMessage());
		return null;
	}

	public String getInitUrl() {
		return initUrl;
	}

	public void setInitUrl(String initUrl) {
		this.initUrl = initUrl;
	}
}
