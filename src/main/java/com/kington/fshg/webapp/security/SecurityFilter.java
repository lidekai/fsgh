package com.kington.fshg.webapp.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.MyApplicationContextUtil;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.model.system.Log;
import com.kington.fshg.model.system.UserVO;
import com.kington.fshg.service.system.LogService;

/**
 * 系统URL过滤、安全处理、过期检查
 * @author lijialin
 *
 */
public class SecurityFilter implements Filter {
	static Logger logger = Logger.getLogger(SecurityFilter.class);
	private static LogService logService;
	/**
	 * 用户SESSION  KEY，通过此KEY可获取UserAccount对象
	 */
	public static final String SESSION_KEY = "_fshg_SESSION_KEY_";
	
	public static Map<String,HttpSession> sessionMap = new HashMap<String,HttpSession>();
	private final static Map<String,UserVO> userMap = new HashMap<String,UserVO>();
	
	public static int getUserMapCount(){
		return userMap.size();
	}
	
	public static void removeUserFromSession(UserVO userVO){
		try {
			for(Entry<String, UserVO> entry : userMap.entrySet()){
				if (entry.getValue() == null || StringUtils.equals(entry.getValue().getId().toString(), userVO.getId().toString())){
					userMap.remove(entry.getKey());
					ServletActionContext.getRequest().getSession().setAttribute(entry.getKey(), null);
					sessionMap.remove(entry.getKey());
					//在线人数减少1
					Common.CUR_USER_NUM --;
					System.out.println("当前在线人数" + userMap.size());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 登陆时, 绑定用户到 session.
	 * @param request
	 * @param user
	 */
	public static void setUser2Session(HttpServletRequest request, UserVO userVO) throws Exception{
		if(userVO == null ) throw new Exception("处理对象无效");
		
	    removeUserFromSession(userVO);		
			userMap.put(request.getSession().getId(), userVO);
		
		HttpSession session = request.getSession(); 
		session.setAttribute(SESSION_KEY, userVO);
		
		sessionMap.put(session.getId(), session);
		
		//在线人数增加1
		Common.CUR_USER_NUM ++;
	}
	
	/**
	 * 注销时调用，需要清除SESSION和MAP中的对象
	 * @throws Exception
	 */
	public static void doLogout(HttpServletRequest request) throws Exception{
		ServletActionContext.getRequest().getSession().setAttribute(SESSION_KEY, null);
		sessionMap.remove(request.getSession().getId());
		
		removeUserFromSession(userMap.get(request.getSession().getId()));
		//userMap.remove(request.getSession().getId());
		//removeUserInfo(ServletActionContext.getRequest().getSession().getId());
	}
	
	/**
	 * 移除用户在内存中的登录、菜单、授权信息
	 * @param userCde
	 */
	private static void removeUserInfo(String sessionId){
//		String userCde = sessionUserMap.get(sessionId);
//		
//		SecurityFilter.removeUser4LoginMap(sessionId);
//		if(StringUtils.isBlank(userCde)) return;
//		if(!sessionUserMap.containsValue(userCde)){
//			//所有此帐号的用户移除后，才能移除权限
////			UserPermission.removeUserPermission(userCde);
////			UserPermission.removeUserURL(userCde);	
//			System.out.println(".....remove...>>sessionId"+sessionId + "   userCde:" + userCde);
//		}
	}
	
	/**
	 * 获取当前已登陆的用户信息，没有登陆则返回Null
	 * @param request
	 * @return
	 */
	private final static String ENCODING_UTF_8 = "utf-8";
	private final static String KT_SESSION_ID = "kt_session_id";
	public static UserVO getUser(HttpServletRequest request) {
		try {
			request.setCharacterEncoding(ENCODING_UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String userSessionId = request.getParameter(KT_SESSION_ID);
//		System.out.println("userSessionId:"+userSessionId);
		HttpSession hs = null;
		if (userSessionId != null){
			hs = MySessionContext.getInstance().getSession(userSessionId);
			if (hs == null) return null;
		} else {
			hs = request.getSession();
		}
		return (UserVO)hs.getAttribute(SESSION_KEY);
	}
	
//	public static UserAccountVO getUser(HttpServletRequest request) {
//		try {
//			request.setCharacterEncoding("utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		String userSessionId = request.getParameter("kt_session_id");
//		if (userSessionId != null){
//			LoginUserVO user = userLoginMap.get(userSessionId);
//			if (user == null) return null;
//			return user.getUserAccountVO();
//		} else {
//			return (UserAccountVO)request.getSession().getAttribute(SESSION_KEY);
//		}
//		
//	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// 用户上下文信息初始化
		UserContext ctx = UserContext.get();
		ctx.setRequest((HttpServletRequest)request);
		ctx.setResponse((HttpServletResponse)response);
		ctx.setUser(getUser(ctx.getRequest()));
		
		// 执行权限过滤
		String ctxpath = ctx.getRequest().getContextPath();
		String url = ctx.getRequest().getRequestURI().substring(ctxpath.length());
//		logger.info("SecurityFilter 处理请求......>>URL:"+url);
		System.out.println("SecurityFilter 处理请求......>>URL:"+url);
		if(url.equals(Common.BASE_PATH) || url.contains(Common.PATH_REGESITER)){
		}else if(needCheckPathAuth(url)){
			Log log = new Log();
			try {
				UserVO user = ctx.getUser();
				log.setLink(url);
				log.setIpAddress(Common.getRealIpAddr(ctx.getRequest()));
				log.setPara(Common.getRequestPara(ctx.getRequest()));
				
				if(url.contains("/info/product/productImg.jhtml")
						|| url.contains("/info/product/viewProduct.jhtml")){
					
				}else if (user == null) {
					jump("/login/index.jhtml", "您尚未登录或登录信息已超时，请登录系统！");
					log.setIsPass(IsType.N);
					return;
				}else if(userMap.get(ctx.getRequest().getSession().getId()) == null || 
                		!StringUtils.equals(userMap.get(ctx.getRequest().getSession().getId()).getId().toString(), user.getId().toString())){
                	 jump("/login/index.jhtml", "您尚未登录或登录信息已超时，请登录系统！");
	                 log.setIsPass(IsType.N);
	                 return;
                }else{
					log.setUserCde(user.getUserCode());
					log.setUserName(user.getUserName());
					
					//判断路径是否是企业访问的路径，如果是则直接通过。企业访问路径只要有登录就能访问
					if(UserPermission.checkCompURL(url)){
						System.out.println("SecurityFilter 处理请求......>>企业请求路径:"+url);
					}else if(!UserPermission.checkURL(user.getId(), url)){
						log.setIsPass(IsType.N);
						jumpError("/error/info.jhtml");
						return;
					}
					log.setIsPass(IsType.Y);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					logService.saveLog(log);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("SecurityFilter.....init....>>>加载：logService");
		logService = (LogService)(MyApplicationContextUtil.getBean("logService"));
	}
	
	@Override
	public void destroy() {
	}

	/**
	 * 跳转到指定地址
	 * @throws IOException
	 * @throws ServletException
	 */
	private void jump(String uri, String message) throws IOException, ServletException {
		if(StringUtils.isBlank(message)) message = "您没有权限执行该操作";
		UserContext ctx = UserContext.get();
		ctx.getResponse().setContentType("text/html;charset=utf-8");
		PrintWriter out = ctx.getResponse().getWriter();
		out.print("<script>");
	//	if (StringUtils.isNotBlank(message)) out.print("alert('"+message+"');");
		out.print("top.location.href='" + ctx.getRequest().getContextPath() + uri + "';");
		out.print("</script>");
		out.flush();
		out.close();
	}
	
	private void jumpMessage(String message) throws IOException, ServletException {
		if(StringUtils.isBlank(message)) message = "您没有权限执行该操作";
		UserContext ctx = UserContext.get();
		ctx.getResponse().setContentType("text/html;charset=utf-8");
		PrintWriter out = ctx.getResponse().getWriter();
		if (StringUtils.isNotBlank(message)) out.print("提示信息："+message);
		out.flush();
		out.close();
	}
	
	private void jumpError(String uri) throws IOException, ServletException {
		UserContext ctx = UserContext.get();
		ctx.getResponse().setContentType("text/html;charset=utf-8");
		PrintWriter out = ctx.getResponse().getWriter();
		out.print("<script>");
		out.print("alert('您没有权限执行该操作！');");
		out.print("</script>");
		out.flush();
		out.close();
	}
	
	/**
	 * 判断路径是否需要进行权限验证
	 * 返回true:表示此路径需要权限验证
	 * @param uri
	 */
	
	private boolean needCheckPathAuth(String url){
		for(String path : Common.PUBLIC_PATH){
			if(Common.matcher.match(path, url)){
				return false;
			}
		}
		return true;
	}
}
