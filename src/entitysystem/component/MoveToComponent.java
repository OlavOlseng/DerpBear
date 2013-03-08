package entitysystem.component;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

public class MoveToComponent extends Component {
	
	public boolean shouldMove;
	private ArrayList<Vec2> targets;
	private Vec2 currentTarget;
	
	public MoveToComponent(){
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
