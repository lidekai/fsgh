package com.kington.fshg.model.system;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
//import com.kington.fshg.common.PublicType.UserType;
import com.kington.fshg.model.VOSupport;

/**
 * 用户登录信息表
 * 
 * @author lijialin
 * 
 */
public class LoginUserVO extends VOSupport {
	private static final long serialVersionUID = 2245301997857183894L;

	private User user;			//用户
	private String ipAddress;	//登录IP
	private Date loginTime;		//登录时间
	private Date activeTime;	//最后一次激活时间
	private Date logoutTime;	//登出时间
	private String sessionId;
	private Date minLoginTime;//最小登录时间
	private Date maxLoginTime;//最大登录时间
	
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

//	public Date getCalStart() {
//		return calStart;
//	}
//
//	public void setCalStart(Date calStart) {
//		this.calStart = calStart;
//	}
//
//	public Date getCalEnd() {
//		return calEnd;
//	}
//
//	public void setCalEnd(Date calEnd) {
//		this.calEnd = calEnd;
//	}
//
//	public Integer getDuration() {
//		return duration;
//	}
//
//	public void setDuration(Integer duration) {
//		this.duration = duration;
//	}

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

	public Date getMinLoginTime() {
		return minLoginTime;
	}

	public void setMinLoginTime(Date minLoginTime) {
		this.minLoginTime = minLoginTime;
	}

	public Date getMaxLoginTime() {
		return maxLoginTime;
	}

	public void setMaxLoginTime(Date maxLoginTime) {
		this.maxLoginTime = maxLoginTime;
	}
	
	
}
