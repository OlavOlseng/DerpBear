package component;

import org.jbox2d.common.Transform;

import rendering.Pipeline;
import rendering.Sprite;
import util.DepthLevel;
import util.GameConstants;
import world.entity.Entity;
import component.base.GraphicsComponent;
import component.container.ComponentMessage;

public class PlayerGraphicsComponent extends GraphicsComponent {
	private Sprite sprite;
	private Pipeline pipeline;
	private DepthLevel depth;
	public PlayerGraphicsComponent(Sprite sprite,Pipeline pipeline,DepthLevel depth){
		this.sprite = sprite;
		this.pipeline = pipeline;
		this.depth = depth;
		sprite.setDepth(depth.getDepth());
	}
	public void onUpdate(Entity entity, double dt){
		Transform transform =  entity.getBody().getTransform();
		
		sprite.setPosition(transform.position.x*GameConstants.PIXELSCALE, transform.position.y*GameConstants.PIXELSCALE);
		sprite.setOrientation(transform.getAngle());
		sprite.render(pipeline);
	}

	
	@Override
	public void receiveMessage(ComponentMessage message) {
		
		
	}
	
}
