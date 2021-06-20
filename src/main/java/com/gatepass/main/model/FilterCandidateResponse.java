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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="filtercandidateresponse",uniqueConstraints=
@UniqueConstraint(columnNames={"CandidateId","PositionId"}))
public class FilterCandidateResponse {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;	

	@NotNull
	@Column(name="CandidateId")
	@Min(1)
	private int candidateId;
	
	@NotNull
	@Column(name="PositionId")
	@Min(1)
	private int positionId;
	@Column(name="Marks")
	private int marks;
	
	@Column(name="Response",length=3000)
	private String response;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Date")
	private Date createdOn;

	
}
