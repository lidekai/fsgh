package com.kington.fshg.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.model.system.UserVO;

import jregex.Pattern;
import jregex.REFlags;
import jregex.Replacer;

public class SafeHtmlUtil {

	private static Pattern scriptPattern = new Pattern("script",REFlags.IGNORE_CASE);
	private static Replacer scriptReplacer = scriptPattern.replacer("&#x73;cript");

	public static String HTMLEntityEncode(String input) {
		if(StringUtils.isBlank(input)) return null;
		
		String next = scriptReplacer.replace(input);

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < next.length(); ++i) {
			char ch = next.charAt(i);

			if (ch == '<')
				sb.append("&lt;");
			else if (ch == '>')
				sb.append("&gt;");
			else
				sb.append(ch);
		}

		return sb.toString();
	}
	
	/**
	 * 通过反射将对象所有String类的数据全部过滤一遍
	 * @param o
	 */
	private final static String TYPE_STRING = "java.lang.String";
	public static void EncodeByObject(Object o){
		try {
			if(o == null) return ;
			Field[] f = o.getClass().getDeclaredFields();
			Method get = null;
			Method set = null;
			String val = null;
			for(Field field : f){
				//只处理String类型的数据
				if(field.getGenericType().toString().equals(TYPE_STRING)){
					get = (Method) o.getClass().getMethod("get" + getMethodName(field.getName()));
					val = (String)get.invoke(o);
					if(StringUtils.isNotBlank(val)){
						set = (Method) o.getClass().getMethod("set" + getMethodName(field.getName()), get.getReturnType());
						set.invoke(o, HTMLEntityEncode(val));
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 把一个字符串的第一个字母大写
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
	
	public static void main(String[] args) throws Exception{
		EncodeByObject(new UserVO());
	}
}