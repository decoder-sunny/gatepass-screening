package com.gatepass.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.CandidateServices;

@Repository
public interface CandidateServiceRepository extends CrudRepository<CandidateServices,Integer>{
	
	boolean existsByCandidateIdAndOrganisationId(int candidate_id,int organisation_id);
	CandidateServices findByCandidateIdAndOrganisationId(int candidate_id,int organisation_id);

}
