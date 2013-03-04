package component;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import world.GameWorld;
import world.gameobject.GameObject;
import component.base.Component;
import component.base.PhysicsComponent;
import component.container.ComponentMessage;

public class PlayerPhysicsComponent extends PhysicsComponent {

	public PlayerPhysicsComponent(GameWorld w, GameObject owner, FixtureDef fDef, BodyDef bDef){
		super(w, owner, fDef, bDef);
		
	}
	@Override
	public void receiveMessage(ComponentMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate(GameObject gameObject, double dt) {
		// TODO Auto-generated method stub
		
	}
	
}
