package com.kington.fshg.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 日志URL参数类
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_url")
public class Url  {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, length = 200)
	private String urlPath;// url地址
	@Column(length = 200)
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