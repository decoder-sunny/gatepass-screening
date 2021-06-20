package com.gatepass.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.Position;

@Repository
public interface PositionRepository extends CrudRepository<Position,Integer>{
	
	Page<Position> findAllByCreatedByOrderByIdDesc(int createdBy,Pageable pageable);
	
	List<Position> findAllByIdIn(List<Integer> id);

}
