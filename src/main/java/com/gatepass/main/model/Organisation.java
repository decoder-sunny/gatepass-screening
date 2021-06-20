package com.gatepass.main.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="organisation")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organisation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@Column(name="OrganisationName")
	private String organisationName;
	
	@NotNull
	@Column(name="Location")
	private String location;	
	
	@NotNull
	@Column(name="ContactPerson")
	private String contactPerson;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CreatedOn")
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UpdatedOn")
	private Date updatedOn;

	public Organisation(int organisationId) {
		super();
		this.id = organisationId;
	}
	public Organisation(int organisationId,String organisationName) {
		super();
		this.id = organisationId;
		this.organisationName = organisationName;
	}	
	
	
	
	
	
	
}
