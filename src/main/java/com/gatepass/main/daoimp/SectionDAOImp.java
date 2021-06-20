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

import com.gatepass.main.dao.SectionDAO;
import com.gatepass.main.dto.SectionDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.model.Section;

@Repository
public class SectionDAOImp implements SectionDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SectionDTO> getSectionList(Condition condition) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SectionDTO> criteriaQuery = criteriaBuilder.createQuery(SectionDTO.class);
		Root<Section> pRoot = criteriaQuery.from(Section.class);	
		criteriaQuery.multiselect(pRoot.get("id"),
								pRoot.get("sectionName"));
		List <Predicate> p = new ArrayList <Predicate> ();
		if(condition.getText().trim().length()>1) {
			p.add(criteriaBuilder.like(pRoot.get("sectionName"), condition.getText()+"%"));
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
	public int getSectionListCount(Condition condition) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Section> pRoot = criteriaQuery.from(Section.class);
		criteriaQuery.select(criteriaBuilder.count(pRoot));	
		criteriaQuery.distinct(true);		
		List <Predicate> p = new ArrayList <Predicate> ();
		if(condition.getText().trim().length()>1) {
			p.add(criteriaBuilder.like(pRoot.get("sectionName"), condition.getText()+"%"));
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult().intValue();
	}

}
