package com.gatepass.main.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.gatepass.main.dto.TemplateDTO;
import com.gatepass.main.dto.TemplateUtility;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.Pagination;

public interface TemplateService {
	
	TemplateDTO saveTemplate(TemplateDTO dto);
	TemplateDTO updateTemplate(TemplateDTO dto);
	TemplateDTO getTemplateById(int id);
	
	Pagination getTemplates(List<Integer> createdBy,Condition condition);
	
	List<TemplateUtility> getTemplateDistribution(int template_id);
	
	ByteArrayInputStream generateTemplatePDF(int id);

}
