package rendering;


import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffer {

	private int handle;
	private int type;
	private int drawMode;
	private int stride;
	private int size;
	private int dataType;
	public Buffer(int type,int drawMode,int dataType,int stride){
		this.type = type;
		this.drawMode = drawMode;
		this.stride = stride;
		this.dataType = dataType;
		handle = glGenBuffers();
	}
	public void setData(FloatBuffer dataBuffer){
		this.size = dataBuffer.capacity();
		glBindBuffer(type, handle);
		glBufferData(type, dataBuffer, drawMode);
		
	}
	public void setData(IntBuffer dataBuffer){
		this.size = dataBuffer.capacity();
		glBindBuffer(type, handle);
		glBufferData(type, dataBuffer, drawMode);
		
	}
	public void updateData(FloatBuffer dataBuffer,int offset,int size){
		
		this.size = size;
		glBindBuffer(type, handle);
		glBufferSubData(type, offset, dataBuffer);
	}
	public int getSize() {
		return size;
	}
	public void bind(){
		glBindBuffer(type, handle);
		
	}
	public void bindTo(int attribute){
		glBindBuffer(type, handle);
		glEnableVertexAttribArray(attribute);
		glVertexAttribPointer(attribute, stride, dataType, false,0, 0);
		
	}
}
