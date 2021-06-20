package com.gatepass.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatepass.main.dto.AssignDTO;
import com.gatepass.main.service.AssignService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class AssignController {

	@Autowired private AssignService assign_service;

	//assign template to candidate
	@PostMapping("assign")
	public ResponseEntity<?> saveAssignTemplate(@RequestBody List<AssignDTO> assignDTO) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(assign_service.assignTemplate(assignDTO));
	}
	
	//re-assign template to candidate 
	@GetMapping("re-assign/{assign_id}")
	public ResponseEntity<?> reassignTemplate(@PathVariable("assign_id") int assign_id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(assign_service.reAssignTemplate(assign_id));
	}

}
