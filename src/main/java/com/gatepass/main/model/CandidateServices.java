package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name="candidateservices",uniqueConstraints=
@UniqueConstraint(columnNames={"CandidateId","OrganisationId"}))
public class CandidateServices {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@Column(name="CandidateId")
	private Integer candidateId;
	
	@NotNull
	@Column(name="OrganisationId")
	private Integer organisationId;
	
	@Column(name="ScreenService")
	private boolean screenService;
	
	@Column(name="TestService")
	private boolean testService;
	
	@Column(name="InterviewService")
	private boolean interviewService;

	

	public CandidateServices(@NotNull Integer candidateId, @NotNull Integer organisationId, boolean screenService,
			boolean testService, boolean interviewService) {
		super();
		this.candidateId = candidateId;
		this.organisationId = organisationId;
		this.screenService = screenService;
		this.testService = testService;
		this.interviewService = interviewService;
	}

		

}
