package com.kington.fshg.webapp.actions;

import com.jtframework.websupport.mvc.action.BaseActionSupport;

public class MessageAction extends BaseActionSupport {
	private static final long serialVersionUID = 5519967560602454970L;

	private String url;
	private String mess;

	/**
	 * 提示信息页面
	 * 
	 * @return
	 */
	public String info() {
		return "info";
	}
	
	public String info2() {
		if(this.getActionMessages().size()>0){
			System.out.println(this.getActionMessages().toString());
			mess = this.getActionMessages().toString();
		}
		return "info2";
	}
	
	public String close() {
		return "close";
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
