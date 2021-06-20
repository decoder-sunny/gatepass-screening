package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name="proctorinfo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProctorInfo {
	
	@Id
	@Column(name="AssignId")
	private int assign_id;
	
	@Column(name="Toggle")
	private int toggle;
	
	@Column(name="Images",length=3500)
	private String images;
	
	@Transient
	private boolean isToggled;
	
	public ProctorInfo(int assign_id, int toggle, String images) {
		super();
		this.assign_id = assign_id;
		this.toggle = toggle;
		this.images = images;
	}

	
	
	
}
