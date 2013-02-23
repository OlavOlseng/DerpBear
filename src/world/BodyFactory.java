package world;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class BodyFactory {
	
	public static BodyDef createDynamicBodyDef(Vec2 position, float rotation) {
		BodyDef def = new BodyDef();
		def.position = position;
		def.angle = rotation;
		def.type = BodyType.DYNAMIC;
		return def;
	}
	
	public static BodyDef createStaticBodyDef(Vec2 position, float rotation) {
		BodyDef def = new BodyDef();
		def.position = position;
		def.angle = rotation;
		def.type = BodyType.STATIC;
		return def;
	}
	
	public static BodyDef createKinematicBodyDef(Vec2 position, float rotation) {
		BodyDef def = new BodyDef();
		def.position = position;
		def.angle = rotation;
		def.type = BodyType.KINEMATIC;
		return def;
	}
	
	public static FixtureDef createBox(float mass, float width, float height) {
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width, height);
		
		FixtureDef fDef = new FixtureDef(); 
		fDef.shape = ps;
		fDef.density = width*height/mass;
		
		return fDef;
	}
	
	public static FixtureDef createCircle(float mass, float radius) {
		CircleShape cs = new CircleShape();
		cs.m_radius = radius;
		
		FixtureDef def = new FixtureDef(); 
		def.shape = cs;
		def.density = (float) ((Math.PI)*(Math.pow(radius,2.0)/mass));
		
		return def;
	}
}