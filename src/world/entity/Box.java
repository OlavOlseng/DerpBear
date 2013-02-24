package world.entity;

import org.jbox2d.common.Vec2;

import util.DepthLevel;
import world.BodyFactory;
import world.GameWorld;

public class Box extends Entity{
	
	public Box(GameWorld w, Vec2 position, float width, float height, float rotation, float mass) {
		super(DepthLevel.ACTOR_LVL);
		this.bDef = BodyFactory.createDynamicBodyDef(position, rotation);
		
		bDef.linearDamping = (1.0f / (20.0f / mass) + 2.0f) / 0.95f;
		bDef.angularDamping = 1.9f;
		
		this.fDef = BodyFactory.createBox(mass, width/2, height/2);
		fDef.restitution = 0.8f;
		
		this.addToWorld(w);
		
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}
}
