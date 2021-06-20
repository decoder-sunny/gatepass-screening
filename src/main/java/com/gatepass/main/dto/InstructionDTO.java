package com.gatepass.main.dto;



import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class InstructionDTO {
	
	private Integer section_id;
	private String section;
	private Long totalQuestions;
	private Long totalTime;
	private Boolean completionStatus;
	
	
	public InstructionDTO(Integer section_id, String section, Long totalQuestions, Long totalTime) {
		super();
		this.section_id = section_id;
		this.section = section;
		this.totalQuestions = totalQuestions;
		this.totalTime = totalTime;
	}
	public InstructionDTO(Object[] obj) {
		super();
		this.section_id = Integer.parseInt(obj[0].toString());
		this.section = obj[1].toString();
		this.totalQuestions = Long.valueOf(obj[2].toString());
		this.totalTime = Long.valueOf(obj[3].toString());
		this.completionStatus = Long.valueOf(obj[4].toString())>0?true:false;
	}
	
	
	
	

}
