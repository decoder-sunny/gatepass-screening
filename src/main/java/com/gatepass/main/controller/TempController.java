package com.gatepass.main.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.gatepass.main.service.MailService;
import com.pdfcrowd.Pdfcrowd;

@Controller
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api")
public class TempController {
	

@Autowired SpringTemplateEngine templateEngine;
@Autowired MailService mailService;

@GetMapping("/get-pdf")
public ResponseEntity<byte[]> convert() throws UnsupportedEncodingException,Exception {
    try {
		
	   // create the API client instance
    	
        Pdfcrowd.HtmlToPdfClient client =
            new Pdfcrowd.HtmlToPdfClient("decode_sunny", "e303c14e02a20ceace287eaf787a656c");

        // configure the conversion
        client.setPageWidth("330mm");
        client.setPageHeight("450mm");
        client.setNoMargins(true);

        // run the conversion and store the result into the "pdf" variable
//        byte[] pdf = client.convertString(getPDF().getView().toString());
        
        byte[] pdf =client.convertUrl("https://www.techwisedigital.co.in/api/test-report/38/2");
        System.out.println(pdf);
        mailService.reportAttachment(pdf);
       

        // set HTTP response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Cache-Control", "max-age=0");
        headers.add("Accept-Ranges", "none");
        headers.add("Content-Disposition", "attachment; filename*=UTF-8''" +
                    URLEncoder.encode("result.pdf", "UTF-8").replace("+", "%20"));

        // send the result in the HTTP response
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
    catch(Pdfcrowd.Error why) {
        // send the error in the HTTP response
    	why.printStackTrace();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain");
        String msg = String.format("Pdfcrowd Error: %d - %s",
                                   why.getCode(), why.getMessage());
        return new ResponseEntity<>(msg.getBytes(), headers, HttpStatus.BAD_REQUEST);
    }
}
	
	

}
