package com.gatepass.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.Code;

@Repository
public interface CodeRepository extends CrudRepository<Code, Integer>{
	
	

}
