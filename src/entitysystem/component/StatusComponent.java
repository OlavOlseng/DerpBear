package entitysystem.component;

import world.gameobject.Damage;
import world.gameobject.DamageType;

public class StatusComponent extends Component{
	
	public static final int ARMOR_CAP = 500;
	public static final int RESISTANCE_CAP = 500;
	
	int maxHp;
	int maxShield;
	int hp;
	int shield;
	int armor;
	int fireRes;
	int poisonRes;
	int electricalRes;
	boolean alive;
	//dots, skavanker, powerups 
	
	/**
	 *Contains all status effects and current health/shield/armor state of corresponding entity 
	 */
	public StatusComponent(int maxHp, int maxShield, int armor, int fireRes, int poisonRes, int electricalRes) {
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.maxShield = maxShield;
		this.shield = maxShield;
		this.armor = armor;
		this.fireRes = fireRes;
		this.poisonRes = poisonRes;
		this.electricalRes = electricalRes;
		this.alive = true;
	}
	
	
	
	public void takeDamage(Damage damage) {
		int amount = damage.getAmount();
		int remainder = takeShieldDamage(amount);
		float damageReduction = calculateReduction(damage, remainder);
		takeHealthDamage((int)(remainder*damageReduction));
	}
	
	
	private int takeShieldDamage(int amount) {
		int remainder = amount - shield;
		shield -= amount;
		if((remainder > 0)) {
			shield = 0;
		} 
		else {
			remainder = 0;
		}
		return remainder;
	}
	
	private void takeHealthDamage(int amount) {
		this.hp -= amount;
		if (hp <= 0) {
			alive = false;
			hp = 0;
		}
	}
	private float calculateReduction(Damage dam, int amount) {
		float reduction = 1.0f;
		switch(dam.getDamageType()) {
		case NORMAL:
			reduction = calculateArmorReduction();
			break;
		case POISION:
			reduction = calculateElementalReduction(poisonRes);
			break;
		case FIRE:
			reduction = calculateElementalReduction(fireRes);
			break;
		case ELECTRICAL:
			reduction = calculateElementalReduction(electricalRes);
			break;
		default:
		}
		return reduction;
	}
	
	private float calculateArmorReduction() {
		float fraction = (float)armor/ARMOR_CAP; 
		if (fraction > 0.9f) fraction = 0.9f;
		return 1.0f - fraction;
	}
	
	private float calculateElementalReduction(int resistance) {
		return ((float)(resistance/RESISTANCE_CAP))*0.85f;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public String toString() {
		String s = String.format("hp: %d \nshields: %d \nalive: %b", hp, shield, alive);
		return s;
	}
}