package com.kington.fshg.model.system;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jtframework.websupport.pagination.Pagination;
import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.FuncType;
import com.kington.fshg.common.PublicType.StateType;

/**
 * 系统功能表，管理菜单及角色权限
 * 
 * @author lijialin
 * 
 */
public class FunctionVO extends Pagination implements Serializable {
	private static final long serialVersionUID = -590490596207989829L;

	private int version;
	private Date createTime;
	private Date updateTime;
	private String funcCde; // 功能代码
	private String funcName; // 功能名称
	private Function parent;// 所属功能
	private int clevel; // 用于排序
	private Integer sort;//菜单级别
	private String link; // 点击链接
	private String perLink; // 权限链接，多个用;号分隔
	private FuncType menuType;// 菜单的类型
	private StateType state;// 状态
	private String key;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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

	public Function getParent() {
		return parent;
	}

	public void setParent(Function parent) {
		this.parent = parent;
	}

	public String getFuncCde() {
		return funcCde;
	}

	public void setFuncCde(String funcCde) {
		this.funcCde = funcCde;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public int getClevel() {
		return clevel;
	}

	public void setClevel(int clevel) {
		this.clevel = clevel;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPerLink() {
		return perLink;
	}

	public void setPerLink(String perLink) {
		this.perLink = perLink;
	}

	public FuncType getMenuType() {
		return menuType;
	}

	public void setMenuType(FuncType menuType) {
		this.menuType = menuType;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public StateType getState() {
		return state;
	}

	public String getKey() {
		return funcCde != null ? Common.getIdMD5(getFuncCde().toString(),
				Function.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
