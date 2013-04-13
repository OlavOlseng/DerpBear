package entitysystem.component;

public class DamageComponent {
	
	private int damageAmount;
	
	public DamageComponent(int damageAmount) {
		this.damageAmount = damageAmount;
	}
	
	public int getDamageAmount() {
		return this.damageAmount;
	}
}
