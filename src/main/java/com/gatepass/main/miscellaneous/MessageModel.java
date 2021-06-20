package com.gatepass.main.miscellaneous;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageModel {
	
	private int status;
	private String message;
	
	public MessageModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageModel(int status, String message) {
		this.status = status;
		this.message = message;
	}
	

}
