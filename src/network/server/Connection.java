package network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import network.Syncable;

import entitysystem.component.Component;

public class Connection {

	private Socket socket;
	
	public Connection(Socket socket){
		this.socket = socket;
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
		this.socket = new Socket(addr, port);
	
	}
	
	/**
	 * Reads the first block of changed objects from the buffer
	 * Method is blocking
	 * 
	 * @return {@link ArrayList} of components that needs an update
	 * @throws IOException
	 */
	public ArrayList<Syncable> readObjects() throws IOException{
		
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		try {
			return (ArrayList<Syncable>) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param objects - A {@link ArrayList} of components that has changed
	 * @throws IOException
	 */
	public void sendObjects(ArrayList<Syncable> objects) throws IOException{
		
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(objects);
	}
	
}
