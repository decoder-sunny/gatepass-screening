package com.gatepass.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gatepass.main.model.AssignedTemplate;

@Repository
public interface DashboardRepository extends CrudRepository<AssignedTemplate, Integer>{

	//to get dashboard info
	@Query(value="select \r\n" + 
			"(select count(cd.id) from candidatedetails cd left join candidateservices cs on cd.Id=cs.CandidateId\r\n" + 
			"where cs.OrganisationId=:org_id) as totalCandidates,\r\n" + 
			"(select count(asst.id) from assignedtemplate asst left join candidateservices cs on\r\n" + 
			"cs.CandidateId=asst.CandidateId\r\n" + 
			"where CompletionStatus=true and cs.OrganisationId=:org_id) as testConducted,\r\n" + 
			"(select count(p.id) from position p left join userorganisationmapping uom on p.CreatedBy=uom.UserId\r\n" + 
			"where uom.Organisation=:org_id) as positions,\r\n" + 
			"(select sum(p.Completed) from position p left join userorganisationmapping uom on p.CreatedBy=uom.UserId\r\n" + 
			"where uom.Organisation=:org_id) as screened\r\n" + 
			"from dual",nativeQuery=true)
	List<Object[]> getDashboardInfo(@Param("org_id") int org_id);


	//to get rankers data
	@Query(value="select u.Name,u.Email,sum(cr.Marks) as totalMarks,cd.Id from candidateresponse cr\r\n" + 
			"left join assignedtemplate asst on cr.AssignId=asst.Id\r\n" + 
			"left join candidatedetails cd on cd.Id=asst.CandidateId\r\n" + 
			"left join user u on cd.User=u.Id\r\n" + 
			"left join userorganisationmapping uom on u.CreatedBy=uom.UserId\r\n" + 
			"where uom.Organisation=:org_id\r\n" + 
			"group by cr.AssignId order by totalMarks desc",
			countQuery="select count(*) from candidateresponse cr\r\n" + 
					"left join assignedtemplate asst on cr.AssignId=asst.Id\r\n" + 
					"left join candidatedetails cd on cd.Id=asst.CandidateId\r\n" + 
					"left join user u on cd.User=u.Id\r\n" + 
					"left join userorganisationmapping uom on u.CreatedBy=uom.UserId\r\n" + 
					"where uom.Organisation=:org_id\r\n" + 
					"group by cr.AssignId "
			,nativeQuery=true)
	Page<Object[]> getRankers(@Param("org_id") int org_id,Pageable pageable);
	
	
	//to get tests conducted recently
	@Query(value="select date(cr.Date),count(cr.Id) from candidateresponse cr \r\n" + 
			"left join assignedtemplate asst on cr.AssignId=asst.Id\r\n" + 
			"left join candidatedetails cd on cd.Id=asst.CandidateId\r\n" + 
			"left join user u on cd.User=u.Id\r\n" + 
			"left join userorganisationmapping uom on u.CreatedBy=uom.UserId\r\n" + 
			"where uom.Organisation=:org_id\r\n" + 
			"group by date(cr.Date) order by date(cr.Date) desc",nativeQuery=true)
	List<Object[]> getTestConductedRecently(@Param("org_id") int org_id,Pageable pageable);

}
