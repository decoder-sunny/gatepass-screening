package com.gatepass.main.controller;

import javax.validation.Valid;

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

import com.gatepass.main.dto.TestCaseDTO;
import com.gatepass.main.model.CandidateResponse;
import com.gatepass.main.service.TestService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class TestController {
	
	@Autowired private TestService test_service;
	
	@GetMapping("instructions/{id}")
	public ResponseEntity<?> instructionsByID(@PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(test_service.getInstructionDistribution(id));
	}
	
	@GetMapping("test-sections/{assign_id}/{template_id}")
	public ResponseEntity<?> testSections(@PathVariable("assign_id") int assign_id,
			@PathVariable("template_id") int template_id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(test_service.getSectionDistribution(template_id, assign_id));
	}
	
	@GetMapping("test-questions/{section_id}/{template_id}")
	public ResponseEntity<?> testQuestions(@PathVariable("section_id") int section_id,
			@PathVariable("template_id") int template_id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(test_service.
				getTestQuestions(template_id, section_id));
	}
	
	@PostMapping("response")
	public ResponseEntity<?> saveSection(@Valid @RequestBody CandidateResponse response) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(test_service.saveCandidateResponse(response));
	}	
	
	@GetMapping("evaluate/{assign_id}")
	public ResponseEntity<?> evaluateTest(@PathVariable("assign_id") int assign_id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(test_service.evaluateCandidateResponses(assign_id));
	}	
	
	@PostMapping("run-testcase")
	public ResponseEntity<?> runTestCase(@Valid @RequestBody TestCaseDTO testCaseDTO) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(test_service.runTestCase(testCaseDTO));
	}

}
