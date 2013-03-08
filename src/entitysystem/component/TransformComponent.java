package entitysystem.component;

import world.gameobject.Transform;

public class TransformComponent extends Component {
	
	Transform transform;
	
	public TransformComponent() {
		transform = new Transform();
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	
}
