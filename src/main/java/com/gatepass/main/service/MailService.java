package com.gatepass.main.service;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.model.CandidateDetails;
import com.gatepass.main.repository.CandidateRepository;
import com.sun.istack.ByteArrayDataSource;

@Service
public class MailService {

	@Value("${mail_username}")
	private String username;

	@Value("${mail_password}")
	private String mail_password; 
	
	@Value("${test_link}")
	private String url; 
	
	@Value("${home_link}")
	private String home_url; 
	
	@Autowired CandidateRepository candidate_repository;
	
	//mail to activate user 
	public boolean activationMail(String email,String password,String name) {
		boolean status=false; 
		Session session = Session.getInstance(getMailProperties(),
				new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, mail_password);
			}
		});
//		session.setDebug(true);	
		MimeMessage mm=new MimeMessage(session);
		try {
			mm.setHeader("Content-Type", "text/html");
			mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			mm.setFrom(new InternetAddress(username,"GATEPASS"));
			mm.setSubject("Account Confirmation | GATEPASS ");	
			mm.setContent(
							"Dear "+name+" <br><br> Congratulations! <br><br> "
							+ "You have been successfully registered .<br>"
							+ "Your login credentials are :<br>"
							+ "Username: "+email+"<br>"
							+ "Temporary Password: "+password+" <br>"
							+ "URL: <a href="+home_url+">Click here</a><br><br>"
							+ "Note: You can change this temporary password anytime. <br><br>"
							,"text/html");
			Transport.send(mm);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to send Mail");	
		} 
		return status;	
	} 

	
	//mail to reset password
	public boolean sendResetPasswordmail(String to,String password,String name) {
		boolean status=false;
		Session session = Session.getInstance(getMailProperties(),
				new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, mail_password);
			}
		});
//		session.setDebug(true);	
		MimeMessage mm=new MimeMessage(session);
		try {
			mm.setHeader("Content-Type", "text/html");
			mm.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mm.setFrom(new InternetAddress(username,"GATEPASS"));
			mm.setSubject("Reset Password | GATEPASS ");		
			mm.setContent(
					"Dear "+name+" <br><br>  "
					+ "Your request to reset the password has been accepted.<br><br>"
					+ "Your Temporary Password is "+password+" . <br>"
					,"text/html");
			Transport.send(mm);

			status=true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to send Mail");	
		} 
		return status;
	}
	
	//to send test link for online test 
	@Caching(evict = {
    	    @CacheEvict(value = "getCredentialByEmail", key = "#candidateDetails.user.email")})
	@Async
	public void candidateLink(CandidateDetails candidateDetails,String password,int assigned_id) {
		String loginUrl=this.url+assigned_id;
		Session session = Session.getInstance(getMailProperties(),
				new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, mail_password);
			}
		});
		session.setDebug(true);	
		MimeMessage mm=new MimeMessage(session);
		try {
			mm.setHeader("Content-Type", "text/html");
//			mm.addRecipient(Message.RecipientType.TO, new InternetAddress("sunny.singh@techwisedigital.com"));
			mm.addRecipient(Message.RecipientType.TO, new InternetAddress(candidateDetails.getUser().getEmail()));
			mm.setFrom(new InternetAddress(username,"GATEPASS"));
			mm.setSubject("Account Info | GATEPASS ");	
			mm.setContent(
							"Dear "+candidateDetails.getUser().getName()+" <br><br> "
							+"Appreciate your application. Congratulations, we have shortlisted you for the further process. "
							+ "There would be 1 hour online test followed by online interview upon meeting the criteria. <br><br>"
							+"The test is a general aptitude test around reasoning, comprehension etc.  <br><br>"
							+ "We have successfully registered you for the test and please find below the credentials.<br><br>"
							+ "Your login credentials are :<br>"
							+ "Username: "+candidateDetails.getUser().getEmail()+"<br>"
							+ "Password: "+password+" <br><br>"
							+ "Please <a href="+loginUrl+">Click here</a> to sign in. <br><br>"
							+"Wish you the best! <br><br>"
							+"Please share any feedback, if any at feedback@gatepass.co.in <br><br>"
							+"Warm regards from<br><br>"
							+"Team Gatepass"
							,"text/html");
			Transport.send(mm);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to send Mail");			
		} 	
	} 
	
	//mail with report attachment
		public boolean reportAttachment(byte[] attachment) {
			boolean status=false; 
			Session session = Session.getInstance(getMailProperties(),
					new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, mail_password);
				}
			});
//			session.setDebug(true);	
			MimeMessage mm=new MimeMessage(session);
			try {
				mm.setHeader("Content-Type", "text/html");
				mm.addRecipient(Message.RecipientType.TO, new InternetAddress("sunny.singh@techwisedigital.com"));
				mm.setFrom(new InternetAddress(username,"GATEPASS"));
				mm.setSubject("Account Confirmation | GATEPASS ");	
				
				DataSource dataSource = new ByteArrayDataSource(attachment, "application/pdf");
				
				MimeBodyPart textBodyPart = new MimeBodyPart();
				textBodyPart.setHeader("Content-Type", "text/html");
				textBodyPart.setText("Report");
				
				
		        MimeBodyPart pdfBodyPart = new MimeBodyPart();
		        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
		        pdfBodyPart.setFileName("sunny.pdf");
		        
		        MimeMultipart mimeMultipart = new MimeMultipart();
		        mimeMultipart.addBodyPart(textBodyPart);
		        mimeMultipart.addBodyPart(pdfBodyPart);

				
				mm.setContent(mimeMultipart);
				Transport.send(mm);
				status=true;
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Failed to send Mail");	
			} 
			return status;	
		} 
	
	
	
	
	
	public Properties getMailProperties() {
		Properties props=new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "mail.techwisedigital.co.in");
		props.put("mail.smtp.port", "587");  
		return props;
	}

}
