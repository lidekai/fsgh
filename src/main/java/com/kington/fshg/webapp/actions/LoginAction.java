package com.kington.fshg.webapp.actions;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.jtframework.strutscoc.annotation.TextResult;
import com.jtframework.websupport.identify.Identify;
import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.StateType;
import com.kington.fshg.common.PwdUtils;
import com.kington.fshg.common.RamdomLetter;
import com.kington.fshg.model.system.LoginUser;
import com.kington.fshg.model.system.User;
import com.kington.fshg.model.system.UserVO;
import com.kington.fshg.service.system.LoginUserService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.security.SecurityFilter;
import com.kington.fshg.webapp.security.UserContext;

/**
 * 登录用ACTION
 * @author lijialin
 *
 */
public class LoginAction extends BaseAction {
	private static final long serialVersionUID = 4830674886895958305L;
	@Resource
	private UserService userService;
	@Resource
	private LoginUserService loginUserService;
	
	private UserVO vo;
	private String returnURL;//返回地址
	
	private String username;//登录帐号
	private String password;//登录密码
	private String secCode;	//验证码
	

	public String index() {
		try {
			setAttr("SYSTEM_TYPE", Common.SYSTEM_TYPE);
			getVerifyNumber();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index1";
	}

	/**
	 * 管理员登录后的界面
	 */
	private static String USER_TO_PATH = "/main/enter.jhtml";

	public String login() {
		String mess = StringUtils.EMPTY;
		try {
			//解密
			vo.setUserCode(PwdUtils.decode(vo.getUserCode()));
			vo.setPwd(PwdUtils.decode(vo.getPwd()));
			
			UserVO userVO = userService.getVOByCde(vo.getUserCode());
			if (userVO == null) {
				mess = "无此帐号，请核对帐号";
			} else if(!userVO.getPwd().equals(Common.getMD5(vo.getPwd()))){
				mess = "密码错误，请重新输入密码";
			} else if (userVO.getState() != StateType.USE) {
				mess = "此帐号已被停用，请联系管理员！";
				//判断登录类型
			}else if(SecurityFilter.getUserMapCount() >= PublicType.SYSTEM_USER_COUNT){
				mess = "系统用户数已达上限，请稍后再登录！";
			}else{
//				LoginUser loginUser = loginUserService.getUserLogin(userVO.getUserCode());
				//将用户登录信息写入数据库中
				LoginUser login = new LoginUser();
				HttpServletRequest request = ServletActionContext.getRequest();
				User userPO = userService.getById(userVO.getId());
				login.setUser(userPO);
				login.setIpAddress(Common.getRealIpAddr(request));
				login.setLoginTime(new Date());
				login.setActiveTime(new Date());
				login.setSessionId(request.getSession().getId());
				if(loginUserService.doLogin(login)){
					//用户信息写入SESSION中
					SecurityFilter.setUser2Session(ServletActionContext.getRequest(), userVO);
				}else{
					mess = "登录不成功，可能存在异常退出情况，请1分钟后再次尝试";
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mess = "操作出现异常";
		}
		if(StringUtils.isNotBlank(mess)){
			this.addActionError(mess);
			return index();
		}
		return redirect(USER_TO_PATH);
	}
	
	public void validateLogin()throws Exception{
		this.setInputResult("index");
		if(StringUtils.isBlank(username)){
			this.addActionError("请输入登录帐号");
		}else if(StringUtils.isBlank(password)){
			this.addActionError("请输入登录密码");
		}
		if(this.getActionErrors().size() > 0){
			getVerifyNumber();
		}else{
			vo = new UserVO();
			vo.setUserCode(username);
			vo.setPwd(password);
		}
	}
		
	

	/**
	 * 退出系统
	 * 
	 * @return
	 */
	public String logout() {
		String url=StringUtils.EMPTY;
		try {
			SecurityFilter.doLogout(ServletActionContext.getRequest());
			loginUserService.doLoginOut(UserContext.get().getUserCode());
			
			url = getRequest().getParameter("url");
			if(url == null){
				url = "/login/index.jhtml";
			}else{
				url = url.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return redirect(url);
	}
	
	/**
	 * 获取验证码，必须在action中调用
	 * 
	 * @return
	 */
	private static final String CODE_SESSION = "verify_code_key_login";
	private String result;
	private String codeVal;
	private String usercm;
	private String email;

	public String getVerifyCode() {
		return (String) ServletActionContext.getRequest().getSession()
				.getAttribute(CODE_SESSION);
	}
	/**
	 * 获取验证码，暂时不加入图片，直接返回生成的数字
	 * 
	 * @return
	 * @throws Exception
	 */
	@TextResult(value = "codeVal")
	public String getVerifyNumber() throws Exception {
		codeVal = StringUtils.EMPTY;
		Identify id = RamdomLetter.getMixedNumberAndLetterIdentify(4);
		ServletActionContext.getRequest().getSession().setAttribute(CODE_SESSION, id.getValue());
		codeVal = id.getValue();
		return null;
	}

	/**
	 * 关闭首页窗口时调用
	 * 
	 * @return
	 */
	public void closewin() {
		try {
			SecurityFilter.doLogout(ServletActionContext.getRequest());
			loginUserService.doLoginOut(UserContext.get().getUserCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String doException(String errorInfo) {
		this.redirectError("/error/info.jhtml", errorInfo);
		return null;
	}
	
	
	public String getCodeVal() {
		return codeVal;
	}

	public void setCodeVal(String codeVal) {
		this.codeVal = codeVal;
	}

	public String getUsercm() {
		return usercm;
	}

	public void setUsercm(String usercm) {
		this.usercm = usercm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserVO getVo() {
		return vo;
	}

	public void setVo(UserVO vo) {
		this.vo = vo;
	}


	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecCode() {
		return secCode;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}


}
