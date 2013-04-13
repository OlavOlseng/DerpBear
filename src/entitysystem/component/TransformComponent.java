package entitysystem.component;

import java.io.Serializable;

import network.Syncable;
import world.gameobject.Transform;

public class TransformComponent extends Component implements Syncable, Serializable {
	
	Transform transform;
	public TransformComponent() {
		transform = new Transform();
		
	}
	
	public Transform getTransform() {
		return transform;
	}

	@Override
	public boolean didChange() {
		return transform.didChange();
	}
	
	
}
