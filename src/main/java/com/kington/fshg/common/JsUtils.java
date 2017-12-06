package com.kington.fshg.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.kington.fshg.webapp.security.UserContext;

public class JsUtils {

	/**
	 * 把 java 的异常信息用 alert 显示
	 * @throws IOException 
	 */
	public static void alert(String msg){
		String alert = "alert('" + StringEscapeUtils.escapeJavaScript(msg) + "');";
		exec(alert);
	}
	
	/**
	 * 执行JS脚本
	 * @param js
	 * @throws IOException
	 */
	public static void exec(String js){
		UserContext ctx = UserContext.get();
		HttpServletResponse resp = ctx.getResponse();
		resp.reset();
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = resp.getWriter();
			out.print("<script>");
			if (StringUtils.isNotBlank(js)) out.print(js);
			out.print("</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void writeJson(String key, Object value){
		String[] keys = {key};
		Object[] values = {value};
		
		writeJson(keys, values);
	}
	
	public static void writeJson(String[] keys, Object[] values){
		Map<String,Object> map = new HashMap<String,Object>();
		for (int i=0; i< keys.length; i++){
			if (StringUtils.isBlank(keys[i])) continue;
			map.put(keys[i], values[i]);
		}

		JSONObject json = JSONObject.fromObject(map);
		
		writeText(json.toString());
	}
	
	public static void writeText(String content){
		UserContext ctx = UserContext.get();
		HttpServletResponse resp = ctx.getResponse();
		resp.reset();
		resp.setDateHeader("Expires", -1);
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = resp.getWriter();
			out.print(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("JSON返回值异常");
			e.printStackTrace();
		}
	}
	
}
