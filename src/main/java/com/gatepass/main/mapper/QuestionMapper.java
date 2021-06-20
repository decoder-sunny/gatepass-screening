package com.gatepass.main.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gatepass.main.dto.QuestionDTO;
import com.gatepass.main.model.Questions;

public class QuestionMapper {
	
	public static List<QuestionDTO> mapQuestionsForReport(List<Questions> questions) {
		List<QuestionDTO> list=new ArrayList<QuestionDTO>();
		try {
			Collections.sort(questions, Comparator.comparingInt(Questions ::getId));
			for (Questions questions2 : questions) {
				QuestionDTO dto=new QuestionDTO();
				dto.setQuestion(questions2.getQuestion());
				dto.setQuestionType(questions2.getQuestionType().getId());
				dto.setAnswer(questions2.getAnswer());
				dto.setCodeQuestion(questions2.getCodeQuestion());
				dto.setOptionsImage(questions2.getOptionsImage());
				dto.setOptionsObjective(questions2.getOptionsObjective());
				dto.setQuestionImage(questions2.getQuestionImage());
				dto.setMarks(questions2.getMarks());
				list.add(dto);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Question mapper failed");
		}
		return list;	
			
	}

}
