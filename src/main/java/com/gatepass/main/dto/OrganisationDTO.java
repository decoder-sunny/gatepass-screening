package com.gatepass.main.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class OrganisationDTO {
	
	private int id;
	@NotNull(message="OrganisationName cannot be empty")
	private String organisationName;
	@NotNull(message="Location cannot be empty")
	private String location;
	@NotNull(message="ContactPerson cannot be empty")
	private String contactPerson;
	private Date createdOn;
	private Date updatedOn;
	
	
	
	public OrganisationDTO(int organisationId,
			@NotNull(message = "OrganisationName cannot be empty") String organisationName,
			@NotNull(message = "Location cannot be empty") String location,
			@NotNull(message = "ContactPerson cannot be empty") String contactPerson) {
		this.id = organisationId;
		this.organisationName = organisationName;
		this.location = location;
		this.contactPerson = contactPerson;
	}
	
	
	

}
