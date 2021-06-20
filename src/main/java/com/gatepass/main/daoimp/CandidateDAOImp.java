package com.gatepass.main.daoimp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gatepass.main.dao.CandidateDAO;
import com.gatepass.main.dto.CandidateDTO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.miscellaneous.Condition;

@Repository
public class CandidateDAOImp implements CandidateDAO{

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateDTO> getUserList(Condition condition, int org_id,String position) {
		String sql="select cd.Id,u.Name,u.Email,cs.ScreenService,cs.TestService,cs.InterviewService from \r\n" + 
				"candidateservices cs inner join candidatedetails cd on cs.CandidateId=cd.Id\r\n" + 
				"left join user u on cd.User=u.Id\r\n" + 
				"left join filtercandidateresponse fcr on cd.Id=fcr.CandidateId\r\n" + 
				"left join position p on fcr.PositionId=p.Id\r\n" + 
				"where cs.OrganisationId="+org_id+" ";
		if(condition.getText().length() > 2) {
			sql=sql+" and u.Name like '"+condition.getText()+"%' ";
		}
		if(position.length() > 2) {
			sql=sql+" and p.Name like '"+position+"%' ";
		}
		sql=sql+ " group by cd.Id order by cd.Id desc";		
		List<Object[]> list=entityManager
				.createNativeQuery(sql)
				.setMaxResults(condition.getPagesize())
				.setFirstResult((condition.getIndex()-1)*condition.getPagesize())
				.getResultList();

		List<CandidateDTO> clist=new ArrayList<>();
		for (Object[] objects : list) {
			CandidateDTO cd=new CandidateDTO();
			cd.setId(Integer.parseInt(objects[0].toString()));
			cd.setUserDTO(new UserDTO(objects[1].toString(),objects[2].toString()));
			cd.setIsGatepass(Boolean.valueOf(objects[4].toString()));
			cd.setIsScreening(Boolean.valueOf(objects[3].toString()));
			clist.add(cd);
		}
		return clist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getUserListCount(Condition condition, int org_id,String position) {
		String sql="select count(cd.Id) from \r\n" + 
				"candidateservices cs inner join candidatedetails cd on cs.CandidateId=cd.Id\r\n" + 
				"left join user u on cd.User=u.Id\r\n" + 
				"left join filtercandidateresponse fcr on cd.Id=fcr.CandidateId\r\n" + 
				"left join position p on fcr.PositionId=p.Id\r\n" + 
				"where cs.OrganisationId="+org_id+" ";
		if(condition.getText().length() > 2) {
			sql=sql+" and u.Name like '"+condition.getText()+"%' ";
		}
		if(position.length() > 2) {
			sql=sql+" and p.Name like '"+position+"%' ";
		}	
		List<Object[]> list=entityManager.createNativeQuery(sql).getResultList();
		if(list.isEmpty())
			return 0;
		else
			return Integer.parseInt(String.valueOf(list.get(0)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateDTO> getCandidateRanking(Condition condition, int position_id) {
		String sql="select cd.Id,u.Name,u.Email,fcr.Marks from\r\n" + 
				"candidatedetails cd \r\n" + 
				"left join filtercandidateresponse fcr on cd.Id=fcr.CandidateId\r\n" + 
				"left join user u on cd.User=u.Id\r\n" + 
				"left join position p on fcr.PositionId=p.Id\r\n" + 
				"where p.Id=:position_id order by fcr.Marks desc";
		List<Object[]> list=entityManager
				.createNativeQuery(sql).setParameter("position_id", position_id)
				.setMaxResults(condition.getPagesize())
				.setFirstResult((condition.getIndex()-1)*condition.getPagesize())
				.getResultList();

		List<CandidateDTO> clist=new ArrayList<>();
		for (Object[] objects : list) {
			CandidateDTO cd=new CandidateDTO();
			cd.setId(Integer.parseInt(objects[0].toString()));
			cd.setUserDTO(new UserDTO(objects[1].toString(),objects[2].toString()));
			cd.setMarks(Integer.parseInt(objects[3].toString()));
			clist.add(cd);
		}
		return clist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getCandidateRankingCount(Condition condition, int position_id) {
		String sql="select count(cd.Id) from\r\n" + 
				"candidatedetails cd \r\n" + 
				"left join filtercandidateresponse fcr on cd.Id=fcr.CandidateId\r\n" + 
				"left join user u on cd.User=u.Id\r\n" + 
				"left join position p on fcr.PositionId=p.Id\r\n" + 
				"where p.Id=:position_id";
		List<Object[]> list=entityManager.createNativeQuery(sql)
				.setParameter("position_id", position_id)
				.getResultList();
		if(list.isEmpty())
			return 0;
		else
			return Integer.parseInt(String.valueOf(list.get(0)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateDTO> getRecentCandidates() {
		String sql="select u.Id as userId,cd.Id as candidateId,u.Name,u.Email,group_concat(cs.OrganisationId),u.mobileNo from candidatedetails cd\r\n" + 
				"left join user u on cd.User=u.Id\r\n" + 
				"inner join candidateservices cs on cs.CandidateId=cd.Id\r\n"+
				"where u.UpdatedOn >= DATE_SUB(NOW(),INTERVAL 30 minute) group by u.Id";
		List<Object[]> list = entityManager
							.createNativeQuery(sql)
							.getResultList();

		List<CandidateDTO> clist=new ArrayList<>();
		for (Object[] objects : list) {
			CandidateDTO cd=new CandidateDTO();			
			cd.setId(Integer.parseInt(objects[1].toString()));
			cd.setUserDTO(new UserDTO(Integer.parseInt(objects[0].toString()),
					objects[2].toString(),objects[3].toString(),objects[5].toString()));
			cd.setOrganisation(objects[4].toString());
			clist.add(cd);
		}
		return clist;
	}

}
