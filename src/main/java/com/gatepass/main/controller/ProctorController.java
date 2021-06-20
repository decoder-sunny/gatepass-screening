package com.gatepass.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.service.ProctorService;


@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class ProctorController {
	
	@Autowired private ProctorService proctor_service;
	
	@PostMapping(value="/proctor")
	public ResponseEntity<?> saveProctor(@RequestParam(value="image",required=false) MultipartFile image,
			@RequestParam("object") String object) throws Exception {
		proctor_service.saveProctorData(image,object);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageModel(200,"Proctor Saved"));
	}

}
