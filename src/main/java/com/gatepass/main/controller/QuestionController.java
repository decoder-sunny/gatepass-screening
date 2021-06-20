package com.gatepass.main.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import com.gatepass.main.dto.QuestionDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.service.QuestionService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class QuestionController {
	
	@Autowired private QuestionService question_service;
	
	@PostMapping(value="/question")
	public ResponseEntity<?> saveQuestion(@Valid @RequestBody QuestionDTO question) throws NoSuchElementException,DuplicateKeyException, IOException {
		return ResponseEntity.status(HttpStatus.OK)
				.body(question_service.saveQuestion(question));
	}
	
	@GetMapping("questions")
	public ResponseEntity<?> getQuestions(int section_id,int sub_section_id,Condition condition) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(question_service.getQuestions(section_id, sub_section_id, condition));
	}
	
	@GetMapping("question/{id}")
	public ResponseEntity<?> questionByID( @PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(question_service.getQuestionById(id));
	}
	
	@PutMapping("question/{id}")
	public ResponseEntity<?> updateQuestion(@Valid @RequestBody QuestionDTO questionDTO,
			@PathVariable("id") int id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(question_service.updateQuestion(questionDTO));
	}

}
