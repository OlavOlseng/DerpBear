package rendering;

import org.lwjgl.util.vector.Matrix4f;

public class Pipeline {

	Matrix4f viewMatrix;
	Matrix4f projectionMatrix;
	Matrix4f worldTransForm;
	
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	public void setViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix = viewMatrix;
	}
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	public void setProjectionMatrix(Matrix4f projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
	}
	public Matrix4f getWorldTransForm() {
		return worldTransForm;
	}
	public void setWorldTransForm(Matrix4f worldTransForm) {
		this.worldTransForm = worldTransForm;
	}
	
}
