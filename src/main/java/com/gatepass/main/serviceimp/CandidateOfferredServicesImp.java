package com.gatepass.main.serviceimp;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.model.CandidateServices;
import com.gatepass.main.repository.CandidateServiceRepository;
import com.gatepass.main.service.CandidateOfferredServices;

@Service
public class CandidateOfferredServicesImp implements CandidateOfferredServices{

	@Autowired CandidateServiceRepository candidateservice_repository;

	@Override
	@Transactional
	public CandidateServices saveCandidateService(CandidateServices candidateServices) {
		try {
			if(!candidateservice_repository
					.existsByCandidateIdAndOrganisationId(candidateServices.getCandidateId(), 
							candidateServices.getOrganisationId())) {
				candidateServices=candidateservice_repository.save(candidateServices);
			}
		} 
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			System.out.println("Candidate Service Not Saved");			
		}
		catch (Exception e) {
			throw new CustomException("Candidate Operation failed " );
		}
		return candidateServices;
	}

	@Override
	@Transactional
	public CandidateServices updateCandidateService(CandidateServices candidateServices) {
		System.out.println(candidateServices);
		try {
			if(candidateservice_repository
					.existsByCandidateIdAndOrganisationId(candidateServices.getCandidateId(), 
							candidateServices.getOrganisationId())) {
				CandidateServices tempCandidateServices=candidateservice_repository
						.findByCandidateIdAndOrganisationId(candidateServices.getCandidateId(), 
								candidateServices.getOrganisationId());
				if(candidateServices.isInterviewService()) {
					tempCandidateServices.setInterviewService(true);
				}
				if(candidateServices.isScreenService()) {
					tempCandidateServices.setScreenService(true);
				}
				if(candidateServices.isTestService()) {
					tempCandidateServices.setTestService(true);
				}
				System.out.println(tempCandidateServices);
				candidateservice_repository.save(tempCandidateServices);
				return tempCandidateServices;
			}
			else {
				candidateservice_repository.save(candidateServices);
				return candidateServices;
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Something went wrong" );
		}
	}

}
