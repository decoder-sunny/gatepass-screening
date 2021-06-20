package com.gatepass.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.model.Credentials;
import com.gatepass.main.model.UserRoleMapping;
import com.gatepass.main.repository.CredentialsRepository;
import com.gatepass.main.repository.UserRoleMappingRepository;


@Service
@Transactional
public class UserDetailService implements UserDetailsService{
	
	@Autowired  private CredentialsRepository credential_repository;
	@Autowired  private UserRoleMappingRepository urm_repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String password=null;		
		Credentials credentials= Optional.ofNullable(credential_repository.findByUser_Email(username)).
				orElseThrow(() -> new CustomException("Username Not Found"));
		if(!credentials.getUser().isActive()) {
			throw new CustomException("Status Disabled For " + username);
		}		
		password=credentials.getPassword();
		return new org.springframework.security.core.userdetails.User(username,password,new ArrayList<GrantedAuthority>());
	}
	
	public UserDetails getUserDataForToken(String username,boolean isCandidate) {
		UserDetails userDetails=loadUserByUsername(username);
		List<GrantedAuthority> authorityList = new ArrayList<>();
		List<UserRoleMapping> user_role= Optional.ofNullable(urm_repository.findByUser_Email(username)).
				orElseThrow(() -> new CustomException("Role Not Defined"));
		String role="";
		List<Integer> roles=user_role.stream()
									 .map(x-> x.getRole().getId())
									 .collect(Collectors.toList());
		if(isCandidate) {
			if(roles.contains(3)) {
				role="ROLE_CANDIDATE";
			}
		}
		else {
			role=user_role.stream().findFirst().get().getRole().getRole();
		}
		authorityList.add(new SimpleGrantedAuthority(role));
		authorityList.add(new SimpleGrantedAuthority(String.valueOf(user_role.get(0).getUser().getId())));
		return new org.springframework.security.core.userdetails.User(username,userDetails.getPassword(),authorityList);
	}
}
