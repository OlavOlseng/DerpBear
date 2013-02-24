package world.entity;

import org.jbox2d.common.Vec2;

import util.DepthLevel;
import world.BodyFactory;
import world.GameWorld;

public class Box extends Entity{
	
	public Box(GameWorld w, Vec2 position, float width, float height, float rotation, float mass) {
		super(DepthLevel.ACTOR_LVL);
		this.bDef = BodyFactory.createDynamicBodyDef(position, rotation);
		
		bDef.linearDamping = (mass / (10000.0f) + 1.5f);
		bDef.angularDamping = (mass / (500.0f) + 1.2f);
		
		this.fDef = BodyFactory.createBox(mass, width/2, height/2);
		fDef.restitution = 0.68f;
		
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
