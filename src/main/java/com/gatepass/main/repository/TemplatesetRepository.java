package com.gatepass.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.TemplateSet;

@Repository
public interface TemplatesetRepository extends CrudRepository<TemplateSet, Integer>{

}
