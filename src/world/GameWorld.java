package world;

import java.util.ArrayList;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import rendering.LineDrawer;
import rendering.MatrixUtil;
import rendering.Pipeline;

import world.entity.Box;
import world.entity.Entity;
import world.entity.Wall;
import world.entity.actor.Actor;
import world.entity.actor.TestActor;


public class GameWorld {

	//DEBUG STUFF
	
	
	Vec2 g;
	World world;
	int velocityIterations;
	int positionIterations;
	ArrayList<Entity> entities;
	ArrayList<Actor> actors;
	
	
	
	public GameWorld() {
		g = new Vec2(0.0f, -0.0f);
		world = new World(g, true);
		entities = new ArrayList<Entity>();
		actors = new ArrayList<Actor>();
		velocityIterations = 8;
		positionIterations = 4;
		
		//test(100);
	}
	
	
	public World getPhysWorld() {
		return this.world; 
	}
	
	public void test(int ents) {
		int s = (int)(Math.sqrt(ents)); 
		
		float floorWidth = 30;
		new Wall(this, floorWidth, 1, new Vec2(floorWidth/2 + 10, 3.0f), 0.0f);
		new Wall(this, floorWidth, 1, new Vec2(floorWidth/2 + 10, 32.0f), 0.0f);
		new Wall(this, floorWidth, 1, new Vec2(46.0f, floorWidth/2 + 3.0f), (float)Math.PI/2.0f);
		new Wall(this, floorWidth, 1, new Vec2(4.0f, floorWidth/2 + 3.0f), (float)Math.PI/2.0f);
		
		TestActor test = new TestActor(this, new Vec2(5.0f, 15.0f), 0);
		test.getBody().setLinearVelocity(new Vec2(5.0f, 0));
		test.getBody().setAngularVelocity(3.0f);
		
		for (int x = 0; x < s; x++) {
			for (int y = 0; y < s; y++) {
				new Box(this, new Vec2(x+15f, y+15f), 0.35f, 0.35f, x*y/16, 1);
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