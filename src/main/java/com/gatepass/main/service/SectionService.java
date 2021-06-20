package com.gatepass.main.service;

import com.gatepass.main.dto.SectionDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.Pagination;

public interface SectionService {
	
	public SectionDTO saveSection(SectionDTO sectionDTO);
	public SectionDTO updateSection(SectionDTO sectionDTO);
	public SectionDTO getSectionById(int id);
	
	public Pagination getSectionList(Condition condition);

}
