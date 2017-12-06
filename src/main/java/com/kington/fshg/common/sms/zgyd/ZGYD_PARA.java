package com.kington.fshg.common.sms.zgyd;

/**
 * 中国移动短信网关配置参数
 * 
 * @author lijialin
 * 
 */
public class ZGYD_PARA {

	private static String clientId;
	private static String clientUid;
	private static String clientPwd;
	private static String bizCode;
	private static String port;
	private static String url;

	public static String getClientId() {
		return clientId;
	}

	public static void setClientId(String clientId) {
		ZGYD_PARA.clientId = clientId;
	}

	public static String getClientUid() {
		return clientUid;
	}

	public static void setClientUid(String clientUId) {
		ZGYD_PARA.clientUid = clientUId;
	}

	public static String getClientPwd() {
		return clientPwd;
	}

	public static void setClientPwd(String clientPwd) {
		ZGYD_PARA.clientPwd = clientPwd;
	}

	public static String getBizCode() {
		return bizCode;
	}

	public static void setBizCode(String bizCode) {
		ZGYD_PARA.bizCode = bizCode;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		ZGYD_PARA.url = url;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		ZGYD_PARA.port = port;
	}


}
