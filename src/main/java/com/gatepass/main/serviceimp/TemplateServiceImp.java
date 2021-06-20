package com.gatepass.main.serviceimp;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gatepass.main.dao.TemplateDAO;
import com.gatepass.main.dto.TemplateDTO;
import com.gatepass.main.dto.TemplateUtility;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.Pagination;
import com.gatepass.main.model.Questions;
import com.gatepass.main.model.TemplateSet;
import com.gatepass.main.model.Templates;
import com.gatepass.main.pdf.TemplatePDF;
import com.gatepass.main.repository.TemplateRepository;
import com.gatepass.main.repository.TemplatesetRepository;
import com.gatepass.main.service.TemplateService;

@Service
public class TemplateServiceImp implements TemplateService{
	
	@Autowired TemplateRepository template_repository;
	@Autowired TemplatesetRepository templateset_repository;
	@Autowired TemplateDAO template_dao;
	
	@Value("${server_link}")
	private String  url;

	@Override
	@Transactional
	public TemplateDTO saveTemplate(TemplateDTO dto) {
		try {
			Templates templates=new Templates();
			templates.setCreatedBy(dto.getCreatedBy());
			templates.setUpdatedBy(dto.getUpdatedBy());
			templates.setCreatedOn(new Date());
			templates.setUpdatedOn(new Date());
			templates.setTemplateName(dto.getName());
			templates=template_repository.save(templates);
			dto.setId(templates.getId());
			if(dto.getQuestions()!=null && !dto.getQuestions().isEmpty()) {				
				List<TemplateSet> list=new ArrayList<TemplateSet>();
				for (Integer iterable_element : dto.getQuestions()) {
					list.add(new TemplateSet(0,templates.getId(),iterable_element));
				}
				templateset_repository.saveAll(list);
			}
			return dto;
		} 
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new DuplicateKeyException("Duplicate Data"  );
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new CustomException("Template not saved");
		}	
	}

	@Override
	@Transactional
	public Pagination getTemplates(List<Integer> createdBy, Condition condition) {
		try {
			Pagination pagination=new Pagination(); 
			Pageable pageable = PageRequest.of(condition.getIndex()-1,condition.getPagesize());
			if(!createdBy.contains(1)) {
				createdBy.add(1);
			}
			Page<Templates> data=template_repository.findAllByCreatedByInOrderByUpdatedOnDesc(createdBy,pageable);
			pagination.setTemplates(data.getContent().stream()
					.map(x -> new TemplateDTO(x.getId(),x.getTemplateName()))
					.collect(Collectors.toList()));
			pagination.setLength((int)data.getTotalElements());
			return pagination;
			
		} 
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Templates not found");
		}	
	}

	@Override
	@Transactional
	public TemplateDTO updateTemplate(TemplateDTO dto) {
		try {			
			if(template_repository.existsById(dto.getId())) {
				Templates template=template_repository.findById(dto.getId()).get();
				template.setTemplateName(dto.getName());
				template.setUpdatedOn(new Date());
				template.setUpdatedBy(dto.getUpdatedBy());
				template_repository.save(template);
				if(dto.getQuestions()!=null && !dto.getQuestions().isEmpty()) {				
					List<TemplateSet> list=new ArrayList<TemplateSet>();
					for (Integer iterable_element : dto.getQuestions()) {
						list.add(new TemplateSet(0,dto.getId(),iterable_element));
					}
					templateset_repository.saveAll(list);
				}
			}
			else {
				throw new CustomException("Invalid request");
			}
			return dto;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}	
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new DuplicateKeyException("Duplicate Data"  );
		}	
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Template Updated");
		}	
	}

	@Override
	@Transactional
	public TemplateDTO getTemplateById(int id) {
		try {			
			TemplateDTO templateDTO=new TemplateDTO();
			if(template_repository.existsById(id)) {
				Templates templates=template_repository.findById(id).get();	
				templateDTO.setId(id);
				templateDTO.setName(templates.getTemplateName());
				templateDTO.setCreatedBy(templates.getCreatedBy());
			}
			else {
				throw new CustomException("Invalid request");
			}
			return templateDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}	
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Template not found");
		}
	}

	@Override
	@Transactional
	public List<TemplateUtility> getTemplateDistribution(int template_id) {
		try {			
			return template_dao.getTemplateDistribution(template_id);
		} 
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Data not found");
		}
	}

	@Override
	@Transactional
	public ByteArrayInputStream generateTemplatePDF(int id) {
		try {		
			if(template_repository.existsById(id)) {
				Templates templates=template_repository.findById(id).get();
				List<Questions> list=template_dao.getTestQuestions(id,0);
				return new TemplatePDF().generatePDFReport(list, templates.getTemplateName(),url);
			}
			else {
				throw new CustomException("Invalid request");
			}			
		} 
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Failed to generate template");
		}
	}

}
