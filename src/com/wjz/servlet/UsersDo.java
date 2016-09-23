package com.wjz.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.wjz.dao.Message;
import com.wjz.dao.Users;
import com.wjz.util.MessageUtil;
import com.wjz.util.SQLUtil;
import com.wjz.util.SensitiveWord;

@ServerEndpoint(value = "/UsersDo/{userName}")
public class UsersDo {
	private Session session = null;
	private String myName = null;
	private List<Message> messages = new ArrayList<Message>();
	private List<Message> msgRecord = null;
	public static final String NORMAL = "normal";
	public static final String SYSTEM = "system";
	public static final String RECORD = "record";
	/**
	 * WebSocket连接
	 * @param userName 用户名
	 * @param session 该用户的一个会话
	 * @param config 配置
	 */
	@OnOpen
	public void open(@PathParam("userName") String userName, Session session, EndpointConfig config) {
		this.session = session;
		myName = userName;
		Users.userMap.put(userName, session);
		// 广播用户列表
		broadCastName();
		// 通知上线
		prepareMessage(UsersDo.SYSTEM, "所有人", false, myName + "上线啦！");
		sendMsgRecord();
	}
	public void sendMsgRecord(){
		// 获取消息记录
		msgRecord = SQLUtil.readMessageAboutMe(myName);
		for (Message msg : msgRecord){
			String to = msg.getTo();
			String from = msg.getFrom();
			String msgContent = msg.getMessage();
			Date time = msg.getTime();
			String nohtml = msg.getNoHtml();
			JSONObject json = new JSONObject();
			json.put("type", "content");
			JSONObject content = new JSONObject();
			content.put("message", msgContent);
			content.put("msgtype", UsersDo.RECORD);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String datetime = sdf.format(time);
			content.put("time", datetime);
			content.put("from", from);
			content.put("to", to);
			content.put("noHtml", nohtml);
			json.put("content", content);
			try {
				session.getBasicRemote().sendText(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 收到用户发来和消息
	 * @param session 该用户的一个会话
	 * @param config 配置
	 */
	@OnMessage
	public void onMessage(Session session, String message) {
		JSONObject json = new JSONObject(message);
		String to = json.getString("to");
		boolean noHtml = json.getBoolean("noHtml");
		String content = json.getString("message");
		prepareMessage(UsersDo.NORMAL, to, noHtml, content);
	}

	/**
	 * 用户下线
	 * @param session 该用户的一个会话
	 */
	@OnClose
	public void onClose(Session session) {
		SQLUtil.storeMessageFormMe(messages);
		Users.userMap.remove(myName);
		System.out.println("有人下线。。。");
		broadCastName();
		prepareMessage("system", "所有人", false, myName + "下线了！");
	}

	@OnError
	public void onError(Throwable t) {
		SQLUtil.storeMessageFormMe(messages);
	}

	/**
	 * 广播用户名到每个用户
	 */
	public void broadCastName() {
		System.out.println("人员个数：" + Users.userMap.size());
		String users = "";
		Set<String> userNames = Users.userMap.keySet();
		for (String name : userNames) {
			users += name + "@";
		}
		if (users.length() > 0) {
			users = users.substring(0, users.length() - 1);
		}
		System.out.println(users);
		JSONObject json = new JSONObject();
		json.put("type", "name");
		json.put("content", users);
		for (Session s : Users.userMap.values()) {
			try {
				s.getBasicRemote().sendText(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 准备数据，将数据封装成JSON对象，以准备发送。
	 * 
	 * @param msgtype
	 *            消息类型（normal 普通，system 系统）
	 * @param to
	 *            发送对象用户名
	 * @param message
	 *            发送的消息正文
	 */
	public void prepareMessage(String msgtype, String to, boolean noHtml, String message) {
		if (!noHtml) {
			message = MessageUtil.fomatTag(message);
		}
		message = SensitiveWord.formatWord(message);
		message = MessageUtil.formatFace(message);
		JSONObject json = new JSONObject();
		json.put("type", "content");
		JSONObject content = new JSONObject();
		content.put("message", message);
		content.put("msgtype", msgtype);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date d = new Date();
		String date = sdf.format(d);
		content.put("time", date);
		content.put("from", myName);
		if (msgtype.equals(UsersDo.NORMAL)) {
			content.put("to", to);
			Message msg = new Message();
			msg.setFrom(myName);
			msg.setTo(to);
			msg.setMessage(message);
			msg.setTime(d);
			messages.add(msg);
		} else if (msgtype.equals(UsersDo.SYSTEM)) {
			content.put("number", Users.userMap.size());
		}
		json.put("content", content);
		System.out.println(json.toString());
		System.out.println("to: " + to);
		if (to.equals("所有人")) {
			sendMessageToAll(json);
		} else
			sendMessageToOne(json, to);
	}

	/**
	 * 发送消息到全体用户（广播）
	 * 
	 * @param json
	 *            封装好的消息体
	 */
	public void sendMessageToAll(JSONObject json) {
		for (Session s : Users.userMap.values()) {
			// s.getAsyncRemote().sendText(json.toString());
			try {
				s.getBasicRemote().sendText(json.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送消息到单个用户（单播）
	 * 
	 * @param json	消息
	 * @param to	要发送到的用户
	 */
	public void sendMessageToOne(JSONObject json, String to) {
		Session toSession = Users.userMap.get(to);
		Session fromSession = Users.userMap.get(myName);
		try {
			toSession.getBasicRemote().sendText(json.toString());
			fromSession.getBasicRemote().sendText(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
