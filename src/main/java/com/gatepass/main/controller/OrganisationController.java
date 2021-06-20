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

import com.gatepass.main.dto.OrganisationDTO;
import com.gatepass.main.service.OrganisationService;
import com.gatepass.main.miscellaneous.Condition;


@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class OrganisationController {
	
	@Autowired private OrganisationService organisation_service;	

	@PostMapping("organisation")
	public ResponseEntity<?> saveOrganisation(@Valid @RequestBody OrganisationDTO organisationDTO) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(organisation_service.saveOrganisation(organisationDTO));
	}	
	
	@PutMapping("organisation/{id}")
	public ResponseEntity<?> updateOrganisation(@Valid @RequestBody OrganisationDTO organisation,
			@PathVariable("id") int id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(organisation_service.updateOrganisation(organisation));
	}
	
	@GetMapping("organisation/{id}")
	public ResponseEntity<?> organisationByID( @PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(organisation_service.getOrganisationById(id));
	}
	
	@GetMapping("organisation")
	public ResponseEntity<?> organisationList(Condition condition) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(organisation_service.getOrganisationList(condition));
	}
	
	
}
