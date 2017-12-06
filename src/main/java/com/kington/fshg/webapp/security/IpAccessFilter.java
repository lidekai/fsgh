package com.kington.fshg.webapp.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kington.fshg.common.Common;

public class IpAccessFilter implements Filter {
	
	private ConcurrentHashMap<String, Long> ipAccessMap = new  ConcurrentHashMap<String, Long>();

	@Override
	public void destroy() {
	}

	/**
	 * 设定某些url的访问次数
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest httpRequest = (HttpServletRequest)request;
//		HttpServletResponse httpResponse = (HttpServletResponse)response;
		// ip地址和访问路径
		String ctxpath = httpRequest.getContextPath();
		String uri = httpRequest.getRequestURI().substring(ctxpath.length());
//		String ip = httpRequest.getRemoteAddr();
		String ip = Common.getRealIpAddr(httpRequest);
		
		try {
			if(Common.IP_URL_MAP.containsKey(uri)){
				String ipuri = ip+"_"+uri;
				long cTimes = new Date().getTime();
				if(ipAccessMap.get(ipuri) != null){
					Long aTimes = ipAccessMap.get(ipuri);//上次访问时间
					Long bTimes = aTimes + Common.IP_URL_MAP.get(uri);//本次可以访问的时间
			
					/**
					 * 当前时间小于本次可访问时间时，不能访问
					 */
					if(bTimes > cTimes){
						error();
						return;
					}
				}
				ipAccessMap.put(ip+"_"+uri, cTimes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	/**
	 * 跳转到指定地址
	 * @throws IOException
	 * @throws ServletException
	 */
	private void error() throws IOException, ServletException {
		UserContext ctx = UserContext.get();
		ctx.getResponse().setContentType("text/html;charset=utf-8");
		PrintWriter out = ctx.getResponse().getWriter();
		out.print("<script>");
		out.print("top.location.href='" + ctx.getRequest().getContextPath() +"/error/often.jhtml';");
		out.print("</script>");
		out.flush();
		out.close();
	}
}
