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

@WebServlet("/ZhuanzhangServlet")
public class ZhuanzhangServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			DecimalFormat df2 = new DecimalFormat("#.0");
			// 1.接收数据
			// 处理中文乱码
			request.setCharacterEncoding("UTF-8");
			String account_num = request.getParameter("account_num");
			String password = request.getParameter("password");
			String in_account_num = request.getParameter("in_account_num");
			String amount = request.getParameter("amount");
			
			float sub=Float.parseFloat(amount);
			//对password进行加密
			password = MD5Utils.getPwd(password);
			
			//2.判断该密码账户是否匹配
			String sql = "select * from account where account_num=? and password=?";
			String[] args = {account_num,password};
			Account account = DbcpUtils.AccountQuery(sql, args);
			// System.out.println(account);
			if (account.getAccount_id()==0) {
				// 账户或密码错误
				// 向request域中保存一个信息
				request.setAttribute("msg", "账户或密码错误，请确认后重新提交");
				request.getRequestDispatcher("trans/zhuanzhang.jsp").forward(request, response);
				return;
			}
			float balance = account.getBalance();
			// 3.查询账户，判断该账户是否被冻结
			
			if (account.getStatus()==1) {
				// 账户被冻结，取款失败
				// 向request域中保存一个信息
				request.setAttribute("msg", "该账户已被冻结，无法存款");
				// 使用请求转发进行页面跳转-----------由于请求转发是服务器端请求，所以直接不用工程名
				request.getRequestDispatcher("trans/zhuanzhang.jsp").forward(request, response);
				return;
			}
			if (balance<sub) {
				// 余额不足，取款失败
				// 向request域中保存一个信息
				request.setAttribute("msg", "余额不足");
				// 使用请求转发进行页面跳转-----------由于请求转发是服务器端请求，所以直接不用工程名
				request.getRequestDispatcher("trans/zhuanzhang.jsp").forward(request, response);
				return;
			}
			
			//4.判断转入账户是否存在
			String sql2 = "select * from account where account_num=?";
			String[] args2 = {in_account_num};
			Account in_account = DbcpUtils.AccountQuery(sql2, args2);
			if (in_account.getAccount_id()==0) {
				// 转入账户不存在
				request.setAttribute("msg", "转入账户不存在");
				request.getRequestDispatcher("trans/zhuanzhang.jsp").forward(request, response);
				return;
			}
			// 5.转入账户是否被冻结
			if (in_account.getStatus()==1) {
				// 转入账户被冻结，转账失败
				// 向request域中保存一个信息
				request.setAttribute("msg", "转入账户已被冻结，无法存款");
				// 使用请求转发进行页面跳转-----------由于请求转发是服务器端请求，所以直接不用工程名
				request.getRequestDispatcher("trans/zhuanzhang.jsp").forward(request, response);
				return;
			}
			
			// 6.执行转账操作,并记录,批处理
			
			String sqlupdate1 = "update account set balance = balance-"+amount+" where account_num=?";
			String sqlupdate2 = "update account set balance = balance+"+amount+" where account_num=?";
			String sqlrecord1 = "insert into record values(null,?,?,?,?,?,?,?,?)";
			String sqlrecord2 = "insert into record values(null,?,?,?,?,?,?,?,?)";
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date2 = df.format(date);
			Object args3[][] = {{account_num},{in_account_num},{account_num,4,"-"+amount,0,balance-sub,date2,"西青区天津理工大学支行",in_account_num},{in_account_num,4,"+"+amount,0,balance+sub,date2,"西青区天津理工大学支行",account_num}};
			System.out.println(Arrays.deepToString(args3));
			ArrayList<String> listSql = new ArrayList<String>();
			listSql.add(sqlupdate1);
			listSql.add(sqlupdate2);
			listSql.add(sqlrecord1);
			listSql.add(sqlrecord2);
			boolean accountUpdate = DbcpUtils. executeBatch(listSql,args3);
			if (accountUpdate==false) {
				request.setAttribute("msg", "转账失败，未知原因！");
				// 使用请求转发进行页面跳转-----------由于请求转发是服务器端请求，所以直接不用工程名
				request.getRequestDispatcher("trans/zhuanzhang.jsp").forward(request, response);
			}else {
				
				double gg = account.getBalance()-sub;
				String gg_str = df2.format(gg);
				float gg_float=Float.parseFloat(gg_str);
			
				request.setAttribute("type", "zhuanzhang.jsp");
				request.setAttribute("balance", gg_float);
				request.getRequestDispatcher("success.jsp").forward(request, response);
			}
			
			
			
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
	}

}
