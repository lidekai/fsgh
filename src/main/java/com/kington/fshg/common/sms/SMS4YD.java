package com.kington.fshg.common.sms;

import com.kington.fshg.common.sms.zgyd.DCMRequest;
import com.kington.fshg.common.sms.zgyd.DCMResponse;
import com.kington.fshg.common.sms.zgyd.ObjectFactory;
import com.kington.fshg.common.sms.zgyd.Service;
import com.kington.fshg.common.sms.zgyd.ServiceSoap;


/**
 *  移动发送短信接口
 * @author lijialin
 *
 */
public class SMS4YD {
	
	public static String send(String phone,String content) {
		
		String result = null;
//		phone = "13600341232";
//		content = "测试短信";
		
		try {
			ObjectFactory factory = new ObjectFactory();		//工厂类  用于生成WebService输入参数
			Service service = new Service();					//建立服务
			DCMRequest request = factory.createDCMRequest();	//建立请求
			request.genRequest(phone,content);					//为请求赋值(实例化参数DCMRequest)
			
			System.out.println("clientForDCM.para.ClientID=" + request.getClientID());
			System.out.println("clientForDCM.para.ClientUID=" + request.getClientUID());
			System.out.println("clientForDCM.para.ClientPwd=" + request.getClientPwd());
			System.out.println("clientForDCM.para.BizCode=" + request.getBizCode());
			System.out.println("clientForDCM.para.TransID=" + request.getTransID());
			System.out.println("clientForDCM.para.ActionCode=" + request.getActionCode());
			System.out.println("clientForDCM.para.TimeStamp=" + request.getTimeStamp());
			System.out.println("clientForDCM.para.Dealkind=" + request.getDealkind());
			System.out.println("clientForDCM.para.SvcCont=" + request.getSvcCont());
			
			ServiceSoap serviceSoap = service.getServiceSoap();	//获得SOAP实例
			DCMResponse response = serviceSoap.clientForDCM(request); //方法调用
			
			System.out.println("ResultCode: "+response.getResultCode());
			System.out.println("ResultMsg: "+response.getResultMsg());
			System.out.println("svcCont: "+response.getSvcCont());
			if(!"0".equals(response.getResultCode())){
				result = response.getResultMsg();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		
		return result;
	}
	
	
}
