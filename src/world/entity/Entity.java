/*
 * This class contains everything that you can add to the physicsworld and render.
 * For objects needing gamelogic see actor.
 * 
 * REMARKS:
 * 
 */

package world.entity;

import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import rendering.Node;

import util.DepthLevel;
import world.GameWorld;

public abstract class Entity {
	
	protected Body b;
	protected FixtureDef fDef;
	protected BodyDef bDef;
	protected DepthLevel dpt;
	protected Node node;
	
	public Entity(DepthLevel dpt){
		this.dpt = dpt;
	}
	
	protected void addToWorld(GameWorld w){
		//You need to instantiate the bodydef and fixturedef before calling this function
		b = w.getPhysWorld().createBody(bDef);
		b.createFixture(fDef);
		w.add(this);
	}
	
	public void removeFromSimulation() {
		b.getWorld().destroyBody(b);
		b = null;
	}
	
	public Body getBody() {
		return b;
	}
	
	public void setDepth(DepthLevel dpt) {
		this.dpt = dpt;
		//TODO add node depth;
	}
	
	public Transform getTransform() {
		return b.getTransform();
	}
	
	public abstract void render();
	public abstract void update(float dt);
}
