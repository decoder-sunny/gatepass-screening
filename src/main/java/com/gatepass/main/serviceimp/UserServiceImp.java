package com.gatepass.main.serviceimp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gatepass.main.dao.UserDAO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.mapper.ObjectMapper;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.Pagination;
import com.gatepass.main.model.Organisation;
import com.gatepass.main.model.User;
import com.gatepass.main.model.UserOrganisationMapping;
import com.gatepass.main.model.UserRoleMapping;
import com.gatepass.main.repository.UserOrganisationRepository;
import com.gatepass.main.repository.UserRepository;
import com.gatepass.main.repository.UserRoleMappingRepository;
import com.gatepass.main.service.UserService;

@Service
public class UserServiceImp implements UserService{

	@Autowired UserRepository user_repository;
	@Autowired UserRoleMappingRepository urm_repository;
	@Autowired UserDAO user_dao;
	@Autowired UserOrganisationRepository userorganisation_repository;	
	
	@Override
	@Transactional
	public UserDTO saveUser(UserDTO userDTO) {
		try {
			if(!checkUserRole(userDTO, userDTO.getRole())) {
				User user=new User();
				user.setActive(false);
				user.setCreatedBy(userDTO.getCreatedBy());
				user.setUpdatedBy(userDTO.getUpdatedBy());
				user.setName(userDTO.getName());
				user.setDesignation(userDTO.getDesignation());
				user.setEmail(userDTO.getEmail());
				user.setMobileNo(userDTO.getMobileNo());
				user.setCreatedOn(new Date());
				user.setUpdatedOn(new Date());
				user.setProfilePicName(userDTO.getProfilePicName());
				user=user_repository.save(user);
				userDTO.setId(user.getId());			
				urm_repository.save(new UserRoleMapping(0,user.getId(),userDTO.getRole()));
				userorganisation_repository.save(new UserOrganisationMapping(user.getId(),
						new Organisation(userDTO.getOrganisation().getId())));				
			}	
			return userDTO;
		} 
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new DuplicateKeyException("User already Present"  );
		}	
		catch (Exception e) {e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("User not saved");
		}	
	}

	@Override
	@Transactional
	public UserDTO updateUser(UserDTO userDTO) {
		try {			
			if(user_repository.existsById(userDTO.getId())) {
				User user=user_repository.findById(userDTO.getId()).get();
				user.setName(userDTO.getName());
				user.setEmail(userDTO.getEmail());
				user.setMobileNo(userDTO.getMobileNo());
				user.setActive(userDTO.isActive());
				user.setDesignation(userDTO.getDesignation());
				user.setProfilePicName(userDTO.getProfilePicName());
				user.setUpdatedOn(new Date());
				user.setUpdatedBy(userDTO.getUpdatedBy());
				user_repository.save(user);
			}
			else {
				throw new CustomException("Invalid request");
			}
			return userDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}	
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new DuplicateKeyException("User already Present"  );
		}	
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("User not updated");
		}	
	}

	@Override
	@Transactional
	public UserDTO getUserById(int id) {
		try {			
			UserDTO userDTO=new UserDTO();
			if(user_repository.existsById(id)) {
				List<UserRoleMapping> urm=urm_repository.findByUser_Id(id);
				userDTO.setId(urm.get(0).getUser().getId());
				userDTO.setName(urm.get(0).getUser().getName());
				userDTO.setEmail(urm.get(0).getUser().getEmail());
				userDTO.setMobileNo(urm.get(0).getUser().getMobileNo());
				userDTO.setActive(urm.get(0).getUser().isActive());
				userDTO.setDesignation(urm.get(0).getUser().getDesignation());
				userDTO.setProfilePicName(urm.get(0).getUser().getProfilePicName());
				if(userorganisation_repository.existsByUserId(id)) {
					UserOrganisationMapping uom=userorganisation_repository.findByUserId(id);
					userDTO.setOrganisation(new Organisation(uom.getOrganisation().getId(),
							uom.getOrganisation().getOrganisationName()));
				}				
				userDTO.setRoles(urm.stream().map(x-> x.getRole().getId()).collect(Collectors.toList()));
			}
			else {
				throw new CustomException("Invalid request");
			}
			return userDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}	
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("User not found");
		}	
	}

	@Override
	@Transactional
	public Pagination getUserList(Condition condition,int org_id,List<Integer> role) {
		try {
			Pagination pagination=new Pagination();
			pagination.setUsers(user_dao.getUserList(condition, org_id));
			pagination.setLength(user_dao.getUserListCount(condition, org_id));
			return pagination;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DuplicateKeyException("Users not found");
		}
	}

	@Override
	@Transactional
	public List<UserDTO> getUsersByIds(List<Integer> ids) {
		List<UserDTO> users=new ArrayList<UserDTO>();
		try {
			List<Object[]> list=user_repository.getUsers(ids);
			for (Object[] objects : list) {
				UserDTO dto=new UserDTO();
				dto.setId(ObjectMapper.mapObjToInteger(objects[0]));
				dto.setName(ObjectMapper.mapObjToString(objects[1]));
				dto.setEmail(ObjectMapper.mapObjToString(objects[2]));
				users.add(dto);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DuplicateKeyException("Users not found");
		}
		return users;
	}

	@Override
	@Transactional
	public Boolean checkUserRole(UserDTO userDTO, Integer role) {
		boolean status=false;
		try {
			if(urm_repository.existsByUser_Email(userDTO.getEmail())) {
				if(urm_repository.existsByUser_EmailAndRole_Id(userDTO.getEmail(), role)) {
					throw new CustomException("Duplicate User");
				}
				else {
					List<UserRoleMapping> urlist=urm_repository.findByUser_Email(userDTO.getEmail());
					userorganisation_repository.save(new UserOrganisationMapping(urlist.get(0).getUser().getId(),
							new Organisation(userDTO.getOrganisation().getId())));				
					urm_repository.save(new UserRoleMapping(0,urlist.get(0).getId(),role));
					status=true;
				}
			}			
			return status;
		} 
		catch (CustomException e) {
			e.printStackTrace();
			throw new DuplicateKeyException(e.getMessage());
		}		
		catch (Exception e) {
			e.printStackTrace();
			throw new DuplicateKeyException("User not Saved");
		}
	}
	
	

}
