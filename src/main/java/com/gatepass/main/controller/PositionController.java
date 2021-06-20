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

import com.gatepass.main.dto.PositionDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.model.mongodb.SharedPosition;
import com.gatepass.main.service.PositionService;
import com.gatepass.main.service.ScreenService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class PositionController {
	
	@Autowired private PositionService position_service;
	@Autowired private ScreenService screen_service;
	
	@PostMapping("position")
	public ResponseEntity<?> savePosition(@Valid @RequestBody PositionDTO positionDTO) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(position_service.savePosition(positionDTO));
	}	
	
	@PutMapping("position/{id}")
	public ResponseEntity<?> updatePosition(@RequestBody PositionDTO position,
			@PathVariable("id") int id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(position_service.updatePosition(position));
	}
	
	@PutMapping("position/status/{id}")
	public ResponseEntity<?> updatePositionStatus(@RequestBody PositionDTO position,
			@PathVariable("id") int id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(position_service.updatePositionStatus(position));
	}
	
	@GetMapping("position/{id}")
	public ResponseEntity<?> positionByID( @PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(position_service.getPositionById(id));
	}
	
	@GetMapping("position")
	public ResponseEntity<?> positionList(int created_by,Condition condition) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(position_service.getPositionList(created_by, condition));
	}
	
	@GetMapping("position/ranking")
	public ResponseEntity<?> positionRankins(int position_id,Condition condition) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(screen_service.getScreenReport(position_id,condition));
	}
	
	@PostMapping("position/share")
	public ResponseEntity<?> saveSharePosition(@Valid @RequestBody SharedPosition positionDTO) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(position_service.saveSharedPosition(positionDTO));
	}
	
	@GetMapping("position/share/{id}")
	public ResponseEntity<?> getSharedPosition(@PathVariable(value="id",required=true) int id) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(position_service.getSharedPosition(id));
	}

}
