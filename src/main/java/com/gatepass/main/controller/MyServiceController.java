package com.gatepass.main.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatepass.main.model.Services;
import com.gatepass.main.service.MyServices;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class MyServiceController {
	
	@Autowired private MyServices my_services;	
	
	@PutMapping("service/{id}")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	public ResponseEntity<?> updateServices(@Valid @RequestBody Services service,
			@PathVariable("id") int id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(my_services.updateServices(service));
	}
	
	@GetMapping("service/{id}")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	public ResponseEntity<?> serviceByID( @PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(my_services.getServiceById(id));
	}

}
