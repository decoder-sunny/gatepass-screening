package com.gatepass.main.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gatepass.main.model.CodeQuestion;
import com.gatepass.main.model.OptionsImage;
import com.gatepass.main.model.OptionsObjective;
import com.gatepass.main.model.QuestionImage;

import lombok.Getter;
import lombok.Setter;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class QuestionDTO {

	private Integer id;
	@NotNull(message="Question is mandatory")
	private String question;
	private QuestionImage questionImage;
	@NotNull(message="QuestionType is mandatory")
	private Integer questionType;
	@NotNull(message="Level is mandatory")
	private Integer level;
	@NotNull(message="Sub Section is mandatory")
	private Integer subsection;
	private String subsection_name;
	private OptionsObjective optionsObjective;
	private OptionsImage optionsImage;
	private String answer;
	@NotNull(message="Time is mandatory")
	private int time;
	@NotNull(message="Marks is mandatory")
	private int marks;
	private CodeQuestion codeQuestion;
	
}
