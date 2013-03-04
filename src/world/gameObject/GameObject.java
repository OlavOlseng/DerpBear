/*
 * This class contains everything that you can add to the physicsworld and render.
 * For objects needing gamelogic see actor.
 * 
 * REMARKS:
 * 
  */

package world.gameobject;

import component.container.ComponentContainer;

public abstract class GameObject {
	
	protected boolean toBeRemoved;
	protected Transform transform;
	ComponentContainer componentContainer;
	
	public GameObject(Transform t){
		toBeRemoved = false;
		this.transform = t;
		componentContainer = new ComponentContainer(); 
	}
	
	public Transform getTransform() {
		return this.transform;
	}
	
	public void update(float dt){
		onUpdate(dt);
	}
	
	public boolean isToBeRemoved() {
		return toBeRemoved;
	}
	
	public abstract void onUpdate(float dt);
}
