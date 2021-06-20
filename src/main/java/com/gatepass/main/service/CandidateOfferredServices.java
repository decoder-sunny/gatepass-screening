package com.gatepass.main.service;

import com.gatepass.main.model.CandidateServices;

public interface CandidateOfferredServices {
	
	CandidateServices saveCandidateService(CandidateServices candidateServices);
	CandidateServices updateCandidateService(CandidateServices candidateServices);

}
