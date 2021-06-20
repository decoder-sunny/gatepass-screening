package com.gatepass.main.serviceimp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gatepass.main.dto.DashboardDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.repository.DashboardRepository;
import com.gatepass.main.service.DashboardService;

@Service
public class DashboardServiceImp implements DashboardService{
	
	@Autowired DashboardRepository dashboard_repository;	

	@Override
	@Transactional
	public DashboardDTO getClientDashboardInfo(int org_id) {
		DashboardDTO dto=new DashboardDTO();
		try {
			List<Object[]> list=dashboard_repository.getDashboardInfo(org_id);
			if(!list.isEmpty()) {
				dto.setTotalCandidate(Integer.parseInt(list.get(0)[0].toString()));
				dto.setTestConducted(Integer.parseInt(list.get(0)[1].toString()));
				dto.setTotalPositions(Integer.parseInt(list.get(0)[2].toString()));
				dto.setTotalScreened(list.get(0)[3]!=null?Integer.parseInt(list.get(0)[3].toString()):0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DuplicateKeyException("Failed to fetch data");
		}
		return dto;
	}

	@Override
	@Transactional
	public List<DashboardDTO> getRankersDetails(int org_id,Condition condition) {
		List<DashboardDTO> mainList=new ArrayList<DashboardDTO>();
		try {
			Page<Object[]> page=dashboard_repository.getRankers(org_id, PageRequest
						.of(condition.getIndex()-1,5));
			List<Object[]> list=page.getContent();
			if(!list.isEmpty()) {
				for (Object[] objects : list) {
					DashboardDTO dto=new DashboardDTO();
					dto.setCandidateName(objects[0].toString());
					dto.setCandidateEmail(objects[1].toString());
					dto.setCandidateMarks(Integer.parseInt(objects[2].toString()));
					dto.setCandidateId(Integer.parseInt(objects[3].toString()));
					mainList.add(dto);
				}
				mainList.get(0).setTotalCandidate(Integer.parseInt(String.valueOf(page.getTotalElements())));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DuplicateKeyException("Failed to fetch data");
		}
		return mainList;
	}

	@Override
	@Transactional
	public List<DashboardDTO> getRecentTestConducted(int org_id) {
		List<DashboardDTO> mainList=new ArrayList<DashboardDTO>();
		try {
			List<Object[]> list=dashboard_repository.getTestConductedRecently(org_id, PageRequest
						.of(0,5));
			if(!list.isEmpty()) {
				for (Object[] objects : list) {
					DashboardDTO dto=new DashboardDTO();
					dto.setTestDate((Date)objects[0]);
					dto.setTotalCandidate(Integer.parseInt(objects[1].toString()));
					mainList.add(dto);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DuplicateKeyException("Failed to fetch data");
		}
		return mainList;
	}


}
