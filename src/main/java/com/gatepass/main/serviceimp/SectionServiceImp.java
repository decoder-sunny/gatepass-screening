package com.gatepass.main.serviceimp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gatepass.main.dao.SectionDAO;
import com.gatepass.main.dto.SectionDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.Pagination;
import com.gatepass.main.model.Section;
import com.gatepass.main.repository.SectionRepository;
import com.gatepass.main.service.SectionService;


@Service
public class SectionServiceImp implements SectionService{
	
	@Autowired private SectionRepository section_repository;
	@Autowired private SectionDAO section_dao;

	@Override
	@Transactional
	public SectionDTO saveSection(SectionDTO sectionDTO) {
		try {
			Section section=new Section();
			section.setSectionName(sectionDTO.getSectionName());
			section=section_repository.save(section);
			sectionDTO.setId(section.getId());
			return sectionDTO;
		} 
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Section not saved");
		}	
	}

	@Override
	@Transactional
	public SectionDTO updateSection(SectionDTO sectionDTO) {
		try {			
			if(section_repository.existsById(sectionDTO.getId())) {
				Section section=section_repository.findById(sectionDTO.getId()).get();
				section.setSectionName(sectionDTO.getSectionName());
				section_repository.save(section);
			}
			else {
				throw new CustomException("Invalid request");
			}
			return sectionDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Section not updated");
		}	
	}

	@Override
	@Transactional
	public SectionDTO getSectionById(int id) {
		try {			
			SectionDTO sectionDTO=new SectionDTO();
			if(section_repository.existsById(id)) {
				Section section=section_repository.findById(id).get();				
				sectionDTO.setId(section.getId());;
				sectionDTO.setSectionName(section.getSectionName());
			}
			else {
				throw new CustomException("Invalid request");
			}
			return sectionDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}	
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Section not found");
		}
	}

	@Override
	@Transactional
	public Pagination getSectionList(Condition condition) {
		try {
			Pagination pagination=new Pagination();
			pagination.setSections(section_dao.getSectionList(condition));
			pagination.setLength(section_dao.getSectionListCount(condition));
			return pagination;			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Sections not found");
		}
	}

}
