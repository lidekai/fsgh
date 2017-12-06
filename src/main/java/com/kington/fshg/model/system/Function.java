package com.kington.fshg.model.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.FuncType;
import com.kington.fshg.common.PublicType.StateType;

/**
 * 系统功能表，管理菜单及角色权限
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_function")
public class Function implements Serializable {
	private static final long serialVersionUID = 1131959191952973067L;

	@Version
	private int version;
	@Column(nullable = false)
	private Date createTime;
	@Column(nullable = false)
	private Date updateTime;
	@Id
	@Column(nullable = false, length = 20)
	private String funcCde; // 功能代码

	@Column(length = 50)
	private String funcName; // 功能名称

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "parentCde")
	private Function parent;// 所属功能

	@Column(nullable = false)
	private Integer clevel; // 用于排序
	
	private Integer sort;//菜单级别

	@Basic(fetch = FetchType.LAZY)
	@Column(length = 255)
	private String link; // 点击链接

	@Basic(fetch = FetchType.LAZY)
	@Column(length = 2000)
	private String perLink; // 权限链接，多个用,号分隔

	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	private FuncType menuType;// 菜单的类型

	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	private StateType state;// 状态

	@Transient
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

	public void setFuncCde(String funcCde) {
		this.funcCde = funcCde;
	}

	public String getFuncCde() {
		return funcCde;
	}

	public void setMenuType(FuncType menuType) {
		this.menuType = menuType;
	}

	public FuncType getMenuType() {
		return menuType;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public StateType getState() {
		return state;
	}

	public void setClevel(Integer clevel) {
		this.clevel = clevel;
	}

	public void setParent(Function parent) {
		this.parent = parent;
	}

	public Function getParent() {
		return parent;
	}

	public String getKey() {
		return funcCde!=null ? Common.getIdMD5(getFuncCde().toString(),
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