package com.kington.fshg.webapp.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;

/**
 * 系统URL过滤、安全处理、过期检查
 * @author lijialin
 *
 */
public class CheckKeyFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		UserContext ctx = UserContext.get();
		String ctxpath = ctx.getRequest().getContextPath();
		String url = ctx.getRequest().getRequestURI().substring(ctxpath.length());
		
		if(needCheckKeyPath(url) && !checkIds(url, (HttpServletRequest)request)){
			jumpError("/error/info.jhtml");
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
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
	 * 判断路径是否需要进行数据安全校验
	 * 返回true:表示此路径需要进行数据安全校验
	 * @param uri
	 */
	private boolean needCheckKeyPath(String url){
		for(String path : Common.TOP_PATH){
			if(Common.matcher.match(path, url)){
				System.out.println("CheckKeyFilter....need check>>>"+url);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据传入的URL判断相关的ID和KEY是否合法
	 * @param url
	 * @return
	 */
	private boolean checkIds(String url, HttpServletRequest request){
		String id = request.getParameter("vo.id");
		String key = request.getParameter("key");
		String ids = request.getParameter("ids");
		String keys = request.getParameter("keys");
		String table = StringUtils.EMPTY;
		if(StringUtils.isNotBlank(url)){
			String[] s = StringUtils.split(url, "/");
			if(s.length > 2){
				table = s[s.length -2];
			}
		}
		if(table.length() > 0){
			//校验ID
			table =StringUtils.replace(table, "-", "");
			if(StringUtils.isNotBlank(id)){
				if(!Common.getIdMD5(id, table).equals(key)){
					return false;
				}
			}else if(StringUtils.isNotBlank(ids)){
				String[] idss = StringUtils.split(ids, Common.SYMBOL_COMMA);
				String[] keyss = StringUtils.split(keys, Common.SYMBOL_COMMA);
				if(idss.length != keyss.length){
					return false;
				}
				int i = 0;
				for(String s : idss){
					if(!Common.getIdMD5(s, table).equals(keyss[i])){
						return false;
					}
					i++;
				}
			}
		}
		
		return true;
	}
}
