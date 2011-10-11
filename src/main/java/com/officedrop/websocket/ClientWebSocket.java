package com.officedrop.websocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jetty.websocket.WebSocket;

public class ClientWebSocket implements WebSocket.OnTextMessage {

	private Connection connection;	
	private List<Message> messages = Collections.synchronizedList( new ArrayList<Message>() );
	
	public List<Message> getMessages() {
		return messages;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void onClose(int closeCode, String message) {
		//System.out.printf( "Closing with %d and message %s%n", closeCode, message );
	}

	public void onOpen(Connection connection) {
		//System.out.printf( "Opened connection %s%n", connection );
		this.connection = connection;	
	}

	public void onMessage(String text) {
		//System.out.printf( "Received message on client - %s%n", text );
		this.messages.add( new Message(text) );
	}

}
