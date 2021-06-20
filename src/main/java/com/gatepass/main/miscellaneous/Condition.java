package com.gatepass.main.miscellaneous;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;

import org.apache.commons.codec.DecoderException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Condition {
	
	private int index;
	private String text;
	private int pagesize;
	
	public static void main(String[] args) throws UnsupportedEncodingException, DecoderException, CharacterCodingException {
		String hex = "0201060303AAFE1116AAFE20000B0C1700009824FF05F90E70040855464F09FFB10455464F61511F00000000000000000000000000000000000000000000";
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < hex.length(); i+=2) {
		    String str = hex.substring(i, i+2);
		    output.append((char)Integer.parseInt(str, 16));
		}
		System.out.println(output.toString().trim());
	   
	}

}
