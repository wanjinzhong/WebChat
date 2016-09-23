package com.wjz.dao;

import java.util.Date;

public class Message {
	private String from;
	private String to;
	private String message;
	private Date time;
	private String noHtml;
	public String getNoHtml() {
		return noHtml;
	}
	public void setNoHtml(String noHtml) {
		this.noHtml = noHtml;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
