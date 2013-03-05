package component;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.ContactEdge;

import world.GameWorld;
import world.gameobject.GameObject;
import component.base.PhysicsComponent;

public class KinematicPhysicsComponent extends PhysicsComponent{

	public KinematicPhysicsComponent(GameWorld w, GameObject owner, FixtureDef fDef, BodyDef bDef) {
		super(w, owner, fDef, bDef,false);
		
	}
	
	public void onUpdate(GameObject owner,float dt){
		super.onUpdate(owner, dt);
		ContactEdge contactEdge = body.getContactList();
		if(contactEdge != null){
			Vec2 normal = body.getContactList().contact.getManifold().localNormal;
		//	System.out.println(normal);
			
		}
	}

}
