package com.gatepass.main.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.gatepass.main.dto.SubSectionDTO;
public interface SubSectionService {
	
	@Caching(evict = {
    	    @CacheEvict(value = "getSubSectionBySection", allEntries=true)})
	public SubSectionDTO saveSubSection(SubSectionDTO sectionDTO);
	
	@Caching(evict = {
    	    @CacheEvict(value = "getSubSectionBySection", allEntries=true)})
	public SubSectionDTO updateSubSection(SubSectionDTO sectionDTO);
	
	
	public SubSectionDTO getSubSectionById(int id);
	
	@Cacheable(value = "getSubSectionBySection", key="#p0", condition="#p0!=null")
	public List<SubSectionDTO> getSubSectionList(int section_id);

}
