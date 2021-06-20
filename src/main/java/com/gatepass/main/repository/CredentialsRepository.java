package com.gatepass.main.repository;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.Credentials;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials,Integer>{
	
	@Cacheable(value = "getCredentialByEmail", key="#p0", condition="#p0!=null",unless="#result == null")
	public Credentials findByUser_Email(String email);
	
	boolean existsByUser_Email(String email);
	
	public Credentials findByUser_Id(int id);

}
