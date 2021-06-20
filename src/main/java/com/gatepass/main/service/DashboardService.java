package com.gatepass.main.service;

import java.util.List;

import com.gatepass.main.dto.DashboardDTO;
import com.gatepass.main.miscellaneous.Condition;

public interface DashboardService {
	
	
	DashboardDTO getClientDashboardInfo(int org_id);
	
	List<DashboardDTO> getRankersDetails(int org_id,Condition condition);
	
	List<DashboardDTO> getRecentTestConducted(int org_id);
	
}
