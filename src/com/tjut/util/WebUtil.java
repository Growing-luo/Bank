package com.tjut.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class WebUtil {
	// ������ķ�װ
	public static String getRequestParameter(HttpServletRequest request,String name) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String value=request.getParameter(name);
		return value==null?"":value;
	}
	
	// ����Ӧ�ķ�װ
	public static void handleResponse(HttpServletResponse response,String result) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();		// JAVA�з�Ϊ�ֽ������ַ���������
		out.print(result);
		out.flush();		// ��ˢ������
		out.close();		// ��encodeURI(VALUE)
	}
	
	public static void handleResponse(HttpServletResponse response,String status,String reason) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();		// JAVA�з�Ϊ�ֽ������ַ���������
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("status", status);
		map.put("reason", reason);
		out.print(JSON.toJSONString(map));
		out.flush();		// ��ˢ������
		out.close();		// ��encodeURI(VALUE)
	}

}
