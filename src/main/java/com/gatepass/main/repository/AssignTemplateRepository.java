package com.gatepass.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.AssignedTemplate;

@Repository
public interface AssignTemplateRepository extends CrudRepository<AssignedTemplate,Integer>{
	
	Page<AssignedTemplate> findByCandidate_Id(int id,Pageable pageable);
	Page<AssignedTemplate> findByCandidate_IdAndOrganisation(int id,int organisation,Pageable pageable);
	boolean existsByCandidate_IdAndTemplate_IdAndOrganisation(int candidate,int template,int organisation);
	AssignedTemplate findByCandidate_IdAndTemplate_IdAndOrganisation(int candidate,int template,int organisation);

}
