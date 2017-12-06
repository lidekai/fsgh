package com.kington.fshg.model.system;

/**
 * 日志URL参数类
 * @author lijialin
 *
 */
public class UrlVO  {

	private String urlPath;// url地址
	private String name;// 名称
	
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}