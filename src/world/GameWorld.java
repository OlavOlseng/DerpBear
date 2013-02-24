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
		g = new Vec2(0.0f, -9.0f);
		world = new World(g, true);
		entities = new ArrayList<Entity>();
		velocityIterations = 8;
		positionIterations = 4;
		test(500);
	}
	
	
	public World getPhysWorld() {
		return this.world; 
	}
	
	public void test(int ents) {
		int s = (int)(Math.sqrt(ents)/2); 
		new Box(this, new Vec2(0.5f,0.5f), 0.25f, 0.25f, 10, 0);
		for (int x = -s; x < s; x++) {
			for (int y = -s; y < s; y++) {
				new Box(this, new Vec2(x, y), 0.25f, 0.25f, 0, 10);
			}
		}
	}
	
	public void addEntity(Entity ent) {
		entities.add(ent);
	}
	
	public void deleteEntity(Entity ent) {
		entities.remove(ent);
		world.destroyBody(ent.getBody());
	}
	
	public void update(float dt) {
		world.step(dt, velocityIterations, positionIterations);
//		for(Entity ent : entities) {
//			System.out.println("X:\t" + ent.getTransform().position.x + "\tY:\t" + ent.getTransform().position.y );
//		}
	}
	
}