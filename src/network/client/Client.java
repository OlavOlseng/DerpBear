package network.client;

import java.io.IOException;
import java.net.UnknownHostException;

import entitysystem.NetworkClientSystem;

import network.server.Connection;

public class Client {
	private Connection connection;
	private Game game;
	public Client(){
		
		try {
			this.connection = new Connection("127.0.0.1", 1337);
			System.out.println("connected!");
			this.game = new Game(connection);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	public static void main(String[] args) {
		new Client();
	}
}
