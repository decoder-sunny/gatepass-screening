package com.gatepass.main.service;

import java.util.List;

import com.gatepass.main.dto.AssignDTO;
import com.gatepass.main.miscellaneous.MessageModel;

public interface AssignService {
	
	MessageModel assignTemplate(List<AssignDTO> list);
	MessageModel reAssignTemplate(int assign_id);

}
