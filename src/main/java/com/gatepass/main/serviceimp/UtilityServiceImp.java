package com.gatepass.main.serviceimp;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gatepass.main.dao.UtilityDAO;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.model.Code;
import com.gatepass.main.model.Insights;
import com.gatepass.main.repository.CodeRepository;
import com.gatepass.main.repository.InsightsRepository;
import com.gatepass.main.service.UtilityService;

@Service
public class UtilityServiceImp implements UtilityService{
	
	@Autowired CodeRepository code_repository;
	@Autowired InsightsRepository insights_repository;
	@Autowired UtilityDAO utility_dao;

	@Override
	@Transactional
	public List<Code> getCodeValues(String text, String type) {
		try {
			return utility_dao.getCodeData(text, type);			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Data not found");
		}
	}

	@Override
	@Transactional
	public Insights getInsight(int id) {
		try {
			return insights_repository.findById(id).get();	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Data not found");
		}
	}	

	@Override
	@Transactional
	public Code saveCodeValue(Code code) {
		try {
			code=code_repository.save(code);
			return code;	
		} 
		catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new CustomException("Value already present"  );
		}	
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Data not saved");
		}
	}

	

}
