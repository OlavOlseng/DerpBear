package entitysystem.component;

import java.io.Console;
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

	@Override
	public Object sync(Object object) {
		TransformComponent remoteObject = (TransformComponent)object;
		transform.sync(remoteObject.getTransform());
		System.out.println(this.transform);
		return this;
	}
	public String toString(){
		return this.transform.toString();
	}
	
	
	
}
