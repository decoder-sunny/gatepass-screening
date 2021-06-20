package com.gatepass.main.miscellaneous;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.gatepass.main.dto.JSONResponse;
import com.gatepass.main.dto.JSONTestCase;
import com.gatepass.main.dto.TestCaseDTO;
import com.gatepass.main.model.Questions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Utility {

	public static boolean timeDifference(Date time) {

		long diff = new Date().getTime() - time.getTime();
		long minutes=TimeUnit.MINUTES.convert(diff,TimeUnit.MILLISECONDS);
		if((minutes/60)<=24) {
			return true;
		}
		return false;
	}

	public static int evaluateObjectiveResponses(List<Questions> questions,String responses) {
		int marks=0;
		try {
			Collections.sort(questions, Comparator.comparingInt(Questions ::getId));
			ArrayList<JSONResponse> list = new Gson().fromJson(responses,
					TypeToken.getParameterized(ArrayList.class, JSONResponse.class).getType());
			Collections.sort(list, Comparator.comparingInt(JSONResponse ::getQuestion_id));
			
			if(questions.size()==list.size()) {
				for (int i = 0; i < questions.size(); i++) {

					if(list.get(i).getResponse()!=null) {
						if(list.get(i).getResponse()==questions.get(i).getAnswer() || 
								list.get(i).getResponse().equals(questions.get(i).getAnswer())) {
							marks=marks+questions.get(i).getMarks();
						}
					}					
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Evaluation Failed");
		}
		return marks;
	}

	public static List<JSONTestCase> evaluateCodeResponses(List<Questions> questions,String responses) {
		List<JSONTestCase> jsonList=new ArrayList<>();
		try {			
			Collections.sort(questions, Comparator.comparingInt(Questions ::getId));		
			ArrayList<JSONResponse> list = new Gson().fromJson(responses,
					TypeToken.getParameterized(ArrayList.class, JSONResponse.class).getType());
			Collections.sort(list, Comparator.comparingInt(JSONResponse ::getQuestion_id));
			if(questions.size()==list.size()) {
				for (int i = 0; i < questions.size(); i++) {
					JSONTestCase htc=new JSONTestCase();
					htc.setQuestion_id(questions.get(i).getId());
					htc.setCode_id(questions.get(i).getCodeQuestion().getId());
					TestCaseDTO caseDTO=new TestCaseDTO();
					caseDTO.setResponse(list.get(i).getResponse());
					caseDTO.setLanguage(list.get(i).getLanguage());
					for (int j = 0; j < questions.get(i).getCodeQuestion().getTestcases().size(); j++) {
						caseDTO.setStdin(questions.get(i).getCodeQuestion().getTestcases().get(j).getExpectedInput());
						JSONObject jsonObj = new JSONObject(Programme_Util.getCodeOutput(caseDTO));
						if(jsonObj.getInt("statusCode")==200) {
							System.out.println("200");
							if(jsonObj.get("output")==questions.get(i).getCodeQuestion().getTestcases()
									.get(j).getExpectedOutput() || jsonObj.get("output").equals(
											questions.get(i).getCodeQuestion().getTestcases().get(j).getExpectedOutput())) {
								System.out.println("Success match");
								htc.setSuccessful_testcase(htc.getSuccessful_testcase()+1);
								htc.setMarks(htc.getMarks()+questions.get(i).getCodeQuestion().getTestcases().get(j).getMarks());
							}
							else {
								htc.setSuccessful_testcase(htc.getSuccessful_testcase()+0);
								htc.setMarks(htc.getMarks()+0);
								System.out.println("Out didnot match");
								//								throw new CustomException("Error in Code");
							}
						}
					}
					System.out.println(htc);
					jsonList.add(htc);
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Evaluation Failed");
		}
		return jsonList;
	}

	
}
