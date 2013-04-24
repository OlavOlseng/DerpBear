package entitysystem.component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import network.Syncable;

public class InputComponent extends Component implements Syncable,
		Serializable, Iterable<Integer> {

	private transient List<Integer> keyBoardInput;
	private transient boolean didChange;

	public InputComponent() {

		keyBoardInput = Collections.synchronizedList(new ArrayList<Integer>());
	}

	public void addKeyBoardInput(int key) {

		keyBoardInput.add(key);
		didChange = true;

	}

	
	private void addKeyBoardInputs(List<Integer> keys) {
		synchronized (keys) {
			this.keyBoardInput.addAll(keys);
		}

	}

	@Override
	public boolean didChange() {
		if (didChange) {
			didChange = false;
			return true;
		}
		return false;
	}

	@Override
	public Object sync(Object object) {
		
		InputComponent remote = (InputComponent) object;
		this.addKeyBoardInputs(remote.keyBoardInput);
		if(this.keyBoardInput.size() > 0)
			System.out.println(this.keyBoardInput);

		return null;
	}

	@Override
	public Iterator<Integer> iterator() {

		return this.iterator();

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
	
}
