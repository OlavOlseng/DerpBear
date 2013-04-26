package world.gameobject;

import java.io.Serializable;

import network.Syncable;

import util.DepthLevel;

public class Transform implements Serializable{
	private float x, y, orientation;
	private DepthLevel depth;
	private boolean didChange;
	public Transform() {
		this(0,0,0, DepthLevel.TOP_LVL);
		
	}
	
	public Transform(float x, float y) {
		this(x, y, 0, DepthLevel.TOP_LVL);
	}
	
	public Transform(float x, float y, float orientation) {
		this(x, y, orientation, DepthLevel.TOP_LVL);
	}
	
	public Transform(float x, float y, float orientation, DepthLevel dpt) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.depth = dpt;
	}

	
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getOrientation() {
		return orientation;
	}

	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}

	public DepthLevel getDepth() {
		return depth;
	}

	public void setDepth(DepthLevel depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "Transform [x=" + x + ", y=" + y + ", orientation="
				+ orientation + ", depth=" + depth 
				+ "]";
	}
	
	
	
}
