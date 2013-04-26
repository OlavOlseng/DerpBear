package entitysystem.component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import world.gameobject.Transform;

import network.Syncable;

public class InputComponent extends Component implements Syncable,
		Serializable{

	private transient List<Integer> keyBoardInput;
	private transient boolean didChange;
	private transient int inputReadCursor;

	public InputComponent() {

		keyBoardInput = Collections.synchronizedList(new ArrayList<Integer>());
	}

	/**
	 * Appends a key to the input buffer
	 * @param key - Key to be appended
	 */
	public void addKeyBoardInput(int key) {
	
		keyBoardInput.add(key);
		didChange = true;

	}

	/**
	 * Checks if there is any unprocessed keys in the buffer
	 * @return - True if there is any unprocessed keys in the buffer. False otherwise.
	 */
	public boolean hasNext(){
		
		return inputReadCursor < keyBoardInput.size();
	}
	
	/**
	 * Get the next unprocessed key in the buffer.
	 * @return - unprocessed key from the buffer.
	 */
	public int getNextKey(){
		return keyBoardInput.get(inputReadCursor++);
		
	}
	
	
	
	private void addKeyBoardInputs(List<Integer> keys) {
		synchronized (this.keyBoardInput) {
			this.keyBoardInput.addAll(keys);
		}

	}

	@Override
	public boolean didChange() {
		System.out.println(didChange);
		return didChange;
	}

	@Override
	public Object onRead(Object object) {
		
		InputComponent remote = (InputComponent) object;
		this.addKeyBoardInputs(remote.keyBoardInput);
	
		return null;
	}

	
	private void writeObject(ObjectOutputStream oos) throws IOException {
			oos.defaultWriteObject();
			oos.writeObject(new ArrayList<Integer>(keyBoardInput));
	}
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
			    ois.defaultReadObject();
				
			    if(keyBoardInput != null){
			    
				    synchronized (keyBoardInput) {
				    	this.keyBoardInput = Collections.synchronizedList((List<Integer>) ois.readObject());
					}
			    }else{
			    	this.keyBoardInput = Collections.synchronizedList((List<Integer>) ois.readObject());
			    }

	}

	@Override
	public Object onWrite(Object object) {
		didChange = false;
		synchronized (keyBoardInput) {
			
			this.keyBoardInput.clear();
		}
		
		return this;
	}
	
}
