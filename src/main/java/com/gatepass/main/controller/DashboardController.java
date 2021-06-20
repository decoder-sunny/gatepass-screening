package com.gatepass.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.service.DashboardService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class DashboardController {
	
	@Autowired DashboardService dashboard_service;
	
	@GetMapping("client/info/{id}")
	public ResponseEntity<?> getClientDashboardInfo( @PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(dashboard_service.getClientDashboardInfo(id));
	}
	
	@GetMapping("client/rankers/{id}")
	public ResponseEntity<?> getClientRankersInfo( @PathVariable("id") int id,Condition condition) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(dashboard_service.getRankersDetails(id,condition));
	}
	
	@GetMapping("client/tests/{id}")
	public ResponseEntity<?> getRecentTestConducted( @PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(dashboard_service.getRecentTestConducted(id));
	}

}
