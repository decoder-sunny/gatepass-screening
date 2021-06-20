package com.gatepass.main.serviceimp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gatepass.main.dao.CandidateDAO;
import com.gatepass.main.dao.FilterCandidateResponseDAO;
import com.gatepass.main.dto.AssignDTO;
import com.gatepass.main.dto.CandidateDTO;
import com.gatepass.main.dto.ScreenResultDTO;
import com.gatepass.main.dto.TemplateDTO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.mapper.ObjectMapper;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.miscellaneous.Pagination;
import com.gatepass.main.model.AssignedTemplate;
import com.gatepass.main.model.CandidateDetails;
import com.gatepass.main.model.CandidateServices;
import com.gatepass.main.model.Code;
import com.gatepass.main.model.FilterCandidateResponse;
import com.gatepass.main.model.Position;
import com.gatepass.main.model.User;
import com.gatepass.main.model.UserOrganisationMapping;
import com.gatepass.main.model.UserRoleMapping;
import com.gatepass.main.repository.AssignTemplateRepository;
import com.gatepass.main.repository.CandidateRepository;
import com.gatepass.main.repository.CandidateServiceRepository;
import com.gatepass.main.repository.PositionRepository;
import com.gatepass.main.repository.UserOrganisationRepository;
import com.gatepass.main.repository.UserRepository;
import com.gatepass.main.repository.UserRoleMappingRepository;
import com.gatepass.main.service.CandidateOfferredServices;
import com.gatepass.main.service.CandidateService;

@Service
public class CandidateServiceImp implements CandidateService{

	@Autowired CandidateDAO candidate_dao;
	@Autowired UserRoleMappingRepository urm_repository;
	@Autowired CandidateRepository candidate_repository;
	@Autowired AssignTemplateRepository assign_repository;
	@Autowired FilterCandidateResponseDAO filter_dao;
	@Autowired PositionRepository position_repository;
	@Autowired CandidateOfferredServices candidate_services;
	@Autowired CandidateServiceRepository candidateservice_repository;
	@Autowired UserOrganisationRepository userorg_repository;
	@Autowired UserRepository user_repository;


	@Override
	@Transactional
	public CandidateDTO saveCandidate(CandidateDTO candidateDTO) {
		CandidateDetails cd=new CandidateDetails();
		User user=new User();
		try {			
			//check if exist.. If exist check tagged to specific organisation
			if(candidate_repository.existsByUser_Email(candidateDTO.getUserDTO().getEmail())) {
				cd=candidate_repository.findByUser_Email(candidateDTO.getUserDTO().getEmail());
				if(candidateservice_repository
						.existsByCandidateIdAndOrganisationId(cd.getId(), 
								candidateDTO.getUserDTO().getOrganisation().getId())) {
					throw new CustomException("Candidate Already Present");
				}
				else {
					candidate_services.saveCandidateService(new CandidateServices(cd.getId(),
							candidateDTO.getUserDTO().getOrganisation().getId(),false,false,false));
				}				
			}
			else {
				if(user_repository.existsByEmail(candidateDTO.getUserDTO().getEmail())) {
					user=user_repository.findByEmail(candidateDTO.getUserDTO().getEmail());
				}
				else {
					user.setActive(true);
					user.setCreatedBy(candidateDTO.getUserDTO().getCreatedBy());
					user.setUpdatedBy(candidateDTO.getUserDTO().getUpdatedBy());
					user.setName(candidateDTO.getUserDTO().getName());
					user.setDesignation(candidateDTO.getUserDTO().getDesignation());
					user.setEmail(candidateDTO.getUserDTO().getEmail());
					user.setMobileNo(candidateDTO.getUserDTO().getMobileNo());
					user.setCreatedOn(new Date());
					user.setUpdatedOn(new Date());
					user.setProfilePicName(candidateDTO.getUserDTO().getProfilePicName()!=null?candidateDTO.getUserDTO().getProfilePicName():null);
				}
				cd.setFunction(new Code(candidateDTO.getFunction_id()));
				cd.setGender(candidateDTO.getGender_id()!=null?new Code(candidateDTO.getGender_id()):null);
				cd.setLocation(candidateDTO.getLocation());
				cd.setExperienceMonths(candidateDTO.getExperienceMonths()!=null?candidateDTO.getExperienceMonths():0);
				cd.setExperienceYears(candidateDTO.getExperienceYears()!=null?candidateDTO.getExperienceYears():0);
				cd.setPositionApplied(candidateDTO.getPositionApplied());				
				cd.setUser(user);
				cd=candidate_repository.save(cd);
				cd.setId(cd.getId());			
				urm_repository.save(new UserRoleMapping(0,cd.getUser().getId(),3));
				candidate_services.saveCandidateService(new CandidateServices(cd.getId(),
						candidateDTO.getUserDTO().getOrganisation().getId(),false,false,false));
			}

		} 
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			e.printStackTrace();
			throw new DuplicateKeyException("Candidate already Present"  );
		}
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {e.printStackTrace();
		System.out.println(e);
		throw new DuplicateKeyException("Candidate not saved");
		}	
		return candidateDTO;
	}

	@Override
	@Transactional
	public CandidateDTO updateCandidate(CandidateDTO candidateDTO) {
		try {			
			if(candidate_repository.existsById(candidateDTO.getId())) {
				CandidateDetails cd=candidate_repository.findById(candidateDTO.getId()).get();
				cd.getUser().setName(candidateDTO.getUserDTO().getName());
				cd.getUser().setEmail(candidateDTO.getUserDTO().getEmail());
				cd.getUser().setMobileNo(candidateDTO.getUserDTO().getMobileNo());
				cd.getUser().setUpdatedOn(new Date());
				cd.getUser().setUpdatedBy(candidateDTO.getUserDTO().getUpdatedBy());
				cd.setExperienceMonths(candidateDTO.getExperienceMonths()!=null?candidateDTO.getExperienceMonths():0);
				cd.setExperienceYears(candidateDTO.getExperienceYears()!=null?candidateDTO.getExperienceYears():0);
				cd.setLocation(candidateDTO.getLocation());
				cd.setPositionApplied(candidateDTO.getPositionApplied()!=null?candidateDTO.getPositionApplied():null);
				cd.setGender(candidateDTO.getGender_id()!=null?new Code(candidateDTO.getGender_id()):null);
				cd.setFunction(candidateDTO.getFunction_id()!=null?new Code(candidateDTO.getFunction_id()):null);
				candidate_repository.save(cd);
			}
			else {
				throw new CustomException("Invalid request");
			}
			return candidateDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}	
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new DuplicateKeyException("Candidate already Present"  );
		}	
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Candidate not updated");
		}	
	}

	@Override
	@Transactional
	public CandidateDTO getCandidateById(int id) {
		try {			
			CandidateDTO candidateDTO=new CandidateDTO();
			if(candidate_repository.existsById(id)) {
				CandidateDetails cd=candidate_repository.findById(id).get();
				candidateDTO.setFunction_id(cd.getFunction()!=null?cd.getFunction().getId():null);
				candidateDTO.setFunction(cd.getFunction()!=null?cd.getFunction().getValue():null);
				candidateDTO.setId(id);
				candidateDTO.setGender_id(cd.getGender()!=null?cd.getGender().getId():null);
				candidateDTO.setLocation(cd.getLocation());
				candidateDTO.setExperienceMonths(cd.getExperienceMonths());
				candidateDTO.setExperienceYears(cd.getExperienceYears());
				candidateDTO.setPositionApplied(cd.getPositionApplied());
				UserDTO user=new UserDTO();
				user.setName(cd.getUser().getName());
				user.setDesignation(cd.getUser().getDesignation());
				user.setEmail(cd.getUser().getEmail());
				user.setMobileNo(cd.getUser().getMobileNo());
				candidateDTO.setUserDTO(user);
			}
			else {
				throw new CustomException("Invalid request");
			}
			return candidateDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}	
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Candidate not found");
		}	
	}

	@Override
	@Transactional
	public Pagination getCandidateList(Condition condition, int org_id,String position) {
		try {
			Pagination pagination=new Pagination();
			pagination.setCandidates(candidate_dao.getUserList(condition, org_id, position));
			pagination.setLength(candidate_dao.getUserListCount(condition, org_id,position));
			return pagination;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Candidates not found");
		}
	}

	@Override
	@Transactional
	public CandidateDTO getCandidateInfo(int candidate_id,int org_id ,Condition condition) {
		try {
			CandidateDTO candidateDTO=new CandidateDTO();
			CandidateDetails cd=candidate_repository.findById(candidate_id).get();
			candidateDTO.setFunction(cd.getFunction()!=null?cd.getFunction().getValue():null);
			candidateDTO.setGender(cd.getGender()!=null?cd.getGender().getValue():null);
			candidateDTO.setLocation(cd.getLocation());
			candidateDTO.setExperienceMonths(cd.getExperienceMonths());
			candidateDTO.setExperienceYears(cd.getExperienceYears());
			candidateDTO.setPositionApplied(cd.getPositionApplied());
			candidateDTO.setCv(cd.getCv());
			candidateDTO.setId(cd.getId());
			UserDTO user=new UserDTO();
			user.setName(cd.getUser().getName());
			user.setEmail(cd.getUser().getEmail());
			user.setMobileNo(cd.getUser().getMobileNo());
			candidateDTO.setUserDTO(user);
			List<AssignDTO> list=new ArrayList<>();
			Pageable pageable = PageRequest.of(condition.getIndex()-1,condition.getPagesize());
			Page<AssignedTemplate> data=assign_repository.findByCandidate_IdAndOrganisation(candidate_id,org_id,pageable);
			for (AssignedTemplate assignedTemplate : data.getContent()) {
				AssignDTO assignDTO=new AssignDTO();
				assignDTO.setTemplate(new TemplateDTO(assignedTemplate.getTemplate().getId(),
						assignedTemplate.getTemplate().getTemplateName()));
				assignDTO.setUpdatedOn(assignedTemplate.getUpdatedOn());
				assignDTO.setId(assignedTemplate.getId());
				assignDTO.setCompletionStatus(assignedTemplate.isCompletionStatus());
				assignDTO.setProgress(assignedTemplate.getCandidateResponses().size());
				list.add(assignDTO);
			}
			candidateDTO.setLength(data.getTotalElements());
			candidateDTO.setAssignDTO(list);
			List<ScreenResultDTO> screen=new ArrayList<>();
			List<FilterCandidateResponse> flist=filter_dao.getFilterCandidateResponse(org_id,candidate_id);
			for (FilterCandidateResponse filterCandidateResponse : flist) {
				Position position=position_repository.findById(filterCandidateResponse.getPositionId()).get();
				ScreenResultDTO dto=new ScreenResultDTO();
				dto.setPosition_id(position.getId());
				dto.setCreatedOn(filterCandidateResponse.getCreatedOn());
				dto.setMarks(filterCandidateResponse.getMarks());
				dto.setPositionName(position.getName());
				dto.setResponse_id(filterCandidateResponse.getId());
				screen.add(dto);
			}
			candidateDTO.setScreen(screen);
			return candidateDTO;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Candidate information not found");
		}
	}

	@Override
	@Transactional
	public Pagination getCandidateRanking(Condition condition, int position_id) {
		try {
			Pagination pagination=new Pagination();
			pagination.setCandidates(candidate_dao.getCandidateRanking(condition, position_id));
			pagination.setLength(candidate_dao.getCandidateRankingCount(condition, position_id));
			return pagination;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Candidates not found");
		}
	}

	@Override
	public MessageModel saveBulkCandidates(List<CandidateDTO> list) {
		String message="";
		int status=0;
		try {
			if(!list.isEmpty()) {
				UserOrganisationMapping uom=userorg_repository.findByUserId(list.get(0).getUserDTO().getCreatedBy());
				for (CandidateDTO candidateDTO : list) {
					try {						
						//check if exist.. If exist check tagged to specific organisation
						if(candidate_repository.existsByUser_Email(candidateDTO.getUserDTO().getEmail())) {
							System.out.println("Candidates exist in master data");
							CandidateDetails cd=candidate_repository.findByUser_Email(candidateDTO.getUserDTO().getEmail());
							if(candidateservice_repository
									.existsByCandidateIdAndOrganisationId(cd.getId(), 
											uom.getOrganisation().getId())) {
								status=401;
								message=new StringBuilder(message).append(" "+candidateDTO.getUserDTO().getName()).toString();
								System.out.println("Duplicate Candidate with organisation");
							}
							else {
								candidate_services.saveCandidateService(new CandidateServices(cd.getId(),
										uom.getOrganisation().getId(),false,false,false));
								System.out.println("Candidate with organisation mapped");
							}				
						}
						else {
							System.out.println("Candidates Saved");
							CandidateDetails cd=new CandidateDetails();
							User user=new User();

							if(user_repository.existsByEmail(candidateDTO.getUserDTO().getEmail())) {
								user=user_repository.findByEmail(candidateDTO.getUserDTO().getEmail());
							}

							else {
								user.setActive(true);
								user.setCreatedBy(candidateDTO.getUserDTO().getCreatedBy());
								user.setUpdatedBy(candidateDTO.getUserDTO().getUpdatedBy());
								user.setName(candidateDTO.getUserDTO().getName());
								user.setDesignation(candidateDTO.getUserDTO().getDesignation());
								user.setEmail(candidateDTO.getUserDTO().getEmail());
								user.setMobileNo(candidateDTO.getUserDTO().getMobileNo());
								user.setCreatedOn(new Date());
								user.setUpdatedOn(new Date());
							}

							cd.setFunction(new Code(candidateDTO.getFunction_id()));
							cd.setPositionApplied(candidateDTO.getPositionApplied());

							cd.setUser(user);
							cd=candidate_repository.save(cd);
							cd.setId(cd.getId());			
							urm_repository.save(new UserRoleMapping(0,cd.getUser().getId(),3));
							candidate_services.saveCandidateService(new CandidateServices(cd.getId(),
									uom.getOrganisation().getId(),false,false,false));

						}

					} catch (Exception e) {
						e.printStackTrace();
						throw new CustomException("Failed to save candidates");
					}
					Thread.sleep(1000);
				}
			}

		} catch (Exception e) {
			throw new CustomException("Failed to save candidates");
		}
		return new MessageModel(status, message);
	}



	@Override
	public void exportExcel(HttpServletResponse response,List<Integer> users) {
		try {
			export(response,users);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to generate file");
		}

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	public void export(HttpServletResponse response,List<Integer> userids) throws IOException {
		XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Users");	         
		Row row = sheet.createRow(0);	         
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(12);
		style.setFont(font);

		createCell(row, 0, "Salutation", style);      
		createCell(row, 1, "Name", style);       
		createCell(row, 2, "Email", style);    
		createCell(row, 3, "Mobile No", style);
		createCell(row, 4, "Designation", style);
		createCell(row, 5, "Function", style);
		createCell(row, 6, "Experience (Years)", style);
		createCell(row, 7, "Experience (Months)", style);

		List<Object[]> users=candidate_repository.getCandidates(userids);
		int rowCount=1;
		for (Object[] user : users) {
			Row irow = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(irow, columnCount++, ObjectMapper.mapObjToString(user[4]), style);
			createCell(irow, columnCount++, ObjectMapper.mapObjToString(user[0]), style);
			createCell(irow, columnCount++, ObjectMapper.mapObjToString(user[1]), style);
			createCell(irow, columnCount++, ObjectMapper.mapObjToString(user[2]), style);
			createCell(irow, columnCount++, ObjectMapper.mapObjToString(user[3]), style);
			createCell(irow, columnCount++, ObjectMapper.mapObjToString(user[5]), style);
			createCell(irow, columnCount++, ObjectMapper.mapObjToInteger(user[6]), style);
			createCell(irow, columnCount++, ObjectMapper.mapObjToInteger(user[7]), style);

		}

		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);

		outputStream.close();

	}


}
