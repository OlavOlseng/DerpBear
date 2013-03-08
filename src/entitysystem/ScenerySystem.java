package entitysystem;

import java.util.ArrayList;

import javax.xml.transform.TransformerConfigurationException;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import util.GameConstants;
import world.gameobject.Transform;

import entitysystem.component.PhysicsComponent;
import entitysystem.component.TransformComponent;

public class ScenerySystem extends BaseSystem{

	Class phyComp = PhysicsComponent.class;
	
	public ScenerySystem(EntityManager entityManager,
			EntityFactory entityFactory) {
		super(entityManager, entityFactory);
		
	}

	@Override
	public void update(float dt) {
		ArrayList<Entity> l = getEntityManager().getAllEntitiesPossesingComponentsOfClass(PhysicsComponent.class, TransformComponent.class);
		float ps = GameConstants.PIXELSCALE;
		
		for (Entity ent : l) {
			Transform t = ((TransformComponent) (ent.getComponentOfType(TransformComponent.class))).getTransform();
			Body b = ((PhysicsComponent) (ent.getComponentOfType(phyComp))).getBody();
			
			t.x = b.getPosition().x * ps;
			t.y = b.getPosition().y * ps;
			t.orientation = b.getAngle();
		}
	}
}
