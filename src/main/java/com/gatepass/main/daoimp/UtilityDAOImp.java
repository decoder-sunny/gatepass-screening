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

import com.gatepass.main.model.Code;
import com.gatepass.main.dao.UtilityDAO;

@Repository
public class UtilityDAOImp implements UtilityDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Code> getCodeData(String text, String cvt) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Code> criteriaQuery = criteriaBuilder.createQuery(Code.class);
		Root<Code> pRoot = criteriaQuery.from(Code.class);	
		criteriaQuery.multiselect(pRoot.get("id"),
									pRoot.get("value"));		
		List <Predicate> p = new ArrayList <Predicate> ();
		if(text.length()>4) {
			p.add(criteriaBuilder.like(pRoot.get("value"), text+"%"));
		}		
		p.add(criteriaBuilder.equal(pRoot.get("codeValueType").get("name"),cvt));
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}
