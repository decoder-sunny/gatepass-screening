package com.gatepass.main.miscellaneous;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Encryption {
	

	public static String randomPassword() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*(){][";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 8) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	public static Date getModifiedDate(){
		 Calendar cal  = Calendar.getInstance();
		 cal.add(Calendar.DATE, -5);
		 return new Date(cal.getTimeInMillis());
	}

	
}