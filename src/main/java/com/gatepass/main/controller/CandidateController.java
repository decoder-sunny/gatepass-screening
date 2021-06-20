package com.gatepass.main.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatepass.main.dto.CandidateDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.service.CandidateService;
import com.gatepass.main.service.FileService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class CandidateController {
	
	@Autowired private CandidateService candidate_service;
	@Autowired private FileService file_service;
	
	
	//to save candidate
	@PostMapping("candidate")
	public ResponseEntity<?> saveCandidate(@RequestBody CandidateDTO candidateDTO) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(candidate_service.saveCandidate(candidateDTO));
	}	
	
	//to update candidate
	@PutMapping("candidate/{id}")
	public ResponseEntity<?> updateCandidate(@RequestBody CandidateDTO candidate,
			@PathVariable("id") int id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(candidate_service.updateCandidate(candidate));
	}
	
	//to get candidate by id
	@GetMapping("candidate/{id}")
	public ResponseEntity<?> candidateByID(@PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(candidate_service.getCandidateById(id));
	}
	
	//to downoad CV of candidate
	@GetMapping(value = "/download/cv/{name}",produces = MediaType.APPLICATION_PDF_VALUE)
	public byte[] getReportFile(@PathVariable("name") String name) throws Exception{
		try {
			byte[] resume=file_service.downloadFile(name);
			return resume;
		} catch (Exception e) {
			System.out.println("Failed to fetch CV");
			throw new CustomException("Failed to fetch CV");
		}
	}
	
	//get all candidate list
	@GetMapping("candidates")
	public ResponseEntity<?> candidateList(Condition condition,int org_id,String position) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(candidate_service
				.getCandidateList(condition, org_id,position));
	}
	
	//to to get Candidate detail
	@GetMapping("candidate/detail")
	public ResponseEntity<?> candidateDetail(Condition condition,int candidate_id,int org_id) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(candidate_service
				.getCandidateInfo(candidate_id,org_id,condition));
	}
	
	//to save bulk candidate
	@PostMapping("candidates")
	public ResponseEntity<?> saveBulkCandidate(@RequestBody List<CandidateDTO> candidates) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(candidate_service.saveBulkCandidates(candidates));
	}
	
	//to to export candidate info
	@GetMapping("candidate/export")
	public void exportCandidate(String users,HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Candidate.xlsx";
        response.setHeader(headerKey, headerValue);
		candidate_service.exportExcel(response,new Gson().fromJson(users,
				TypeToken.getParameterized(ArrayList.class, Integer.class).getType()));
	}

}
