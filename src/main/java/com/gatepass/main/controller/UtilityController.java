package com.gatepass.main.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.model.Code;
import com.gatepass.main.service.FileService;
import com.gatepass.main.service.UtilityService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class UtilityController {
	
	@Autowired UtilityService utility_service;
	@Autowired private FileService file_service;
	
	

	@GetMapping("utility/{name}/{type}")
	public ResponseEntity<?> getUtility( @PathVariable("name") String name,
			@PathVariable("type") String type) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(utility_service.getCodeValues(name, type));
	}
	
	@PostMapping(value="/code")
	public ResponseEntity<?> saveCodeValue(@RequestBody Code code) throws NoSuchElementException,DuplicateKeyException, IOException {
		return ResponseEntity.status(HttpStatus.OK).body(utility_service.saveCodeValue(code));
	}
	
	
	@GetMapping("insights/{id}")
	public ResponseEntity<?> getOrgInsights( @PathVariable("id")int id) throws Exception{	
		return ResponseEntity.status(HttpStatus.OK).body(utility_service.getInsight(id));
	}
	
	@PostMapping(value="/upload-file")
	public ResponseEntity<?> saveProfilePicture(@RequestParam("image") MultipartFile image,
			@RequestParam("name") String name) throws NoSuchElementException,DuplicateKeyException, IOException {
		Optional.ofNullable(name).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		if(file_service.uploadFile(name, image)){
			return ResponseEntity.status(HttpStatus.OK).body(new MessageModel(200, "File Uploaded"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageModel(400, "File Not Saved"));
	}	
	
	
	@GetMapping(value="/get-profile-pic/{picture}", produces = MediaType.IMAGE_JPEG_VALUE)
	public  byte[] getImage(@PathVariable("picture") String picture) throws IOException{
		byte[] image=file_service.downloadFile(picture);
		if(image==null || image.length==0)
			return null;
		else return image;	
	}

}
