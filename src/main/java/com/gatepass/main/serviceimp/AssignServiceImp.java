package com.gatepass.main.serviceimp;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gatepass.main.dto.AssignDTO;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.Encryption;
import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.model.AssignedTemplate;
import com.gatepass.main.model.CandidateDetails;
import com.gatepass.main.model.CandidateServices;
import com.gatepass.main.model.Credentials;
import com.gatepass.main.model.Templates;
import com.gatepass.main.repository.AssignTemplateRepository;
import com.gatepass.main.repository.CandidateRepository;
import com.gatepass.main.repository.CredentialsRepository;
import com.gatepass.main.service.AssignService;
import com.gatepass.main.service.CandidateOfferredServices;
import com.gatepass.main.service.MailService;
import com.gatepass.main.service.MyServices;

@Service
public class AssignServiceImp implements AssignService{

	@Autowired private AssignTemplateRepository assign_repository;
	@Autowired private CredentialsRepository credential_repository;
	@Autowired private CandidateRepository candidate_repository;
	@Autowired private MailService mail_service;
	@Autowired private PasswordEncoder bcryptEncoder;
	@Autowired private MyServices myservices;
	@Autowired private CandidateOfferredServices candidateoffered_service;

	@Override
	@Transactional
	public MessageModel assignTemplate(List<AssignDTO> list) {
		try {
			myservices.validateServices(true, false,false);
			MessageModel msg=new MessageModel();
			if(!list.isEmpty()) {
				for (AssignDTO assignDTO : list) {					
					if(candidate_repository.existsById(assignDTO.getCandidate().getId())) {
						CandidateDetails cd=candidate_repository.findById(assignDTO.getCandidate().getId()).get();						
						Credentials cred=credential_repository.findByUser_Id(cd.getUser().getId());
						String password=Encryption.randomPassword();
						if(cred==null) {
							credential_repository.save(new Credentials(0, cd.getUser().getId(),
									bcryptEncoder.encode(password)));							
						}
						else {
							cred.setPassword(bcryptEncoder.encode(password));
							credential_repository.save(cred);
						}
						if(assign_repository.existsByCandidate_IdAndTemplate_IdAndOrganisation(assignDTO.getCandidate().getId(),
								assignDTO.getTemplate().getId(), assignDTO.getOrganisationId())) {
							throw new CustomException("Duplicate Template Assignment");
						}
						else {
							AssignedTemplate assignedTemplate=new AssignedTemplate();
							assignedTemplate.setCandidate(new CandidateDetails(assignDTO.getCandidate().getId()));
							assignedTemplate.setCompletionStatus(false);
							assignedTemplate.setCreatedOn(new Date());
							assignedTemplate.setUpdatedOn(new Date());
							assignedTemplate.setTemplate(new Templates(assignDTO.getTemplate().getId()));
							assignedTemplate.setOrganisation(assignDTO.getOrganisationId());
							assignedTemplate=assign_repository.save(assignedTemplate);
							candidateoffered_service.updateCandidateService(new CandidateServices(assignDTO.getCandidate().getId(), 
									assignDTO.getOrganisationId(), false,true,false));
							mail_service.candidateLink(cd, password, assignedTemplate.getId());
							Thread.sleep(20000);
						}						
					}
				}
				msg.setMessage("Templates Assigned");
			}
			else {
				throw new CustomException("Tagged not saved");
			}
			return msg;
		} 
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new CustomException("Duplicate Tagging"  );
		}
		catch (CustomException e) {
			e.printStackTrace();
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			throw new DuplicateKeyException("Tagged not saved");
		}	
	}

	@Override
	@Transactional
	public MessageModel reAssignTemplate(int assign_id) {
		try {
			myservices.validateServices(true, false,false);
			MessageModel msg=new MessageModel();
			if(assign_repository.existsById(assign_id)) {
				AssignedTemplate assignedTemplate=assign_repository.findById(assign_id).get();
				if(assignedTemplate.isCompletionStatus()) {
					throw new CustomException("Test Completed");
				}
				assignedTemplate.setUpdatedOn(new Date());
				assign_repository.save(assignedTemplate);
				Credentials cred=credential_repository.findByUser_Id(assignedTemplate.getCandidate().getUser().getId());
				String password=Encryption.randomPassword();
				cred.setPassword(bcryptEncoder.encode(password));
				credential_repository.save(cred);
				mail_service.candidateLink(assignedTemplate.getCandidate(), password, assign_id);
				msg.setMessage("Template Re-Assigned");								
			}
			else {
				throw new CustomException("Tagged not saved");
			}
			return msg;
		} 
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new CustomException("Duplicate Tagging"  );
		}
		catch (CustomException e) {
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			throw new CustomException("Failed to re-assign template");
		}	
	}

}
