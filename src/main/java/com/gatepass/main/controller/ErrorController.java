package com.gatepass.main.controller;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.MessageModel;

@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleDisabledException(DisabledException e){
        final String message = e.getMessage() ;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new MessageModel(401, message));
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new MessageModel(401, "Provide Valid Values"));
    }	
	
	@ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageModel(401, e.getMessage()));
    }
	
	@ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e){
        final String message = e.getMessage() ;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new MessageModel(401, message));
    }
	
	@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e){
        final String message = "Access Denied";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new MessageModel(403, message));
    }
	
	@ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new MessageModel(404, e.getMessage()));
    }
	
	@ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKeyException(DuplicateKeyException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new MessageModel(404, e.getMessage()));
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateMySQLKeyException(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageModel(404, "Duplicate Entries"));
    }
}
