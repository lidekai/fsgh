package com.kington.fshg.common.sms.zgyd;

import java.util.Calendar;
import java.util.Date;

import com.kington.fshg.common.DateFormat;


public class SenderDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date d=null;
		try {
			d = DateFormat.addDateByStep(new Date(), Calendar.DAY_OF_MONTH, 180);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("..>"+DateFormat.date2Str(d, 2));
	}
	
	
	public static void dosend(String[] args) {
		
		String phone = "13600341232";
//		String content = "中山市质量技术监督局食品相关办温馨提示：您好！您单位的生产许可证（编号：粤XK16-204-00397）已到年度自查时间，请贵单位按年度自查要求将自查报告资料提交市质监局食品相关产品办。逾期未办理，我局将按有关规定进行处理。若已办理，此信息忽略。【咨询电话：0760-88923100】";
//		String content = "中山市质量技术监督局食品相关办温馨提示：您好！您单位的生产许可证（编号：粤XK16-204-00397）已到证书申请延续时间，请贵单位按证书申请延续相关要求，向省质量技术监督局申请办理。若已办理，此信息忽略。【咨询电话：0760-88923100】";
		String content = "中山市质量技术监督局食品相关办温馨提示：您好！您单位的生产许可证（编号：粤XK16-204-00397）已过有效期。按规定应停止生产许可产品。如证书已申请延续，此信息忽略。【咨询电话：0760-88923100】";
		
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
	}
}
