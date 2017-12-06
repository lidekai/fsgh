package com.kington.fshg.webapp.actions;

import com.jtframework.websupport.mvc.action.BaseActionSupport;

public class ErrorAction extends BaseActionSupport {
	private static final long serialVersionUID = 3645197037687750684L;

	private String url;
	private String mess;

	public String info() {
		return "info";
	}
	
	public String back() {
		return "back";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}
}
