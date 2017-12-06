package com.kington.fshg.common.sms.zgyd;


public class ReceiverDemo {

	public static void main(String[] args) {
		
		ObjectFactory factory = new ObjectFactory();	//工厂类  用于生成WebService输入参数
		Service service = new Service();				//建立服务
		DCMRequest request = factory.createDCMRequest();	//建立请求
		
//		request.genRequest("端口号");					//为请求赋值(实例化参数DCMRequest)此处传入"接收端口号"corpport
		
		ServiceSoap serviceSoap = service.getServiceSoap();	//获得SOAP实例
		
		DCMResponse response = serviceSoap.clientForDCM(request);
		
		System.out.println("ResultCode: "+response.getResultCode());
		System.out.println("ResultMsg: "+response.getResultMsg());
		System.out.println("ActionCode: "+response.getActionCode());
		System.out.println("svcCont: "+response.getSvcCont());
	}
}
