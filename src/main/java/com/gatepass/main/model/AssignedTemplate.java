package com.gatepass.main.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="assignedtemplate")
@Getter
@Setter
public class AssignedTemplate {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@OneToOne
	@JoinColumn(name="CandidateId",nullable=false)
	private CandidateDetails candidate;	
	
	@OneToOne
	@JoinColumn(name="TemplateId",nullable=false)
	private Templates template;
	
	@NotNull
	@Column(name="OrganisationId")
	private Integer organisation;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CreatedOn")
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UpdatedOn")
	private Date updatedOn;
	
	@Column(name="CompletionStatus")
	private boolean completionStatus;
	
	@OneToMany(mappedBy="assignId",fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value= {"assignId"})
	private List<CandidateResponse> candidateResponses;

	

}
