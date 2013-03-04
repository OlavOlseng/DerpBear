package component.base;

import component.container.ComponentMessage;

import rendering.Pipeline;
import rendering.Sprite;
import util.DepthLevel;
import util.GameConstants;
import world.gameobject.GameObject;
import world.gameobject.Transform;



public class GraphicsComponent extends Component {

	private Sprite sprite;
	private Pipeline pipeline;
	
	public GraphicsComponent(Sprite sprite, Pipeline pipeline){
		this.sprite = sprite;
		this.pipeline = pipeline;
	}

	@Override
	public void receiveMessage(ComponentMessage message) {
		
		
	}

	@Override
	public void onUpdate(GameObject gameObject, float dt) {
		
		Transform transform = gameObject.getTransform();
		sprite.setPosition(transform.x, transform.y);
		sprite.setOrientation(transform.orientation);
		sprite.render(pipeline);
		
	}
	


}
