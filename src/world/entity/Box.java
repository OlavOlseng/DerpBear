package world.entity;

import org.jbox2d.common.Vec2;

import util.DepthLevel;
import world.BodyFactory;
import world.GameWorld;

public class Box extends Entity{
	
	public Box(GameWorld w, Vec2 position, float width, float height, float rotation, float mass) {
		super(DepthLevel.ACTOR_LVL);
		this.bDef = BodyFactory.createDynamicBodyDef(position, rotation);
		this.fDef = BodyFactory.createBox(mass, width, height);
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
