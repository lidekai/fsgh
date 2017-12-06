package com.kington.fshg.common.sms.zgyd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for DCMResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DCMResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClientID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClientUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClientPwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BizCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ActionCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TimeStamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dealkind" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ResultCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResultMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DCMResponse", propOrder = { "clientID", "clientUID",
		"clientPwd", "bizCode", "transID", "actionCode", "timeStamp",
		"dealkind", "resultCode", "resultMsg", "svcCont" })
public class DCMResponse {

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
	@XmlElement(name = "ActionCode")
	protected int actionCode;
	@XmlElement(name = "TimeStamp")
	protected String timeStamp;
	@XmlElement(name = "Dealkind")
	protected int dealkind;
	@XmlElement(name = "ResultCode")
	protected String resultCode;
	@XmlElement(name = "ResultMsg")
	protected String resultMsg;
	@XmlElement(name = "SvcCont")
	protected String svcCont;

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
	 * Gets the value of the resultCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * Sets the value of the resultCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setResultCode(String value) {
		this.resultCode = value;
	}

	/**
	 * Gets the value of the resultMsg property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * Sets the value of the resultMsg property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setResultMsg(String value) {
		this.resultMsg = value;
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
