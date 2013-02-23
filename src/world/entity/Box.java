package world.entity;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import world.BodyFactory;

public class Box extends Entity{
	
	public Box(World w, Vec2 position, float width, float height, float rotation, float mass) {
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
