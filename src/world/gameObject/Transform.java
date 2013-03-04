package world.gameobject;

import util.DepthLevel;

public class Transform {
	public float x, y, orientation;
	public DepthLevel depth;
	
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
}
