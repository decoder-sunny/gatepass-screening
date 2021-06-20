package com.gatepass.main.daoimp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.gatepass.main.dao.TemplateDAO;
import com.gatepass.main.dto.InstructionDTO;
import com.gatepass.main.dto.TemplateUtility;
import com.gatepass.main.model.Questions;
import com.gatepass.main.model.TemplateSet;

@Repository
public class TemplateDAOImp implements TemplateDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TemplateUtility> getTemplateDistribution(int template_id) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TemplateUtility> criteriaQuery = criteriaBuilder.createQuery(TemplateUtility.class);
		Root<TemplateSet> pRoot = criteriaQuery.from(TemplateSet.class);	
		criteriaQuery.multiselect(pRoot.get("question").get("subsection").get("section").get("sectionName"),
				criteriaBuilder.countDistinct(pRoot.get("id")));	
		List<Predicate> p = new ArrayList<Predicate>();
		if(template_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("template").get("id"),template_id));
		}			
		criteriaQuery.groupBy(pRoot.get("question").get("subsection").get("section").get("id"));
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}		
		return entityManager.createQuery(criteriaQuery).getResultList();	
	}
	
	@Override
	public List<InstructionDTO> getInstructionDistribution(int template_id) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InstructionDTO> criteriaQuery = criteriaBuilder.createQuery(InstructionDTO.class);
		Root<TemplateSet> pRoot = criteriaQuery.from(TemplateSet.class);	
		criteriaQuery.multiselect(pRoot.get("question").get("subsection").get("section").get("id"),
				pRoot.get("question").get("subsection").get("section").get("sectionName"),
				criteriaBuilder.countDistinct(pRoot.get("id")),
				criteriaBuilder.sum(pRoot.get("question").get("time")));	
		List<Predicate> p = new ArrayList<Predicate>();
		if(template_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("template").get("id"),template_id));
		}			
		criteriaQuery.groupBy(pRoot.get("question").get("subsection").get("section").get("id"));
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}		
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getSectionDistribution(int template_id, int assign_id) {
		String sql="SELECT sec.Id,sec.SectionName,count(distinct q.Id),sum(q.time),\r\n" + 
				"(select count(cr.Id) from candidateresponse cr where cr.Section=sec.Id \r\n" + 
				"and cr.AssignId="+assign_id+") as completionStatus\r\n" + 
				"FROM gatepass2021.templateset t\r\n" + 
				"left join questions q on t.QuestionId=q.Id\r\n" + 
				"left join subsection sub on q.SubSection=sub.Id\r\n" + 
				"left join section sec on sub.Section=sec.Id\r\n" + 
				"where t.TemplateId="+template_id+" group by sec.Id";		
		return entityManager.createNativeQuery(sql).getResultList();
	}

	@Override
	public List<Questions> getTestQuestions(int template_id, int section_id) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Questions> criteriaQuery = criteriaBuilder.createQuery(Questions.class);
		Root<TemplateSet> pRoot = criteriaQuery.from(TemplateSet.class);	
		criteriaQuery.select(pRoot.get("question"));	
		List<Predicate> p = new ArrayList<Predicate>();
		if(template_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("template").get("id"),template_id));
		}
		if(section_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("question").get("subsection").get("section").get("id"),section_id));
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}		
		return entityManager.createQuery(criteriaQuery).getResultList();	
	}

}
