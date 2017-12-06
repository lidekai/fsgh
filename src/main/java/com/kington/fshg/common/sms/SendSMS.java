package com.kington.fshg.common.sms;

/**
 * 发送短信接口类
 * @author lijialin
 *
 */
public class SendSMS {
	
	/**
	 * 发送短信接口
	 * @param mobiles 手机号码
	 * @param content 短信内容
	 * @param id 记录ID号
	 * @return
	 */
	public String send(String mobiles,String content,String id){
		
		String result = null;
		
		//暂时只有移动的接口，全部走移动短信网关
		result = SMS4YD.send(mobiles,content);
		
		return result;
	}
}
