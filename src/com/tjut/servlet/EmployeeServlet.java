package com.tjut.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String caozuo = WebUtil.getRequestParameter(request, "caozuo");
		switch (caozuo) {
		case "initdatagrid":
			initdatagrid(request, response);
			break;
		case "add":
			add(request, response);
			break;
		case "delete":
			delete(request, response);
			break;
			
		case "edit":
			edit(request,response);
			break;

		default:
			break;
		}
	}

	// {"total":100,"rows":[{},{},{}]}
	private void initdatagrid(HttpServletRequest request, HttpServletResponse response) {
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
			if (condition.equals("")) {
				sql = "select * from employee";
			} else {
				sql = "select * from employee where " + condition; 
			}
			list = DbcpUtils.executeQuery(sql, r, p);
			
			
			for (int i = 0; i < list.size(); i++) {
				//把数字转成相应的省份和城市
				String pid = list.get(i).get("province");
				String cid = list.get(i).get("city");
				
				String sqlp = "select name from province where pid="+pid;
				String sqlc = "select name from city where cid="+cid;
				String province = DbcpUtils.QueryPC(sqlp);
				String city = DbcpUtils.QueryPC(sqlc);
				list.get(i).put("province", province);
				list.get(i).put("city", city);
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
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sql = "";
		try {
			String name_cn = WebUtil.getRequestParameter(request, "name_cn");
			String name_en = WebUtil.getRequestParameter(request, "name_en");
			String sex = WebUtil.getRequestParameter(request, "sex");
			String person_id = WebUtil.getRequestParameter(request, "person_id");
			String email = WebUtil.getRequestParameter(request, "email");
			String phone = WebUtil.getRequestParameter(request, "phone");
			String telphone = WebUtil.getRequestParameter(request, "telphone");
			String nationality = WebUtil.getRequestParameter(request, "nationality");
			String province = WebUtil.getRequestParameter(request, "province");
			String city = WebUtil.getRequestParameter(request, "city");
			String address = WebUtil.getRequestParameter(request, "address");
			String poscode = WebUtil.getRequestParameter(request, "poscode");
			String regplace = WebUtil.getRequestParameter(request, "regplace");
			String branch_bank = WebUtil.getRequestParameter(request, "branch_bank");
			System.out.println(branch_bank);

			if (DbUtil.judge("employee", "person_id", person_id)) {
				WebUtil.handleResponse(response, "error", "职员已存在！");
				return;
			}
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date2 = df.format(date);
			sql = "insert into employee values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String args[] = { name_cn, name_en, sex, person_id, email, phone, telphone, nationality, province, city, address, poscode, date2,regplace,branch_bank };
			boolean flag = DbcpUtils.AccountUpdate(sql, args);
			if (flag) {
				WebUtil.handleResponse(response, "success", "操作成功！");
				return;
			} else {
				WebUtil.handleResponse(response, "error", "操作数据库失败!");
				return;
			}

		} catch (Exception e) {
			WebUtil.handleResponse(response, "error", "添加失败！原因:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 String sql = "";
		 try {
			String employee_id = WebUtil.getRequestParameter(request, "employee_id");
			sql = "delete from employee where employee_id=?";
			Object args[] = {employee_id};
			boolean flag = DbcpUtils.AccountUpdate(sql, args);
			if (flag) {
				WebUtil.handleResponse(response, "success", "操作成功！");
				return;
			} else {
				WebUtil.handleResponse(response, "error", "操作数据库失败!");
				return;
			}
		} catch (Exception e) {
			WebUtil.handleResponse(response, "error","删除失败！原因："+e.getMessage());
			e.printStackTrace();
		}
	 }

	private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sql = "";
		try {
			String employee_id = WebUtil.getRequestParameter(request, "employee_id");
			String name_cn = WebUtil.getRequestParameter(request, "name_cn");
			String name_en = WebUtil.getRequestParameter(request, "name_en");
			String sex = WebUtil.getRequestParameter(request, "sex");
			String person_id = WebUtil.getRequestParameter(request, "person_id");
			String email = WebUtil.getRequestParameter(request, "email");
			String phone = WebUtil.getRequestParameter(request, "phone");
			String telphone = WebUtil.getRequestParameter(request, "telphone");
			String nationality = WebUtil.getRequestParameter(request, "nationality");
			String province = WebUtil.getRequestParameter(request, "province");
			String city = WebUtil.getRequestParameter(request, "city");
			String address = WebUtil.getRequestParameter(request, "address");
			String poscode = WebUtil.getRequestParameter(request, "poscode");
			String regplace = WebUtil.getRequestParameter(request, "regplace");
			String branch_bank = WebUtil.getRequestParameter(request, "branch_bank");
			
			sql = "update employee set name_cn=?,name_en=?,sex=?,person_id=?,email=?,phone=?,telphone=?,nationality=?,province=?,city=?,address=?,poscode=?,regplace=?,branch_bank=? where employee_id=?";
			String args[] = { name_cn, name_en, sex, person_id, email, phone, telphone, nationality, province, city, address, poscode,regplace,branch_bank,employee_id };
			boolean flag = DbcpUtils.AccountUpdate(sql, args);
			if (flag) {
				WebUtil.handleResponse(response, "success", "操作成功！");
				return;
			} else {
				WebUtil.handleResponse(response, "error", "操作数据库失败!");
				return;
			}

		} catch (Exception e) {
			WebUtil.handleResponse(response, "error", "添加失败！原因:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
