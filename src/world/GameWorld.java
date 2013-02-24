package world;

import java.util.ArrayList;

import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import world.entity.Box;
import world.entity.Entity;


public class GameWorld {

	Vec2 g;
	World world;
	int velocityIterations;
	int positionIterations;
	ArrayList<Entity> entities;
	
	
	public GameWorld() {
		g = new Vec2(0.0f, 0.0f);
		world = new World(g, true);
		entities = new ArrayList<Entity>();
		velocityIterations = 8;
		positionIterations = 3;
		test();
	}
	
	public World getPhysWorld() {
		return this.world; 
	}
	
	public void test() {
		for (int x = -10; x < 10; x++) {
			for (int y = -10; y < 10; y++) {
				new Box(this, new Vec2(x, y), 0.25f, 0.25f, 0, 10);
			}
		}
	}
	
	
	public void addEntity(Entity ent) {
		entities.add(ent);
	}
	
	public void update(float dt) {
		world.step(dt, velocityIterations, positionIterations);
		for(Entity ent : entities) {
			System.out.println("X:\t" + ent.getTransform().position.x + "\tY:\t" + ent.getTransform().position.y );
		}
	}
	
}