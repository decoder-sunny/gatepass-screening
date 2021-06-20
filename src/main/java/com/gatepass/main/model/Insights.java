package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="insights")
public class Insights {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="OrganisationId")
	private int organisationId;
	
	@Column(length=1500,name="About")
	private String about;
	
	@Column(name="Website")
	private String website;
}
