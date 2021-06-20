package com.gatepass.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.gatepass.main.model.Questions;

@Repository
public interface QuestionRepository extends CrudRepository<Questions,Integer>{

}
