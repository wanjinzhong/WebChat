package com.wjz.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.PreparedStatement;
import com.wjz.util.SQLUtil;

/*
 * 
 */
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("userName");
		String pwd = request.getParameter("pwd");
		String repwd = request.getParameter("repwd");
		// 判断非法跳转
		if (userName == null || pwd == null) {
			response.sendRedirect("regist.jsp");
			return;
		}

		// 验证主要信息不为空
		if (userName.equals("") || pwd.equals("")) {
			out.print("<script type='text/javascript'>alert('用户名，密码不能为空');window.location = 'regist.jsp';</script>");
			return;
		}
		// 用户名不能为所有人
		if (userName.equals("所有人")) {
			out.print("<script type='text/javascript'>alert('用户名不能为所有人');window.location = 'regist.jsp';</script>");
			return;
		}
		// 不能包含非法字符
		if (userName.indexOf('@') != -1 || userName.indexOf('<') != -1 || userName.indexOf('>') != -1) {
			out.print(
					"<script type='text/javascript'>alert('不能包含非法字符“@”，“<”，“>”');window.location = 'regist.jsp';</script>");
			return;
		}
		// 验证密码一致
		if (!pwd.equals(repwd)) {
			out.print("<script type='text/javascript'>alert('两次密码不一致');window.location = 'regist.jsp';</script>");
			return;
		}
		// 验证用户名是否存在
		if (SQLUtil.userIsExist(userName)) {
			out.print("<script type='text/javascript'>alert('用户名已存在');window.location = 'regist.jsp';</script>");
			return;
		}
		// 注册
		boolean regist = SQLUtil.regist(userName, pwd);
		if (regist) {
			out.print("<script type='text/javascript'>alert('注册成功');window.location = 'login.jsp';</script>");
			return;
		} else {
			out.print(
					"<script type='text/javascript'>alert('注册失败，清联系管理员维护系统QQ:1051750377');window.location = 'regist.jsp';</script>");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
