package com.gatepass.main.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gatepass.main.dto.CandidateFilterResponse;
import com.gatepass.main.dto.CandidateScreenReport;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.model.PositionQuestions;

public interface ScreenService {
	
	List<PositionQuestions> getPositionQuestions(int position_id);
	
	MessageModel validateMustHave(CandidateFilterResponse candidateFilterResponse);
	
	MessageModel submitFilterResponse(String information,MultipartFile image);
	
	CandidateScreenReport getScreenReport(int position_id,Condition condition);
	
	

}
