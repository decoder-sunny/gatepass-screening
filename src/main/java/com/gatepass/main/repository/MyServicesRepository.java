package com.gatepass.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.Services;

@Repository
public interface MyServicesRepository extends CrudRepository<Services,Integer>{

}
