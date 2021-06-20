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

import com.gatepass.main.dao.QuestionsDAO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.model.Questions;

@Repository
public class QuestionDAOImp implements QuestionsDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Questions> getQuestions(int section_id, int sub_section_id, Condition condition) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Questions> criteriaQuery = criteriaBuilder.createQuery(Questions.class);
		Root<Questions> pRoot = criteriaQuery.from(Questions.class);	
		List <Predicate> p = new ArrayList <Predicate> ();
		if(section_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("subsection").get("section").get("id"),section_id));
		}
		if(sub_section_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("subsection").get("id"),sub_section_id));
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		criteriaQuery.orderBy(criteriaBuilder.desc(pRoot.get("id")));
		return entityManager.createQuery(criteriaQuery).setMaxResults(condition.getPagesize()).
				setFirstResult((condition.getIndex()-1)*condition.getPagesize())
				.getResultList();
	}

	@Override
	public int getQuestionCount(int section_id, int sub_section_id, Condition condition) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Questions> pRoot = criteriaQuery.from(Questions.class);
		criteriaQuery.select(criteriaBuilder.count(pRoot));	
		criteriaQuery.distinct(true);		
		List <Predicate> p = new ArrayList <Predicate> ();
		if(section_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("subsection").get("section").get("id"),section_id));
		}
		if(sub_section_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("subsection").get("id"),sub_section_id));
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult().intValue();
	}

	

}
