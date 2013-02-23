package world;

import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;


public class GameWorld {

	public static void main(String[] argv) {
		GameWorld t = new GameWorld();
		t.start();
	}

	WorldManifold wm = new WorldManifold();
	Vec2 g;
	World world;
	int velocityIterations;
	int positionIterations;
	Body box;
	Body wall;
	Body actor;
	
	public GameWorld() {
		
		g = new Vec2(0.0f, 0.0f);
		world = new World(g, true);
		velocityIterations = 8;
		positionIterations = 3;
	
	}
	
	public void start() {
		
//		box = BodyFactory.createBox(world, 10, 5, 5);
		box = BodyFactory.createCircle(world, 0.5f, 10.0f, new Vec2(20.0f, 0.5f), 0);
		actor = BodyFactory.createActor(world, 1.0f, new Vec2(0.0f, 0.0f), 0);
		actor.setLinearVelocity(new Vec2(10.0f, 0.0f));
//		wall = BodyFactory.createWall(world, 10.0f, 2.0f, new Vec2(0.0f, -5.0f), (float)(-Math.PI/6.0f));  
		for(int i = 0; i < 100; i++){
			update(1.0f/60.0f);
		}
	}
	
	public void update(float dt) {
		System.out.println("CIRCLE:\t" + box.getPosition().x + "\t" + box.getPosition().y + "\t" + box.getAngle());
		System.out.println("ACTOR:\t" + actor.getPosition().x + "\t" + actor.getPosition().y + "\t" + actor.getAngle());
		world.step(dt, velocityIterations, positionIterations);
	}
	
}