package com.gatepass.main.daoimp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gatepass.main.dao.FilterCandidateResponseDAO;
import com.gatepass.main.model.FilterCandidateResponse;

@Repository
public class FilterCandidateResponseDAOImp implements FilterCandidateResponseDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<FilterCandidateResponse> getFilterCandidateResponse(int org_id,int candidate_id) {
		List<FilterCandidateResponse> flist=new ArrayList<FilterCandidateResponse>();
		String sql="select fcr.Id,fcr.PositionId,fcr.Date,fcr.Marks from filtercandidateresponse fcr\r\n" + 
				"left join position p on fcr.PositionId=p.Id\r\n" + 
				"left join userorganisationmapping uom on p.CreatedBy=uom.UserId\r\n" + 
				"where uom.Organisation="+org_id+" and fcr.CandidateId="+candidate_id+" order by fcr.Id desc";
		List<Object[]> list=entityManager
				.createNativeQuery(sql)
				.setMaxResults(10)
				.setFirstResult(0)
				.getResultList();
		for (Object[] objects : list) {
			FilterCandidateResponse fcr=new FilterCandidateResponse();
			fcr.setId(Integer.parseInt(objects[0].toString()));
			fcr.setPositionId(Integer.parseInt(objects[1].toString()));
			fcr.setCreatedOn((Date)objects[2]);
			fcr.setMarks(Integer.parseInt(objects[3].toString()));
			flist.add(fcr);
		}
		return flist;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllCandidateResponse(int candidate_id) {
		String sql="select p.Id,p.Name,fcr.Response from filtercandidateresponse fcr\r\n" + 
				"left join position p on p.Id=fcr.PositionId\r\n" +  
				"where fcr.CandidateId=:candidate_id";
		List<Object[]> list=entityManager
							.createNativeQuery(sql)
							.setParameter("candidate_id", candidate_id)
							.getResultList();
		return list;
	}

}
