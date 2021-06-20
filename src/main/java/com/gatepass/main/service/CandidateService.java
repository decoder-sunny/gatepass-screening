package com.gatepass.main.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.gatepass.main.dto.CandidateDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.miscellaneous.Pagination;

public interface CandidateService {
	
	public CandidateDTO saveCandidate(CandidateDTO candidateDTO);
	public CandidateDTO updateCandidate(CandidateDTO candidateDTO);
	public CandidateDTO getCandidateById(int id);
	
	public Pagination getCandidateList(Condition condition,int org_id,String position);
	
	public Pagination getCandidateRanking(Condition condition,int position_id);
	
	public CandidateDTO getCandidateInfo(int candidate_id,int org_id,Condition condition);
	
	MessageModel saveBulkCandidates(List<CandidateDTO> list);
	
	void exportExcel(HttpServletResponse response,List<Integer> user);

}
