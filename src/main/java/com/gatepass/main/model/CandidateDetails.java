package com.gatepass.main.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="candidatedetails")
public class CandidateDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@OneToOne
	@JoinColumn(name="CandidateFunction")
	private Code function;
	
	@OneToOne
	@JoinColumn(name="Gender")
	private Code gender;	
	
	@Column(name="PositionApplied")
	private String positionApplied;
	
	private String location;
	private int experienceYears;
	private int experienceMonths;
	
	@Column(name="CV")
	private String cv;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="User",nullable=false)
	private User user;

	public CandidateDetails(int id) {
		super();
		this.id = id;
	}
	
	

}
