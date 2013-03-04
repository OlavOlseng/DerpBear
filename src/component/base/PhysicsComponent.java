package component.base;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import world.GameWorld;

public abstract class PhysicsComponent extends Component {
	
	protected Body b;
	protected FixtureDef fDef;
	protected BodyDef bDef;
	
	protected void addToWorld(GameWorld w){
		//You need to instantiate the bodydef and fixturedef before calling this function
		b = w.getPhysWorld().createBody(bDef);
		b.createFixture(fDef);
		b.setSleepingAllowed(false);
		
		//TODO implement world add
//		w.add(this);
	}
	
	public void removeFromSimulation() {
		b.getWorld().destroyBody(b);
		b = null;
	}
	
	public Body getBody() {
		return b;
	}
	
	public Vec2 getPosition(){
		return b.getTransform().position;
		
	}
	
	public void setPosition(Vec2 position){
		b.setTransform(position, b.getAngle());
	}
}
