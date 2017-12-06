package com.kington.fshg.model.system;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.model.VOSupport;

/**
 * 日志类
 * 
 * @author lijialin
 * 
 */
public class LogVO extends VOSupport {
	private static final long serialVersionUID = 6434363354176519532L;

	private String userCde;// 用户帐号
	private String userName;// 用户名称
	private String ipAddress;// 用户Ip
	private String link; // 访问的链接
	private String operInfo;
	private String para;//参数链接
	private IsType isPass;
	
	private Date startTime;//开始时间
	private Date endTime;//结束时间

	public String getUserCde() {
		return userCde;
	}

	public void setUserCde(String userCde) {
		this.userCde = userCde;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getOperInfo() {
		return operInfo;
	}

	public void setOperInfo(String operInfo) {
		this.operInfo = operInfo;
	}

	public IsType getIsPass() {
		return isPass;
	}

	public void setIsPass(IsType isPass) {
		this.isPass = isPass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Log.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}