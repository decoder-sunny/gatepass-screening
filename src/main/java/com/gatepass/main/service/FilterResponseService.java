package com.gatepass.main.service;

import javax.servlet.http.HttpServletResponse;

import com.gatepass.main.dto.ScreenResultDTO;

public interface FilterResponseService {
	
	ScreenResultDTO getScreenResponse(int id);
	
	void exportScreeningExcel(HttpServletResponse response,int position_id);

}
