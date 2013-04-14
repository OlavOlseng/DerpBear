package world.gameobject;

public class Damage {
	
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
