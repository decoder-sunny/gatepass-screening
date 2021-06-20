package com.gatepass.main.dao;

import java.util.List;

import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.model.Questions;

public interface QuestionsDAO {
	
	List<Questions> getQuestions(int section_id,int sub_section_id,Condition condition);
	int getQuestionCount(int section_id,int sub_section_id,Condition condition);
	
	

}
