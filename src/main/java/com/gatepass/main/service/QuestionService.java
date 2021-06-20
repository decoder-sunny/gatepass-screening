package com.gatepass.main.service;
import com.gatepass.main.dto.QuestionDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.Pagination;

public interface QuestionService {
	
	QuestionDTO saveQuestion(QuestionDTO questionDTO);
	
	Pagination getQuestions(int section_id,int subection_id,Condition condition);
	
	QuestionDTO getQuestionById(int id);
	QuestionDTO updateQuestion(QuestionDTO questionDTO);

}
