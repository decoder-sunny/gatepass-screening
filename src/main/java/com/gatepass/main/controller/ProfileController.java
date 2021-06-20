package com.gatepass.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.service.SearchService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class ProfileController {

	@Autowired SearchService search_service;

	//to get profiles
	@GetMapping("search")
	public ResponseEntity<?> getSearchResult(Condition condition,int org_id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(search_service.getSearchResult(org_id, condition));
	}



}
