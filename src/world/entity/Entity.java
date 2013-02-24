package world.entity;


import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public abstract class Entity {
	
	protected Body b;
	protected FixtureDef fDef;
	protected BodyDef bDef;
	protected double health;
	
	protected void addToWorld(World w){
		//You need to instantiate the bodydef and fixturedef before calling this function
		b = w.createBody(bDef);
		b.createFixture(fDef);
	}
	
	public Transform getTransform() {
		return b.getTransform();
	}
	
	public abstract void render();
	public abstract void update(float dt);
	
	
}
