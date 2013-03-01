package component.container;

import java.util.ArrayList;
import java.util.Iterator;

import component.base.Component;



public class ComponentContainer implements Iterable<Component> {

	private ArrayList<Component> components;
	public ComponentContainer(){
		components = new ArrayList<Component>();
	}
	public void addComponent(Component component){
		components.add(component);
	}
	
	public void removeComponent(Component component){
		components.remove(component);
	}
	public void sendMessage(ComponentMessage message){
		for (Component component: components){
			if(component != message.getSender())
				component.receiveMessage(message);
		}
	}
	
	public Iterator<Component> iterator() {
		// TODO Auto-generated method stub
		return components.iterator();
	}
}
