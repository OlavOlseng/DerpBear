package rendering;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Node {
	
	private Vector2f position;
	private Vector3f scale;
	private float orientation;
	protected Matrix4f modelMatrix;
	protected Vector3f rotationAxis;

	protected int height;
	protected int width;
	private float depth;
	
	public Node(){
		position = new Vector2f();
		modelMatrix = new Matrix4f();
		rotationAxis = new Vector3f(0,0,1);
		scale = new Vector3f(1,1,1);
		this.width = 1;
		this.height = 1;
		
		
	}
	public void setSize(float width,float height){
		
		setWidth(width);
		setHeight(height);
		
	}
	public void setWidth(float width){
		
		
		setScaleX(width/(float)this.width);
		this.width = (int)width;
	}
	public void setHeight(float height){
		setScaleY(height/(float)this.height);
		this.height = (int)this.height;
	}
	public void scale(float x,float y){
		scale.x += x;
		scale.y += y;
		
		
	}
	public void move(float x,float y){
		position.x += x;
		position.y += y;
		
	}
	
	public void rotate(float rotation){
		
		orientation+= rotation;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public void setPosition(float x,float y) {
		this.position.x = x;
		this.position.y = y;
	}
	public float getOrientation() {
		return orientation;
	}
	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}
	public Vector3f getScale() {
		return scale;
	}

	public void setScale(float x,float y) {
		this.scale.x = x;
		this.scale.y = y;
		
	}
	
	void setScaleX(float x){
		
		this.scale.x = x;
	}
	
	void setScaleY(float y){
		this.scale.y = y;
		
	}
	
	public float getDepth() {
		return depth;
	}
	public void setDepth(float depth) {
		this.depth = depth;
	}
	
	
	public void render(Pipeline pipeline){
		
		
	}
	
	public Matrix4f getModelMatrix(){
		Matrix4f.setIdentity(modelMatrix);
		modelMatrix.translate(position);
		modelMatrix.rotate(orientation, rotationAxis);
		modelMatrix.scale(scale);
		return modelMatrix;
		
		
	}

}
