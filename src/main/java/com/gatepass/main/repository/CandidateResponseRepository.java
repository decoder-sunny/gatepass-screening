package com.gatepass.main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.CandidateResponse;

@Repository
public interface CandidateResponseRepository extends CrudRepository<CandidateResponse,Integer>{
	
	List<CandidateResponse> findByAssignId(int assign_id);
	boolean existsByAssignIdAndSection(int assign_id,int section);

}
