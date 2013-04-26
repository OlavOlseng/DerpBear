package entitysystem.component;

import network.Syncable;

public class PlayerComponent extends Component implements Syncable {
	
	private int playerNumber;
	private transient boolean didChange;
	public int getPlayerNumber() {
		return playerNumber;
	}

	@Override
	public boolean didChange() {
		
		return didChange;
	}

	@Override
	public Object onRead(Object object) {
		
		return null;
	}

	@Override
	public Object onWrite(Object object) {
		didChange = false;
		return null;
	}

}

