package com.gatepass.main.serviceimp;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gatepass.main.dto.FilterCriteriaDTO;
import com.gatepass.main.dto.ScreenResultDTO;
import com.gatepass.main.mapper.ObjectMapper;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.model.FilterCandidateResponse;
import com.gatepass.main.model.Position;
import com.gatepass.main.model.PositionQuestions;
import com.gatepass.main.repository.CandidateRepository;
import com.gatepass.main.repository.FilterCandidateResponseRepository;
import com.gatepass.main.repository.PositionQuestionRepository;
import com.gatepass.main.repository.PositionRepository;
import com.gatepass.main.service.FilterResponseService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class FilterResponseServiceImp implements FilterResponseService{
	
	@Autowired FilterCandidateResponseRepository response_repository;
	@Autowired PositionRepository position_repository;
	@Autowired PositionQuestionRepository positionquestion_repository;
	@Autowired CandidateRepository candidate_repository;
	

	@Override
	@Transactional
	public ScreenResultDTO getScreenResponse(int id) {
		ScreenResultDTO dto=new ScreenResultDTO();
		try {
			if(response_repository.existsById(id)) {
				FilterCandidateResponse fcr=response_repository.findById(id).get();
				Position position=position_repository.findById(fcr.getPositionId()).get();
				List<PositionQuestions> list=positionquestion_repository
						.findAllByPositionIdOrderByIsMustHaveDesc(position.getId());	
				
				dto.setCreatedOn(fcr.getCreatedOn());
				dto.setMarks(fcr.getMarks());
				dto.setPositionName(position.getName());
				dto.setResponse(fcr.getResponse());
				dto.setQuestions(list.stream().map( x-> x.getQuestion()).collect(Collectors.toList()));
				dto.setMustHaves(list.stream().map( x-> x.isMustHave()).collect(Collectors.toList()));
				dto.setExpected(list.stream().map( x-> x.getPreferredOptions()).collect(Collectors.toList()));
								
			}
			else {
				throw new CustomException("Invalid request");
			}
		} catch (CustomException e) {
			System.out.println(e);
			throw new CustomException(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e);
			throw new CustomException("Failed to fetch messaes");
		}
		return dto;
	}
	
	@Override
	@Transactional
	public void exportScreeningExcel(HttpServletResponse response, int position_id) {
		try {
			XSSFWorkbook workbook=new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Users");	         
			Row row0 = sheet.createRow(0);	  
			
			CellStyle style1 = workbook.createCellStyle();
			XSSFFont font1 = workbook.createFont();
			font1.setFontHeight(13);
			font1.setBold(true);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setFont(font1);
			
			CellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setFontHeight(12);
			style.setFont(font);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			
			CellStyle redstyle = workbook.createCellStyle();
			XSSFFont redFont = workbook.createFont();
			redFont.setColor(IndexedColors.RED.getIndex());
			redstyle.setFont(redFont);
			redstyle.setAlignment(CellStyle.ALIGN_CENTER);
			
			Position position=position_repository.findById(position_id).get();
			List<PositionQuestions> list=positionquestion_repository
					.findAllByPositionIdAndIsMustHaveOrderByIsMustHaveDesc(position_id, false);
			
			List<PositionQuestions> positionQuestions=positionquestion_repository
					.findAllByPositionIdOrderByIsMustHaveDesc(position_id);
			
			createCell(row0, 0, "Position Name", style1);      
			createCell(row0, 1, "Applied Candidates", style1);       
			createCell(row0, 2, "Cleared Candidates", style1);   
			
			Row row1 = sheet.createRow(1);	 
			createCell(row1, 0, position.getName(), style);      
			createCell(row1, 1, position.getApplied(), style);       
			createCell(row1, 2, position.getCompleted(), style);  		
			
			if(list.isEmpty()) {
				throw new CustomException("No good to have questions found");
			}
			sheet.createRow(2);	
			sheet.createRow(3);	
			
			Row row2 = sheet.createRow(4);	 
			createCell(row2, 0, "Candidate Name", style1);      
			createCell(row2, 1, "Total Score ", style1);       
			createCell(row2, 2, "Must Have (Total)", style1); 
			createCell(row2, 3, "Good To Have (Total)", style1); 
			for (int i = 0; i < list.size(); i++) {
				StringBuilder sb=new StringBuilder();
				sb.append(list.get(i).getQuestion());
				sb.append(" Expected - "+list.get(i).getPreferredOptions());
				createCell(row2, i+4, sb.toString(), style1);  
			}
			
			int rowCount=5;			
			List<FilterCandidateResponse> fcrList=response_repository.findAllByPositionIdOrderByMarksDesc(position_id);
			for (int i = 0; i < fcrList.size(); i++) {
				Row irow = sheet.createRow(rowCount++);
				int columnCount = 0;
				List<Integer> marksList=totalMarks(position_id,false,fcrList.get(i),positionQuestions);
				
				List<Object[]> candidate=candidate_repository.getCandidateInfo(fcrList.get(i).getCandidateId());
                createCell(irow, columnCount++, ObjectMapper.mapObjToString(candidate.get(0)[0]), style);
                createCell(irow, columnCount++, fcrList.get(i).getMarks(), style);
                createCell(irow, columnCount++, 
                		totalMarks(position_id,true,fcrList.get(i),positionQuestions)
                		.stream().mapToInt(x-> x).sum(), style);
                createCell(irow, columnCount++, marksList
                		.stream().mapToInt(x-> x).sum(), style);
                List<FilterCriteriaDTO> responses=new Gson().fromJson(fcrList.get(i).getResponse(),
    					TypeToken.getParameterized(ArrayList.class, FilterCriteriaDTO.class).getType());
                int index=0;
                
                for (PositionQuestions questions : list) {
                	FilterCriteriaDTO fcr=responses.stream().filter(x-> x.getId()==questions.getId())
                			.findFirst().get();
                	StringBuilder sb=new StringBuilder();
                	for (String str : fcr.getResponse()) {
						sb.append(" "+str);
					}
                	
                	createCell(irow, columnCount++, sb.toString(),marksList.get(index)>0?redstyle:style);
                	index++;
				} 
			}
			OutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);

			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to generate report");
		}
		
	}
	
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	
	public List<Integer> totalMarks(int position_id,boolean isMustHave,FilterCandidateResponse fcr,
			List<PositionQuestions> list) {
		List<Integer> marks=new ArrayList<>();
		
		for (int i = 0; i < list.size(); i++) {
			List<String> preferredOptions=new Gson().fromJson(list.get(i).getPreferredOptions(),
					TypeToken.getParameterized(ArrayList.class, String.class).getType());
			List<FilterCriteriaDTO> responses=new Gson().fromJson(fcr.getResponse(),
					TypeToken.getParameterized(ArrayList.class, FilterCriteriaDTO.class).getType());
			if(isMustHave) {
				if(list.get(i).isMustHave() && list.get(i).getId()==responses.get(i).getId()) {
					Boolean disjoint=Collections.disjoint(preferredOptions, 
											responses.get(i).getResponse());
					int mark=0;
					if(!disjoint) {						
						if(!list.get(i).isMultiple()) {
							mark=1;
						}
						else {							
							for (String string : responses.get(i).getResponse()) {
								if(preferredOptions.contains(string)) {
									mark+=1;
								}
							}							
						}
					}
					marks.add(mark);
				}
			}
			else {
				if(!list.get(i).isMustHave() && list.get(i).getId()==responses.get(i).getId()) {
					Boolean disjoint=Collections.disjoint(preferredOptions, 
											responses.get(i).getResponse());
					int mark=0;
					if(!disjoint) {
						if(!list.get(i).isMultiple()) {
							mark=1;
						}
						else {
							for (String string : responses.get(i).getResponse()) {
								if(preferredOptions.contains(string)) {
									mark+=1;
								}
							}
						}
					}
					marks.add(mark);
				}
			}
		}
		return marks;
	}
	
	

}
