package com.gatepass.main.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CredentialsDTO {
	
	@NotNull
	private String username;
	@NotNull
	private String password;
	private Integer id;
	
	

}
