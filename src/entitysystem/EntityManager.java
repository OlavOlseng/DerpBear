package entitysystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import entitysystem.component.Component;

public class EntityManager {
	private HashMap<Long, Entity> entities;
	private HashMap<String, HashMap<Long, Component>> componentsByClass;
	private Long lowestEID;
	
	
	public EntityManager(){
		entities = new HashMap<Long, Entity>();
		componentsByClass = new HashMap<String, HashMap<Long, Component>>();
		lowestEID = Long.MIN_VALUE+1;
	}
	
	public long generateNewEID(){
		return lowestEID++;
	}
	
	public Entity createEntity(){
		Long eid = generateNewEID();
		Entity entity = new Entity(eid, this);
		entities.put(eid, entity);
		return entity;
	}
	
	public void addComponentToEntity(Component component,Entity entity){
		HashMap<Long, Component> components = componentsByClass.get(component.getClass().getName());
		if(components == null){
			components = new HashMap<Long, Component>();
			componentsByClass.put(component.getClass().getName(), components);
		}
		component.setOwnerEntity(entity);
		components.put(entity.getEID(), component);
	}
	
	public Component getComponentOfClassForEntity(Class type,Entity entity){
		
		return componentsByClass.get(type.getName()).get(entity.getEID());
	}
	
	public Collection<Component> getAllComponentsOfClass(Class type){
		HashMap<Long, Component> components = componentsByClass.get(type.getName());
		if(components != null)
		return components.values();
		
		return null;
	}
	
	public void removeEntity(Entity entity){
		for(HashMap<Long, Component> components : componentsByClass.values()){
			
			if(components.get(entity.getEID()) != null){
				components.remove(entity.getEID());
			}
		}
		
	}
	
	public ArrayList<Entity> getAllEntitiesPossesingComponentOfClass(Class type){
		HashMap<Long, Component> components = componentsByClass.get(type.getName());
		if(components != null){
			ArrayList<Entity> retval = new ArrayList<Entity>(components.size());
			for(Long eid : components.keySet()){
				retval.add(entities.get(eid));
			}
			return retval;
		}else{
			return new ArrayList<Entity>();
		}
	}
	
	public ArrayList<Entity> getAllEntitiesPossesingComponentsOfClass(Class ...types){
		
		ArrayList<HashMap<Long, Component>> components = new ArrayList<HashMap<Long,Component>>(types.length);
		
		for(int i = 0; i< types.length; i++){
			HashMap<Long, Component> componentMap = componentsByClass.get(types[i].getName());
			if(componentMap != null)
				components.add(componentMap);
			else
				components.add(new HashMap<Long,Component>());
		}
		
		java.util.Collections.sort(components, new HashMapComparator());
		
		Set<Long> currentKeySet = new HashSet<Long>();
		currentKeySet.addAll(components.get(0).keySet());
		
		for(int i = 1; i< components.size();i++){
			Set<Long> nextKeySet = components.get(i).keySet();
			currentKeySet.retainAll(nextKeySet);
		}
		
		ArrayList<Entity> retval = new ArrayList<>(currentKeySet.size());
		
		for(Long eid:currentKeySet){
			retval.add(entities.get(eid));
		}
		return retval;
	}
	
	private class HashMapComparator implements Comparator<HashMap<Long, Component>>{

		@Override
		public int compare(HashMap<Long, Component> o1,HashMap<Long, Component> o2) {
	
			return o1.size() - o2.size();
		}
		
	}
}
