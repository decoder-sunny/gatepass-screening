package com.gatepass.main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.UserRoleMapping;

@Repository
public interface UserRoleMappingRepository extends CrudRepository<UserRoleMapping,Integer>{

	List<UserRoleMapping> findByUser_Email(String email);
	List<UserRoleMapping> findByUser_Id(Integer id);
	
	
	Boolean existsByUser_Email(String email);
	
	Boolean existsByUser_EmailAndRole_Id(String email,Integer role);
	
	UserRoleMapping findByUser_EmailAndRole_Id(String email,Integer role);
}
