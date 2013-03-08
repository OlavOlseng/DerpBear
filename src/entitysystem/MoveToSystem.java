package entitysystem;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import entitysystem.component.MoveToComponent;
import entitysystem.component.PhysicsComponent;

public class MoveToSystem extends BaseSystem{
	
	Class physComp = PhysicsComponent.class;
	Class moveToComp = MoveToComponent.class;
	
	public MoveToSystem(EntityManager entityManager, EntityFactory entityFactory) {
		super(entityManager, entityFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		ArrayList<Entity> l = getEntityManager().getAllEntitiesPossesingComponentsOfClass(physComp, moveToComp);
		for (Entity e : l) {
			MoveToComponent mtc = (MoveToComponent) e.getComponentOfType(moveToComp);
			PhysicsComponent pc = (PhysicsComponent) e.getComponentOfType(physComp);
			
			pc.getBody().setLinearDamping(1.5f);
			if (mtc.shouldMove) {
				Vec2 target = mtc.getCurrentTarget();
				Vec2 pos = pc.getBody().getPosition();
				Vec2 dir = target.sub(pos);
				
				if(Math.abs(dir.length()) < 1.0f) {
					mtc.goToNextTarget();
					pc.getBody().setLinearVelocity(new Vec2(0.0f, 0.0f));
				}
				else 
				{
					dir.normalize();
					dir.mulLocal(1000);
					pc.getBody().applyForce(dir, pc.getBody().getPosition());
				}
			}
		}
	}

}
