package entitysystem;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.input.Mouse;

import world.gameobject.Transform;

import entitysystem.component.LookAtComponent;
import entitysystem.component.PhysicsComponent;
import entitysystem.component.TransformComponent;

public class LookAtSystem extends BaseSystem {

	public LookAtSystem(EntityManager entityManager, EntityFactory entityFactory) {
		super(entityManager, entityFactory);
		
	}

	@Override
	public void update(float dt) {
		ArrayList<Entity> entities = getEntityManager().getAllEntitiesPossesingComponentsOfClass(LookAtComponent.class,TransformComponent.class);
		
		
		for(Entity ent:entities){
			TransformComponent trans = ((TransformComponent) ent.getComponentOfType(TransformComponent.class));
			LookAtComponent lookAt = (LookAtComponent) ent.getComponentOfType(LookAtComponent.class);
			
			Vec2 target = lookAt.getTarget();
			
			float dx = trans.getX() - target.x;
			float dy = trans.getY() - target.y;
			float angle;
			if (dx < 0) {
				angle = (float)Math.atan(dy/dx) + 3.14f/2;
				
		    }else{
		    	angle = (float)Math.atan(dy/dx) - 3.14f/2;
		    }
			
			PhysicsComponent physics = (PhysicsComponent) ent.getComponentOfType(PhysicsComponent.class);
			if(physics != null){
				Body body = physics.getBody();
				body.setTransform(body.getPosition(),angle);
			}
			trans.setOrientation(angle);
			
			
	}

	}
}
