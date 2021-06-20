package com.gatepass.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.Insights;

@Repository
public interface InsightsRepository extends CrudRepository<Insights,Integer>{

}
