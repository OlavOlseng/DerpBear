package rendering;

import org.lwjgl.util.vector.Matrix4f;

public class MatrixUtil {

	public static Matrix4f getOrthographicProjection(float left,float right,float bottom,float top){
		Matrix4f mat = new Matrix4f();
		Matrix4f.setIdentity(mat);
		mat.m00 = 2/(right - left);
		mat.m11 = 2/(top - bottom);
		mat.m22 = -1;
		mat.m30 = -(right + left)/(right - left);
		mat.m31 = -(top + bottom)/(top - bottom);
		
		
		return mat;
		
	}
}
