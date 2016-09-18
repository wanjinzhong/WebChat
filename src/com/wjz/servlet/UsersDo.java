package com.wjz.servlet;

import java.util.HashMap;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/UsersDo/{userName}")
public class UsersDo {
	private static HashMap<String, Session> userMap = new HashMap<String, Session>();

	@OnOpen
	public void open(@PathParam("userName") String userName, Session session, EndpointConfig config) {
		System.out.println("Do: " + userName);
		userMap.put(userName, session);
		System.out.println("人员个数：" + userMap.size());
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		System.out.println(message);
		for (Session s : userMap.values())
			s.getAsyncRemote().sendText(message);
	}

	@OnClose
	public void end(Session session) {

	}

}
