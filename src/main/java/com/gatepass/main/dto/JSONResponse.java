package com.gatepass.main.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JSONResponse {
	
	 private int question_id;
	 private String language;
	 private String	response;
	 private int time;
	 private int attempts;
	 
	 
	
	 

}
