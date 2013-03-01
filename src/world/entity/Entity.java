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
	
	public Entity(DepthLevel dpt){
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
			node.setDepth(dpt.getDepth());
		
	}
	
	public Node getNode() {
		return node;
	}
	public void setNode(Node node)
	{
		this.node = node;
		this.node.setDepth(dpt.getDepth());
		
		
	}
	
	public abstract void render();
	
	public void update(float dt){
		
		onUpdate(dt);
		
		Transform transform =  b.getTransform();
		node.setPosition(transform.position.x*GameConstants.PIXELSCALE, transform.position.y*GameConstants.PIXELSCALE);
		node.setOrientation(transform.getAngle());
		
		
		
	}
	
	public abstract void onUpdate(float dt);
}
