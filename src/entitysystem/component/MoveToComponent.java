package entitysystem.component;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

public class MoveToComponent extends Component {
	
	public boolean shouldMove;
	private ArrayList<Vec2> targets;
	private Vec2 currentTarget;
	
	public float force;
	public float maxSpeed;
	public float targetRadius;
	
	public MoveToComponent(float force, float maxSpeed, float targetRadius){
		this.force = force;
		this.maxSpeed = maxSpeed;
		this.targetRadius = targetRadius;
		
		shouldMove = false;
		targets = new ArrayList<Vec2>();
		currentTarget = null;
	}
	
	public void goToNextTarget() {
		if (targets.isEmpty()) {
			currentTarget = null;
			shouldMove = false;
			return;
		}
		currentTarget = targets.remove(0);
	}
	
	public void setTargets(ArrayList<Vec2> targets) {
		this.targets = targets;
		shouldMove = true;
		goToNextTarget();
	}
	
	public Vec2 getCurrentTarget() {
		//THIS METHOD MIGHT RETURN NULLPOINTER!
		return currentTarget;
	}
	
}
