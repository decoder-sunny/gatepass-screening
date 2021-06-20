package com.gatepass.main.dao;

import java.util.List;

import com.gatepass.main.dto.CandidateDTO;
import com.gatepass.main.miscellaneous.Condition;

public interface CandidateDAO {
	
	public List<CandidateDTO> getUserList(Condition condition,int org_id,String position);
	public int getUserListCount(Condition condition,int org_id,String position);
	
	public List<CandidateDTO> getCandidateRanking(Condition condition,int position_id);
	public int getCandidateRankingCount(Condition condition,int position_id);
	
	//candidate list updated in last 30 minutes
	public List<CandidateDTO> getRecentCandidates();

	
	

}
