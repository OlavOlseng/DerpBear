package entitysystem.component;

import java.io.Console;
import java.io.Serializable;

import network.Syncable;
import sun.net.ftp.FtpClient.TransferType;
import util.DepthLevel;
import world.gameobject.Transform;

public class TransformComponent extends Component implements Syncable,
		Serializable {

	private Transform transform;
	private Transform oldTransform;
	private boolean didChange;

	public TransformComponent() {
		transform = new Transform();
		oldTransform = new Transform();

	}

	public void setX(float x) {
		transform.setX(x);
		
	}

	public void setY(float y) {
		transform.setY(y);
	}

	public void setOrientation(float orientation) {
		transform.setOrientation(orientation);
		
	}

	public void setDepth(DepthLevel depth) {
		transform.setDepth(depth);
	}
	
	public float getX() {
		return transform.getX();
	}

	public float getY() {
		return transform.getY();
	}
	public float getOrientation() {
		return transform.getOrientation();
	}
	public DepthLevel getDepth(){
		return transform.getDepth();
	}

	
	
	@Override
	public boolean didChange() {
	
		return !(oldTransform.getX() == transform.getX() && transform.getY() == oldTransform.getY() && oldTransform.getOrientation() == transform.getOrientation());
	}

	@Override
	public Object onRead(Object object) {
		TransformComponent remoteTransform = (TransformComponent) object;
		
		setX(remoteTransform.getX());
		setY(remoteTransform.getY());
		setOrientation(remoteTransform.getOrientation());
		setDepth(remoteTransform.getDepth());

		return this;

	}

	@Override
	public Object onWrite(Object object) {
	
		oldTransform.setX(getX());
		oldTransform.setY(getY());
		oldTransform.setOrientation(getOrientation());
		return this;
	}

	public String toString() {

		return this.transform.toString();
	}

}
