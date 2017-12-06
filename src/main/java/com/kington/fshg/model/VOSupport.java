package com.kington.fshg.model;

import java.io.Serializable;
import java.util.Date;

import com.jtframework.websupport.pagination.Pagination;

/**
 * Value Object的基类，主要是继承EntitySupport中定义的属性。
 * 添加KEY密钥，用作校验数据合法性
 * @author lijialin
 * 
 */
public abstract class VOSupport extends Pagination implements Serializable {
	private static final long serialVersionUID = -8579300837291673603L;
	private Long id;
	private Date createTime;
	private Date updateTime;
	private String key;
	private String myQueryStr;
	private String myOrderStr;
	private String myForeignStr;//外键对象

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 由继承类去定义获取KEY的方法
	 * 
	 * @return
	 */
	public abstract String getKey();

	public void setKey(String key) {
		this.key = key;
	}

	public String getMyQueryStr() {
		return myQueryStr;
	}

	public void initMyQueryStr(String myQueryStr) {
		this.myQueryStr = myQueryStr;
	}

	/**
	 * 不能使用此接口赋值，防止前台JSP传值
	 * @param myQueryStr
	 */
	public void setMyQueryStr(String myQueryStr) {
	}

	public String getMyOrderStr() {
		return myOrderStr;
	}
	
	/**
	 * 不能使用此接口赋值，防止前台JSP传值
	 * @param myQueryStr
	 */
	public void setMyOrderStr(String myOrderStr) {
	}
	
	public void initMyOrderStr(String myOrderStr) {
		this.myOrderStr = myOrderStr;
	}
	
	/**
	 * 不能使用此接口赋值，防止前台JSP传值
	 * @param myQueryStr
	 */
	public void setMyForeignStr(String myForeignStr) {
	}
	
	public void initMyForeignStr(String myForeignStr) {
		this.myForeignStr = myForeignStr;
	}
	public String getMyForeignStr(){
		return myForeignStr;
	}
}
