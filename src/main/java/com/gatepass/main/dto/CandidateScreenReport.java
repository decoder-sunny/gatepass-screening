package com.gatepass.main.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CandidateScreenReport {
	
	private PositionDTO position;
	private List<CandidateFilterResponse> responses;
	private Long length;

}
