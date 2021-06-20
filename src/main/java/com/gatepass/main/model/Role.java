package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
@Table(name="role")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotEmpty
	@Column(name="Role")
	private String role;
	
	@NotEmpty
	@Column(name="Description")
	private String description;
	
	public Role(int role_id,String role) {
		super();
		this.id = role_id;
		this.role = role;
	}
	public Role(int id) {
		super();
		this.id = id;
	}	
	
	
	
	
	
		
}
