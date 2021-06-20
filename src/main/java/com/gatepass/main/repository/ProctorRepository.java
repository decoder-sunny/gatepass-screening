package com.gatepass.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.ProctorInfo;

@Repository
public interface ProctorRepository extends CrudRepository<ProctorInfo,Integer>{

}
