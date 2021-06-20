package com.gatepass.main.service;

import com.gatepass.main.model.Services;

public interface MyServices {
	
	Services saveServices(Services services);
	Services updateServices(Services services);
	Services getServiceById(int id);
	
	void validateServices(boolean test,boolean screen,boolean interview);

}
