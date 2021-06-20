package com.gatepass.main.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignDTO {
	
	private int id;
	private CandidateDTO candidate;
	private TemplateDTO template;	
	private Date createdOn;
	private Date updatedOn;
	private Boolean completionStatus;
	private int progress;
	private Integer organisationId;
	
}
