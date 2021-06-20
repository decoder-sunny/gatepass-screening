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

import com.gatepass.main.dto.SubSectionDTO;
import com.gatepass.main.service.SubSectionService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class SubSectionController {
	
	@Autowired private SubSectionService subsection_service;	

	@PostMapping("sub-section")
	public ResponseEntity<?> saveSubSection(@Valid @RequestBody SubSectionDTO subsectionDTO) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(subsection_service.saveSubSection(subsectionDTO));
	}	
	
	@PutMapping("sub-section/{id}")
	public ResponseEntity<?> updateSubSection(@Valid @RequestBody SubSectionDTO subsection,
			@PathVariable("id") int id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(subsection_service.updateSubSection(subsection));
	}
	
	@GetMapping("sub-section/{id}")
	public ResponseEntity<?> subSectionByID( @PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(subsection_service.getSubSectionById(id));
	}
	
	@GetMapping("sub-sections/{id}")
	public ResponseEntity<?> subSectionList(@PathVariable("id") int id) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(subsection_service.getSubSectionList(id));
	}
	

}
