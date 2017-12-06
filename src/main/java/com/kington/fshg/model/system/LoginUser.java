package com.kington.fshg.model.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
//import com.kington.fshg.common.PublicType.UserType;
import com.kington.fshg.model.POSupport;

/**
 * 用户登录信息表
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_user_login")
public class LoginUser extends POSupport {
	private static final long serialVersionUID = 2245301997857183894L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;			//用户
	
	@Column(nullable = false, length = 255)
	private String ipAddress;	//登录IP
	private Date loginTime;		//登录时间
	private Date activeTime;	//最后一次激活时间
	private Date logoutTime;	//登出时间
	private String sessionId;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				LoginUser.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
