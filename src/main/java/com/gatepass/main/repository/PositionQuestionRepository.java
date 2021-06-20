package com.gatepass.main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.PositionQuestions;

@Repository
public interface PositionQuestionRepository extends CrudRepository<PositionQuestions, Integer>{
	
	List<PositionQuestions> findAllByPositionIdOrderByIsMustHaveDesc(int position_id);
	
	List<PositionQuestions> findAllByPositionIdAndIsMustHaveOrderByIsMustHaveDesc(int position_id,Boolean isMusthave);
	

}
