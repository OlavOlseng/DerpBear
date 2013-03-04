package component.base;


import world.gameobject.GameObject;
import component.container.ComponentContainer;
import component.container.ComponentMessage;

public abstract class Component {

	private ComponentContainer container;
	
	public ComponentContainer getContainer() {
		return container;
	}
	
	public void setContainer(ComponentContainer container){
		this.container = container;
	}

	public abstract void receiveMessage(ComponentMessage message);
	public abstract void onUpdate(GameObject gameObject, float dt);
}
