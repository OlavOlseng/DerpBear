package world.gameobject;

public class Damage {
	
	enum DamageType {
		NORMAL, 
		ARMOR_PIERCING,
		FIRE,
		POISION,
		ELECTRICAL;
	}
	
	private DamageType type;
	private int amount;
	
	public Damage(DamageType type, int amount) {
		this.type = type;
		this.amount = amount;
	}
	
	public DamageType getDamageType() {
		return type;
	}
	
	public int getAmount() {
		return amount;
	}
}
