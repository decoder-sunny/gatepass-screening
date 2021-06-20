package com.gatepass.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gatepass.main.service.ReportService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class ReportController {
	
	@Autowired private ReportService report_service;
	

	@GetMapping("report/{assign_id}/{org_id}")
	public ResponseEntity<?> reassignTemplate(@PathVariable("assign_id") int assign_id,
			@PathVariable("org_id") int org_id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(report_service.getTestReport(assign_id,org_id));
	}

	@GetMapping("test-report/{assign_id}/{org_id}")
	public ModelAndView getPDF(@PathVariable("assign_id") int assign_id,
			@PathVariable("org_id") int org_id) throws Exception{	
		return report_service.getTestReportView(assign_id,org_id);		
	}
}
