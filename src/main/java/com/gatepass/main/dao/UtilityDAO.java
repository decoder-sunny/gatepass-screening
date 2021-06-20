package com.gatepass.main.dao;

import java.util.List;
import org.springframework.data.repository.query.Param;

import com.gatepass.main.model.Code;

public interface UtilityDAO {
	
	public List<Code> getCodeData(String text,@Param("cvt") String cvt);

}
