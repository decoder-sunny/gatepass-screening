package com.gatepass.main.service;

import com.gatepass.main.dto.PositionDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.miscellaneous.Pagination;
import com.gatepass.main.model.mongodb.SharedPosition;

public interface PositionService {
	
	PositionDTO savePosition(PositionDTO positionDTO);
	PositionDTO updatePosition(PositionDTO positionDTO);
	PositionDTO getPositionById(int id);
	
	PositionDTO updatePositionStatus(PositionDTO positionDTO);
	
	Pagination getPositionList(int created_by,Condition condition);
	
	void updateApplied(int position_id);
	
	MessageModel saveSharedPosition(SharedPosition sharedPosition);
	SharedPosition getSharedPosition(int id);

}
