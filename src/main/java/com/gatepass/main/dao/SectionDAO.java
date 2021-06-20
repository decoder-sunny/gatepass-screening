package com.gatepass.main.dao;

import java.util.List;
import com.gatepass.main.dto.SectionDTO;
import com.gatepass.main.miscellaneous.Condition;

public interface SectionDAO {
	
	public List<SectionDTO> getSectionList(Condition condition);
	public int getSectionListCount(Condition condition);

}
