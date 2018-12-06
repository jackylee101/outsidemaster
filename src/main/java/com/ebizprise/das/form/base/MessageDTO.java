package com.ebizprise.das.form.base;

import java.awt.TrayIcon.MessageType;

public class MessageDTO {

	private MessageType error;
	private String msg;

	public MessageDTO(MessageType error, String msg) {
		super();
		this.error = error;
		this.msg = msg;
	}

	public MessageType getError() {
		return error;
	}

	public void setError(MessageType error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
