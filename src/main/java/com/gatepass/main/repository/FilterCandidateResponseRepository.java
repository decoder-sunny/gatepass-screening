package com.gatepass.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.FilterCandidateResponse;

@Repository
public interface FilterCandidateResponseRepository extends 
CrudRepository<FilterCandidateResponse,Integer>{
	
	boolean existsByPositionId(int id);
	
	List<FilterCandidateResponse> findAllByPositionIdOrderByMarksDesc(int position_id);
	
	//to get filter responses
	@Query(value="select cd.Id,u.Name,fcr.Marks,fcr.Response from filtercandidateresponse fcr \r\n" + 
			"inner join candidatedetails cd on fcr.CandidateId=cd.Id\r\n" + 
			"inner join user u on cd.User=u.Id where fcr.PositionId=:position_id order by fcr.Marks desc",
			countQuery="select count(fcr.Id) from filtercandidateresponse fcr where fcr.PositionId=:position_id",
			nativeQuery=true)
	Page<Object[]> getResponsesOfCandidates(@Param("position_id") int position_id,Pageable pageable);
	
	

}
