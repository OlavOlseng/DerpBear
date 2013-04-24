package entitysystem;

import java.util.ArrayList;
import java.util.Collection;

import entitysystem.component.RenderComponent;
import entitysystem.component.TransformComponent;
import rendering.Node;
import rendering.Pipeline;
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
		
		for(Entity ent : ents){
			
			RenderComponent rc = (RenderComponent) ent.getComponentOfType(RenderComponent.class);
			if(rc.isReady()){
				TransformComponent tc = (TransformComponent) ent.getComponentOfType(TransformComponent.class);
				Node n = rc.getNode();	
			
				n.setPosition(tc.getTransform().getX(), tc.getTransform().getY());
				n.setOrientation(tc.getTransform().getOrientation());
				n.setDepth(tc.getTransform().getDepth().getDepth());
				n.render(pipeline);
				
			}
		}
		
	}

	
}
