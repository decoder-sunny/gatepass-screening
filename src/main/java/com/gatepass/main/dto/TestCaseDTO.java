package com.gatepass.main.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestCaseDTO {
	
	@NotNull(message="Code cannot be empty")
	private int code_id;
	
	@NotNull(message="Choose language")
	private String language;
	
	@NotNull(message="Code cannot be empty")
	private String response;
	
	private int versionIndex;	
	private String stdin;

	

}
