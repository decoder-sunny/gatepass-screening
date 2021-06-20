package com.gatepass.main.dao;

import java.util.List;

public interface DashboardDAO {
	
	 List<Object[]> getClientInfo(int clinet_id);
	 List<Object[]> getTestConductedInfo(int clinet_id);

}
