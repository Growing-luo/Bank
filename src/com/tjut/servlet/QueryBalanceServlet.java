package com.tjut.servlet;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tjut.bean.Account;
import com.tjut.dbutil.DbcpUtils;
import com.tjut.util.MD5Utils;

/**
 * Servlet implementation class QueryBalanceServlet
 */
@WebServlet("/QueryBalanceServlet")
public class QueryBalanceServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			DecimalFormat df2 = new DecimalFormat("#.0");
			// 1.��������
			// ������������
			request.setCharacterEncoding("UTF-8");
			String account_num = request.getParameter("account_num");
			String password = request.getParameter("password");
			//��password���м���
			password = MD5Utils.getPwd(password);
			//System.out.println(password);
			// 3.��ѯ�˻����
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
				request.getRequestDispatcher("trans/query_balance.jsp").forward(request, response);
				return;
			}
			if (account.getStatus()==1) {
				// �˻������ᣬ���ʧ��
				// ��request���б���һ����Ϣ
				request.setAttribute("msg", "���˻��ѱ�����");
				// ʹ������ת������ҳ����ת-----------��������ת���Ƿ���������������ֱ�Ӳ��ù�����
				request.getRequestDispatcher("trans/query_balance.jsp").forward(request, response);
			}else {
				double gg = account.getBalance();
				String gg_str = df2.format(gg);
				float gg_float=Float.parseFloat(gg_str);
				//System.out.println("gggg"+gg_float);
				request.setAttribute("type", "query_balance.jsp");
				request.setAttribute("balance", gg_float);
				request.getRequestDispatcher("success.jsp").forward(request, response);
			}
			
			
			
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
	}

}
