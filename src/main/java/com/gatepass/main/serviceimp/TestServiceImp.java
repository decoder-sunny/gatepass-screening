package com.gatepass.main.serviceimp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gatepass.main.dao.TemplateDAO;
import com.gatepass.main.dto.InstructionDTO;
import com.gatepass.main.dto.JSONTestCase;
import com.gatepass.main.dto.QuestionDTO;
import com.gatepass.main.dto.TestCaseDTO;
import com.gatepass.main.dto.TestQuestions;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.MessageModel;
import com.gatepass.main.miscellaneous.Programme_Util;
import com.gatepass.main.miscellaneous.Utility;
import com.gatepass.main.model.AssignedTemplate;
import com.gatepass.main.model.CandidateResponse;
import com.gatepass.main.model.CodeQuestion;
import com.gatepass.main.model.Questions;
import com.gatepass.main.repository.AssignTemplateRepository;
import com.gatepass.main.repository.CandidateResponseRepository;
import com.gatepass.main.repository.CodeQuestionRepository;
import com.gatepass.main.service.TestService;
import com.google.gson.Gson;

@Service
public class TestServiceImp implements TestService{
	
	@Autowired private TemplateDAO template_dao;
	@Autowired private CandidateResponseRepository response_repository;
	@Autowired private AssignTemplateRepository assign_repository;
	@Autowired private CodeQuestionRepository code_repository;

	@Override
	@Transactional
	public List<InstructionDTO> getInstructionDistribution(int template_id) {
		try {
			return template_dao.getInstructionDistribution(template_id);
		} catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Data not found");
		}
	}

	@Override
	@Transactional
	public List<InstructionDTO> getSectionDistribution(int template_id, int assign_id) {
		try {
			List<InstructionDTO> ilist=new ArrayList<InstructionDTO>();
			List<Object[]> list=template_dao.getSectionDistribution(template_id, assign_id);
			if(!list.isEmpty()) {
				for (Object[] objects : list) {
					ilist.add(new InstructionDTO(objects));
				}
			}
			return ilist;
		} catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Data not found");
		}
	}

	@Override
	@Transactional
	public TestQuestions getTestQuestions(int template_id, int section_id) {
		try {
			TestQuestions tq=new TestQuestions();
			List<QuestionDTO> qlist=new ArrayList<QuestionDTO>();
			List<Questions> list=template_dao.getTestQuestions(template_id, section_id);
			if(!list.isEmpty()) {
				for (Questions questions : list) {
					QuestionDTO dto=new QuestionDTO();
					dto.setCodeQuestion(questions.getCodeQuestion());
					dto.setId(questions.getId());
					dto.setOptionsImage(questions.getOptionsImage());
					dto.setQuestion(questions.getQuestion());
					dto.setQuestionImage(questions.getQuestionImage());
					dto.setOptionsObjective(questions.getOptionsObjective());
					dto.setQuestionType(questions.getQuestionType().getId());
					qlist.add(dto);
				}
				tq.setQuestions(qlist);
				tq.setSection(list.get(0).getSubsection().getSection().getSectionName());
				tq.setTime(list.stream().mapToInt(x-> x.getTime()).sum());				
			}
			return tq;
		} catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Failed to fetch questions");
		}
	}

	@Override
	@Transactional
	public CandidateResponse saveCandidateResponse(CandidateResponse candidateResponse) {
		try {
			candidateResponse.setDate(new Date());
			return response_repository.save(candidateResponse);
		} catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Response not saved");
		}
	}

	@Override
	@Transactional
	public MessageModel evaluateCandidateResponses(int assign_id) {
		try {
			MessageModel msg=new MessageModel();
			if(assign_repository.existsById(assign_id)) {
				AssignedTemplate assignedTemplate=assign_repository.findById(assign_id).get();
				List<CandidateResponse> responses=response_repository.findByAssignId(assign_id);
				if(!responses.isEmpty()) {
					for (CandidateResponse candidateResponse : responses) {
						if(!Arrays.asList(7,29).contains(candidateResponse.getSection())) {
							List<Questions> questions=template_dao
									.getTestQuestions(assignedTemplate.getTemplate().getId(),
											candidateResponse.getSection());
							candidateResponse.setMarks(Utility
									.evaluateObjectiveResponses(questions, candidateResponse.getResponse()));
							response_repository.save(candidateResponse);							
						}
						if(Arrays.asList(7).contains(candidateResponse.getSection())) {
							List<Questions> questions=template_dao
									.getTestQuestions(assignedTemplate.getTemplate().getId(),
											candidateResponse.getSection());
							List<JSONTestCase> jsonList=Utility.evaluateCodeResponses(questions, 
									candidateResponse.getResponse());
							candidateResponse.setTestCases(new Gson().toJson(jsonList));
							candidateResponse.setMarks(jsonList.stream().mapToInt(x-> x.getMarks()).sum());
							response_repository.save(candidateResponse);							
						}
					}
					if(!assignedTemplate.isCompletionStatus()) {
						assignedTemplate.setCompletionStatus(true);
					}	
					assign_repository.save(assignedTemplate);
					msg.setMessage("Test Submitted Sucessfully");
				}
				else {
					throw new CustomException("Something went wrong");
				}
			}
			else {
				throw new CustomException("Invalid request");
			}
			return msg;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Something went wrong");
		}
	}

	@Override
	@Transactional
	public MessageModel runTestCase(TestCaseDTO caseDTO) {
		try {
			MessageModel msg=new MessageModel();
			if(code_repository.existsById(caseDTO.getCode_id())) {
				CodeQuestion codeQuestion=code_repository.findById(caseDTO.getCode_id()).get();
				caseDTO.setStdin(codeQuestion.getTestcases().get(0).getExpectedInput());
				JSONObject jsonObj = new JSONObject(Programme_Util.getCodeOutput(caseDTO));
				if(jsonObj.getInt("statusCode")==200) {
					if(jsonObj.get("output")==codeQuestion.
						getTestcases().get(0).getExpectedOutput() || jsonObj.get("output").equals(
								codeQuestion.getTestcases().get(0).getExpectedOutput())) {
						msg.setMessage("Test Case Ran Successfully");
					}
					else {
						throw new CustomException("Error in Code");
					}
				}
				else {
					throw new CustomException("Error in Code");
				}				
			}
			return msg;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Something went wrong");
		}
	}

	
}
