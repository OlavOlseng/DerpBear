package world.entity;

import org.jbox2d.common.Vec2;

import util.DepthLevel;
import world.BodyFactory;
import world.GameWorld;

public class Wall extends Entity{

	public Wall(GameWorld w, float width, float height, Vec2 position, float rotation) {
		super(DepthLevel.ACTOR_LVL);
		// TODO Auto-generated constructor stub
		bDef = BodyFactory.createStaticBodyDef(position, rotation);
		fDef = BodyFactory.createBox(0, width/2, height/2);
		fDef.restitution = 1.2f;
		fDef.friction = 0.8f;
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
