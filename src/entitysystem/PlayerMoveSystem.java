package entitysystem;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.input.Keyboard;

import entitysystem.component.PhysicsComponent;
import entitysystem.component.PlayerComponent;
import entitysystem.component.RenderComponent;
import entitysystem.component.TransformComponent;

public class PlayerMoveSystem  extends BaseSystem{

	public PlayerMoveSystem(EntityManager entityManager,EntityFactory entityFactory) {
		super(entityManager, entityFactory);
		
	}

	@Override
	public void update(float dt) {
		ArrayList<Entity> entities = getEntityManager().getAllEntitiesPossesingComponentsOfClass(PhysicsComponent.class, PlayerComponent.class);
		
		for(Entity ent:entities){
			
			PhysicsComponent physics = (PhysicsComponent) ent.getComponentOfType(PhysicsComponent.class);
			
			Body body = physics.getBody();
		
			body.setLinearDamping(4.5f);
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				body.applyForce(new Vec2(-500.0f, 0.0f), body.getPosition());
				System.out.println("hello");
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				body.applyForce(new Vec2(500.0f, 0.0f), body.getPosition());
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				body.applyForce(new Vec2(0.0f, 500.0f), body.getPosition());
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				body.applyForce(new Vec2(0.0f, -500.0f), body.getPosition());
			}
			
			
			
		}
		
	}


}
