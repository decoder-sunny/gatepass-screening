package com.gatepass.main.miscellaneous;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gatepass.main.dto.QuestionDTO;
import com.gatepass.main.dto.SectionDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportSupport {

	private SectionDTO sectionDTO;
	private List<QuestionDTO> questionDTO;
	private List<String> response;
	private Integer marksObtained;
	private Integer totalMarks;
	private String language;
	private List<Integer> attempts;
	private List<Integer> time;
	private List<Integer> testCases;
	
	
}
