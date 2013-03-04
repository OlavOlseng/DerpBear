package component.base;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import component.container.ComponentMessage;

import util.GameConstants;
import world.GameWorld;
import world.gameobject.GameObject;
import world.gameobject.Transform;

public class PhysicsComponent extends Component {
	
	protected Body body;
	protected FixtureDef fixtureDef;
	protected BodyDef bodyDef;
	protected GameWorld world;
	protected GameObject owner;
	
	public PhysicsComponent(GameWorld w, GameObject owner,FixtureDef fDef, BodyDef bDef) {
		this.fixtureDef = fDef;
		this.bodyDef = bDef;
		this.owner = owner;
	
		this.world = w;
		addToWorld(owner);
		owner.setPhysicsSimulated(this);
	}
	
	public void addToWorld(GameObject owner){
		
		//You need to instantiate the bodydef and fixturedef before calling this function
		if(body != null)
			throw (new IllegalStateException("Body already in simulation!"));
		
		body = world.getPhysWorld().createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(owner);
	}
	
	public void removeFromWorld() {
		body.getWorld().destroyBody(body);
		body = null;
	}
	
	public void setPosition(float x, float y) {
		if (body == null)
			return;
		body.setTransform(new Vec2(x / GameConstants.PIXELSCALE, y / GameConstants.PIXELSCALE), body.getAngle());
	}
	
	public void setOrientation(float orientation) {
		if (body == null)
			return;
		body.setTransform(body.getPosition(), orientation);
	}	
	
	@Override
	public void onUpdate(GameObject gameObject, float dt) {
		Transform t = gameObject.getTransform();
		Vec2 p = body.getPosition();
		
		t.orientation = body.getAngle();
		t.x = p.x * GameConstants.PIXELSCALE; 
		t.y = p.y * GameConstants.PIXELSCALE;
	}

	@Override
	public void receiveMessage(ComponentMessage message) {
		// TODO Auto-generated method stub
		
	}
}
