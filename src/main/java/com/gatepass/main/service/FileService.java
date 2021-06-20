package com.gatepass.main.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.gatepass.main.miscellaneous.CustomException;

@Service
public class FileService {
	
private Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private AmazonS3 s3client;

	@Value("${jsa.s3.bucket}")
	private String bucketName;

	@Cacheable(value = "downloadFile", key = "#keyName")
	public byte[] downloadFile(String keyName) throws IOException {	
		InputStream in=null;
		try {			
            System.out.println("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(
            		bucketName, keyName));
            System.out.println("Content-Type: "  + 
            		s3object.getObjectMetadata().getContentType());
           // Utility.displayText(s3object.getObjectContent());
            logger.info("===================== Import File - Done! =====================");
            in=s3object.getObjectContent();
            return IOUtils.toByteArray(in);
            
        } catch (AmazonServiceException ase) {
        	logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
        	logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } 	
		  return null;
	}

	
	public boolean uploadFile(String keyName,  MultipartFile image) {	
		boolean status=false;
		
		try {	
			byte[] bytes = IOUtils.toByteArray(image.getInputStream());
			ObjectMetadata metadata=new ObjectMetadata();
			metadata.setContentLength(bytes.length);
			metadata.setContentType(image.getContentType());
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);			
	        s3client.putObject(new PutObjectRequest(bucketName, keyName, byteArrayInputStream, metadata));
	        status=true;
	        logger.info("===================== Upload File - Done! =====================");	        
		} 
		catch (IOException e) {
			logger.info("IO Exception Caught") ;	
			throw new CustomException("CV not saved");
        }
		catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
		return status;
	}


}
