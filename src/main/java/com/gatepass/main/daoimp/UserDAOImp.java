package com.gatepass.main.daoimp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.gatepass.main.dao.UserDAO;
import com.gatepass.main.dto.UserDTO;
import com.gatepass.main.miscellaneous.Condition;

@Repository
public class UserDAOImp implements UserDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDTO> getUserList(Condition condition, int org_id) {
		String sql="select u.Id,u.Name,u.Email,u.Designation,u.IsActive from \r\n" + 
				"userorganisationmapping uom\r\n" + 
				"inner join user u on uom.UserId=u.Id\r\n" + 
				"where uom.Organisation="+org_id+"";
		if(condition.getText().length() > 2) {
			sql=sql+" and u.Name like '"+condition.getText()+"%' ";
		}	
		List<Object[]> list=entityManager
							.createNativeQuery(sql)
							.setMaxResults(condition.getPagesize())
							.setFirstResult((condition.getIndex()-1)*condition.getPagesize())
							.getResultList();

		List<UserDTO> clist=new ArrayList<>();
		for (Object[] objects : list) {
			UserDTO userDTO=new UserDTO();
			userDTO.setId(Integer.parseInt(objects[0].toString()));
			userDTO.setName(objects[1].toString());
			userDTO.setEmail(objects[2].toString());
			userDTO.setDesignation(objects[3].toString());
			userDTO.setActive(Boolean.valueOf(objects[4].toString()));
			clist.add(userDTO);
		}
		return clist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getUserListCount(Condition condition, int org_id) {
			String sql="select count(u.Id) from \r\n" + 
					"userorganisationmapping uom\r\n" + 
					"inner join user u on uom.UserId=u.Id\r\n" + 
					"where uom.Organisation="+org_id+"";
			if(condition.getText().length() > 2) {
				sql=sql+" and u.Name like '"+condition.getText()+"%' ";
			}		
		List<Object[]> list=entityManager.createNativeQuery(sql).getResultList();
		if(list.isEmpty())
			return 0;
		else
			return Integer.parseInt(String.valueOf(list.get(0)));
	}

}
