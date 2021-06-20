package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name="role_entitlement_mapping")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role_Entitlement_Mapping {
	
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@OneToOne
	@JoinColumn(name="RoleId")
	private Role role;
	
	@OneToOne
	@JoinColumn(name="EntitlementId")
	private Entitlement entitlement;	
	
	public Role_Entitlement_Mapping(int id,  Entitlement entitlement) {
		this.id = id;
		this.entitlement = entitlement;
	}
}
