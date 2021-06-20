package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="entitlement")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entitlement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;	
	
	@NotNull
	@Column(name="PageName")
	private String pageName;
	
		
	@NotNull
	@Column(name="Path")
	private String path;
	
	@NotNull
	@Column(name="IconName")
	private String iconName;		

}
