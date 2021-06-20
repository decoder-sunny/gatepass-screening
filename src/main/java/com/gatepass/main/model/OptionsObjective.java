package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="optionsobjective")
public class OptionsObjective {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@Column(name="OptionA")
	private String optionA;
	
	@NotNull
	@Column(name="OptionB")
	private String optionB;
	
	@NotNull
	@Column(name="OptionC")
	private String optionC;
	
	@NotNull
	@Column(name="OptionD")
	private String optionD;
}
