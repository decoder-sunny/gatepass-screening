package com.gatepass.main.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gatepass.main.model.PositionQuestions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class PositionDTO {
	
	private Integer id;
	
	@NotNull(message="Position name is required")
	private String name;	
	private Boolean isActive;	
	
	private String jdDocument;	
	
	@NotNull(message="Created By is required")
	private Integer createdBy;
	
	@NotNull(message="Created By is required")
	private Integer updatedBy;
	
	private Date createdOn;	
	private Date updatedOn;
	
	@NotNull(message="Question is required")
	private List<PositionQuestions> positionQuestion;
	
	private Integer applied;
	private Integer completed;
	
	private String userName;

	

	public PositionDTO(Integer id, @NotNull(message = "Position name is required") String name,
			Boolean isActive,
			Date createdOn,int applied,int completed) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.applied=applied;
		this.completed=completed;
	}
	
	

	
}
