package entitysystem.component;

import world.gameobject.Damage;

public class DamageComponent extends Component{
	
	private Damage dmg;
	
	public DamageComponent(Damage object) {
		dmg = object;
	}
	
	public Damage getDamage() {
		return dmg;
	}
}
