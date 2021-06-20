package com.gatepass.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.CandidateDetails;

@Repository
public interface CandidateRepository extends CrudRepository<CandidateDetails,Integer>{
	
	public CandidateDetails findByUser_Email(String email);
	boolean existsByUser_Email(String email);
	
	//to get dashboard info
	@Query(value="select u.Name,u.Email,u.MobileNo,u.Designation,c1.Value as gender,c2.Value as candidateFunction,\r\n" + 
			"cd.experienceYears,cd.experienceMonths  \r\n" + 
			"from candidatedetails cd left join user u on cd.User=u.Id \r\n" + 
			"left join code c1 on cd.Gender=c1.Id \r\n" + 
			"left join code c2 on cd.CandidateFunction=c2.Id where cd.Id in (:users)",nativeQuery=true)
	List<Object[]> getCandidates(@Param("users") List<Integer> users);
	
	//to get candidate detail like name
	@Query(value="select u.Name\r\n" + 
			"from candidatedetails cd left join user u on cd.User=u.Id \r\n" + 
			"where cd.Id =:id",nativeQuery=true)
	List<Object[]> getCandidateInfo(@Param("id") int id);

}
