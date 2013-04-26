package entitysystem;

import java.util.ArrayList;

import javax.xml.transform.TransformerConfigurationException;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import util.GameConstants;
import world.gameobject.Transform;

import entitysystem.component.PhysicsComponent;
import entitysystem.component.TransformComponent;

public class PhysicsSystem extends BaseSystem{

	Class phyComp = PhysicsComponent.class;
	
	public PhysicsSystem(EntityManager entityManager,
			EntityFactory entityFactory) {
		super(entityManager, entityFactory);
		
	}

	@Override
	public void update(float dt) {
		ArrayList<Entity> l = getEntityManager().getAllEntitiesPossesingComponentsOfClass(PhysicsComponent.class, TransformComponent.class);
		float ps = GameConstants.PIXELSCALE;
		
		for (Entity ent : l) {
			TransformComponent t = ((TransformComponent) (ent.getComponentOfType(TransformComponent.class)));
			
			Body b = ((PhysicsComponent) (ent.getComponentOfType(phyComp))).getBody();
			t.setX(b.getPosition().x * ps);
			t.setY(b.getPosition().y * ps);
			t.setOrientation(b.getAngle());
			
			
		}
	}
}
