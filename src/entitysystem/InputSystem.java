package entitysystem;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import entitysystem.component.InputComponent;

public class InputSystem extends BaseSystem {

	private HashMap<Integer, Boolean> keyMap;
	public InputSystem(EntityManager entityManager, EntityFactory entityFactory) {
		super(entityManager, entityFactory);
		keyMap = new HashMap<Integer, Boolean>();
	}

	@Override
	public void update(float dt) {
		ArrayList<Entity> ents = getEntityManager().getAllEntitiesPossesingComponentsOfClass(InputComponent.class);
		
		for(Entity entity: ents){
		
			InputComponent inputComp =  (InputComponent)entity.getComponentOfType(InputComponent.class);
			//test
			
			while(Keyboard.next()){
				int key = Keyboard.getEventKey();
				if(Keyboard.isKeyDown(key)){
					keyMap.put(key, true);
				}else{
					keyMap.remove(key);
				}
			}
			
			for(Integer key: keyMap.keySet()){
				if(Keyboard.isKeyDown(key)){
					inputComp.addKeyBoardInput(key);
				}
			}
			
			
		
		}
	}

}
