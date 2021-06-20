package com.gatepass.main.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardDTO {
	
	private Integer totalCandidate;
	private Integer testConducted;
	private Integer totalPositions;
	private Integer totalScreened;
	private String candidateName;
	private String candidateEmail;
	private Integer candidateMarks;
	private Date testDate;
	private Integer candidateId;
	

}
