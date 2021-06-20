package com.gatepass.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Integer>{
	
	boolean existsByEmail(String email);
	User findByEmail(String email);
	
	//to get user based on ids
	@Query(value="select u.Id,u.Name,u.Email from user u where u.Id in (:ids)",nativeQuery=true)
	List<Object[]> getUsers(@Param("ids")List<Integer> ids);

}
