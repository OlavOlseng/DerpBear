package world;

import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import rendering.Pipeline;



public class GameWorld {

	Vec2 g;
	World world;
	int velocityIterations;
	int positionIterations;

	
	public GameWorld() {
		g = new Vec2(0.0f, 0.0f);
		world = new World(g, true);
		
		velocityIterations = 8;
		positionIterations = 3;
		
	}
	
	
	public World getPhysWorld() {
		return this.world; 
	}
	
	public void render(Pipeline pipeline) {
		world.drawDebugData();
	}
	
	public void update(float dt) {
		world.step(dt, velocityIterations, positionIterations);
	}
}