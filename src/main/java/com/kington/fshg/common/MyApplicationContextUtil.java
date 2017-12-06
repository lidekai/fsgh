package com.kington.fshg.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MyApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context = null;
	private static MyApplicationContextUtil stools = null;

	public synchronized static MyApplicationContextUtil init() {
		if (stools == null) {
			stools = new MyApplicationContextUtil();
		}
		return stools;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		System.out
				.println("MyApplicationContextUtil..............注入context对象，供其它类调用");
		context = applicationContext;
	}

	public synchronized static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
}
