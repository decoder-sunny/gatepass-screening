package com.gatepass.main.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.gatepass.main.model.Organisation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@ToString
public class UserDTO {
	
	private int id;
	
	@NotNull(message="Name is mandatory")
	private String name;
	@NotNull(message="Email is mandatory")
	private String email;
	@NotNull(message="Mobile No is mandatory")
	@Size(min=0,max=10)
	@Pattern(regexp="(^$|[0-9]{10})")
	private String mobileNo;
	
	private String profilePicName;
	private Date createdOn;
	private Date updatedOn;
	
	@NotNull
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Organisation organisation;
	
	
	private boolean isActive;		
	@NotNull(message="Designation is mandatory")
	private String designation;
	
	@NotNull
	private Integer createdBy;
	@NotNull
	private Integer updatedBy;
	
	private Integer role;
	private List<Integer> roles;
	
	public UserDTO(int id,String name,String email,
			String mobileNo,String designation, boolean isActive) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.mobileNo = mobileNo;
		this.isActive = isActive;
		this.designation = designation;
	}
	public UserDTO(@NotNull(message = "Name is mandatory") String name,
			@NotNull(message = "Email is mandatory") String email) {
		super();
		this.name = name;
		this.email = email;
	}
	public UserDTO(int id, @NotNull(message = "Name is mandatory") String name,
			@NotNull(message = "Email is mandatory") String email,String mobileNo) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.mobileNo=mobileNo;
	}
	
	

}
