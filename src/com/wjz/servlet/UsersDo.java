package com.wjz.servlet;

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

	@OnOpen
	public void open(@PathParam("userName") String userName, Session session, EndpointConfig config) {

		myName = userName;
		Users.userMap.put(userName, session);
		broadCastName();
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		prepareMessage("normal", "大家", message);
	}

	@OnClose
	public void onClose(Session session) {
		Users.userMap.remove(myName);
		broadCastName();
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
		JSONObject json = new JSONObject();
		json.put("type", "name");
		json.put("content", users);
		for (Session s : Users.userMap.values())
			s.getAsyncRemote().sendText(json.toString());
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
		content.put("from", myName);
		content.put("message", message);
		content.put("to", "大家");
		content.put("msgtype", "nomal");
		json.put("content", content);
		sendMessageToAll(json);
	}
	/**
	 * 发送消息到全体用户（广播）
	 * @param json 封装好的消息体
	 */
	public void sendMessageToAll(JSONObject json) {
		for (Session s : Users.userMap.values())
			s.getAsyncRemote().sendText(json.toString());
	}
}
