package com.gatepass.main.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gatepass.main.model.CodeValueType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class FilterQuestionsDTO {
	
	private Integer id;
	private String questions;
	private CodeValueType codeValueType;
	private Integer type;
	private Boolean isMustHave;	
	
	public FilterQuestionsDTO(Integer id, String questions, CodeValueType codeValueType,
			Boolean isMustHave) {
		super();
		this.id = id;
		this.questions = questions;
		this.codeValueType = codeValueType;
		this.isMustHave = isMustHave;
	}
	
	

}
