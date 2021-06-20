package com.gatepass.main.mapper;

import java.util.Date;

public class ObjectMapper {
	
	public static String mapObjToString(Object obj) {
			return obj!=null?String.valueOf(obj):null;
	}
	
	public static Integer mapObjToInteger(Object obj) {
		return obj!=null?Integer.parseInt(String.valueOf(obj)):null;
	}
	
	public static Float mapObjToFloat(Object obj) {
		return obj!=null?Float.parseFloat(String.valueOf(obj)):null;
	}
	
	public static Boolean mapObjToBoolean(Object obj) {
		return obj!=null?Boolean.valueOf(String.valueOf(obj)):null;
	}
	
	public static Date mapObjToDate(Object obj) {
		return obj!=null?(Date)obj:null;
	}

}
