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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="credentials",uniqueConstraints=
@UniqueConstraint(columnNames={"UserId"}))
public class Credentials {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@OneToOne
	@JoinColumn(name="UserId")
	private User user;
	
	@Column(name="Password")
	private String password;	
	
	public Credentials(int id, int user, String password) {
		super();
		this.id = id;
		this.user = new User(user);
		this.password = password;
	}
	
	
	

}
