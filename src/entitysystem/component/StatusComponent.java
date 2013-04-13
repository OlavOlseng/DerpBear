package entitysystem.component;
public class StatusComponent extends Component{
	
	public static final int ARMOR_CAP = 500;
	
	int maxHp;
	int maxShield;
	int hp;
	int shield;
	int armor;
	boolean alive;
	//dots, skavanker, powerups 
	
	/**
	 *Contains all status effects and current health/shield/armor state of corresponding entity 
	 */
	public StatusComponent(int maxHp, int maxShield, int armor) {
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.maxShield = maxShield;
		this.shield = maxShield;
		this.armor = armor;
		this.alive = true;
	}
	
	private float calculateArmorReduction() {
		float fraction = (float)armor/ARMOR_CAP; 
		if (fraction > 0.9f) fraction = 0.9f;
		return 1.0f - fraction;
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
	
	public void takeDamage(int amount) {
		int remainder = takeShieldDamage(amount);
		float armorReduction = calculateArmorReduction();
		takeHealthDamage((int)(remainder*armorReduction));
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