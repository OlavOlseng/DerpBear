package entitysystem;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import util.GameConstants;
import world.gameobject.Transform;

import entitysystem.component.ChaseComponent;
import entitysystem.component.PhysicsComponent;
import entitysystem.component.RenderComponent;
import entitysystem.component.TransformComponent;

public class ChaseSystem extends BaseSystem {

	public ChaseSystem(EntityManager entityManager, EntityFactory entityFactory) {
		super(entityManager, entityFactory);
		
	}

	@Override
	public void update(float dt) {
		
		ArrayList<Entity> ents = getEntityManager().getAllEntitiesPossesingComponentsOfClass(ChaseComponent.class, PhysicsComponent.class);
		Vec2 dir = new Vec2();
		for(Entity chaser:ents){
			ChaseComponent chaseComp = (ChaseComponent) chaser.getComponentOfType(ChaseComponent.class);
			Body body = ((PhysicsComponent)chaser.getComponentOfType(PhysicsComponent.class)).getBody();
			Entity target = chaseComp.getTarget();
			TransformComponent  trans = ((TransformComponent)target.getComponentOfType(TransformComponent.class));
			Vec2 bodypos = body.getPosition();
			dir.x = trans.getX() - bodypos.x*GameConstants.PIXELSCALE;
			dir.y = trans.getY() - bodypos.y * GameConstants.PIXELSCALE;
			dir.normalize();
			dir.mulLocal(100.0f);
			body.setLinearDamping(4.5f);
			body.applyForce(dir, bodypos);
			
			System.out.println(dir);
			
		}
	}

}
