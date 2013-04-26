package network.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitysystem.component.Component;
import entitysystem.component.PlayerComponent;
import entitysystem.component.RenderComponent;

public class Server implements ConnectionListener {
	private List<Connection> connections;
	private Game game;
	public Server(){
		this.game = new Game();
		ServerConnection conn = new ServerConnection(this);
		connections =  Collections.synchronizedList(new ArrayList<Connection>());
		
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
		System.out.println("Client connected!");
		Connection connection = new Connection(socket);
		connections.add(connection);
		game.addClient(connection);
	}
	
	public static void main(String[] args) {
		new Server();
	}

}
