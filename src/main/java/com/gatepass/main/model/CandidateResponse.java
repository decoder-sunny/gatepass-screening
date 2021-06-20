package com.gatepass.main.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name="candidateresponse",uniqueConstraints=
@UniqueConstraint(columnNames={"AssignId","Section"}))
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateResponse {
	
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@Column(name="AssignId")
	@Min(1)
	private int assignId;
	
	@Column(name="Response",length=5000)
	private String response;
	
	@NotNull
	@Column(name="Section")
	@Min(1)
	private int section;	
	
	@Column(name="Marks")
	private int marks;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Date")
	private Date date;
	
	@Column(name="TestCases",length=500,nullable=true)
	private String testCases;

	
	

}
