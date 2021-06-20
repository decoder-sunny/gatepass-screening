package com.gatepass.main.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gatepass.main.miscellaneous.ReportSupport;
import com.gatepass.main.model.ProctorInfo;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ReportDTO {
	
	private CandidateDTO candidateDTO;
	private List<ReportSupport> detail;
	private ProctorInfo proctorInfo;
}
