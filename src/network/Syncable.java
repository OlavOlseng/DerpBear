package network;

public interface Syncable {
	/**
	 * 
	 * @return true if the syncable has changed and needs to be sent
	 */
	public boolean didChange();
	
	/**
	 * The receivers uses this method to sync its properties with the server side object
	 * @param object - the remote object. Should always be the same type as the implementer.
	 * @return should return itself
	 */
	public Object onRead(Object object);
	
	
	/**
	 * The receivers uses this method to sync its properties with the server side object
	 * @param object - the object that was written. Should always be the same type as the implementer.
	 * @return should return itself
	 */
	public Object onWrite(Object object);
	
	
	
	
	
}

