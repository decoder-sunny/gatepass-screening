package com.gatepass.main.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gatepass.main.dto.CandidateDTO;
import com.gatepass.main.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	
	private final String jwttoken;
	private List<Role_Entitlement_Mapping> entitlements;
	private UserDTO userDTO;
	private CandidateDTO candidateDTO;

	public JwtResponse(String jwttoken, List<Role_Entitlement_Mapping> list,UserDTO dto) {
		super();
		this.jwttoken = jwttoken;
		this.entitlements = list;
		this.userDTO=dto;
	}
	
	public JwtResponse(String jwttoken, CandidateDTO candidateDTO) {
		super();
		this.jwttoken = jwttoken;
		this.candidateDTO=candidateDTO;
	}

	
	
}