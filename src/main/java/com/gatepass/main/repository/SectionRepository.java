package com.gatepass.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.Section;

@Repository
public interface SectionRepository extends CrudRepository<Section,Integer>{

}