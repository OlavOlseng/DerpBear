package component;



import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import world.GameWorld;
import world.gameobject.GameObject;
import component.base.Component;
import component.base.PhysicsComponent;
import component.container.ComponentMessage;

public class SensorComponent  extends Component{

	private Body sensorBody;
	private PhysicsComponent physicsComponent;
	public SensorComponent(GameWorld world,PhysicsComponent physicsComponent){
		this.physicsComponent = physicsComponent;
		Shape shape = physicsComponent.getBody().getFixtureList().getShape().clone();
		FixtureDef sensor = new FixtureDef();
		sensor.shape = shape;
		sensor.isSensor = true;
		sensor.density = 1.0f;
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.DYNAMIC;
		sensorBody = world.getPhysWorld().createBody(bDef);
		sensorBody.createFixture(sensor);
		
		
	}

	@Override
	public void receiveMessage(ComponentMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate(GameObject gameObject, float dt) {
		System.out.println(sensorBody.getContactList().contact.getManifold().localNormal);
		sensorBody.setTransform(physicsComponent.getBody().getTransform().position, physicsComponent.getBody().getTransform().getAngle());
		
		
	}
	
}
