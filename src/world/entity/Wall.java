package world.entity;

import org.jbox2d.common.Vec2;

import util.DepthLevel;
import util.GameConstants;
import world.BodyFactory;
import world.GameWorld;

public class Wall extends Entity{

	public Wall(GameWorld w, float width, float height, Vec2 position, float rotation) {
		super(DepthLevel.ACTOR_LVL);

		position.x /= GameConstants.PIXELSCALE;
		position.y /= GameConstants.PIXELSCALE;
		width /=GameConstants.PIXELSCALE;
		height /= GameConstants.PIXELSCALE;
		
		// TODO Auto-generated constructor stub
		bDef = BodyFactory.createStaticBodyDef(position, rotation);
		fDef = BodyFactory.createBox(0, width/2, height/2);
		fDef.restitution = 0.7f;
		fDef.friction = 0.8f;
		addToWorld(w);
		
	}

	

	@Override
	public void onUpdate(float dt) {
		// TODO Auto-generated method stub
	}

}
