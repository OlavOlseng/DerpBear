package entitysystem.component;

import java.util.ArrayList;

import network.Syncable;

public class NetworkComponent extends Component {

	private ArrayList<Syncable> syncables;
	public NetworkComponent(Syncable...syncables){
		this.syncables = new ArrayList<Syncable>(syncables.length);
		for(int i = 0; i < syncables.length; i++){
			this.syncables.add(syncables[i]);
		}
	}
	
	public ArrayList<Syncable> getSyncableComponents(){
		return this.syncables;
	}
}
