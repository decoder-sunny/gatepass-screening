package com.gatepass.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="user_role_mapping",uniqueConstraints=
@UniqueConstraint(columnNames={"UserId","RoleId"}))
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleMapping {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	
	@OneToOne
    @JoinColumn(name="UserId")	
	private User user;
	
	@OneToOne
	@JoinColumn(name="RoleId")	
	private Role role;

	public UserRoleMapping(int id, int user, int role) {
		super();
		this.id = id;
		this.user = new User(user);
		this.role = new Role(role);
	}
	
	
	

	
	
	

}
