package com.gatepass.main.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ScreenResultDTO {
	
	private Integer position_id;
	private Integer response_id;
	private String positionName;
	private Date createdOn;
	private int marks;
	private List<String> questions;
	private List<Boolean> mustHaves;
	private List<String> expected;
	private String response;
	
	

}
