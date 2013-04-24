package entitysystem.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import network.Syncable;

public class NetworkComponent extends Component implements Syncable, Serializable{

	
	
	private List<Syncable> hostWriteSyncables;
	private List<Syncable> clientWriteSyncables;
	private transient List<Syncable> newSyncables;
	private transient boolean didChange;
	
	
	public NetworkComponent(){
		
		hostWriteSyncables = Collections.synchronizedList( new ArrayList<Syncable>());
		clientWriteSyncables =  Collections.synchronizedList( new ArrayList<Syncable>());
		newSyncables = Collections.synchronizedList( new ArrayList<Syncable>());
		
	}
	
	
	public void addSyncable(Syncable syncable, boolean clientReadOnly, boolean hostReadOnly){
		
		if(!clientReadOnly){
			clientWriteSyncables.add(syncable);
		}
		
		if(!hostReadOnly){
			hostWriteSyncables.add(syncable);
		}else{
			newSyncables.add(syncable);
			
		}
	
		this.didChange = true;
	}
	
	//Only used to avoid unnecessary allocs
	private ArrayList<Syncable> listCacheClient = new ArrayList<Syncable>();
	
	public List<Syncable> getReadyClientWriteSyncables(){
		
		
		if(listCacheClient.size() == 0){
			synchronized (clientWriteSyncables) {
				for(Syncable syncable: clientWriteSyncables){
					if(syncable.didChange()){
						listCacheClient.add(syncable);
					}
				}
				}
			return listCacheClient;
		}else{
			
			return listCacheClient;
		}
		
	}
	
	//Only used to avoid unnecessary allocs
	private ArrayList<Syncable> listCacheHost = new ArrayList<Syncable>();
	
	public List<Syncable> getReadyHostWriteSyncables(){
		
		if(listCacheHost.size() == 0){
			if(newSyncables.size() > 0){
				synchronized (newSyncables) {
					listCacheHost.addAll(newSyncables);
					newSyncables.clear();
				}
			}
			synchronized (hostWriteSyncables) {
				for(Syncable syncable: hostWriteSyncables){
					if(syncable.didChange()){
						listCacheHost.add(syncable);
					}
				}
			}
			return listCacheHost;
		}else{
			
			return listCacheHost;
		}
		
	}

	
	/**
	 * Warning slow
	 * @return A complete copy of the gamestate that should be synced.
	 */
	public List<Syncable> getAllHostWriteSyncables(){
		ArrayList<Syncable> retVal = new ArrayList<Syncable>();
		retVal.addAll(hostWriteSyncables);
		retVal.removeAll(clientWriteSyncables);
		retVal.addAll(clientWriteSyncables);
		return retVal;
	}
	
	/**
	 * Warning slow
	 * @return A complete copy of the gamestate that should be synced.
	 */
	public List<Syncable> getAllClientWriteSyncables(){
		
		return this.clientWriteSyncables;
	}
	
	
	public void clear(){
		listCacheClient.clear();
		listCacheHost.clear();
	}

	@Override
	public boolean didChange() {
		if(didChange){
			didChange = false;
			return true;
		}
		return false;
	}


	@Override
	public Object sync(Object object) {
	
		
		return null;
	}
}
