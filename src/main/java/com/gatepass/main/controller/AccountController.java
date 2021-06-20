package com.gatepass.main.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gatepass.main.dto.CredentialsDTO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.service.AccountService;


@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class AccountController {

	@Autowired private AccountService account_service;

	//for user login
	@PostMapping("login")
	public ResponseEntity<?> userLogin(@Valid @RequestBody CredentialsDTO credentials) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(account_service.userLogin(credentials));
	}
	
	//for candidate login
	@PostMapping("candidate-login")
	public ResponseEntity<?> candidateLogin(@Valid @RequestBody CredentialsDTO credentials) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(account_service.candidateLogin(credentials));
	}

	
	//for password reset
	@PostMapping(value="/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody String[] credentials) throws InterruptedException {
		return ResponseEntity.status(HttpStatus.OK).body(account_service.resetPassword(credentials));
	}

	
	//for user activation
	@PostMapping("activate-user")
	public ResponseEntity<?> activateUser(@RequestBody UserDTO user) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(account_service.activateUser(user));
	}

	//for update password
	@PostMapping(value="/update-password")
	public ResponseEntity<?> updatePassword(@RequestBody String[] cred) {
		return ResponseEntity.status(HttpStatus.OK).body(account_service.updatePassword(cred));	
	}
	
	//to update profile picture and profile
	@PostMapping(value="/profile")
	public ResponseEntity<?> saveUserProfile(@RequestParam(value="image",required=false) MultipartFile image,
			@RequestParam("user") String user) throws NoSuchElementException,DuplicateKeyException, IOException {
		return ResponseEntity.status(HttpStatus.OK).body(account_service.saveUserProfile(user, image));	
	}
	

}
