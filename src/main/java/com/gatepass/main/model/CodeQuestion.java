package com.gatepass.main.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="codequestion")
public class CodeQuestion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@Column(length=2000,name="Question_Java")
	private String question_java;
	
	@Column(length=2000,name="Question_C")
	private String question_c;
	
	@Column(length=2000,name="Question_Cplus")
	private String question_cplus;
	
	@Column(length=2000,name="Question_Csharp")
	private String question_csharp;
	
	@Column(length=2000,name="Question_Python")
	private String question_python;
	
	@OneToMany(mappedBy="codequestion",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value= {"codequestion"})
	private List<TestCases> testcases;

}
