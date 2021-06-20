package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="userorganisationmapping",uniqueConstraints=
@UniqueConstraint(columnNames={"UserId","Organisation"}))
public class UserOrganisationMapping {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@Column(name="UserId")
	private Integer userId;
	
	@OneToOne
	@JoinColumn(name="Organisation",nullable=false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Organisation organisation;


	public UserOrganisationMapping(@NotNull Integer userId, Organisation organisation) {
		super();
		this.userId = userId;
		this.organisation = organisation;
	}
}
