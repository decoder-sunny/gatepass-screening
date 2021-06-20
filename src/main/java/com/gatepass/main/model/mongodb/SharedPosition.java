package com.gatepass.main.model.mongodb;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection="sharedposition")
@NoArgsConstructor
public class SharedPosition {
	
	@Id
	private int id;
	private Set<Integer> users;
	
	@Transient
	private Integer requestedBy;

	public SharedPosition(int id, Set<Integer> users) {
		super();
		this.id = id;
		this.users = users;
	} 
	
	

}
