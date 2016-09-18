package com.wjz.dao;

import java.util.Vector;

public class Users {
	private static Vector<String> users;
	public Users(){
		if (users == null)
			users = new Vector<String>();
	}
	public static Vector<String> getUsers(){
		if (users == null)
			users = new Vector<String>();
		return users;
	}
	/**
	 * 添加用户
	 * @param name 用户名
	 * @return false 用户已存在
	 * @return true 添加成功
	 */
	public boolean addUser(String name){
		for(String str : users){
			if (str.equals(name))
				return false;
		}
		users.add(name);
		return true;
	}
	/**
	 * 删除用户
	 * @param name 用户名
	 * @return true 删除成功
	 * @return false 用户名不存在
	 */
	public boolean removeUser(String name){
		return users.remove(name);
	}
}
