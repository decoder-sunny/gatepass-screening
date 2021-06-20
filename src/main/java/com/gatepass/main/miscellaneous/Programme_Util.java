package com.gatepass.main.miscellaneous;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.gatepass.main.dto.TestCaseDTO;

public class Programme_Util {
	
	 private static String clientId = "2c86fca7fef3fe4298000f8854adefe9"; //Replace with your client ID
	 private static String clientSecret = "719e60a046bf5f9c6f5a2c2cfb81619cf9e0810a03da4668df413d1a06aa16e5"; 

	 public static String getCodeOutput(TestCaseDTO code)  {
		if(code.getLanguage()=="java" || code.getLanguage().equals("java")) {
			code.setLanguage("java");
			code.setVersionIndex(1);
		}
		if(code.getLanguage()=="c" || code.getLanguage().equals("c") ){
			code.setLanguage("c");
			code.setVersionIndex(4);
		}
		if(code.getLanguage()=="cplus"  || code.getLanguage().equals("cplus") ) {
			code.setLanguage("cpp");
			code.setVersionIndex(4);
		}
		if(code.getLanguage()=="csharp" || code.getLanguage().equals("csharp") ) {
			code.setLanguage("csharp");
			code.setVersionIndex(3);
		}
		if(code.getLanguage()=="python" || code.getLanguage().equals("python") ) {
			code.setLanguage("python3");
			code.setVersionIndex(3);
		}
		 String output=null;
		 try {
	            URL url = new URL("https://api.jdoodle.com/v1/execute");
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoOutput(true);
	            connection.setRequestMethod("POST");
	            connection.setRequestProperty("Content-Type", "application/json");
	            
	            JSONObject json = new JSONObject();
	            json.put("clientId", clientId);
	            json.put("clientSecret", clientSecret);
	            json.put("script", code.getResponse());
	            json.put("stdin", code.getStdin());
	            json.put("language", code.getLanguage());
	            json.put("versionIndex",code.getVersionIndex());
            
	            String input=json.toString();	            
	            System.out.println(input);
	            OutputStream outputStream = connection.getOutputStream();
	            outputStream.write(input.getBytes());
	            outputStream.flush();
	            
	            System.out.println(connection);
	            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
	                throw new RuntimeException("Please check your inputs : HTTP error code : "+ connection.getResponseCode());
	            }

	            BufferedReader bufferedReader;
	            bufferedReader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
	           
	            System.out.println("Output from JDoodle .... \n");
	            
	            while ((output = bufferedReader.readLine()) != null) {
	            	 System.out.println(output);
	            	return output;	               
	            }
	            
	            connection.disconnect();
		        } catch (MalformedURLException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				 catch (JSONException e) {
			            e.printStackTrace();
			        }
		 return null;
	    }
	 
	

}
