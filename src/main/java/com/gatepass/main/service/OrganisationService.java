package com.gatepass.main.service;

import com.gatepass.main.dto.OrganisationDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.Pagination;

public interface OrganisationService {
	
	public OrganisationDTO saveOrganisation(OrganisationDTO userDTO);
	public OrganisationDTO updateOrganisation(OrganisationDTO userDTO);
	public OrganisationDTO getOrganisationById(int id);
	
	public Pagination getOrganisationList(Condition condition);

}
