package com.gatepass.main.miscellaneous;


import java.util.List;

import com.gatepass.main.dto.CandidateDTO;
import com.gatepass.main.dto.OrganisationDTO;
import com.gatepass.main.dto.PositionDTO;
import com.gatepass.main.dto.QuestionDTO;
import com.gatepass.main.dto.SectionDTO;
import com.gatepass.main.dto.SubSectionDTO;
import com.gatepass.main.dto.TemplateDTO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.dto.profile.Profile;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagination{
	
	private Integer length;
	private List<OrganisationDTO> organisations;
	private List<UserDTO> users;
	private List<SectionDTO> sections;
	private List<SubSectionDTO> subsections;
	private List<QuestionDTO> questions;
	private List<TemplateDTO> templates;
	private List<CandidateDTO> candidates;
	private List<PositionDTO> positions;
	private List<Profile> profile;
	
	

	
}
