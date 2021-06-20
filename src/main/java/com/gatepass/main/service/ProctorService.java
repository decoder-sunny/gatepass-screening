package com.gatepass.main.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProctorService {
	
	void saveProctorData(MultipartFile image,String data);

}
