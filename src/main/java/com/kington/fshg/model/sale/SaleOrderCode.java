package com.kington.fshg.model.sale;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;
/**
 *  销售订单订单编号表
 *
 */

@Entity
@Table(name = "sale_order_code")
public class SaleOrderCode extends POSupport {

	private static final long serialVersionUID = -6817858000819368261L;

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
				SaleOrderCode.class.getSimpleName()) : StringUtils.EMPTY;
	}
	
	
}
