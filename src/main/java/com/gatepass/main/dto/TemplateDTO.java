package com.gatepass.main.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class TemplateDTO {
	
	private int id;
	@NotNull
	private String name;
	@NotNull
	private Integer createdBy;
	@NotNull
	private Integer updatedBy;
	private Set<Integer> questions;
	
	
	public TemplateDTO(int id, @NotNull String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	

}
