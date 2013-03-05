package entitysystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;

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
		System.out.println(component.getClass().getName());
		HashMap<Long, Component> components = componentsByClass.get(component.getClass().getName());
		if(components == null){
			components = new HashMap<Long, Component>();
			componentsByClass.put(component.getClass().getName(), components);
		}
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
}
