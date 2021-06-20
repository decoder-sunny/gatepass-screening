package com.gatepass.main.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.multipart.MultipartFile;

import com.gatepass.main.dto.CredentialsDTO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.model.JwtResponse;
import com.gatepass.main.miscellaneous.MessageModel;

public interface AccountService {

	JwtResponse userLogin(CredentialsDTO credentials);
	JwtResponse candidateLogin(CredentialsDTO credentials);


	@Caching(evict = {
			@CacheEvict(value = "getCredentialByEmail", key = "#data[0]")})
	MessageModel resetPassword(String data[]);

	MessageModel activateUser(UserDTO userDTO);

	@Caching(evict = {
			@CacheEvict(value = "getCredentialByEmail", key = "#data[0]")})
	MessageModel updatePassword(String data[]);

	UserDTO saveUserProfile(String information,MultipartFile image);

}
