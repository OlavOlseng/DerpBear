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

public abstract class Entity {
	
	protected Body b;
	protected FixtureDef fDef;
	protected BodyDef bDef;
	protected DepthLevel dpt;
	protected Node node;

	


	public Entity(DepthLevel dpt,Component ...components){
		
		this(dpt,new Node());

	}
	public Entity(DepthLevel dpt,Node node){
		this.node = node;
		setDepth(dpt);
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

	public void setNode(Node node)
	{
		this.node = node;
		this.node.setDepth(dpt.getDepth());
		
		
	}
	
	
	public void setDepth(DepthLevel depth){
		this.dpt = depth;
	}
	public DepthLevel getDepth() {
		return dpt;
	}

	
	public abstract void render();
	

	public void update(float dt){
		onUpdate(dt);
		
	}

	public abstract void onUpdate(float dt);

}
