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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="user",uniqueConstraints=
@UniqueConstraint(columnNames={"Email","MobileNo"}))
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@Column(name="Name")
	private String name;
	
	@NotNull
	@Column(name="Email",unique=true)
	private String email;
	
	@NotNull
	@Size(min=0,max=10)
	@Pattern(regexp="(^$|[0-9]{10})")
	@Column(name="MobileNo")
	private String mobileNo;
	
	@Column(name="ProfilePicName")
	private String profilePicName;
	
	@Column(name="CreatedOn")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Column(name="UpdatedOn")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	@Column(name="IsActive")
	private boolean isActive;	
	
	@Column(name="Designation")
	private String designation;
	
	@NotNull
	@Column(name="CreatedBy")
	private Integer createdBy;

	@NotNull
	@Column(name="UpdatedBy")
	private Integer updatedBy;

	public User(int id) {
		super();
		this.id = id;
	}
	

	
}
