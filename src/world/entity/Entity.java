/*
 * This class contains everything that you can add to the physicsworld and render.
 * For objects needing gamelogic see actor.
 * 
 * REMARKS:
 * 
  */

package world.entity;

import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import component.base.Component;
import component.base.GraphicsComponent;
import component.base.PhysicsComponent;
import component.container.ComponentContainer;

import rendering.Node;
import rendering.Pipeline;

import util.DepthLevel;
import util.GameConstants;
import world.GameWorld;

public class Entity {
	
	protected Body b;
	protected FixtureDef fDef;
	protected BodyDef bDef;
	protected DepthLevel dpt;
	protected Node node;
	private PhysicsComponent physicsComponent;
	private GraphicsComponent graphicsComponent;
	private ComponentContainer container;
	

	public Entity(GraphicsComponent graphicsComponent, PhysicsComponent physicsComponent){	
		container = new ComponentContainer();
		this.physicsComponent = physicsComponent;
		this.graphicsComponent = graphicsComponent;
		container.addComponent(graphicsComponent);
		container.addComponent(physicsComponent);
	
	}
	
	protected void addToWorld(GameWorld w){
		//You need to instantiate the bodydef and fixturedef before calling this function
		b = w.getPhysWorld().createBody(bDef);
		b.createFixture(fDef);
		b.setSleepingAllowed(false);
		w.add(this);
	}
	
	public void removeFromSimulation() {
		b.getWorld().destroyBody(b);
		b = null;
	}
	
	public Body getBody() {
		return b;
	}
	
	public Vec2 getPosition(){
		return b.getTransform().position;
		
	}
	
	public void setPosition(Vec2 position){
		b.setTransform(position, b.getAngle());
	}
	public void update(float dt){
		
		physicsComponent.onUpdate(this, dt);
		graphicsComponent.onUpdate(this, dt);
		
		
		
		
	}

}
