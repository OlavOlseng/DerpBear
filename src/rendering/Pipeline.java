package rendering;

import org.lwjgl.util.vector.Matrix4f;

public class Pipeline {

	private Matrix4f viewMatrix;
	private Matrix4f projectionMatrix;
	private Matrix4f worldTransForm;
	private Matrix4f viewProjectionMatrix;
	private boolean nodeNeedsUpdate;
	
	
	public Pipeline(){
		projectionMatrix = new Matrix4f();
		Matrix4f.setIdentity(projectionMatrix);
		viewMatrix = new Matrix4f();
		Matrix4f.setIdentity(viewMatrix);
		viewProjectionMatrix = new Matrix4f();
		worldTransForm = new Matrix4f();
		Matrix4f.setIdentity(worldTransForm);
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	public void setViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix = viewMatrix;
		Matrix4f.mul(projectionMatrix, viewMatrix, viewProjectionMatrix);
	}
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	public void setProjectionMatrix(Matrix4f projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
		Matrix4f.mul(projectionMatrix, viewMatrix, viewProjectionMatrix);
	}
	public Matrix4f getWorldTransForm() {
		return worldTransForm;
	}
	public void setWorldTransForm(Matrix4f worldTransForm) {
		this.worldTransForm = worldTransForm;
	}
	public Matrix4f getViewXProjectionMatrix(){
		return viewProjectionMatrix;
	}
	
	public boolean getNodeNeedsUpdate() {
		return nodeNeedsUpdate;
	}

	public void setNodeNeedsUpdate(boolean nodeNeedsUpdate) {
		this.nodeNeedsUpdate = nodeNeedsUpdate;
	}

	
	
	public void clear(){
		
		Matrix4f.setIdentity(worldTransForm);
		nodeNeedsUpdate = false;
	}
	
}
