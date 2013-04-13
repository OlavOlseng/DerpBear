package network.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import entitysystem.component.Component;
import entitysystem.component.PlayerComponent;
import entitysystem.component.RenderComponent;

public class Server implements ConnectionListener {
	private ArrayList<Connection> connections;
	
	public Server(){
		
		ServerConnection conn = new ServerConnection(this);
		connections = new ArrayList<Connection>();
		
		PlayerComponent playerC = new PlayerComponent();
		Component test = playerC;
		System.out.println(test.getClass());
		
		
		try {
			conn.listen("127.0.0.1", 1337);
		} catch (IOException e) {
			System.out.println("Failed to listen");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	@Override
	public void clientReceived(Socket socket) {
		connections.add(new Connection(socket));
	}
	
	public static void main(String[] args) {
		new Server();
	}

}
