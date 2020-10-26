package com.tjut.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tjut.bean.Account;
import com.tjut.dbutil.DbcpUtils;
import com.tjut.util.MD5Utils;

@WebServlet("/QukuanServlet")
public class QukuanServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			DecimalFormat df2 = new DecimalFormat("#.0");
	
			// 1.��������
			// ������������
			request.setCharacterEncoding("UTF-8");
			String account_num = request.getParameter("account_num");
			System.out.println(account_num);
			String password = request.getParameter("password");
			String amount = request.getParameter("amount");
			
			float sub=Float.parseFloat(amount);
			//��password���м���
			password = MD5Utils.getPwd(password);
			
			//2.�жϸ������˻��Ƿ�ƥ��
			String sql = "select * from account where account_num=? and password=?";
			System.out.println(sql);
			String[] args = {account_num,password};
			Account account = DbcpUtils.AccountQuery(sql, args);
			// System.out.println(account);
			if (account.getAccount_id()==0) {
				// �˻����������
				// ��request���б���һ����Ϣ
				request.setAttribute("msg", "�˻������������ȷ�Ϻ������ύ");
				// ʹ������ת������ҳ����ת-----------��������ת���Ƿ���������������ֱ�Ӳ��ù�����
				request.getRequestDispatcher("trans/qukuan.jsp").forward(request, response);
				return;
			}
			// 3.��ѯ�˻����жϸ��˻��Ƿ񱻶���
			float balance = account.getBalance();
			System.out.println(balance);
			if (account.getStatus()==1) {
				// �˻������ᣬȡ��ʧ��
				// ��request���б���һ����Ϣ
				request.setAttribute("msg", "���˻��ѱ����ᣬ�޷����");
				// ʹ������ת������ҳ����ת-----------��������ת���Ƿ���������������ֱ�Ӳ��ù�����
				request.getRequestDispatcher("trans/qukuan.jsp").forward(request, response);
				return;
			}
			if (balance<sub) {
				// ���㣬ȡ��ʧ��
				// ��request���б���һ����Ϣ
				request.setAttribute("msg", "����");
				// ʹ������ת������ҳ����ת-----------��������ת���Ƿ���������������ֱ�Ӳ��ù�����
				request.getRequestDispatcher("trans/qukuan.jsp").forward(request, response);
				return;
			}
			
			// 4.ִ�д�����,����¼,������
			
			String sqlupdate = "update account set balance = balance-"+amount+" where account_num=?";
			String sqlrecord = "insert into record values(null,?,?,?,?,?,?,?,?)";
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date2 = df.format(date);
			Object args2[][] = {{account_num},{account_num,3,"-"+amount,0,df2.format(balance-sub),date2,"�������������ѧ֧��",0}};
			
			ArrayList<String> listSql = new ArrayList<String>();
			listSql.add(sqlupdate);
			listSql.add(sqlrecord);
			boolean accountUpdate = DbcpUtils. executeBatch(listSql,args2);
			if (accountUpdate==false) {
				request.setAttribute("msg", "���ʧ�ܣ�δ֪ԭ��");
				// ʹ������ת������ҳ����ת-----------��������ת���Ƿ���������������ֱ�Ӳ��ù�����
				request.getRequestDispatcher("trans/cunkuan.jsp").forward(request, response);
			}else {
				double gg = account.getBalance()-sub;
				String gg_str = df2.format(gg);
				float gg_float=Float.parseFloat(gg_str);
				System.out.println("gggg"+gg_float);
				
				request.setAttribute("type", "qukuan.jsp");
				request.setAttribute("balance", gg_float);
				request.getRequestDispatcher("success.jsp").forward(request, response);
			}
			
			
			
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
	}

}
