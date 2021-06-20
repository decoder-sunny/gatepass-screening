package com.gatepass.main.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatepass.main.dto.SectionDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.service.SectionService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class SectionController {
	
	@Autowired private SectionService section_service;
	
	@PostMapping("section")
	public ResponseEntity<?> saveSection(@Valid @RequestBody SectionDTO sectionDTO) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(section_service.saveSection(sectionDTO));
	}	
	
	@PutMapping("section/{id}")
	public ResponseEntity<?> updateSection(@Valid @RequestBody SectionDTO section,
			@PathVariable("id") int id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(section_service.updateSection(section));
	}
	
	@GetMapping("section/{id}")
	public ResponseEntity<?> sectionByID( @PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(section_service.getSectionById(id));
	}
	
	@GetMapping("section")
	public ResponseEntity<?> sectionList(Condition condition) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(section_service.getSectionList(condition));
	}

}
