package world;

import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import world.entity.Box;


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
	Box box;
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
		box = new Box(world, new Vec2(-1.0f, 0.0f), 2.0f, 1.0f, 0.0f, 5.0f);
		
		for(int i = 0; i < 100; i++){
			update(1.0f/60.0f);
		}
	}
	
	public void update(float dt) {
		Transform t = box.getTransform();
		System.out.println("X: " + t.position.x + "\t" + "Y: " + t.position.y + "\t" + "Angle: " + t.getAngle());
		world.step(dt, velocityIterations, positionIterations);
	}
	
}