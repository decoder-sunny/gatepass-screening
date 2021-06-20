package com.gatepass.main.serviceimp;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gatepass.main.dao.QuestionsDAO;
import com.gatepass.main.dto.QuestionDTO;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.Pagination;
import com.gatepass.main.model.Code;
import com.gatepass.main.model.Questions;
import com.gatepass.main.model.SubSection;
import com.gatepass.main.model.TestCases;
import com.gatepass.main.repository.QuestionRepository;
import com.gatepass.main.service.QuestionService;

@Service
public class QuestionServiceImp implements QuestionService{

	@Autowired private QuestionRepository question_repository;
	@Autowired private QuestionsDAO question_dao;

	@Override
	@Transactional
	public QuestionDTO saveQuestion(QuestionDTO questionDTO) {
		try {
			Questions questions=new Questions();
			questions.setAnswer(questionDTO.getAnswer());
			questions.setQuestion(questionDTO.getQuestion());
			questions.setLevel(new Code(questionDTO.getLevel()));
			questions.setTime(questionDTO.getTime());
			questions.setMarks(questionDTO.getMarks());
			questions.setQuestionType(new Code(questionDTO.getQuestionType()));
			questions.setOptionsImage(questionDTO.getOptionsImage());
			questions.setQuestionImage(questionDTO.getQuestionImage());
			questions.setOptionsObjective(questionDTO.getOptionsObjective());
			questions.setSubsection(new SubSection(questionDTO.getSubsection()));
			if(questionDTO.getQuestionType()==6) {
				questions.setCodeQuestion(questionDTO.getCodeQuestion());
				for (TestCases obj : questionDTO.getCodeQuestion().getTestcases()) {
					obj.setCodequestion(questionDTO.getCodeQuestion());
				}
			}			
			questions=question_repository.save(questions);			
			return questionDTO;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new CustomException("Question not saved");
		}
	}

	@Override
	public Pagination getQuestions(int section_id, int subection_id, Condition condition) {
		try {
			Pagination pagination=new Pagination();
			List<QuestionDTO> dtos=new ArrayList<QuestionDTO>();
			List<Questions> list=question_dao.getQuestions(section_id, subection_id, condition);
			if (!list.isEmpty()) {
				for (Questions questions : list) {
					QuestionDTO dto=new QuestionDTO();
					dto.setId(questions.getId());
					dto.setQuestion(questions.getQuestion());
					dto.setSubsection_name(questions.getSubsection().getSubsectionName());
					dtos.add(dto);
				}
				pagination.setQuestions(dtos);
				pagination.setLength(question_dao.getQuestionCount(section_id, subection_id, condition));
			}
			return pagination;			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			throw new DuplicateKeyException("Questions not found");
		}
	}

	@Override
	@Transactional
	public QuestionDTO getQuestionById(int id) {
		try {			
			if(question_repository.existsById(id)) {
				Questions questions=question_repository.findById(id).get();
				QuestionDTO dto=new QuestionDTO();
				dto.setId(questions.getId());
				dto.setAnswer(questions.getAnswer());
				dto.setCodeQuestion(questions.getCodeQuestion());
				dto.setOptionsImage(questions.getOptionsImage());
				dto.setLevel(questions.getLevel().getId());
				dto.setQuestion(questions.getQuestion());
				dto.setMarks(questions.getMarks());
				dto.setTime(questions.getTime());
				dto.setQuestionImage(questions.getQuestionImage());
				dto.setOptionsObjective(questions.getOptionsObjective());
				dto.setSubsection(questions.getSubsection().getId());
				dto.setQuestionType(questions.getQuestionType().getId());
				return dto;
			}
			else {
				throw new CustomException("Invalid request");
			}
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Question not updated");
		}	
	}

	@Override
	@Transactional
	public QuestionDTO updateQuestion(QuestionDTO questionDTO) {
		try {			
			if(question_repository.existsById(questionDTO.getId())) {
				Questions questions=question_repository.findById(questionDTO.getId()).get();
				questions.setAnswer(questionDTO.getAnswer());
				questions.setQuestion(questionDTO.getQuestion());
				questions.setLevel(new Code(questionDTO.getLevel()));
				questions.setTime(questionDTO.getTime());
				questions.setMarks(questionDTO.getMarks());
				questions.setQuestionType(new Code(questionDTO.getQuestionType()));
				questions.setOptionsImage(questionDTO.getOptionsImage());
				questions.setOptionsObjective(questionDTO.getOptionsObjective());
				questions.setQuestionImage(questionDTO.getQuestionImage());
				questions.setSubsection(new SubSection(questionDTO.getSubsection()));
				if(questionDTO.getQuestionType()==6) {
					questions.setCodeQuestion(questionDTO.getCodeQuestion());
					for (TestCases obj : questionDTO.getCodeQuestion().getTestcases()) {
						obj.setCodequestion(questionDTO.getCodeQuestion());
					}
				}	
				question_repository.save(questions);
			}
			else {
				throw new CustomException("Invalid request");
			}
			return questionDTO;
		} 
		catch (CustomException e) {
			System.out.println(e);
			throw new DuplicateKeyException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Question not updated");
		}	
	}
	
	
}
