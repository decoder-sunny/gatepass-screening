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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="templates")
public class Templates {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@Column(name="TemplateName")
	private String templateName;
	
	@Column(name="CreatedBy")
	private Integer createdBy;
	
	@Column(name="UpdatedBy")
	private Integer updatedBy;
	
	@Column(name="CreatedOn")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Column(name="UpdatedOn")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;	

	public Templates(int id) {
		super();
		this.id = id;
	}
	
	
	
	
	
	
	
	
	
	
	
}
