import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;

import rendering.Attribute;
import rendering.MatrixUtil;
import rendering.Pipeline;
import rendering.ProgramManager;
import rendering.Shader;
import rendering.Sprite;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Eiriktest {

	public static void main(String[] args) throws LWJGLException, InterruptedException {
		Display.setDisplayMode(new DisplayMode(400, 400));
		Display.create(new PixelFormat());
		Matrix4f mat = new Matrix4f();
		Matrix4f.setIdentity(mat);
		Matrix4f.translate(new Vector3f(10.0f,10f,10.0f), mat, mat);
		Matrix4f test = new Matrix4f();
		Matrix4f.setIdentity(test);
		Vector4f translated = new Vector4f(0.0f,0.0f,0.0f,1.0f);
		
		
		
		glViewport(0, 0, 400, 400);
		Shader shader = ProgramManager.getShader("sprite2DShader");
		Texture tex = null;
		try {
			tex = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("res/square.png"),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sprite sprite = new Sprite(tex);
		sprite.setWidth(100);
		sprite.setHeight(150);
		Pipeline pipeline = new Pipeline();
		pipeline.setProjectionMatrix(MatrixUtil.getOrthographicProjection(0, 400, 0, 400));
		
		
		long lasTime = System.nanoTime();
		long deltaTime;
		sprite.setPosition(200, 200);
		Keyboard.create();
		
		while(!Display.isCloseRequested()){
			
			glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT );
			Keyboard.getEventKeyState();
			//ttf.drawString(0, 0, "hello");
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				sprite.move(1, 0);
			sprite.render(pipeline);
			Display.sync(60);
			sprite.rotate(0.01f);
			Display.swapBuffers();
			Keyboard.poll();
			deltaTime = System.nanoTime() - lasTime;
			lasTime = System.nanoTime();
			
			System.out.println(1000.0/(deltaTime*Math.pow(10, -6)));
			
			
		}
		
	
		
		
	
		
		
	}
}

