package entitysystem;

import java.util.ArrayList;
import java.util.Collection;

import entitysystem.component.RenderComponent;
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
		for(Component comp:getEntityManager().getAllComponentsOfClass(RenderComponent.class)){
			RenderComponent render = (RenderComponent)comp;
			render.getNode().render(pipeline);
			
			
		}
		
	}

	
}
