package entitysystem;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import util.GameConstants;

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
			
			System.out.println(pc.getBody().isAwake());
			
			if (mtc.shouldMove) {
				pc.getBody().setLinearDamping(2.0f);
				Vec2 target = mtc.getCurrentTarget();
				Vec2 pos = pc.getBody().getPosition();
				Vec2 dir = target.sub(pos);
				
				
				if(Math.abs(dir.length()) < mtc.targetRadius) {
					pc.getBody().setLinearDamping(4.3f);
					mtc.goToNextTarget();
				}
				else 
				{
					if(pc.getBody().getLinearVelocity().length() < mtc.maxSpeed) {
						dir.normalize();
						dir.mulLocal(mtc.force);
						pc.getBody().applyForce(dir, pc.getBody().getPosition());
						
					}
				}
			}
		}
	}

}
