/*
 * This class is the baseclass for everything that requires
 * explicit game logic.
 */

package world.entity.actor;

import util.DepthLevel;
import world.GameWorld;
import world.entity.Entity;

public abstract class Actor extends Entity{
	
	protected boolean dead;
	private int maxHP;
	private int hp;
	
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
