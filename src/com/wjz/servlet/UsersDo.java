package com.wjz.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.wjz.dao.Users;

@ServerEndpoint(value = "/UsersDo/{userName}")
public class UsersDo {
	private String myName = null;
	public static final String NORMAL = "normal";
	public static final String SYSTEM = "system";
	@OnOpen
	public void open(@PathParam("userName") String userName, Session session, EndpointConfig config) {

		myName = userName;
		Users.userMap.put(userName, session);
		broadCastName();
		prepareMessage(UsersDo.SYSTEM,"所有人",myName + "上线啦！");
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		JSONObject json = new JSONObject(message);
		String to = json.getString("to");
		String content = json.getString("message");
		prepareMessage(UsersDo.NORMAL, to, content);
	}

	@OnClose
	public void onClose(Session session) {
		Users.userMap.remove(myName);
		System.out.println("有人下线。。。");
		broadCastName();
		prepareMessage("system","all",myName + "下线了！");
	}

	@OnError
	public void onError(Throwable t) {

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
		for (Session s : Users.userMap.values()){
			//s.getAsyncRemote().sendText(json.toString());
			try {
				s.getBasicRemote().sendText(json.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
	public void prepareMessage(String msgtype, String to, String message) {
		JSONObject json = new JSONObject();
		json.put("type", "content");
		JSONObject content = new JSONObject();
		content.put("message", message);
		content.put("msgtype", msgtype);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String date = sdf.format(d);
		content.put("time", date);
		content.put("from", myName);
		if (msgtype.equals(UsersDo.NORMAL)){
			content.put("to", to);
		}else if (msgtype.equals(UsersDo.SYSTEM)){
			content.put("number", Users.userMap.size());
		}
		json.put("content", content);
		System.out.println(json.toString());
		System.out.println("to: " + to);
		if (to.equals("所有人")){
			sendMessageToAll(json);
		} else
			sendMessageToOne(json, to);
	}
	/**
	 * 发送消息到全体用户（广播）
	 * @param json 封装好的消息体
	 */
	public void sendMessageToAll(JSONObject json) {
		for (Session s : Users.userMap.values()){
			//s.getAsyncRemote().sendText(json.toString());
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
	 * @param json
	 * @param to
	 */
	public void sendMessageToOne(JSONObject json, String to){
		Session s = Users.userMap.get(to);
		try {
			s.getBasicRemote().sendText(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
