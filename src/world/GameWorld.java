package world;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import rendering.Pipeline;

import world.entity.Box;
import world.entity.Entity;
import world.entity.Wall;
import world.entity.actor.Actor;

public class GameWorld {

	Vec2 g;
	World world;
	int velocityIterations;
	int positionIterations;
	ArrayList<Entity> entities;
	ArrayList<Actor> actors;
	
	public GameWorld() {
		g = new Vec2(0.0f, 0.0f);
		world = new World(g, true);
		entities = new ArrayList<Entity>();
		actors = new ArrayList<Actor>();
		velocityIterations = 8;
		positionIterations = 3;
		
		test(100);
	}
	
	
	public World getPhysWorld() {
		return this.world; 
	}
	
	public void test(int ents) {
		int s = (int)(Math.sqrt(ents));
		
		float floorWidth = 700;
		new Wall(this, floorWidth, 20, new Vec2(400, 550), 0.0f);
		new Wall(this, floorWidth, 20, new Vec2(400, 50), 0.0f);
		floorWidth = 500;
		new Wall(this, floorWidth, 20, new Vec2(50, 300), (float)Math.PI/2.0f);
		new Wall(this, floorWidth, 20, new Vec2(750, 300), (float)Math.PI/2.0f);
		
//		TestActor test = new TestActor(this, new Vec2(5.0f, 15.0f), 10);
//		test.getBody().setLinearVelocity(new Vec2(5.0f, 1.0f));
//		test.getBody().setAngularVelocity(10.0f);
//		
		Box megabox = new Box(this, new Vec2(200,300), 32*4, 32*4, 0, 1000.0f);
		megabox.getBody().applyLinearImpulse(new Vec2(100000, 0), megabox.getBody().getPosition());
		
		float boxSide = 10;
		for (int x = 0; x < s; x++) {
			for (int y = 0; y < s; y++) {
				Box b = new Box(this, new Vec2(x*32 + 300, y*32+150), boxSide, boxSide, x*y/16, 1.0f);
				b.getBody().applyLinearImpulse(new Vec2(-70, 5), b.getBody().getPosition());
				b.getBody().applyAngularImpulse(56.0f);
			}
		}
	}
	
	public void add(Entity ent) {
		entities.add(ent);
	}
	
	public void add(Actor act) {
		actors.add(act);
	}
	
	public void delete(Entity ent) {
		world.destroyBody(ent.getBody());
	}
	
	public void delete(Actor act) {
		world.destroyBody(act.getBody());
	}
	
	public void render(Pipeline pipeline) {
		world.drawDebugData();
	}
	
	public void update(float dt) {
		world.step(dt, velocityIterations, positionIterations);
		for(Entity ent : entities) {
			ent.update(dt);
			
		}
	}
}