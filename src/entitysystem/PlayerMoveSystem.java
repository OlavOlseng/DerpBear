package entitysystem;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import entitysystem.component.InputComponent;
import entitysystem.component.LookAtComponent;
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
		ArrayList<Entity> entities = getEntityManager().getAllEntitiesPossesingComponentsOfClass(PhysicsComponent.class, PlayerComponent.class, InputComponent.class);
		
		for(Entity ent:entities){
			PhysicsComponent physics = (PhysicsComponent) ent.getComponentOfType(PhysicsComponent.class);
			InputComponent input = (InputComponent)ent.getComponentOfType(InputComponent.class);
			
		
			
			
			
			Body body = physics.getBody();
			
			body.setLinearDamping(4.5f);
			while(input.hasNext()){
				int key = input.getNextKey();
				if(key == Keyboard.KEY_LEFT){
				
				
					body.applyLinearImpulse(new Vec2(-50.0f, 0.0f), body.getPosition());
					
				}
				
				if(key == Keyboard.KEY_RIGHT){
					body.applyLinearImpulse(new Vec2(50.0f, 0.0f), body.getPosition());
				}
				
				if(key == Keyboard.KEY_UP){
					body.applyLinearImpulse(new Vec2(0.0f, 50.0f), body.getPosition());
				}
				if(key == Keyboard.KEY_DOWN){
					body.applyLinearImpulse(new Vec2(0.0f, -50.0f), body.getPosition());
				}
			
			}
			
			
			
			
			
			
		}
		
	}


}
