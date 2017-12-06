package com.kington.fshg.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.model.POSupport;

/**
 * 日志类
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_log")
public class Log extends POSupport {
	private static final long serialVersionUID = 6434363354176519532L;

	@Column(length = 50)
	private String userCde;// 用户帐号

	@Column(length = 100)
	private String userName;// 用户名称

	@Column(length = 50)
	private String ipAddress;// 用户Ip

	@Column(length = 255)
	private String link; // 访问的链接

	@Column(length = 200)
	private String operInfo;//操作信息
	
	@Column(length = 2000)
	private String para;//参数链接
	
	@Column(length = 2)
	@Enumerated(EnumType.STRING)
	private IsType isPass;
	

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

	public IsType getIsPass() {
		return isPass;
	}

	public void setIsPass(IsType isPass) {
		this.isPass = isPass;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	
}