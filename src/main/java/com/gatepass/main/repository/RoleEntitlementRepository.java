package com.gatepass.main.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.Role_Entitlement_Mapping;

@Repository
public interface RoleEntitlementRepository extends CrudRepository<Role_Entitlement_Mapping, Integer>{
	
	@Cacheable(value = "getEntitlementByRole", key="#p0", condition="#p0!=null")
	public List<Role_Entitlement_Mapping> findByRole_Role(String role);

}
