package com.gatepass.main.dao;

import java.util.List;

import com.gatepass.main.dto.InstructionDTO;
import com.gatepass.main.dto.TemplateUtility;
import com.gatepass.main.model.Questions;

public interface TemplateDAO {
	
	public List<TemplateUtility> getTemplateDistribution(int template_id);
	
	List<InstructionDTO> getInstructionDistribution(int template_id);
	
	List<Object[]> getSectionDistribution(int template_id,int assign_id);
	
	List<Questions> getTestQuestions(int template_id,int section_id);

}
