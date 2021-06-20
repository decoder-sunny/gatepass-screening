package com.gatepass.main.mapper;

import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.model.User;

public class UserMapper {
	
	public static UserDTO userMapper(User user) {
		UserDTO userDTO=new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setDesignation(user.getDesignation());
		userDTO.setEmail(user.getEmail());
		userDTO.setMobileNo(user.getMobileNo());
		userDTO.setProfilePicName(user.getProfilePicName());
		return userDTO;
	}

}
