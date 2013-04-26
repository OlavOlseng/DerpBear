package network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import network.Syncable;

import entitysystem.component.Component;

public class Connection {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	public Connection(Socket socket){
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	/**
	 * Connects to a server on the specified port and address
	 * 
	 * @param addr - Server address
	 * @param port - Server port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Connection(String addr, int port) throws UnknownHostException, IOException{
		this(new Socket(addr, port));
		
		
		
	
	}
	
	/**
	 * Reads the first block of changed objects from the buffer
	 * Method is blocking
	 * 
	 * @return {@link ArrayList} of components that needs an update
	 * @throws IOException
	 */
	public List<Syncable> readObjects() throws IOException{
		
		
		List<Syncable> syncables = null;

		try {
			syncables = (List<Syncable>) in.readObject();
		} catch (ClassNotFoundException e) {
		
		}
		
		return syncables;
	}
	
	
	/**
	 * 
	 * @param objects - A {@link ArrayList} of components that has changed
	 * @throws IOException
	 */
	public void sendObjects(List<Syncable> objects) throws IOException{
		
		
		out.writeObject(objects);
		out.reset();
	}
	
}
