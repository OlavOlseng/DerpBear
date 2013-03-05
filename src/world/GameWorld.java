package world;

import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import rendering.Pipeline;



public class GameWorld implements ContactListener  {

	Vec2 g;
	World world;
	int velocityIterations;
	int positionIterations;

	
	public GameWorld() {
		g = new Vec2(0.0f, 0.0f);
		world = new World(g, true);
		
		velocityIterations = 8;
		positionIterations = 3;
		world.setContactListener(this);
		
//		test(100);
	}
	
	
	public World getPhysWorld() {
		return this.world; 
	}
	
//	public void test(int ents) {
//		int s = (int)(Math.sqrt(ents));
//		
//		float floorWidth = 700;
//		new Wall(this, floorWidth, 20, new Vec2(400, 550), 0.0f);
//		new Wall(this, floorWidth, 20, new Vec2(400, 50), 0.0f);
//		floorWidth = 500;
//		new Wall(this, floorWidth, 20, new Vec2(50, 300), (float)Math.PI/2.0f);
//		new Wall(this, floorWidth, 20, new Vec2(750, 300), (float)Math.PI/2.0f);
//		
////		TestActor test = new TestActor(this, new Vec2(5.0f, 15.0f), 10);
////		test.getBody().setLinearVelocity(new Vec2(5.0f, 1.0f));
////		test.getBody().setAngularVelocity(10.0f);
////		
//		Box megabox = new Box(this, new Vec2(200,300), 32*4, 32*4, 0, 1000.0f);
//		megabox.getBody().applyLinearImpulse(new Vec2(100000, 0), megabox.getBody().getPosition());
//		
//		float boxSide = 10;
//		for (int x = 0; x < s; x++) {
//			for (int y = 0; y < s; y++) {
//				Box b = new Box(this, new Vec2(x*32 + 300, y*32+150), boxSide, boxSide, x*y/16, 1.0f);
//				b.getBody().applyLinearImpulse(new Vec2(-70, 5), b.getBody().getPosition());
//				b.getBody().applyAngularImpulse(56.0f);
//			}
//		}
//	}
	
	
	
	public void render(Pipeline pipeline) {
		world.drawDebugData();
	}
	
	public void update(float dt) {
	
	}


	@Override
	public void beginContact(Contact arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}
}