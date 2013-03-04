package component;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import world.gameobject.GameObject;
import component.base.InputComponent;
import component.container.ComponentMessage;

public class PlayerInputComponent extends InputComponent {

	public PlayerInputComponent(){
		
	}

	@Override
	public void receiveMessage(ComponentMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate(GameObject gameObject, float dt) {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			gameObject.move(-50.0f*dt, 0.0f);
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			gameObject.move(50.0f*dt, 0.0f);
		if(Keyboard.isKeyDown(Keyboard.KEY_UP))
			gameObject.move(0.0f, 50.0f*dt);
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			gameObject.move(0.0f, -50.0f*dt);
		
			
		//	float dx = Mouse.getX()- sprite.getPosition().x;
			
		//	float dy = Mouse.getY() - sprite.getPosition().y;
			
			
			
		
		
		
		
	}
}
