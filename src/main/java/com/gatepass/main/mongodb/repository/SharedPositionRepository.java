package com.gatepass.main.mongodb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.mongodb.SharedPosition;

@Repository
public interface SharedPositionRepository extends MongoRepository<SharedPosition, Integer>{
	
	Page<SharedPosition> findAllByUsersInOrderByIdDesc(List<Integer> user,Pageable pageable);

}
