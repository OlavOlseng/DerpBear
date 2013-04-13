package network.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerConnection {

	private ConnectionListener listener;
	private InternalListener worker;
	private int port;
	private ServerSocket server;
	private String address;
	public ServerConnection(ConnectionListener listener){
		
		this.listener = listener;
	}
	
	public void listen(String address, int port) throws IOException{
		
		this.port = port;
		this.address = address;
		server = new ServerSocket(port);
		worker = new InternalListener();	
		worker.start();
		
	}
	
	
	private class InternalListener extends Thread implements Runnable{
		
		
		public void run()  {
			
			while(true){
				Socket socket = null;
				try {
					socket = server.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				listener.clientReceived(socket);
				
			}
			
		}
		
	}
}
