package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="testcases")
public class TestCases {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@Column(name="ExpectedInput")
	private String expectedInput;
	
	@NotNull
	@Column(name="ExpectedOutput")
	private String expectedOutput;
	
	@Column(name="Marks")
	private int marks;
	
	@Transient
	private boolean status;
	
	@ManyToOne
	@Cascade(value={org.hibernate.annotations.CascadeType.ALL})
	@JsonIgnoreProperties("testcases")
    @JoinColumn(name="CodeId",nullable=false)
	private CodeQuestion codequestion;

	
}
