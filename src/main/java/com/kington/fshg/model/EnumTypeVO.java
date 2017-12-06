package com.kington.fshg.model;

import java.io.Serializable;

/**
 * enum类型值，用于下拉列表显示时使用
 * 
 * @author lijialin
 * 
 */
public class EnumTypeVO implements Serializable {
	private static final long serialVersionUID = -8579300837291673603L;

	private String name;

	private String text;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
