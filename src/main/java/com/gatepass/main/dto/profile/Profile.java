package com.gatepass.main.dto.profile;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(indexName="profile")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile {
	
	@Id
	private Integer candidateId;
	private Integer userId;
	private String orgId;
	private String name;
	private String email;
	private String mobileNo;
	
	private String details;

	public Profile(Integer candidateId, Integer userId, String name, String email) {
		super();
		this.candidateId = candidateId;
		this.userId = userId;
		this.name = name;
		this.email = email;
	}
	
	
	
	
	

}
