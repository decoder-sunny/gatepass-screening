package com.gatepass.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.UserOrganisationMapping;

@Repository
public interface UserOrganisationRepository extends CrudRepository<UserOrganisationMapping,Integer>{
	
	boolean existsByUserId(int user_id);
	public UserOrganisationMapping findByUserId(Integer user_id);

}
