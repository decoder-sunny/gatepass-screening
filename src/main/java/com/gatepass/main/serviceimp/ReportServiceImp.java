package com.gatepass.main.serviceimp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.gatepass.main.dao.TemplateDAO;
import com.gatepass.main.dto.ImageTime;
import com.gatepass.main.dto.JSONResponse;
import com.gatepass.main.dto.JSONTestCase;
import com.gatepass.main.dto.ReportDTO;
import com.gatepass.main.dto.SectionDTO;
import com.gatepass.main.mapper.CandidateMapper;
import com.gatepass.main.mapper.QuestionMapper;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.ReportSupport;
import com.gatepass.main.model.AssignedTemplate;
import com.gatepass.main.model.CandidateResponse;
import com.gatepass.main.model.Organisation;
import com.gatepass.main.model.Questions;
import com.gatepass.main.repository.AssignTemplateRepository;
import com.gatepass.main.repository.OrganisationRepository;
import com.gatepass.main.repository.ProctorRepository;
import com.gatepass.main.repository.ResponseRepository;
import com.gatepass.main.repository.TemplateRepository;
import com.gatepass.main.repository.TemplatesetRepository;
import com.gatepass.main.service.ReportService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class ReportServiceImp implements ReportService{
	
	@Autowired TemplateRepository template_repository;
	@Autowired TemplatesetRepository templateset_repository;
	@Autowired TemplateDAO template_dao;
	@Autowired AssignTemplateRepository assign_repository;
	@Autowired ResponseRepository response_repository;
	@Autowired ProctorRepository proctor_repository;
	@Autowired OrganisationRepository organisation_repository;
	
	@Value("${image_link}")
	private String url;

	@Override
	@Transactional
	public ReportDTO getTestReport(int assign_id,int org_id) {		
		try {
			ReportDTO dto=new ReportDTO();
			if(assign_repository.existsById(assign_id)) {
				AssignedTemplate assignedTemplate=assign_repository.findById(assign_id).get();
				if(!Arrays.asList(1,org_id).contains(assignedTemplate.getOrganisation())) {
					throw new CustomException("Unauthorised request");
				}
				Organisation organisation=organisation_repository.findById(org_id).get();
				dto.setCandidateDTO(CandidateMapper.candidateMapperForReport(assignedTemplate
						.getCandidate()
						,assignedTemplate.getUpdatedOn(),organisation));
				if(proctor_repository.existsById(assign_id)) {
					dto.setProctorInfo(proctor_repository.findById(assign_id).get());
				}
				List<CandidateResponse> responseList=response_repository.findAllByAssignId(assign_id);
				List<ReportSupport> rs=new ArrayList<>();
				
				for (CandidateResponse candidateResponse : responseList) {					
					List<JSONResponse> JSONresponse=new Gson().fromJson(candidateResponse.getResponse(),
							TypeToken.getParameterized(ArrayList.class, JSONResponse.class).getType());
					List<JSONTestCase> JSONTestcase=new Gson().fromJson(candidateResponse.getTestCases(),
							TypeToken.getParameterized(ArrayList.class, JSONTestCase.class).getType());
					Collections.sort(JSONresponse, Comparator.comparingInt(JSONResponse::getQuestion_id));
					List<Questions> questions=template_dao.getTestQuestions(assignedTemplate
							.getTemplate().getId(),
							candidateResponse.getSection());
					if(!questions.isEmpty()) {
						ReportSupport obj=new ReportSupport();
						obj.setQuestionDTO(QuestionMapper.mapQuestionsForReport(questions));
						obj.setTotalMarks(questions.stream().mapToInt(x-> x.getMarks()).sum());
						obj.setMarksObtained(candidateResponse.getMarks());
						obj.setSectionDTO(new SectionDTO(candidateResponse.getSection(),
								questions.get(0).getSubsection().getSection().getSectionName()));
						if(!JSONresponse.isEmpty()) {
							obj.setResponse(JSONresponse.stream().map(x-> x.getResponse()).collect(Collectors.toList()));
						}
						if(candidateResponse.getSection()==7 && !JSONresponse.isEmpty()) {
							obj.setLanguage(JSONresponse.get(0).getLanguage());
							obj.setAttempts(JSONresponse.stream().map(x-> x.getAttempts()).collect(Collectors.toList()));
							obj.setTime(JSONresponse.stream().map(x-> x.getTime()).collect(Collectors.toList()));
						}
						if(candidateResponse.getSection()==7 && !JSONTestcase.isEmpty()) {
							obj.setTestCases(JSONTestcase.stream().map(x-> x.getMarks()).collect(Collectors.toList()));
						}
						rs.add(obj);
					}
				}
				dto.setDetail(rs);
			}
			else {
				throw new CustomException("Tagged not saved");
			}
			return dto;
		}		
		catch (CustomException e) {
			throw new DuplicateKeyException(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to get report");
		}
		
	}

	@Override
	@Transactional
	public ModelAndView getTestReportView(int assign_id, int org_id) {
		try {
			ModelAndView modelAndView = new ModelAndView();
			ReportDTO dto=this.getTestReport(assign_id, org_id);
			
			int totalMarks=dto.getDetail().stream().mapToInt(x-> x.getTotalMarks()).sum();
			int obtainedMarks=dto.getDetail().stream().mapToInt(x-> x.getMarksObtained()).sum();
			int percentage= (int) (obtainedMarks*100)/totalMarks;
			int totalQuestions=0;
			int attempted=0;
			for (ReportSupport iterable_element : dto.getDetail()) {
				totalQuestions+=iterable_element.getQuestionDTO().size();
				attempted+=iterable_element.getResponse().stream().filter(x-> x!=null && x.length()>0).count();
			}
		    modelAndView.setViewName("report.html");
		    modelAndView.addObject("data", dto);
		    modelAndView.addObject("totalMarks", totalMarks);
		    modelAndView.addObject("obtainedMarks", obtainedMarks);
		    modelAndView.addObject("percentage", percentage);
		    modelAndView.addObject("totalQuestions", totalQuestions);
		    modelAndView.addObject("attempted", attempted);
		    modelAndView.addObject("url", this.url);
		    List<ImageTime> imageList=new ArrayList<>();
		    List<String> list=new Gson().fromJson(dto.getProctorInfo().getImages(),
					TypeToken.getParameterized(ArrayList.class, String.class).getType());
		    for (String info : list) {
				imageList.add(new Gson().fromJson(info,ImageTime.class));
			}
		  
		    modelAndView.addObject("proctor", imageList);
		    return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to get report");
		}
	}
	
	

}
