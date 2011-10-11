package com.officedrop.websocket;

public class Message {

	private long currentTime;
	private long serverSendingTime;
	
	public Message( String message ) {
		this.serverSendingTime = Long.valueOf(message);
		this.currentTime = System.currentTimeMillis();
	}
	
	public long getDifference() {
		return this.currentTime - this.serverSendingTime;
	}
	
}
