package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="positionquestions")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionQuestions {
	

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="PositionId",nullable=false)
	private Integer positionId;
	
	@Column(name="Question",nullable=false)
	private String question;
	
	@Column(name="IsMustHave")
	private boolean isMustHave;
	
	@Column(name="IsMultiple",nullable=false)
	private boolean isMultiple;
	
	@Column(name="Options",nullable=false)
	private String options;
	
	@Column(name="PreferredOptions",nullable=false)
	private String preferredOptions;
	
	@Column(name="QuestionType",nullable=false)
	private String questionType;
	
	@Transient
	private Boolean deleted;
	
	@Transient
	private String positionName;

}
