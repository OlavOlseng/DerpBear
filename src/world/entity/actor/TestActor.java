package world.entity.actor;

import org.jbox2d.common.Vec2;

import util.DepthLevel;
import world.BodyFactory;
import world.GameWorld;

public class TestActor extends Actor{

	public TestActor(GameWorld w, Vec2 position, float rotation) {
		super(DepthLevel.ACTOR_LVL);
		bDef = BodyFactory.createKinematicBodyDef(position, rotation);
		
		bDef.linearDamping = 0.5f;
		fDef = BodyFactory.createBox(10, 4, 1);
		fDef.restitution = 1.0f;
		addToWorld(w);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpdate(float dt) {
		// TODO Auto-generated method stub
		
	}

}
