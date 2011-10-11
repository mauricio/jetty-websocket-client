package com.officedrop.websocket;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

public class OfficeDropWebSocketClient {

	private static final List<ClientWebSocket> clients = new ArrayList<ClientWebSocket>();

	public static void main(String[] args) throws Exception {

		WebSocketClientFactory factory = new WebSocketClientFactory();
		factory.setBufferSize(4096);
		factory.start();

		for (int x = 0; x < 1000; x++) {

			WebSocketClient client = factory.newWebSocketClient();
			client.setMaxIdleTime(30000);
			client.setProtocol("ping." + x);

			ClientWebSocket socket = new ClientWebSocket();			
			client.open(new URI( "ws://localhost:8080/ping" ), socket, 10, TimeUnit.SECONDS );
			
			clients.add( socket );
			
		}

		System.out.println("Connected to websocket server");

		Scanner s = new Scanner(System.in);

		boolean leave = false;

		while (!leave) {

			System.out.println("1: print stats");
			System.out.println("2: leave");

			int command = s.nextInt();

			switch (command) {
			case 1: {

				long total = 0;				
				long maximum = 0;
				long minimum = 0;
				int count = 0;
				
				for ( ClientWebSocket socket : clients ) {
					
					for ( Message message : socket.getMessages() ) {
						count++;
						long time = message.getDifference();
						total+= time;
						
						if ( total > maximum ) {
							maximum = total;
						}
						
						if ( total < minimum ) {
							minimum = total;
						}
						
					}
					
				}				
				
				System.out.printf( "With %d messages - average -> %s minimum -> %s - maximum -> %s%n", count, total/count, minimum, maximum );
				
			}
				break;
			case 2: {
				leave = true;
			}
				break;
			default: {
				System.out.println("Command not found");
			}
			}

		}

		for ( ClientWebSocket socket : clients ) {
			socket.getConnection().disconnect();
		}
		
		factory.stop();
	}

}
