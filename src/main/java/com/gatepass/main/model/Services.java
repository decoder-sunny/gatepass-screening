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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="services")
public class Services {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="OrganisationId")
	private int organisationId;
	
	@Column(name="ServiceTest")
	private boolean serviceTest;
	
	@Column(name="ServiceScreening")
	private boolean serviceScreening;
	
	@Column(name="ServiceInterview")
	private boolean serviceInterview;
	
	@Column(name="CreatedOn")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Column(name="UpdatedOn")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	public Services(int organisationId, boolean serviceTest, boolean serviceScreening, boolean serviceInterview) {
		super();
		this.organisationId = organisationId;
		this.serviceTest = serviceTest;
		this.serviceScreening = serviceScreening;
		this.serviceInterview = serviceInterview;
	}

	
	
	
	

}
