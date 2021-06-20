package com.gatepass.main.configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class WebSocketHandler extends AbstractWebSocketHandler{
	
	private static Set<WebSocketSession> clients=Collections.synchronizedSet(new HashSet<WebSocketSession>());
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Connection Established");
		System.out.println("Session : "+session);
		clients.add(session);
		super.afterConnectionEstablished(session);
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("Connection Closed");
		clients.remove(session);
		super.afterConnectionClosed(session, status);		
	}

	@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("New Text Message Received");
        System.out.println("Message : "+message);
        for (WebSocketSession webSocketSession : clients) {
			webSocketSession.sendMessage(message);
		}       
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received");
        for (WebSocketSession webSocketSession : clients) {
			webSocketSession.sendMessage(message);
		}  
    }
	
}
