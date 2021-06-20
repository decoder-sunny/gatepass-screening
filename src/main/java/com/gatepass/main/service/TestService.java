package com.gatepass.main.service;

import java.util.List;

import com.gatepass.main.dto.InstructionDTO;
import com.gatepass.main.dto.TestCaseDTO;
import com.gatepass.main.dto.TestQuestions;
import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.model.CandidateResponse;

public interface TestService {
	
	List<InstructionDTO> getInstructionDistribution(int template_id);
	
	List<InstructionDTO> getSectionDistribution(int template_id,int assign_id);
	
	TestQuestions getTestQuestions(int template_id,int section_id);
	
	CandidateResponse saveCandidateResponse(CandidateResponse candidateResponse);
	
	MessageModel evaluateCandidateResponses(int assign_id);
	
	MessageModel runTestCase(TestCaseDTO caseDTO);
	
}
