/*
 * This class is the baseclass for everything that requires
 * explicit game logic.
 */

package world.entity.actor;

import org.jbox2d.common.Vec2;

import component.base.ControllerComponent;
import component.base.GraphicsComponent;
import component.base.PhysicsComponent;

import rendering.Node;

import util.DepthLevel;
import world.GameWorld;
import world.entity.Entity;

public abstract class Actor extends Entity{
	
	protected boolean dead;
	private int maxHP;
	private int hp;
	private Vec2 velocity;
	
	
	public Actor(DepthLevel dpt) {
		super(dpt);
		dead = false;
	}
	
	@Override
	protected void addToWorld(GameWorld w){
		//You need to instantiate the bodydef and fixturedef before calling this function
		b = w.getPhysWorld().createBody(bDef);
		b.createFixture(fDef);
		w.add(this);
	}
	
	public void move(float dx,float dy){
		b.applyLinearImpulse(new Vec2(dx*1000,dy*1000), b.getPosition());
		
	}
	
	public void accelerate(float ax,float ay){
		
	}
	
	public void rotate(float rotation){
		b.applyAngularImpulse(rotation);
		
	}
	
	public void setOrientation(float orientation){
		//b.setTransform(b.getPosition(), orientation);
		
	}
	public void changeHealth(int amount) {
		hp += amount;
		if (hp > maxHP){
			hp = maxHP;
		} else if (hp < 0) {
			hp = 0;
			dead = true;
		}
		
	}
}
