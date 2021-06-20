package com.gatepass.main.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name="position")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Position {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@Column(name="Name")
	private String name;
	
	@Column(name="IsActive")
	private boolean isActive;	
	
	@Column(name="JdDocument")
	private String jdDocument;
	
	@NotNull
	@Column(name="CreatedBy")
	private int createdBy;
	
	@NotNull
	@Column(name="UpdatedBy")
	private int updatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CreatedOn")
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UpdatedOn")
	private Date updatedOn;
	
	@Column(name="Applied",nullable=false)
	private int applied;
	
	@Column(name="Completed",nullable=false)
	private int completed;
	
	

	
}
