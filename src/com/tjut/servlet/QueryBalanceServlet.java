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
			// 1.接收数据
			// 处理中文乱码
			request.setCharacterEncoding("UTF-8");
			String account_num = request.getParameter("account_num");
			String password = request.getParameter("password");
			//对password进行加密
			password = MD5Utils.getPwd(password);
			//System.out.println(password);
			// 3.查询账户余额
			String sql = "select * from account where account_num=? and password=?";
			System.out.println(sql);
			String[] args = {account_num,password};
			Account account = DbcpUtils.AccountQuery(sql, args);
			// System.out.println(account);
			if (account.getAccount_id()==0) {
				// 账户或密码错误
				// 向request域中保存一个信息
				request.setAttribute("msg", "账户或密码错误，请确认后重新提交");
				// 使用请求转发进行页面跳转-----------由于请求转发是服务器端请求，所以直接不用工程名
				request.getRequestDispatcher("trans/query_balance.jsp").forward(request, response);
				return;
			}
			if (account.getStatus()==1) {
				// 账户被冻结，存款失败
				// 向request域中保存一个信息
				request.setAttribute("msg", "该账户已被冻结");
				// 使用请求转发进行页面跳转-----------由于请求转发是服务器端请求，所以直接不用工程名
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
