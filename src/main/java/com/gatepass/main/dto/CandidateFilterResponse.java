package com.gatepass.main.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CandidateFilterResponse {
	
	@NotNull
	private Integer position_id;
	@NotNull
	private List<FilterCriteriaDTO> responses;	
	private CandidateDTO candidate;
	
	private int marks;
	private String screenResponse;
	
	
}
