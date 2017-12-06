package com.kington.fshg.webapp.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

public class UrlFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (httpRequest.isRequestedSessionIdFromURL()) {
			HttpSession session = httpRequest.getSession();
			if (session != null)
				session.invalidate();
		}
		HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
			@Override
			public String encodeRedirectUrl(String url) {
				return url;
			}
			@Override
			public String encodeRedirectURL(String url) {
				return url;
			}
			@Override
			public String encodeUrl(String url) {
				return url;
			}
			@Override
			public String encodeURL(String url) {
				return url;
			}
		};
		try {
			chain.doFilter(request, wrappedResponse);
		} catch (Exception e) {
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
