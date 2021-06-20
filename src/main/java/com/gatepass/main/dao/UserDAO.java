package com.gatepass.main.dao;

import java.util.List;

import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.miscellaneous.Condition;

public interface UserDAO {
	
	public List<UserDTO> getUserList(Condition condition,int org_id);
	public int getUserListCount(Condition condition,int org_id);

}
