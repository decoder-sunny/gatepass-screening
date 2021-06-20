package com.gatepass.main.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class SubSectionDTO {
	
	private int id;
	private String subsectionName;
	private SectionDTO sectionDTO;
	
	public SubSectionDTO(int id, String subsectionName) {
		super();
		this.id = id;
		this.subsectionName = subsectionName;
	}
	
	
	
	

}
