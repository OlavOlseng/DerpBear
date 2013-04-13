package world;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import rendering.Pipeline;



public class GameWorld {

	Vec2 g;
	World world;
	int velocityIterations;
	int positionIterations;
	ContactHandler ch;

	
	public GameWorld() {
		ch = new ContactHandler();
		g = new Vec2(0.0f, 0.0f);
		world = new World(g, true);
		world.setContactListener(ch);
		velocityIterations = 8;
		positionIterations = 3;
		
	}
	
	public ContactHandler getContactHandler() {
		return this.ch;
	}
	
	public World getPhysWorld() {
		return this.world; 
	}
	
	public void render(Pipeline pipeline) {
		world.drawDebugData();
	}
	
	public void update(float dt) {
		world.step(dt, velocityIterations, positionIterations);
//		System.out.println(world.getBodyCount());
//		System.out.println(world.getContactCount());
	}
}