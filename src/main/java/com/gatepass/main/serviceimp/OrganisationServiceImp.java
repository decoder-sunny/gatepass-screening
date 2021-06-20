package com.gatepass.main.serviceimp;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gatepass.main.dao.OrganisationDAO;
import com.gatepass.main.dto.OrganisationDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.Pagination;
import com.gatepass.main.model.Organisation;
import com.gatepass.main.model.Services;
import com.gatepass.main.repository.OrganisationRepository;
import com.gatepass.main.service.MyServices;
import com.gatepass.main.service.OrganisationService;

@Service
public class OrganisationServiceImp implements OrganisationService{
	
	@Autowired OrganisationRepository organisation_repository;
	@Autowired OrganisationDAO organisation_dao;
	@Autowired MyServices my_service;

	@Override
	@Transactional
	public OrganisationDTO saveOrganisation(OrganisationDTO organisationDTO) {
		try {
			Organisation organisation=new Organisation();
			organisation.setOrganisationName(organisationDTO.getOrganisationName());
			organisation.setContactPerson(organisationDTO.getContactPerson());
			organisation.setLocation(organisationDTO.getLocation());
			organisation.setCreatedOn(new Date());
			organisation.setUpdatedOn(new Date());
			organisation=organisation_repository.save(organisation);
			organisationDTO.setId(organisation.getId());
			my_service.saveServices(new Services(organisationDTO.getId(),false,false,false));
			return organisationDTO;
		} 
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Organisation not saved");
		}	
	}

	@Override
	@Transactional
	public OrganisationDTO updateOrganisation(OrganisationDTO organisationDTO) {
		try {			
			if(organisation_repository.existsById(organisationDTO.getId())) {
				Organisation organisation=organisation_repository.findById(organisationDTO.getId()).get();
				organisation.setContactPerson(organisationDTO.getContactPerson());
				organisation.setOrganisationName(organisationDTO.getOrganisationName());
				organisation.setLocation(organisationDTO.getLocation());
				organisation.setUpdatedOn(new Date());
				organisation_repository.save(organisation);
			}
			else {
				throw new CustomException("Invalid request");
			}
			return organisationDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Organisation not updated");
		}	
	}

	@Override
	@Transactional
	public OrganisationDTO getOrganisationById(int id) {
		try {			
			OrganisationDTO organisationDTO=new OrganisationDTO();
			if(organisation_repository.existsById(id)) {
				Organisation organisation=organisation_repository.findById(id).get();				
				organisationDTO.setOrganisationName(organisation.getOrganisationName());
				organisationDTO.setContactPerson(organisation.getContactPerson());
				organisationDTO.setLocation(organisation.getLocation());
				organisationDTO.setId(organisation.getId());
			}
			else {
				throw new CustomException("Invalid request");
			}
			return organisationDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}	
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Organisation not found");
		}
	}

	@Override
	@Transactional
	public Pagination getOrganisationList(Condition condition) {
		try {
			Pagination pagination=new Pagination();
			pagination.setOrganisations(organisation_dao.getOrganisationList(condition));
			pagination.setLength(organisation_dao.getOrganisationListCount(condition));
			return pagination;			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Organisations not found");
		}
	}

}
