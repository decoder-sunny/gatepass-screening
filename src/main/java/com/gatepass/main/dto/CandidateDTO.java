
package com.gatepass.main.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateDTO {
	
    private Integer id;
	
    private Integer function_id;
	private String function;
	
	private Integer gender_id;
	private String gender;
	
	private String positionApplied;
	
	private String location;
	private Integer experienceYears;
	private Integer experienceMonths;
	
	private String cv;
	
	private UserDTO userDTO;
	
	private List<AssignDTO> assignDTO;
	private Long length;
	private Integer template_id;
	
	private List<ScreenResultDTO> screen;
	
	private Boolean isGatepass;
	private Boolean isScreening;
	
	private int marks;
	
	private String organisation;

	
	
}
