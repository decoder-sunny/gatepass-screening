package com.gatepass.main.controller;
import java.util.List;

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

import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.service.UserService;
import com.gatepass.main.miscellaneous.Condition;


@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class UserController {
	
	@Autowired private UserService user_service;
	
	@PostMapping("user")
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO userDTO) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(user_service.saveUser(userDTO));
	}	
	
	@PutMapping("user/{id}")
	public ResponseEntity<?> updateUser(@RequestBody UserDTO user,
			@PathVariable("id") int id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(user_service.updateUser(user));
	}
	
	@GetMapping("user/{id}")
	public ResponseEntity<?> userByID( @PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(user_service.getUserById(id));
	}
	
	@GetMapping("users/{role}")
	public ResponseEntity<?> userList(Condition condition,int org_id,@PathVariable("role") List<Integer> role) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(user_service
				.getUserList(condition,org_id,role));
	}
	
	
	
	
}
