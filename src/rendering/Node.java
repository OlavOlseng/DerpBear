package rendering;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Node implements Serializable {
	
	private Vector2f position;
	private Vector3f scale;
	private float orientation;
	protected Matrix4f modelMatrix;
	protected Vector3f rotationAxis;

	protected int height;
	protected int width;
	protected LinkedList<Node> children;
	private float depth;
	private boolean didChange;
	
	public Node(){
		position = new Vector2f();
		modelMatrix = new Matrix4f();
		rotationAxis = new Vector3f(0,0,1);
		scale = new Vector3f(1,1,1);
		this.width = 1;
		this.height = 1;
		
		children = new LinkedList<Node>();	
	}
	
	
	public abstract void init();
		
	public void addChild(Node child){
		children.add(child);
	}
	
	public void removeChild(Node child){
		children.remove(child);
	}
	public void setSize(float width,float height){
		
		setWidth(width);
		setHeight(height);
		didChange = true;
		
	}
	public void setWidth(float width){
		
		
		setScaleX(width/(float)this.width);
		this.width = (int)width;
		didChange = true;
	}
	public void setHeight(float height){
		setScaleY(height/(float)this.height);
		this.height = (int)height;
		didChange = true;
	}
	public void scale(float x,float y){
		scale.x += x;
		scale.y += y;
		didChange = true;
		
		
	}
	public void move(float x,float y){
		position.x += x;
		position.y += y;
		didChange = true;
		
	}
	
	public void rotate(float rotation){
		
		orientation+= rotation;
		didChange = true;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public void setPosition(float x,float y) {
		this.position.x = x;
		this.position.y = y;
		didChange = true;
	}
	public float getOrientation() {
		return orientation;
	}
	public void setOrientation(float orientation) {
		this.orientation = orientation;
		didChange = true;
	}
	public Vector3f getScale() {
		return scale;
	}

	public void setScale(float x,float y) {
		this.scale.x = x;
		this.scale.y = y;
		didChange = true;
		
	}
	
	void setScaleX(float x){
		
		this.scale.x = x;
		didChange = true;
	}
	
	void setScaleY(float y){
		this.scale.y = y;
		didChange = true;
		
	}
	
	public float getDepth() {
		return depth;
	}
	public void setDepth(float depth) {
		this.depth = depth;
	}
	
	protected Matrix4f oldWorldTransForm = new Matrix4f();
	protected Matrix4f origionalWorldTransform = new Matrix4f();
	public void render(Pipeline pipeline){
		
		if(pipeline.getNodeNeedsUpdate()){
			this.didChange = true;
		}else{
		
			if(didChange){
				pipeline.setNodeNeedsUpdate(true);
			}
		}
		
		
		
		Matrix4f worldTransform = pipeline.getWorldTransForm();
		Matrix4f.load(worldTransform, origionalWorldTransform);
		Matrix4f.mul(worldTransform,getModelMatrix() , worldTransform);
		Matrix4f.load(worldTransform, oldWorldTransForm);
		for (Node node : children){
			//System.out.println(worldTransform);
			node.render(pipeline);
		//	System.out.println(oldWorldTransForm);
			Matrix4f.load(oldWorldTransForm, worldTransform);
			
		}
		pipeline.setNodeNeedsUpdate(didChange);
		Matrix4f.load(origionalWorldTransform, worldTransform);
	}
	
	public boolean getDidChange() {
		return didChange;
	}

	public void setDidChange(boolean didChange) {
		this.didChange = didChange;
	}

	public String toString(){
		
		StringBuilder builder = new StringBuilder(50);
		builder.append("Position:");
		builder.append(position);
		builder.append("orientation:");
		builder.append(orientation);
		builder.append("Width:");
		builder.append(this.width);
		builder.append("Height:");
		builder.append(this.height);
		return builder.toString();
		
	}
	public Matrix4f getModelMatrix(){
		if(didChange){
			didChange = false;
			Matrix4f.setIdentity(modelMatrix);
			modelMatrix.translate(position);
			modelMatrix.rotate(orientation, rotationAxis);
			modelMatrix.scale(scale);
		}
		return modelMatrix;
		
		
	}

}
