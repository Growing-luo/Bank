package com.tjut.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tjut.dbutil.DbcpUtils;
import com.tjut.util.MD5Utils;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1.��������
			// ������������
			request.setCharacterEncoding("UTF-8");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			//��password���м���
			password = MD5Utils.getPwd(password);
	         
			//2.�жϵ�½�û�����
			String logtype = request.getParameter("logtype");
			
			// 3.��ѯ���ݣ��жϵ�½�û��˻������Ƿ���ȷ
			String sql = "";
			if (logtype.equals("1")) {
				sql = "select * from account where account_num='"+username+"' and password='"+password+"'";
			}
			if (logtype.equals("2")) {
				sql = "select * from admin where admin_num='"+username+"' and password='"+password+"'";
			}
			if (logtype.equals("3")) {
				sql = "select * from manage where manage_num='"+username+"' and password='"+password+"'";
			}
			Boolean exist = DbcpUtils.executeQuery(sql);
			exist = true;
			// 4.ҳ����ת
			if(exist==false) {
				// ��½ʧ��
				// ��request���б���һ����Ϣ
				request.setAttribute("msg", "�û������������! �����µ�½");
				// ʹ������ת������ҳ����ת-----------��������ת���Ƿ���������������ֱ�Ӳ��ù�����
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				}else {
					// ��½�ɹ�
					// ��ҳ����ת֮ǰ�����û���Ϣ�����浽�Ự��
					HttpSession session = request.getSession();
					// ��������
					session.setAttribute("username", username);
					// ʹ���ض���
					if (logtype.equals("1")) {
						response.sendRedirect("/Bank/khindex.jsp");
					}
					if (logtype.equals("2")) {
						response.sendRedirect("/Bank/ywindex.jsp");
					}
					if (logtype.equals("3")) {
						response.sendRedirect("/Bank/jlindex.jsp");
					}
					
				}
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
	}

}
