package com.tjut.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.tjut.dbutil.DbcpUtils;
import com.tjut.util.WebUtil;


@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public Test() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String caozuo = WebUtil.getRequestParameter(request, "caozuo");
		
		switch (caozuo) {
		case "initProvince":
			initProvince(request,response);
			break;
			
		case "initCity":
			initCity(request, response);
			break;
		default:
			break;
		}
		
		
		
	}
	
	void initProvince(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sql = "select * from province";
		ArrayList<HashMap<String, String>> list = DbcpUtils.executeQuery1(sql);
		String result = JSON.toJSONString(list);
		
		WebUtil.handleResponse(response, result);
	}
	
	void initCity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String province = WebUtil.getRequestParameter(request, "province");
		String sql = "select * from city where pid=?";
		Object para[] = {province};
		ArrayList<HashMap<String, String>> list = DbcpUtils.executeQuery(sql,para);
		String result = JSON.toJSONString(list);
		WebUtil.handleResponse(response, result);
	}

}
