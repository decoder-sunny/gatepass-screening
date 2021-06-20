package com.gatepass.main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.CandidateResponse;

@Repository
public interface ResponseRepository extends CrudRepository<CandidateResponse,Integer>{
	
	List<CandidateResponse> findAllByAssignId(int assign_id);

}
