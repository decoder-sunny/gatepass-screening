package com.gatepass.main.dao;

import java.util.List;

import com.gatepass.main.dto.OrganisationDTO;
import com.gatepass.main.miscellaneous.Condition;

public interface OrganisationDAO {
	
	public List<OrganisationDTO> getOrganisationList(Condition condition);
	public int getOrganisationListCount(Condition condition);

}
