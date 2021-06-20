package com.gatepass.main.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.gatepass.main.model.Code;
import com.gatepass.main.model.Insights;
public interface UtilityService {
	
	@Cacheable(value = "getCodeValues", key = "#text.concat('-').concat(#type)")
	public List<Code> getCodeValues(String text,String type);
	
	@Cacheable(value = "getInsight", key = "#id")
	public Insights getInsight(int id); //id is org_id
	
	@Caching(evict = {
    	    @CacheEvict(value = "getCodeValues", key = "'null'.concat('-').concat(#code.codeValueType.name)")})
	public Code saveCodeValue(Code code);
	
	
	
	
	
	
}
