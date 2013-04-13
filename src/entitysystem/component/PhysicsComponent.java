package entitysystem.component;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import world.GameWorld;

public class PhysicsComponent extends Component {
	
	Body body;
	BodyDef bDef;
	ArrayList<FixtureDef> fixtureDefs;
	
	public PhysicsComponent(GameWorld w, BodyDef bDef, FixtureDef fDef, FixtureDef ...more) {
		fixtureDefs = new ArrayList<FixtureDef>();
		
		fixtureDefs.add(fDef);
		this.bDef = bDef;
		for(int i = 0; i < more.length; i++) {
			fixtureDefs.add(more[i]);
		}
		createBody(w);
	}
	
	public void attachFixtureDef(FixtureDef def) {
		fixtureDefs.add(def);
		body.createFixture(def);
	}
	
	private void createBody(GameWorld w) {
		this.body = w.getPhysWorld().createBody(bDef);
		this.body.setAngularDamping(2.6f);
		//TODO maybe set userdata
		for(FixtureDef def : fixtureDefs) {
			body.createFixture(def);
		}
	}
	
	public Body getBody() {
		return body;
	}
	
	
	@Override 
	public void delete() {
		body.getWorld().destroyBody(body);
	}
}
