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
 *         &lt;element name="DCMR" type="{http://Gmcc.SiniOA.Com/}DCMRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "dcmr" })
@XmlRootElement(name = "ClientForDCM")
public class ClientForDCM {

	@XmlElement(name = "DCMR")
	protected DCMRequest dcmr;

	/**
	 * Gets the value of the dcmr property.
	 * 
	 * @return possible object is {@link DCMRequest }
	 * 
	 */
	public DCMRequest getDCMR() {
		return dcmr;
	}

	/**
	 * Sets the value of the dcmr property.
	 * 
	 * @param value
	 *            allowed object is {@link DCMRequest }
	 * 
	 */
	public void setDCMR(DCMRequest value) {
		this.dcmr = value;
	}

}
