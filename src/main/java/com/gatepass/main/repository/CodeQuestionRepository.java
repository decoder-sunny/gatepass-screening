package com.gatepass.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.CodeQuestion;

@Repository
public interface CodeQuestionRepository extends CrudRepository<CodeQuestion,Integer>{

}
