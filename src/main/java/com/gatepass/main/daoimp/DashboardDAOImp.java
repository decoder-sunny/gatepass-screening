package com.gatepass.main.daoimp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gatepass.main.dao.DashboardDAO;

@Repository
public class DashboardDAOImp implements DashboardDAO{
	
	@PersistenceContext
	private EntityManager entityManager;


	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getClientInfo(int client_id) {
		String sql="select \r\n" + 
				"(select count(cd.Id) from candidatedetails cd left join user u \r\n" + 
				"on cd.User=u.Id where u.Organisation="+client_id+") as total_candidates,\r\n" + 
				"(select count(ass.Id) from assignedtemplate ass left join candidatedetails cd\r\n" + 
				"on cd.Id=ass.CandidateId left join user u on cd.User=u.Id\r\n" + 
				"where u.Organisation="+client_id+" and ass.CompletionStatus=true) as total_test,\r\n" + 
				"(select count(t.Id) from templates t) as total_templates\r\n" + 
				"from dual";		
		return entityManager.createNativeQuery(sql).getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTestConductedInfo(int clinet_id) {
		String sql="select cr.Date,count(distinct cr.AssignId) from candidateresponse cr \r\n" + 
				"left join assignedtemplate at on cr.AssignId=at.Id\r\n" + 
				"left join candidatedetails cd on at.CandidateId=cd.Id\r\n" + 
				"left join user u on cd.User=u.Id\r\n" + 
				"where u.Organisation="+clinet_id+" and at.CompletionStatus=true\r\n" + 
				"group by cr.AssignId order by cr.AssignId desc;";		
		return entityManager.createNativeQuery(sql).setMaxResults(5)
							.setFirstResult(0).getResultList();
	}

}
