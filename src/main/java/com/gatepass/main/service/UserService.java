package com.gatepass.main.service;

import java.util.List;

import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.Pagination;

public interface UserService {
	
	public UserDTO saveUser(UserDTO userDTO);
	public UserDTO updateUser(UserDTO userDTO);
	public UserDTO getUserById(int id);
	
	public Pagination getUserList(Condition condition,int org_id,List<Integer> role);
	
	public List<UserDTO> getUsersByIds(List<Integer> ids);
	
	Boolean checkUserRole(UserDTO userDTO,Integer role);

}
