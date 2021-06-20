package com.gatepass.main.serviceimp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gatepass.main.dto.PositionDTO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.miscellaneous.Pagination;
import com.gatepass.main.model.Position;
import com.gatepass.main.model.PositionQuestions;
import com.gatepass.main.model.mongodb.SharedPosition;
import com.gatepass.main.mongodb.repository.SharedPositionRepository;
import com.gatepass.main.repository.FilterCandidateResponseRepository;
import com.gatepass.main.repository.PositionQuestionRepository;
import com.gatepass.main.repository.PositionRepository;
import com.gatepass.main.service.MyServices;
import com.gatepass.main.service.PositionService;
import com.gatepass.main.service.UserService;

@Service
public class PositionServiceImp implements PositionService{
	
	@Autowired PositionRepository position_repository;
	@Autowired MyServices myservices;
	@Autowired FilterCandidateResponseRepository response_repository;
	@Autowired PositionQuestionRepository positionquestion_repository;
	@Autowired UserService user_service;
	@Autowired SharedPositionRepository sharedposition_repository;

	@Override
	@Transactional
	public PositionDTO savePosition(PositionDTO positionDTO) {
		try {
			myservices.validateServices(false,true,false);
			Position position=new Position();
			position.setActive(true);
			position.setCreatedBy(positionDTO.getCreatedBy());
			position.setUpdatedBy(positionDTO.getUpdatedBy());
			position.setName(positionDTO.getName());
			position.setCreatedOn(new Date());
			position.setUpdatedOn(new Date());
			
			position=position_repository.save(position);
			
			sharedposition_repository.save(new SharedPosition(position.getId(), 
					new HashSet<Integer>(Arrays.asList(positionDTO.getCreatedBy()))));
			
			for (PositionQuestions pq : positionDTO.getPositionQuestion()) {
				pq.setPositionId(position.getId());
				positionquestion_repository.save(pq);
			}
			
			positionDTO.setId(position.getId());
			return positionDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Position not saved");
		}	
	}

	@Override
	@Transactional
	public PositionDTO updatePosition(PositionDTO positionDTO) {
		try {	
			myservices.validateServices(false,true,false);			
			if(position_repository.existsById(positionDTO.getId())) {
				if(response_repository.existsByPositionId(positionDTO.getId())) {
					throw new CustomException("Found responses against position. Position cannot be updated");
				}
				Position position=position_repository.findById(positionDTO.getId()).get();
				if(position.getApplied()>0) {
					throw new CustomException("Position cannot be updated");
				}
				position.setUpdatedOn(new Date());
				position.setName(positionDTO.getName()!=null?positionDTO.getName():position.getName());
				position.setJdDocument(positionDTO.getJdDocument()!=null?positionDTO.getJdDocument():null);
				
				for (PositionQuestions pq : positionDTO.getPositionQuestion()) {
					if(pq.getDeleted() && pq.getId()!=0) {
						positionquestion_repository.delete(pq);
					}
					else {
						pq.setPositionId(position.getId());	
						positionquestion_repository.save(pq);
					}
				}
				position.setActive(positionDTO.getIsActive()!=null?positionDTO.getIsActive():position.isActive());
				position.setUpdatedBy(positionDTO.getUpdatedBy());
				position_repository.save(position);
			}
			else {
				throw new CustomException("Invalid request");
			}
			return positionDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new CustomException("Position not updated");
		}
	}	
	

	@Override
	public PositionDTO updatePositionStatus(PositionDTO positionDTO) {
		try {			
			if(position_repository.existsById(positionDTO.getId())) {
				Position position=position_repository.findById(positionDTO.getId()).get();
				
				position.setUpdatedOn(new Date());
				position.setActive(positionDTO.getIsActive());
				position.setUpdatedBy(positionDTO.getUpdatedBy());
				
				position_repository.save(position);
			}
			else {
				throw new CustomException("Invalid request");
			}
			return positionDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Position Status not updated");
		}
	}

	@Override
	@Transactional
	public PositionDTO getPositionById(int id) {
		try {			
			PositionDTO positionDTO=new PositionDTO();
			if(position_repository.existsById(id)) {
				Position position=position_repository.findById(id).get();				
				positionDTO.setName(position.getName());
				positionDTO.setPositionQuestion(positionquestion_repository.findAllByPositionIdOrderByIsMustHaveDesc(id));
				for (PositionQuestions iterable_element : positionDTO.getPositionQuestion()) {
					iterable_element.setDeleted(false);
				}
				positionDTO.setCreatedOn(position.getCreatedOn());
				positionDTO.setApplied(position.getApplied());
				positionDTO.setCompleted(position.getCompleted());
				positionDTO.setId(id);
			}
			else {
				throw new CustomException("Invalid request");
			}
			return positionDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}	
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Position not found");
		}
	}

	@Override
	@Transactional
	public Pagination getPositionList(int created_by, Condition condition) {
		try {
			Pagination pagination=new Pagination(); 
			Pageable pageable = PageRequest.of(condition.getIndex()-1,condition.getPagesize());
			
			Page<SharedPosition> data=sharedposition_repository
					.findAllByUsersInOrderByIdDesc(Arrays.asList(created_by), pageable);
			if(data.hasContent()) {
				List<Position> positionList=position_repository
						.findAllByIdIn(data.getContent()
										.stream()
										.map(x-> x.getId()).collect(Collectors.toList()));
				
				List<UserDTO> users=user_service.getUsersByIds(positionList
									.stream()
									.map(x-> x.getCreatedBy())
									.collect(Collectors.toList()));
				
				List<PositionDTO> positionDTOs=new ArrayList<PositionDTO>();
				for (Position position : positionList) {
					PositionDTO dto=new PositionDTO();
					dto.setApplied(position.getApplied());
					dto.setCompleted(position.getCompleted());
					dto.setName(position.getName());
					dto.setIsActive(position.isActive());
					dto.setId(position.getId());
					dto.setCreatedOn(position.getCreatedOn());
					dto.setUserName(users.stream().filter(x-> x.getId()==position.getCreatedBy())
							.findFirst().get().getName());
					positionDTOs.add(dto);
				}
				pagination.setPositions(positionDTOs);			
				pagination.setLength((int)data.getTotalElements());
				return pagination;	
			}			
			
			return null;	
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Positions not found");
		}
	}

	@Override
	@Transactional
	public void updateApplied(int position_id) {
		try {			
			if(position_repository.existsById(position_id)) {
				Position position=position_repository.findById(position_id).get();
				position.setApplied(position.getApplied()+1);
				position_repository.save(position);
			}
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Invalid Request");
		}		
	}

	@Override
	@Transactional
	public MessageModel saveSharedPosition(SharedPosition sharedPosition) {
		try {
			if(!sharedPosition.getUsers().isEmpty()) {
				
				Position position=position_repository.findById(sharedPosition.getId()).get();
				if(position.getCreatedBy()==sharedPosition.getRequestedBy()) {
					sharedposition_repository.save(sharedPosition);
					return new MessageModel(200, "Position shared successfully");
				}
				else {
					throw new CustomException("You cannot share this position");
				}
				
			}
			return null;
		} 
		 catch (CustomException e) {
				throw new CustomException(e.getMessage());
			}
		catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to share position");
		}
		
	}

	@Override
	@Transactional
	public SharedPosition getSharedPosition(int id) {
		try {
			if(sharedposition_repository.existsById(id)) {
				return sharedposition_repository.findById(id).get();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to get position");
		}
	}

	
	
}
