package network.server;

import java.net.Socket;

public interface ConnectionListener {

	public void clientReceived(Socket socket);
		
	
}
