package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.excel.ExcelAnnotation;

/**
 * 用户导入、导出信息
 * 
 * @author Van Cheng
 *
 */
public class UserExcelVo implements Serializable {
	private static final long serialVersionUID = 4993948311580767281L;
	
	@ExcelAnnotation(exportName = "序号")
	private int sid;
	@ExcelAnnotation(exportName = "账号")
	private String userCde;
	@ExcelAnnotation(exportName = "密码")
	private String pwd;
	@ExcelAnnotation(exportName = "状态")
	private String state;
	
	public String getUserCde() {
		return userCde;
	}
	public void setUserCde(String userCde) {
		this.userCde = userCde;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	
}