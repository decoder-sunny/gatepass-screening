package com.gatepass.main.serviceimp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gatepass.main.dto.SubSectionDTO;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.model.Section;
import com.gatepass.main.model.SubSection;
import com.gatepass.main.repository.SubSectionRepository;
import com.gatepass.main.service.SubSectionService;

@Service
public class SubSectionServiceImp implements SubSectionService{
	
	@Autowired private SubSectionRepository subsection_repository;

	@Override
	@Transactional
	public SubSectionDTO saveSubSection(SubSectionDTO subSectionDTO) {
		try {
			SubSection subSection=new SubSection();
			subSection.setSubsectionName(subSectionDTO.getSubsectionName());
			subSection.setSection(new Section(subSectionDTO.getSectionDTO().getId()));
			subSection=subsection_repository.save(subSection);
			subSectionDTO.setId(subSection.getId());
			return subSectionDTO;
		} 
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Sub Section not saved");
		}	
	}

	@Override
	@Transactional
	public SubSectionDTO updateSubSection(SubSectionDTO sectionDTO) {
		try {			
			if(subsection_repository.existsById(sectionDTO.getId())) {
				SubSection section=subsection_repository.findById(sectionDTO.getId()).get();
				section.setSubsectionName(sectionDTO.getSubsectionName());
				subsection_repository.save(section);
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
			throw new DuplicateKeyException("Sub Section not updated");
		}	
	}

	@Override
	@Transactional
	public SubSectionDTO getSubSectionById(int id) {
		try {			
			SubSectionDTO sectionDTO=new SubSectionDTO();
			if(subsection_repository.existsById(id)) {
				SubSection section=subsection_repository.findById(id).get();				
				sectionDTO.setId(section.getId());;
				sectionDTO.setSubsectionName(section.getSubsectionName());
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
			throw new DuplicateKeyException("Sub Section not found");
		}
	}

	@Override
	@Transactional
	public List<SubSectionDTO> getSubSectionList(int section_id) {
		try {
			List<SubSectionDTO> slist=new ArrayList<>();
			List<SubSection> list=subsection_repository.findBySection_Id(section_id);
			if(!list.isEmpty()) {
				return list.stream().map(x-> 
				new SubSectionDTO(x.getId(),x.getSubsectionName()))
				.collect(Collectors.toList());
			}
			return slist;			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Sub Sections not found");
		}
	}

}
