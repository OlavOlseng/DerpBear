import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class BodyFactory {
	
	private static BodyDef createDynamicBodyDef() {
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		return def;
	}
	
	private static BodyDef createStaticBodyDef() {
		BodyDef def = new BodyDef();
		def.type = BodyType.STATIC;
		return def;
	}
	
	private static BodyDef createKinematicBodyDef() {
		BodyDef def = new BodyDef();
		def.type = BodyType.KINEMATIC;
		return def;
	}
	
	public static Body createBox(World w, float mass, float width, float height, Vec2 position, float rotation) {
		BodyDef bDef = createDynamicBodyDef();
		bDef.position = position;
		bDef.angle = rotation;
		Body b = w.createBody(bDef);
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width, height);
		
		FixtureDef fDef = new FixtureDef(); 
		fDef.shape = ps;
		fDef.density = width*height/mass;
		
		b.createFixture(fDef);
		
		return b; 
	}
	
	public static Body createCircle(World w, float mass, float radius, Vec2 position, float rotation) {
		BodyDef bDef = createDynamicBodyDef();
		bDef.position = position;
		bDef.angle = rotation;
		Body b = w.createBody(bDef);
		
		CircleShape cs = new CircleShape();
		cs.m_radius = radius;
		
		FixtureDef fDef = new FixtureDef(); 
		fDef.shape = cs;
		fDef.density = (float) ((Math.PI)*(Math.pow(radius,2.0)/mass));
		b.createFixture(fDef);
		
		return b;
	}
	
	public static Body createWall(World w, float width, float height, Vec2 position, float rotation) {
		BodyDef bDef  = createStaticBodyDef();
		bDef.position = position;
		bDef.angle = rotation;
		Body b = w.createBody(bDef);
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width, height);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = ps;
		b.createFixture(fDef);
		return b;

	}
	
	public static Body createActor(World w, float radius, Vec2 position, float rotation) {
		BodyDef bDef = createKinematicBodyDef();
		bDef.position = position;
		bDef.angle = rotation;
		Body b = w.createBody(bDef);
		
		CircleShape cs = new CircleShape();
		cs.m_radius = radius;
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = cs;
		b.createFixture(fDef);
		
		return b;
	}
}