package com.tjut.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.tjut.dbutil.DbUtil;
import com.tjut.dbutil.DbcpUtils;
import com.tjut.util.WebUtil;

@WebServlet("/QueryRecordServlet")
public class QueryRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sql = "";
		ArrayList<HashMap<String, String>> list = null;
		HashMap<String, Object> hash = new HashMap<String, Object>();
		int rowsCounts = 0;
		String result = "";
		try {
			String condition = WebUtil.getRequestParameter(request, "condition");
			String page = WebUtil.getRequestParameter(request, "page"); // 第几页
			String rows = WebUtil.getRequestParameter(request, "rows"); // 每页行数
			if (page.equals(""))
				page = "0";
			if (page.equals(""))
				rows = "0";
			int p = Integer.parseInt(page);
			int r = Integer.parseInt(rows);
			System.out.println(condition);
			sql = "select * from record where " + condition + "order by record_id desc"; 
			System.out.println(sql);
			list = DbcpUtils.executeQuery(sql, r, p);
			
			for (int i = 0; i < list.size(); i++) {
				//把数字转成相应的状态
				String type = list.get(i).get("op_type");
				if (type.equals("0")) {
					list.get(i).put("op_type", "注销");
				}
				if (type.equals("1")) {
					list.get(i).put("op_type", "冻结");
				}
				if (type.equals("2")) {
					list.get(i).put("op_type", "存款");
				}
				if (type.equals("3")) {
					list.get(i).put("op_type", "取款");
				}
				if (type.equals("4")) {
					list.get(i).put("op_type", "转账");
				}
				if (type.equals("5")) {
					list.get(i).put("op_type", "解冻");
				}
				if (type.equals("6")) {
					list.get(i).put("op_type", "开户");
				}
				
			}
			rowsCounts = DbUtil.getRowsCount(sql);
			hash.put("total", rowsCounts);
			hash.put("rows", list);
			result = JSON.toJSONString(hash);
			// System.out.println(result);
			WebUtil.handleResponse(response, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
