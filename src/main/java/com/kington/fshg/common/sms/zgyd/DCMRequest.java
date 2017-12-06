package com.kington.fshg.common.sms.zgyd;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.kington.fshg.common.DateFormat;

/**
 * <p>
 * Java class for DCMRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DCMRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClientID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClientUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClientPwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BizCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TimeStamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ActionCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Dealkind" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SvcCont" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DCMRequest", propOrder = { "clientID", "clientUID",
		"clientPwd", "bizCode", "transID", "timeStamp", "actionCode",
		"dealkind", "svcCont" })
public class DCMRequest {

	@XmlElement(name = "ClientID")
	protected String clientID;
	@XmlElement(name = "ClientUID")
	protected String clientUID;
	@XmlElement(name = "ClientPwd")
	protected String clientPwd;
	@XmlElement(name = "BizCode")
	protected String bizCode;
	@XmlElement(name = "TransID")
	protected String transID;
	@XmlElement(name = "TimeStamp")
	protected String timeStamp;
	@XmlElement(name = "ActionCode")
	protected int actionCode;
	@XmlElement(name = "Dealkind")
	protected int dealkind;
	@XmlElement(name = "SvcCont")
	protected String svcCont;
	
//	@XmlElement(name = "BIZCODE")
//	private String BIZCODE = "";
//    private String CLIENTID = "";
//    private String CLIENTUID = "";
//    private String CLIENTPWD = "";
//    private String TRANSID = "SU" + new SerialNumFactory().getNum();
//    private int ACTIONCODE = 1;
//    private int DEALKIND = 0;
    

	//给DCMRequest对象附值	值需由sini公司提供
	public void genRequest(String phone, String content) {
		this.clientID = ZGYD_PARA.getClientId();
		this.bizCode = ZGYD_PARA.getBizCode();
		this.clientUID = ZGYD_PARA.getClientUid();
		this.clientPwd = ZGYD_PARA.getClientPwd();
		this.transID = "SU" + new SerialNumFactory().getNum();
		this.actionCode = 1;
		this.dealkind = 0;
		this.timeStamp = DateFormat.date2Str(new Date(), 99);
		
		this.svcCont = this.genSvcCont(phone, content).toString();
	}
	
//	//给DCMRequest对象附值	值需由sini公司提供
//	public void genRequest(String corpport) {
//		this.clientID = "DCM00040";
//		this.bizCode = "DCM102";
//		this.clientUID = "2001311037";
//		this.clientPwd = "aD32fGs";
//		this.transID = "SU" + new SerialNumFactory().getNum();
//		this.actionCode = 1;
//		this.dealkind = 0;
//		
//		this.svcCont = this.genSvcCont(corpport).toString();
//	}
	
//	private StringBuilder genSvcCont(String corpport) {
//		StringBuilder sb = new StringBuilder();
//		 sb.append("<NodeInceptRequest>");
//        sb.append("	<Body>");
//        sb.append("		<CorpPort>").append(corpport).append("</CorpPort>");//写入端口号
//        sb.append("	</Body>");
//        sb.append("</NodeInceptRequest>");
//		return sb;
//	}

	private StringBuilder genSvcCont(String phone, String content) {
		 StringBuilder sb = new StringBuilder();
       sb.append("<NodeRequest>");
       sb.append("	<Body>");
       sb.append("		<CorpID>").append(ZGYD_PARA.getClientUid()).append("</CorpID>");
       sb.append("		<CorpPort>").append(ZGYD_PARA.getPort()).append("</CorpPort>");
       sb.append("		<Node>");
       sb.append("			<ID>").append(System.currentTimeMillis()).append("</ID>");		//sequence number
       sb.append("			<Phone>").append(phone).append("</Phone>");
       sb.append("			<Content>").append(content).append("</Content>");
       sb.append("			<SendTime>").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("</SendTime>");	//发送时间，时间格式：yyyy-MM-dd HH:mm:ss
       sb.append("			 <ExtItem>");
       sb.append("			<ItemName>type</ItemName>");
       sb.append("			<ItemValue>1</ItemValue>");
       sb.append("			</ExtItem>");
       sb.append("		</Node>");
       sb.append("	</Body>");
       sb.append("</NodeRequest>");
		return sb; 
	}

	/**
	 * Gets the value of the clientID property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getClientID() {
		return clientID;
	}

	/**
	 * Sets the value of the clientID property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setClientID(String value) {
		this.clientID = value;
	}

	/**
	 * Gets the value of the clientUID property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getClientUID() {
		return clientUID;
	}

	/**
	 * Sets the value of the clientUID property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setClientUID(String value) {
		this.clientUID = value;
	}

	/**
	 * Gets the value of the clientPwd property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getClientPwd() {
		return clientPwd;
	}

	/**
	 * Sets the value of the clientPwd property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setClientPwd(String value) {
		this.clientPwd = value;
	}

	/**
	 * Gets the value of the bizCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBizCode() {
		return bizCode;
	}

	/**
	 * Sets the value of the bizCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBizCode(String value) {
		this.bizCode = value;
	}

	/**
	 * Gets the value of the transID property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTransID() {
		return transID;
	}

	/**
	 * Sets the value of the transID property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTransID(String value) {
		this.transID = value;
	}

	/**
	 * Gets the value of the timeStamp property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the value of the timeStamp property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTimeStamp(String value) {
		this.timeStamp = value;
	}

	/**
	 * Gets the value of the actionCode property.
	 * 
	 */
	public int getActionCode() {
		return actionCode;
	}

	/**
	 * Sets the value of the actionCode property.
	 * 
	 */
	public void setActionCode(int value) {
		this.actionCode = value;
	}

	/**
	 * Gets the value of the dealkind property.
	 * 
	 */
	public int getDealkind() {
		return dealkind;
	}

	/**
	 * Sets the value of the dealkind property.
	 * 
	 */
	public void setDealkind(int value) {
		this.dealkind = value;
	}

	/**
	 * Gets the value of the svcCont property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSvcCont() {
		return svcCont;
	}

	/**
	 * Sets the value of the svcCont property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSvcCont(String value) {
		this.svcCont = value;
	}
}
