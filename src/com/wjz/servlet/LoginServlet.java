package com.wjz.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wjz.dao.Users;
import com.wjz.util.SQLUtil;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("userName");
		String pwd = request.getParameter("pwd");
		if (userName == null || userName.trim().equals("")) {
			out.print("<script type='text/javascript'>alert('请输入用户名');window.location = 'login.jsp';</script>");
			return;
		}
		if (userName.equals("所有人")){
			out.print("<script type='text/javascript'>alert('不能取名字为“所有人”');window.location = 'login.jsp';</script>");
			return;
		}
		if (!SQLUtil.userIsExist(userName)){
			out.print("<script type='text/javascript'>alert('用户名不存在');window.location = 'login.jsp';</script>");
			return;
		}
		if (!SQLUtil.login(userName, pwd)){
			out.print("<script type='text/javascript'>alert('用户名或密码不正确');window.location = 'login.jsp';</script>");
			return;
		}
		if (!Users.userMap.containsKey(userName)) {
			request.getSession().setAttribute("userName", userName);
			response.sendRedirect("index.jsp");
			return;
		} else {
			out.print("<script type='text/javascript'>alert('该用户已登陆');window.location = 'login.jsp';</script>");
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
