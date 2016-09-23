package com.wjz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wjz.dao.Message;

public class SQLUtil {
	public static Connection conn;

	/**
	 * 连接数据库
	 */
	private static void connect() {
		try {
			if (conn == null) {
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://localhost:3306/WebChat?characterEncoding=utf-8";
				conn = DriverManager.getConnection(url, "root", "root");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 存储由该用户发出的消息
	 * 
	 * @param messages 消息列表
	 * @return true 成功，false 失败
	 */
	public static boolean storeMessageFormMe(List<Message> messages) {
		connect();
		try {
			String sql = "insert into messages(`from`, `to`, `message`, `time`) values(?,?,?,?)";
			PreparedStatement ps;
			for (Message msg : messages) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, msg.getFrom());
				ps.setString(2, msg.getTo());
				ps.setString(3, msg.getMessage());
				System.out.println(msg.getTime().toString());
				Timestamp time = new Timestamp(msg.getTime().getTime());
				ps.setTimestamp(4, time);
				ps.execute();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 查询所有消息记录
	 * @param userName 与之相关的用户名
	 * @return 消息记录列表
	 */
	public static List<Message> readMessageAboutMe(String userName){
		List<Message> message = new ArrayList<Message>();
		connect();
		try {
			String sql = "select * from messages where `from`=? or `to`=? or `to`='所有人' order by `time`";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, userName);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Message msg = new Message();
				msg.setFrom(rs.getString("from"));
				msg.setTo(rs.getString("to"));
				msg.setMessage(rs.getString("message"));
				msg.setTime(rs.getTimestamp("time"));
				message.add(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
}