package com.gatepass.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gatepass.main.dao.CandidateDAO;
import com.gatepass.main.dao.FilterCandidateResponseDAO;
import com.gatepass.main.dto.CandidateDTO;
import com.gatepass.main.dto.profile.Profile;
import com.gatepass.main.elastic.repository.ProfileRepository;
import com.gatepass.main.service.UtilityService;
import com.google.gson.Gson;

@Component
public class ProjectSchedulers {
	
	@Autowired CandidateDAO  candidate_dao;
	@Autowired ProfileRepository profile_repository;
	@Autowired FilterCandidateResponseDAO filter_responses;
	
	@Autowired UtilityService utility_service;	
	
	@Scheduled(cron = "0 0/30 * 1/1 * *")
	public void syncCandidatesWithDB() {// to make candidates sync with DB and Elasticsercah repositories
		System.out.println("In Scheduler");
		try {
			List<CandidateDTO> candidateList=candidate_dao.getRecentCandidates();// fetch all recent cadidadates
			if(!candidateList.isEmpty()) {
				for (CandidateDTO candidateDTO : candidateList) {
					System.out.println("candidate Name is "+ candidateDTO.getUserDTO().getName());
					
					if(!profile_repository.existsById(candidateDTO.getId())) {					
						List<Object[]> list=filter_responses.getAllCandidateResponse(candidateDTO.getId());					
						Profile profile=new Profile();
						profile.setCandidateId(candidateDTO.getId());
						profile.setUserId(candidateDTO.getUserDTO().getId());
						profile.setName(candidateDTO.getUserDTO().getName());
						profile.setEmail(candidateDTO.getUserDTO().getEmail());
						profile.setOrgId(candidateDTO.getOrganisation());
						profile.setMobileNo(candidateDTO.getUserDTO().getMobileNo());
						if(!list.isEmpty()) {
							profile.setDetails(new Gson().toJson(list));	
						}
						
						profile_repository.save(profile);						
						System.out.println(candidateDTO.getUserDTO().getName()+ " saved");
						
					}
					else {
						List<Object[]> list=filter_responses.getAllCandidateResponse(candidateDTO.getId());
						System.out.println(list.size());
						System.out.println("before");
						
						Profile profile=profile_repository.findById(candidateDTO.getId()).get();
						System.out.println(profile);
						
						profile.setUserId(candidateDTO.getUserDTO().getId());
						profile.setName(candidateDTO.getUserDTO().getName());
						profile.setEmail(candidateDTO.getUserDTO().getEmail());
						profile.setOrgId(candidateDTO.getOrganisation());
						profile.setMobileNo(candidateDTO.getUserDTO().getMobileNo());
						if(!list.isEmpty()) {
							profile.setDetails(new Gson().toJson(list));	
						}
						System.out.println("after");
						System.out.println(profile);
						profile_repository.save(profile);
						System.out.println(candidateDTO.getUserDTO().getName()+ " updated");
					}
					Thread.sleep(1000);					
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to fetch recent candidates");
		}
	}

}
