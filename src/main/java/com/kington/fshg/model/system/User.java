package com.kington.fshg.model.system;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.StateType;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.info.Area;

/**
 * 用户基本信息
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_user")
public class User extends POSupport {
	private static final long serialVersionUID = 2245301997857183894L;

	@Column(nullable = false, length = 50, unique = true)
	private String userCode; // 账号

	@Column(nullable = false, length = 50)
	private String userName; // 姓名

	@Column(length = 255)
	private String phone; // 手机号码
	
	@Column(length = 50)
	private Long townId;
	
	@Column(nullable = false, length = 100)
	private String pwd; // 登录密码

	@Column(nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private StateType state; // 状态

	// 用户角色
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_user", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "roleid") })
	private List<Role> roles = new ArrayList<Role>();
	
	@Column(nullable = true, length = 2)
	private boolean isUpdate = false;//是否已修改
	
	// 用户所属区域
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_area_user", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "areaId") })
	private List<Area> areas = new ArrayList<Area>();

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				User.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}


}
