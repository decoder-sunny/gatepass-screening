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
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="questions")
public class Questions {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@Column(name="Question",length=2000)
	private String question;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="QuestionImage")
	private QuestionImage questionImage;
	
	@OneToOne
	@JoinColumn(name="QuestionType",nullable=false)
	private Code questionType;
	
	@OneToOne
	@JoinColumn(name="Level",nullable=false)
	private Code level;
	
	@OneToOne
	@JoinColumn(name="SubSection",nullable=false)
	private SubSection subsection;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OptionsObjective")
	private OptionsObjective optionsObjective;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OptionsImage")
	private OptionsImage optionsImage;
	
	private String answer;	
	private int time;
	private int marks;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="CodeQuestion")
	private CodeQuestion codeQuestion;
	
	public Questions(int id) {
		super();
		this.id = id;
	}

}
