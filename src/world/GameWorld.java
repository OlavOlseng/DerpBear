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
import world.entity.actor.Actor;


public class GameWorld {

	//DEBUG STUFF
	LineDrawer ldr;
	dbDebugDraw dbgDraw;
	Pipeline pipeline;
	
	Vec2 g;
	World world;
	int velocityIterations;
	int positionIterations;
	ArrayList<Entity> entities;
	ArrayList<Actor> actors;
	
	public static final float PIXELSCALE = 32;
	
	public GameWorld() {
		g = new Vec2(0.0f, -9.0f);
		world = new World(g, true);
		entities = new ArrayList<Entity>();
		velocityIterations = 8;
		positionIterations = 4;
		
		pipeline = new Pipeline();
		pipeline.setProjectionMatrix(MatrixUtil.getOrthographicProjection(0, 1280, 0, 720));
		ldr = new LineDrawer(10000);
		ldr.setScale(PIXELSCALE, PIXELSCALE);
		dbgDraw = new dbDebugDraw(ldr);
		world.setDebugDraw(dbgDraw);
		
		dbgDraw.setFlags(DebugDraw.e_shapeBit);
		
		test(100);
	}
	
	
	public World getPhysWorld() {
		return this.world; 
	}
	
	public void test(int ents) {
		int s = (int)(Math.sqrt(ents)); 
		for (int x = 0; x < s; x++) {
			for (int y = 0; y < s; y++) {
				new Box(this, new Vec2(x+5f, y+5f), 0.5f, 0.5f, x*y/10, 10);
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
	
	public void render() {
		world.drawDebugData();
		ldr.render(pipeline);
		ldr.clear();
	}
	
	public void update(float dt) {
		world.step(dt, velocityIterations, positionIterations);
//		for(Entity ent : entities) {
//			System.out.println("X:\t" + ent.getTransform().position.x + "\tY:\t" + ent.getTransform().position.y );
//		}
	}
	
}