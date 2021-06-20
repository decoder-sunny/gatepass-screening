package com.gatepass.main.dao;

import java.util.List;

import com.gatepass.main.model.FilterCandidateResponse;

public interface FilterCandidateResponseDAO {
	
	List<FilterCandidateResponse> getFilterCandidateResponse(int org_id,int candidate_id);
	
	
	//getting all records from DB of nosql saving
	List<Object[]> getAllCandidateResponse(int candidate_id);

}
