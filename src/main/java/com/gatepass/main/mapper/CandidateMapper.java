package com.gatepass.main.mapper;

import java.util.Date;

import com.gatepass.main.dto.CandidateDTO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.model.CandidateDetails;
import com.gatepass.main.model.Organisation;

public class CandidateMapper {
	
	public static CandidateDTO candidateMapper(CandidateDetails candidateDetails,int template_id,Organisation org) {
		CandidateDTO cd=new CandidateDTO();
		cd.setFunction(candidateDetails.getFunction()!=null?candidateDetails.getFunction().getValue():null);
		cd.setPositionApplied(candidateDetails.getPositionApplied()!=null?
				candidateDetails.getPositionApplied():null);
		cd.setId(candidateDetails.getId());
		
		UserDTO dto=new UserDTO();
		dto.setName(candidateDetails.getUser().getName());
		dto.setEmail(candidateDetails.getUser().getEmail());
		dto.setId(candidateDetails.getUser().getId());
		
		Organisation odto=new Organisation();
		odto.setOrganisationName(org.getOrganisationName());
		odto.setId(org.getId());
		dto.setOrganisation(odto);
		
		cd.setUserDTO(dto);
		cd.setTemplate_id(template_id);
		return cd;
	}
	
	public static CandidateDTO candidateMapperForReport(CandidateDetails candidateDetails,Date date,Organisation org) {
		CandidateDTO cd=new CandidateDTO();
		cd.setPositionApplied(candidateDetails.getPositionApplied());
		cd.setGender(candidateDetails.getGender()!=null?candidateDetails.getGender().getValue():null);
		UserDTO dto=new UserDTO();
		dto.setName(candidateDetails.getUser().getName());
		dto.setEmail(candidateDetails.getUser().getEmail());
		dto.setMobileNo(candidateDetails.getUser().getMobileNo());
		
		dto.setCreatedOn(date);
		
		Organisation odto=new Organisation();
		odto.setOrganisationName(org.getOrganisationName());
		dto.setOrganisation(odto);
		
		cd.setUserDTO(dto);;
		return cd;
	}

}
