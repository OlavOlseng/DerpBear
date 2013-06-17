package entitysystem;

import java.util.ArrayList;
import java.util.Collection;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import entitysystem.component.PlayerComponent;
import entitysystem.component.RenderComponent;
import entitysystem.component.TransformComponent;
import rendering.Node;
import rendering.Pipeline;
import util.GameConstants;
import entitysystem.component.Component;

public class RenderSystem extends BaseSystem {

	private Pipeline pipeline;
	public RenderSystem(EntityManager entityManager, EntityFactory entityFactory,Pipeline pipeline) {
		super(entityManager, entityFactory);
		this.pipeline = pipeline;
		
	}

	@Override
	public void update(float dt) {
	
		ArrayList<Entity> ents = getEntityManager().getAllEntitiesPossesingComponentsOfClass(RenderComponent.class, TransformComponent.class);
	
		ArrayList<Entity> players = getEntityManager().getAllEntitiesPossesingComponentsOfClass(PlayerComponent.class, TransformComponent.class);
		Entity player = null;
		if(players.size() > 0)
			player = players.get(0);
		if(player != null){
			TransformComponent playerTrans = (TransformComponent) player.getComponentOfType(TransformComponent.class);
			RenderComponent playerRender = (RenderComponent) player.getComponentOfType(RenderComponent.class);
			Matrix4f view = new Matrix4f();
			view.translate(new Vector2f(-playerTrans.getX() + GameConstants.CLIENT_SCREEN_WIDTH/2,-playerTrans.getY() + GameConstants.CLIENT_SCREEN_HEIGHT/2));
			pipeline.setViewMatrix(view);
		
			
		}
	
		for(Entity ent : ents){
			
			RenderComponent rc = (RenderComponent) ent.getComponentOfType(RenderComponent.class);
			if(rc.isReady()){
				TransformComponent tc = (TransformComponent) ent.getComponentOfType(TransformComponent.class);
				
				for(Node n: rc){
					n.setPosition(tc.getX(), tc.getY());
					n.setOrientation(tc.getOrientation());
					n.setDepth(tc.getDepth().getDepth());
					n.render(pipeline);
				}
				
				
			}
		}
		
	}

	
}
