package com.gatepass.main.serviceimp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gatepass.main.dto.ImageTime;
import com.gatepass.main.model.ProctorInfo;
import com.gatepass.main.repository.ProctorRepository;
import com.gatepass.main.service.FileService;
import com.gatepass.main.service.ProctorService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class ProctorServiceImp implements ProctorService{

	@Autowired private ProctorRepository proctor_repository;
	@Autowired private FileService file_service;

	@Override
	@Transactional
	public void saveProctorData(MultipartFile image,String data) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
			ProctorInfo info = new Gson().fromJson(data,ProctorInfo.class);
			if(proctor_repository.existsById(info.getAssign_id())) {
				ProctorInfo dto=proctor_repository.findById(info.getAssign_id()).get();
				if(info.isToggled()) {
					dto.setToggle(dto.getToggle()+1);	
					proctor_repository.save(dto);
				}
				else {
					if(!image.isEmpty()) {
						ArrayList<String> res=new ArrayList<>();
						if(dto.getImages()==null) {
							res.add(new Gson().toJson(new ImageTime(
									dateFormat.format(new Date()),info.getImages())));
						}
						else {
							res = new Gson().fromJson(dto.getImages(),
									TypeToken.getParameterized(ArrayList.class, String.class).getType());
							res.add(new Gson().toJson(new ImageTime(
									dateFormat.format(new Date()),info.getImages())));
						}
						dto.setImages(new Gson().toJson(res));	
						proctor_repository.save(dto);
						file_service.uploadFile(info.getImages(), image);
					}					
				}				
			}
			else {
				ProctorInfo pi=new ProctorInfo();
				ArrayList<String> res=new ArrayList<>();
				if(!image.isEmpty()) {
					res.add(new Gson().toJson(new ImageTime(
							dateFormat.format(new Date()),info.getImages())));
				}
				pi.setAssign_id(info.getAssign_id());
				pi.setToggle(info.isToggled()?1:0);
				pi.setImages(new Gson().toJson(res));
				proctor_repository.save(pi);
				if(!image.isEmpty()) {
					file_service.uploadFile(info.getImages(), image);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to record proctor");
		}
	}



}
