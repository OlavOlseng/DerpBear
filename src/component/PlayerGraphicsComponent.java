package component;



import rendering.Pipeline;
import rendering.Sprite;
import util.DepthLevel;
import util.GameConstants;
import world.gameobject.GameObject;
import world.gameobject.Transform;
import component.base.GraphicsComponent;
import component.container.ComponentMessage;

public class PlayerGraphicsComponent extends GraphicsComponent {
	
	
	
	public PlayerGraphicsComponent(Sprite sprite, Pipeline pipeline){
		super(sprite,pipeline);
		
	}
	public void onUpdate(GameObject gameObject, float dt){
		
		
	}

	
	@Override
	public void receiveMessage(ComponentMessage message) {
		
		
	}
	
}
