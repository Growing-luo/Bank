package com.tjut.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class WebUtil {
	// 做请求的封装
	public static String getRequestParameter(HttpServletRequest request,String name) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String value=request.getParameter(name);
		return value==null?"":value;
	}
	
	// 做相应的封装
	public static void handleResponse(HttpServletResponse response,String result) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();		// JAVA中分为字节流和字符流两种流
		out.print(result);
		out.flush();		// 冲刷缓冲区
		out.close();		// 用encodeURI(VALUE)
	}
	
	public static void handleResponse(HttpServletResponse response,String status,String reason) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();		// JAVA中分为字节流和字符流两种流
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("status", status);
		map.put("reason", reason);
		out.print(JSON.toJSONString(map));
		out.flush();		// 冲刷缓冲区
		out.close();		// 用encodeURI(VALUE)
	}

}
