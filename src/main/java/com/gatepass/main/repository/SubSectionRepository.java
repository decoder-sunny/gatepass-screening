package com.gatepass.main.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.SubSection;

@Repository
public interface SubSectionRepository extends CrudRepository<SubSection, Integer>{
	

	List<SubSection> findBySection_Id(Integer section_id);

}
