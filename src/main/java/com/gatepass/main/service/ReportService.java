package com.gatepass.main.service;

import org.springframework.web.servlet.ModelAndView;

import com.gatepass.main.dto.ReportDTO;

public interface ReportService {
	
	ReportDTO getTestReport(int assign_id,int org_id);
	
	ModelAndView getTestReportView(int assign_id,int org_id);

}
