package world.entity;


import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import world.GameWorld;

public abstract class Entity {
	
	protected Body b;
	protected FixtureDef fDef;
	protected BodyDef bDef;
	protected double health;
	
	protected void addToWorld(GameWorld w){
		//You need to instantiate the bodydef and fixturedef before calling this function
		b = w.getPhysWorld().createBody(bDef);
		b.createFixture(fDef);
		w.addEntity(this);
	}
	
	public Body getBody() {
		return b;
	}
	
	public Transform getTransform() {
		return b.getTransform();
	}
	
	
	
	public abstract void render();
	public abstract void update(float dt);
	
	
}
