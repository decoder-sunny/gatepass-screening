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
@Table(name="optionsimage")
public class OptionsImage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@Column(name="OptionAImage")
	private String optionAImage;
	
	@NotNull
	@Column(name="OptionBImage")
	private String optionBImage;
	
	@NotNull
	@Column(name="OptionCImage")
	private String optionCImage;
	
	@NotNull
	@Column(name="OptionDImage")
	private String optionDImage;

	
}
