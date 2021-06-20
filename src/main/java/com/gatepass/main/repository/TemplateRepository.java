package com.gatepass.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.Templates;

@Repository
public interface TemplateRepository extends CrudRepository<Templates, Integer>{
	
	Page<Templates> findAllByCreatedByInOrderByUpdatedOnDesc(List<Integer> createdBy,Pageable pageable);

}
