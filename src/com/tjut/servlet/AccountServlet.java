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

import org.eclipse.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier;

import com.alibaba.fastjson.JSON;
import com.tjut.dbutil.DbUtil;
import com.tjut.dbutil.DbcpUtils;
import com.tjut.util.MD5Utils;
import com.tjut.util.WebUtil;

@WebServlet("/AccountServlet")
public class AccountServlet extends HttpServlet {

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
			System.out.println("servlet������");
			add(request, response);
			break;
		case "frozen":
			frozen(request, response);
			break;
			
		case "unfrozen":
			unfrozen(request,response);
			break;
		case "cancel":
			cancel(request,response);
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
			System.out.println(condition);
			String page = WebUtil.getRequestParameter(request, "page"); // �ڼ�ҳ
			String rows = WebUtil.getRequestParameter(request, "rows"); // ÿҳ����
			if (page.equals(""))
				page = "0";
			if (page.equals(""))
				rows = "0";
			int p = Integer.parseInt(page);
			int r = Integer.parseInt(rows);
			if (condition.equals("")) {
				sql = "select a.account_id,a.account_num,c.name_cn,a.status,a.regdate,a.regplace from account a,customer c where a.customer_id=c.customer_id ";
			} else {
				sql = "select a.account_id,a.account_num,c.name_cn,a.status,a.regdate,a.regplace from account a,customer c where a.account_num='"+condition+"' and a.customer_id=c.customer_id ";
			}
			list = DbcpUtils.executeQuery(sql, r, p);
			
			for (int i = 0; i < list.size(); i++) {
				//������ת����Ӧ��״̬
				String status = list.get(i).get("status");
				if (status.equals("0")) {
					list.get(i).put("status", "����");
				}
				if (status.equals("1")) {
					list.get(i).put("status", "����");
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
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sql = "";
		try {
			String name_cn = WebUtil.getRequestParameter(request, "name_cn");
			String person_id = WebUtil.getRequestParameter(request, "person_id");
			String regplace = WebUtil.getRequestParameter(request, "regplace");
			String password = WebUtil.getRequestParameter(request, "password");
			
			//���������
			password = MD5Utils.getPwd(password);
			
			//�ҳ������֤��customer_id
			sql = "select customer_id from customer where person_id="+person_id;
			String customer_id = DbcpUtils.CustomerQuery(sql);
			if (customer_id.equals("")) {
				WebUtil.handleResponse(response, "error", "�ÿͻ�δע�ᣬ����ע��");
				return;
			}
			//���֤�ź�������һ�£����ش�����Ϣ
			sql = "select customer_id from customer where person_id="+person_id+" and name_cn='"+name_cn+"'";
			customer_id  = DbcpUtils.CustomerQuery(sql);
			if (customer_id.equals("")) {
				WebUtil.handleResponse(response, "error", "���֤����������һ�£���ȷ�Ϻ������ύ");
				return;
			}
			
			sql = "select account_num from account order by account_id desc limit 0,1";
			String account_num_max = DbcpUtils.Querynum(sql);
			long account_num_int_old=10000000000l;
			if (!account_num_max.equals("")) {
				account_num_int_old = Long.parseLong(account_num_max);
			}
			long account_num_int = account_num_int_old+1;
			String account_num = String.valueOf(account_num_int);
			
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date2 = df.format(date);
			sql = "insert into account values(null,?,?,?,?,?,?,?)";
			String args[] = { customer_id, account_num, password, "0", "0", date2, regplace };
			boolean flag = DbcpUtils.AccountUpdate(sql, args);
			if (flag) {
				WebUtil.handleResponse(response, "success", "�����ɹ���");
				return;
			} else {
				WebUtil.handleResponse(response, "error", "�������ݿ�ʧ��!");
				return;
			}
			
					} catch (Exception e) {
						WebUtil.handleResponse(response, "error", "���ʧ�ܣ�ԭ��:" + e.getMessage());
						e.printStackTrace();
					}
	}
	
	private void frozen(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 String sql = "";
		 try {
			String account_id = WebUtil.getRequestParameter(request, "account_id");
			sql = "update account set status=1 where account_id=?";
			Object args[] = {account_id};
			boolean flag = DbcpUtils.AccountUpdate(sql, args);
			if (flag) {
				WebUtil.handleResponse(response, "success", "�����ɹ���");
				return;
			} else {
				WebUtil.handleResponse(response, "error", "�������ݿ�ʧ��!");
				return;
			}
		} catch (Exception e) {
			WebUtil.handleResponse(response, "error","����ʧ�ܣ�ԭ��"+e.getMessage());
			e.printStackTrace();
		}
	 }
	
	private void unfrozen(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 String sql = "";
		 try {
			String account_id = WebUtil.getRequestParameter(request, "account_id");
			sql = "update account set status=0 where account_id=?";
			Object args[] = {account_id};
			boolean flag = DbcpUtils.AccountUpdate(sql, args);
			if (flag) {
				WebUtil.handleResponse(response, "success", "�����ɹ���");
				return;
			} else {
				WebUtil.handleResponse(response, "error", "�������ݿ�ʧ��!");
				return;
			}
		} catch (Exception e) {
			WebUtil.handleResponse(response, "error","�ⶳʧ�ܣ�ԭ��"+e.getMessage());
			e.printStackTrace();
		}
	 }
	
	private void cancel(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 String sql = "";
		 try {
			String account_id = WebUtil.getRequestParameter(request, "account_id");
			sql = "delete from account where account_id=?";
			Object args[] = {account_id};
			boolean flag = DbcpUtils.AccountUpdate(sql, args);
			if (flag) {
				WebUtil.handleResponse(response, "success", "�����ɹ���");
				return;
			} else {
				WebUtil.handleResponse(response, "error", "�������ݿ�ʧ��!");
				return;
			}
		} catch (Exception e) {
			WebUtil.handleResponse(response, "error","ע��ʧ�ܣ�ԭ��"+e.getMessage());
			e.printStackTrace();
		}
	 }
	


	

}
