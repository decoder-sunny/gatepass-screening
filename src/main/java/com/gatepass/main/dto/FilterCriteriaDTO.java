package com.gatepass.main.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class FilterCriteriaDTO {
	
	private Integer id;
	private List<String> response;
	
	
	
	

}
