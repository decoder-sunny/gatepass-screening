package com.gatepass.main.serviceimp;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.model.Credentials;
import com.gatepass.main.model.Services;
import com.gatepass.main.model.UserOrganisationMapping;
import com.gatepass.main.repository.CredentialsRepository;
import com.gatepass.main.repository.MyServicesRepository;
import com.gatepass.main.repository.UserOrganisationRepository;
import com.gatepass.main.service.MyServices;

@Service
public class MyServicesImp implements MyServices{
	
	@Autowired MyServicesRepository service_repository;
	@Autowired CredentialsRepository credential_repository;
	@Autowired UserOrganisationRepository userorganisation_repository;

	@Override
	@Transactional
	public Services saveServices(Services services) {
		try {
			services.setCreatedOn(new Date());
			services.setUpdatedOn(new Date());
			services=service_repository.save(services);
			return services;
		} 
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Services not saved");
		}	
	}

	@Override
	@Transactional
	public Services updateServices(Services services) {
		try {
			if(service_repository.existsById(services.getOrganisationId())) {
				services.setUpdatedOn(new Date());
				services=service_repository.save(services);
			}
			else {
				throw new CustomException("Invalid request");
			}
			return services;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Services not saved");
		}
	}

	@Override
	@Transactional
	public Services getServiceById(int id) {
		Services services=new Services(); 
		try {
			if(service_repository.existsById(id)) {
				services=service_repository.findById(id).get();
			}
			else {
				throw new CustomException("Invalid request");
			}
			return services;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Services not found");
		}
	}

	@Override
	@Transactional
	public void validateServices(boolean test, boolean screen, boolean interview) {
		try {
			UserDetails userDetails =
					 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials=credential_repository.findByUser_Email(userDetails.getUsername());
			if(userorganisation_repository.existsByUserId(credentials.getUser().getId())) {
				UserOrganisationMapping uom=userorganisation_repository.findByUserId(credentials.getUser().getId());
				if(service_repository.existsById(uom.getOrganisation().getId())) {
					Services services=service_repository
							.findById(uom.getOrganisation().getId()).get();
					if(test && !services.isServiceTest()) {
						throw new CustomException("Kindly get Gatepass-Online Test Services");
					}
					if(screen && !services.isServiceScreening()) {
						throw new CustomException("Kindly get Gatepass-Screening Services");
					}
					if(interview && !services.isServiceInterview()) {
						throw new CustomException("Kindly get Gatepass-Interview Services");
					}
				}
				else {
					throw new CustomException("Invalid request");
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
			throw new CustomException("Invalid Reuqest");
		}
		
	}
	
	

}
