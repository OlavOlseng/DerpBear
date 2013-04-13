package entitysystem.component;

public class DamageComponent extends Component{
	
	private int damageAmount;
	
	public DamageComponent(int damageAmount) {
		this.damageAmount = damageAmount;
	}
	
	public int getDamageAmount() {
		return this.damageAmount;
	}
}
