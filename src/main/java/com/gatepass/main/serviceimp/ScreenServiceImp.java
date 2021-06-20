package com.gatepass.main.serviceimp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gatepass.main.dto.CandidateDTO;
import com.gatepass.main.dto.CandidateFilterResponse;
import com.gatepass.main.dto.CandidateScreenReport;
import com.gatepass.main.dto.PositionDTO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.mapper.ObjectMapper;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.model.CandidateDetails;
import com.gatepass.main.model.CandidateServices;
import com.gatepass.main.model.Code;
import com.gatepass.main.model.FilterCandidateResponse;
import com.gatepass.main.model.Position;
import com.gatepass.main.model.PositionQuestions;
import com.gatepass.main.model.User;
import com.gatepass.main.model.UserOrganisationMapping;
import com.gatepass.main.model.UserRoleMapping;
import com.gatepass.main.repository.CandidateRepository;
import com.gatepass.main.repository.FilterCandidateResponseRepository;
import com.gatepass.main.repository.PositionQuestionRepository;
import com.gatepass.main.repository.PositionRepository;
import com.gatepass.main.repository.UserOrganisationRepository;
import com.gatepass.main.repository.UserRepository;
import com.gatepass.main.repository.UserRoleMappingRepository;
import com.gatepass.main.service.CandidateOfferredServices;
import com.gatepass.main.service.FileService;
import com.gatepass.main.service.PositionService;
import com.gatepass.main.service.ScreenService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class ScreenServiceImp implements ScreenService{
	@Autowired PositionRepository position_repository;
	@Autowired FileService file_service;
	@Autowired CandidateRepository candidate_repository;
	@Autowired UserRepository user_repository;
	@Autowired UserRoleMappingRepository urm_repository;
	@Autowired FilterCandidateResponseRepository response_repository;
	@Autowired CandidateOfferredServices candidate_services;
	@Autowired UserOrganisationRepository userorganisation_repository;
	@Autowired PositionQuestionRepository positionquestion_repository;
	@Autowired PositionService position_service;

	@Override
	@Transactional
	public List<PositionQuestions> getPositionQuestions(int position_id) {
		List<PositionQuestions> list=new ArrayList<PositionQuestions>();
		try {
			if(position_repository.existsById(position_id)) {
				Position position=position_repository.findById(position_id).get();
				if(position.isActive()) {
					list=positionquestion_repository.findAllByPositionIdOrderByIsMustHaveDesc(position_id);	
					if(!list.isEmpty()) {
						list.get(0).setPositionName(position.getName());
					}
				}
				else {
					throw new CustomException("Position Inactive");
				}
			} 
			else {
				throw new CustomException("Invalid request");
			}
		} 		
		catch (CustomException e) {
			System.out.println(e);
			e.printStackTrace();
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Failed to fetch questions");
		}
		return list;
	}

	@Override
	@Transactional
	public MessageModel validateMustHave(CandidateFilterResponse candidateFilterResponse) {
		MessageModel model=new MessageModel();
		try {
			if(position_repository.existsById(candidateFilterResponse.getPosition_id())) {
				List<PositionQuestions> list=positionquestion_repository
						.findAllByPositionIdAndIsMustHaveOrderByIsMustHaveDesc(candidateFilterResponse.getPosition_id(),true);	
				
				for (int i = 0; i < list.size(); i++) {
				
					List<String> mustHaves=new Gson().fromJson(list.get(i).getPreferredOptions(),
							TypeToken.getParameterized(ArrayList.class, String.class).getType());
					
					Boolean disjoint=Collections.disjoint(mustHaves, 
							candidateFilterResponse.getResponses().get(i).getResponse());
					
					if(disjoint) {
						throw new CustomException("Sorry.. You are not suitable for this profile");
					}
					model.setStatus(200);
					model.setMessage("Must have criteria verified");
				}				
			}
			else {
				throw new CustomException("Invalid request");
			}
		} catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Failed to fetch information");
		}
		return model;
	}


	@Override
	@Transactional
	public MessageModel submitFilterResponse(String information,MultipartFile image) {
		MessageModel model=new MessageModel();
		try {
			CandidateFilterResponse response=
					Optional.ofNullable(new Gson().fromJson(information,CandidateFilterResponse.class))
					.orElseThrow(() -> new CustomException("Invalid Request"));
			CandidateDetails cd=new CandidateDetails();
			cd=candidate_repository.findByUser_Email(response.getCandidate()
					.getUserDTO().getEmail());
			Position position=position_repository.findById(response.getPosition_id()).get();
			DateFormat dateFormat = new SimpleDateFormat("dd-mm-yy hh:mm:ss");
			String fileName="CV_"+dateFormat.format(new Date());
			if(cd==null) {	
				CandidateDetails candidateDetails=new CandidateDetails();
				User user=new User();
				user.setActive(true);
				user.setCreatedBy(position.getCreatedBy());
				user.setUpdatedBy(position.getCreatedBy());
				user.setName(response.getCandidate().getUserDTO().getName());
				user.setDesignation("CANDIDATE");
				user.setEmail(response.getCandidate().getUserDTO().getEmail());
				user.setMobileNo(response.getCandidate().getUserDTO().getMobileNo());
				user.setCreatedOn(new Date());
				user.setUpdatedOn(new Date());				
				if(image!=null) {
					candidateDetails.setCv(fileName);
					file_service.uploadFile(fileName, image);
				}
				candidateDetails.setGender(response.getCandidate().getGender_id()!=null?new Code(response.getCandidate().getGender_id()):null);
				candidateDetails.setUser(user);
				candidateDetails=candidate_repository.save(candidateDetails);
				cd=candidateDetails;
				urm_repository.save(new UserRoleMapping(0,cd.getUser().getId(),3));			
				
			}
			if(cd!=null && image!=null) {
				cd.setCv(fileName);
				file_service.uploadFile(fileName, image);
				cd.getUser().setUpdatedOn(new Date());
				candidate_repository.save(cd);
			}

			FilterCandidateResponse candidateResponse=new FilterCandidateResponse();
			candidateResponse.setCandidateId(cd.getId());
			candidateResponse.setPositionId(position.getId());
			candidateResponse.setCreatedOn(new Date());
			candidateResponse.setResponse(new Gson().toJson(response.getResponses()));
			candidateResponse.setMarks(responseSubmissionEvaluation(position.getId(), response));
			response_repository.save(candidateResponse);
			position.setCompleted(position.getCompleted()+1);
			
			position_repository.save(position);
			
			if(userorganisation_repository.existsByUserId(position.getCreatedBy())) {
				UserOrganisationMapping uom=userorganisation_repository
						.findByUserId(position.getCreatedBy());
				candidate_services.updateCandidateService(new CandidateServices(cd.getId(),
						uom.getOrganisation().getId(),true,false,false));
			}
			else {
				throw new CustomException("Failed to save responses");
			}
			
			model.setMessage("Thanks for sharing the details, One of our team member will give you a shout soon");
			model.setStatus(200);


		} catch (CustomException e) {
			e.printStackTrace();
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new DuplicateKeyException("Duplicate Response for similar position"  );
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new CustomException("Failed to save data");
		}
		return model;
	}
	
	
	private int responseSubmissionEvaluation(int position_id,CandidateFilterResponse cfr) {
		int marks=0;
		try {
			List<PositionQuestions> list=positionquestion_repository
					.findAllByPositionIdOrderByIsMustHaveDesc(position_id);	
			for (int i = 0; i < list.size(); i++) {
				List<String> preferredOptions=new Gson().fromJson(list.get(i).getPreferredOptions(),
						TypeToken.getParameterized(ArrayList.class, String.class).getType());
				Boolean disjoint=Collections.disjoint(preferredOptions, 
						cfr.getResponses().get(i).getResponse());
				if(!disjoint) {
					if(!list.get(i).isMultiple()) {
						marks+=1;
					}
					else {
						for (String string : cfr.getResponses().get(i).getResponse()) {
							if(preferredOptions.contains(string)) {
								marks+=1;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new CustomException("Failed to evaluate marks");
		}
		return marks;
	}

	@Override
	@Transactional
	public CandidateScreenReport getScreenReport(int position_id, Condition condition) {
		CandidateScreenReport csr=new CandidateScreenReport();
		try {
			List<CandidateFilterResponse> responses=new ArrayList<>();
			PositionDTO dto=position_service.getPositionById(position_id);
			Pageable pageable = PageRequest.of(condition.getIndex()-1,condition.getPagesize());
			Page<Object[]> page=response_repository.getResponsesOfCandidates(position_id, pageable);
			if(!page.getContent().isEmpty()) {
				for (Object[] objects : page.getContent()) {
					CandidateFilterResponse cfr=new CandidateFilterResponse();
					CandidateDTO cdto=new CandidateDTO();
					cdto.setId(ObjectMapper.mapObjToInteger(objects[0]));
					cdto.setUserDTO(new UserDTO(ObjectMapper.mapObjToString(objects[1]), ""));
					cfr.setMarks(ObjectMapper.mapObjToInteger(objects[2]));
					cfr.setScreenResponse(ObjectMapper.mapObjToString(objects[3]));
					cfr.setCandidate(cdto);
					responses.add(cfr);
				}
			}
			csr.setPosition(dto);
			csr.setResponses(responses);
			csr.setLength(page.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to get Data");
		}
		return csr;
	}

	

}
