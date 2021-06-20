package com.gatepass.main.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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

import com.gatepass.main.dto.TemplateDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.service.TemplateService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class TemplateController {
	
	@Autowired private TemplateService template_service;	

	@PostMapping("template")
	public ResponseEntity<?> saveOrganisation(@Valid @RequestBody TemplateDTO templateDTO) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(template_service.saveTemplate(templateDTO));
	}
	
	@PutMapping("template/{id}")
	public ResponseEntity<?> updateTemplate(@Valid @RequestBody TemplateDTO templateDTO,
			@PathVariable("id") int id) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(template_service.updateTemplate(templateDTO));
	}
	
	@GetMapping("template/{id}")
	public ResponseEntity<?> templateByID( @PathVariable("id") int id) throws Exception{		
		return ResponseEntity.status(HttpStatus.OK).body(template_service.getTemplateById(id));
	}
	
	@GetMapping("templates/{created_by}")
	public ResponseEntity<?> userList(Condition condition,@PathVariable("created_by") List<Integer> createdBy) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(template_service
				.getTemplates(createdBy, condition));
	}
	
	@GetMapping("templates-distribution/{id}")
	public ResponseEntity<?> templateDistribution(@PathVariable("id") int id) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(template_service
				.getTemplateDistribution(id));
	}
	
	@GetMapping(value = "download/template/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> getReportFile(@PathVariable("id") int id) throws Exception{
        ByteArrayInputStream bis = template_service.generateTemplatePDF(id);   
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
	}
	

}
