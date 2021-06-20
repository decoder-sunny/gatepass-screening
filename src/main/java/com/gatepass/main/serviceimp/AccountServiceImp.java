package com.gatepass.main.serviceimp;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gatepass.main.dto.CredentialsDTO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.mapper.CandidateMapper;
import com.gatepass.main.mapper.UserMapper;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.Encryption;
import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.miscellaneous.Utility;
import com.gatepass.main.model.AssignedTemplate;
import com.gatepass.main.model.Credentials;
import com.gatepass.main.model.JwtResponse;
import com.gatepass.main.model.Organisation;
import com.gatepass.main.model.Role_Entitlement_Mapping;
import com.gatepass.main.model.User;
import com.gatepass.main.model.UserOrganisationMapping;
import com.gatepass.main.repository.AssignTemplateRepository;
import com.gatepass.main.repository.CredentialsRepository;
import com.gatepass.main.repository.OrganisationRepository;
import com.gatepass.main.repository.RoleEntitlementRepository;
import com.gatepass.main.repository.UserOrganisationRepository;
import com.gatepass.main.repository.UserRepository;
import com.gatepass.main.security.JwtTokenUtil;
import com.gatepass.main.service.AccountService;
import com.gatepass.main.service.FileService;
import com.gatepass.main.service.MailService;
import com.gatepass.main.service.UserDetailService;
import com.google.gson.Gson;

@Service
public class AccountServiceImp implements AccountService{

	@Autowired private CredentialsRepository credentials_repository;
	@Autowired private UserDetailService partner_detial_service;
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private RoleEntitlementRepository rolentitlement_repository;
	@Autowired private UserRepository user_repository;
	@Autowired private PasswordEncoder bcryptEncoder;
	@Autowired private AssignTemplateRepository assign_repository;
	@Autowired private UserOrganisationRepository userorganisation_repository;
	@Autowired private OrganisationRepository organisation_repository;
	@Autowired private MailService mail_service;
	@Autowired private FileService file_service;

	@Override
	@Transactional
	public JwtResponse userLogin(CredentialsDTO credentials) {
		try {
			Optional.ofNullable(credentials).orElseThrow(() -> new CustomException("Invalid Request"));
			authenticate(credentials.getUsername(), credentials.getPassword());		
			final UserDetails userDetails = partner_detial_service.getUserDataForToken(credentials.getUsername(),false);		
			final String[] token = jwtTokenUtil.generateToken(userDetails);
			if(!Arrays.asList("ROLE_MASTER","ROLE_CLIENT").contains(token[1])) {
				throw new CustomException("Invalid Request");
			}
			List<Role_Entitlement_Mapping> entitlements=rolentitlement_repository.findByRole_Role(token[1]);
			UserDTO userDTO=UserMapper.userMapper(user_repository.findById(jwtTokenUtil.getUserId(userDetails)).get());
			if(userorganisation_repository.existsByUserId(userDTO.getId())) {
				UserOrganisationMapping uom=userorganisation_repository.findByUserId(userDTO.getId());
				userDTO.setOrganisation(new Organisation(uom.getOrganisation().getId(), uom.getOrganisation().getOrganisationName()));
			}
			return new JwtResponse(token[0], entitlements,userDTO);
		}
		catch (InternalAuthenticationServiceException e) {
			throw new CustomException(e.getMessage());
		}
		catch (BadCredentialsException | DuplicateKeyException e) {
			throw new CustomException("Invalid Credentials");
		}
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new DisabledException("Login Failed");
		}
	}

	@Override
	@Transactional
	public JwtResponse candidateLogin(CredentialsDTO credentials) {
		try {
			Optional.ofNullable(credentials).orElseThrow(() -> new CustomException("Invalid Request"));
			if(assign_repository.existsById(credentials.getId())) {
				AssignedTemplate assignedTemplate=assign_repository.findById(credentials.getId()).get();
				if(!assignedTemplate.isCompletionStatus()) {
					if(Utility.timeDifference(assignedTemplate.getUpdatedOn())) {
						authenticate(credentials.getUsername(), credentials.getPassword());		
						final UserDetails userDetails = partner_detial_service.getUserDataForToken(credentials.getUsername(),true);
						if(assignedTemplate.getCandidate().getUser().getId()!=jwtTokenUtil.getUserId(userDetails)) {
							throw new CustomException("Invalid Request");
						}
						final String[] token = jwtTokenUtil.generateToken(userDetails);
						Organisation org=organisation_repository.findById(assignedTemplate.getOrganisation()).get();
						return new JwtResponse(token[0],CandidateMapper
								.candidateMapper(assignedTemplate.getCandidate(),
										assignedTemplate.getTemplate().getId(),
										org));
					}
					else {
						throw new CustomException("Link expired");
					}
				}
				else {
					throw new CustomException("Test already submitted");
				}
			}
			return null;
		}
		catch (InternalAuthenticationServiceException e) {
			throw new CustomException(e.getMessage());
		}
		catch (BadCredentialsException | DuplicateKeyException e) {
			throw new CustomException("Invalid Credentials");
		}
		catch (CustomException e) {
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new DisabledException("Login Failed");
		}
	}

	@Override
	@Transactional
	public MessageModel resetPassword(String[] data) {
		try {			
			String password=Encryption.randomPassword();
			Credentials cred=Optional.ofNullable(credentials_repository.findByUser_Email(data[0])).
					orElseThrow(() -> new CustomException("Username Not Found"));
			if(!cred.getUser().isActive()) {
				throw new CustomException("Account Inactive");
			}
			cred.setPassword(bcryptEncoder.encode(password));
			credentials_repository.save(cred);	
			mail_service.sendResetPasswordmail(cred.getUser().getEmail(), 
									password,cred.getUser().getName());
			Thread.sleep(300);
			return new MessageModel(200, "Kindly Check Your Mail");			
		} 
		catch (ConstraintViolationException e) {
			System.out.println(e);
			throw new DuplicateKeyException("Duplicate User ");
		}	
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Failed To reset user credentials");
		}
	}

	@Override
	@Transactional
	public MessageModel activateUser(UserDTO userDTO) {
		try {
			String message="";
			if(credentials_repository.existsByUser_Email(userDTO.getEmail())) {
				Credentials cred=credentials_repository.findByUser_Email(userDTO.getEmail());
				if(cred.getUser().isActive()) {
					cred.getUser().setActive(false);
					message="User is De-Activated";
				}
				else {
					cred.getUser().setActive(true);
					message="User is Activated";
				}
				user_repository.save(cred.getUser());
				return new MessageModel(200, message);
			}
			else {
				Credentials credentials=new Credentials();
				User userObj=user_repository.findById(userDTO.getId()).get();
				userObj.setActive(true);
				String password=Encryption.randomPassword();
				credentials.setPassword(bcryptEncoder.encode(password));
				credentials.setUser(new User(userObj.getId()));
				credentials_repository.save(credentials);
				user_repository.save(userObj);
				message="User is Activated";
				mail_service.activationMail(userDTO.getEmail(), password,userObj.getName());
				return new MessageModel(200, message);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new CustomException("Failed To change status");
		}
	}

	@Override
	@Transactional
	public MessageModel updatePassword(String[] data) {
		try {
			Credentials credentials= Optional.ofNullable(credentials_repository.findByUser_Email(data[0]))
					.orElseThrow(() -> new DisabledException("Username Not Found"));
			if(bcryptEncoder.matches(data[1], credentials.getPassword()) && credentials.getUser().isActive()) {
				credentials.setPassword(bcryptEncoder.encode(data[2]));
				credentials_repository.save(credentials);
				return new MessageModel(200, "Password Updated");
			}
			throw new CustomException("Wrong curent password");		
		}
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Failed To update password");
		}
	}

	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));	
	}

	@Override
	@Transactional
	public UserDTO saveUserProfile(String information, MultipartFile image) {
		try {
			UserDTO userDTO=new Gson().fromJson(information, UserDTO.class);
			if(user_repository.existsById(userDTO.getId())) {
				User user=user_repository.findById(userDTO.getId()).get();							
				if(image!=null) {
					String profilePic="PR_"+new SimpleDateFormat("dd-mm-yy hh:mm:ss").format(new Date());	
					file_service.uploadFile(profilePic, image);
					user.setProfilePicName(profilePic);
				}
				user.setMobileNo(userDTO.getMobileNo());
				user.setDesignation(userDTO.getDesignation());
				user.setName(userDTO.getName());
				user.setUpdatedOn(new Date());
				user=user_repository.save(user);
				userDTO=UserMapper.userMapper(user);
				if(userorganisation_repository.existsByUserId(userDTO.getId())) {
					UserOrganisationMapping uom=userorganisation_repository.findByUserId(userDTO.getId());
					userDTO.setOrganisation(new Organisation(uom.getOrganisation().getId(), uom.getOrganisation().getOrganisationName()));
				}
				return userDTO;
			}
			else {
				throw new CustomException("Invalid request");
			}			
		} 		
		catch (CustomException e) {
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			throw new CustomException("Failed to update profile");
		}
	}

}
