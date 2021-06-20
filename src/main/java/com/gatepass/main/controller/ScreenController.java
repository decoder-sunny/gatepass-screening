package com.gatepass.main.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gatepass.main.dto.CandidateFilterResponse;
import com.gatepass.main.model.PositionQuestions;
import com.gatepass.main.service.FilterResponseService;
import com.gatepass.main.service.PositionService;
import com.gatepass.main.service.ScreenService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class ScreenController {
	
	@Autowired private ScreenService screen_service;
	@Autowired private PositionService position_service;
	@Autowired private FilterResponseService filter_service;
	
	@GetMapping("screen/questions/{id}")
	public ResponseEntity<?> filterQuestionsByID(@PathVariable("id") int id) throws Exception{	
		List<PositionQuestions> list=screen_service.getPositionQuestions(id);
		list.stream().forEach(x-> {
			x.setPreferredOptions(null);
			x.setPositionId(null);
		});
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@PostMapping("screen/validate/must-have")
	public ResponseEntity<?> savePosition(@Valid @RequestBody CandidateFilterResponse candidateFilterResponse ) throws Exception{
    	position_service.updateApplied(candidateFilterResponse.getPosition_id());
		return ResponseEntity.status(HttpStatus.OK).body(screen_service.validateMustHave(candidateFilterResponse));
	}
	
	@PostMapping(value="/screen")
	public ResponseEntity<?> saveScreenResponse(@RequestParam(value="image",required=false) MultipartFile image,
			@RequestParam("object") String object) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(screen_service
				.submitFilterResponse(object, image));
	}
	
	@GetMapping("screen/response/{id}")
	public ResponseEntity<?> filterResponse(@PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(filter_service.getScreenResponse(id));
	}
	
	//to to export candidate info
		@GetMapping("screen/report/export/{position_id}")
	public void exportCandidate(@PathVariable("position_id") int position_id,HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Report.xlsx";
        response.setHeader(headerKey, headerValue);
		filter_service.exportScreeningExcel(response, position_id);
	}

}
