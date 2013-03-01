package world.entity;

import org.jbox2d.common.Vec2;

import rendering.Node;

import util.DepthLevel;
import util.GameConstants;
import world.BodyFactory;
import world.GameWorld;

public class Box extends Entity{
	
	public Box(GameWorld w, Vec2 position, float width, float height, float rotation, float mass) {
		this(w,new Node(),position,width,height,rotation,mass);
	}
	
	public Box(GameWorld w, Node node, Vec2 position, float width, float height, float rotation, float mass) {
		super(DepthLevel.ACTOR_LVL,node);
		position.x /= GameConstants.PIXELSCALE;
		position.y /= GameConstants.PIXELSCALE;
		width /=GameConstants.PIXELSCALE;
		height /= GameConstants.PIXELSCALE;
		
		this.bDef = BodyFactory.createDynamicBodyDef(position, rotation);
		
		bDef.linearDamping = (mass / (10000.0f) + 1.5f);
		bDef.angularDamping = (mass / (500.0f) + 1.2f);
		
		this.fDef = BodyFactory.createBox(mass, width/2, height/2);
		fDef.restitution = 0.68f;
		
		this.addToWorld(w);
		
	}
	
	
	
	@Override
	public void onUpdate(float dt) {
		// TODO Auto-generated method stub
		
	}
}
