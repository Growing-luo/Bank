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
			// 1.接收数据
			// 处理中文乱码
			request.setCharacterEncoding("UTF-8");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			//对password进行加密
			password = MD5Utils.getPwd(password);
	         
			//2.判断登陆用户类型
			String logtype = request.getParameter("logtype");
			
			// 3.查询数据，判断登陆用户账户密码是否正确
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
			// 4.页面跳转
			if(exist==false) {
				// 登陆失败
				// 向request域中保存一个信息
				request.setAttribute("msg", "用户名或密码错误! 请重新登陆");
				// 使用请求转发进行页面跳转-----------由于请求转发是服务器端请求，所以直接不用工程名
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				}else {
					// 登陆成功
					// 在页面跳转之前保存用户信息：保存到会话中
					HttpSession session = request.getSession();
					// 保存数据
					session.setAttribute("username", username);
					// 使用重定向
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
