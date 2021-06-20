package com.gatepass.main.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestQuestions {
	
	private List<QuestionDTO> questions;
	private String section;
	private int time;
}
