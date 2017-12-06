package com.kington.fshg.common.sms.zgyd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClientForDCMResult" type="{http://Gmcc.SiniOA.Com/}DCMResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "clientForDCMResult" })
@XmlRootElement(name = "ClientForDCMResponse")
public class ClientForDCMResponse {

	@XmlElement(name = "ClientForDCMResult")
	protected DCMResponse clientForDCMResult;

	/**
	 * Gets the value of the clientForDCMResult property.
	 * 
	 * @return possible object is {@link DCMResponse }
	 * 
	 */
	public DCMResponse getClientForDCMResult() {
		return clientForDCMResult;
	}

	/**
	 * Sets the value of the clientForDCMResult property.
	 * 
	 * @param value
	 *            allowed object is {@link DCMResponse }
	 * 
	 */
	public void setClientForDCMResult(DCMResponse value) {
		this.clientForDCMResult = value;
	}

}
