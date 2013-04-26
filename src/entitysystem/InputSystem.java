package entitysystem;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import entitysystem.component.InputComponent;

public class InputSystem extends BaseSystem {

	public InputSystem(EntityManager entityManager, EntityFactory entityFactory) {
		super(entityManager, entityFactory);
		
	}

	@Override
	public void update(float dt) {
		ArrayList<Entity> ents = getEntityManager().getAllEntitiesPossesingComponentsOfClass(InputComponent.class);
		
		for(Entity entity: ents){
		
			InputComponent inputComp =  (InputComponent)entity.getComponentOfType(InputComponent.class);
			//test
			
			while(Keyboard.next()){
				inputComp.addKeyBoardInput(Keyboard.getEventKey());
			}
		
		}
	}

}
