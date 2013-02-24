package core;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public abstract class BaseGame{
	Thread loop;
	private int fps, currentFPS,screenWidth,screenHeight;
	private long lastFPS;
	String title = "DerpBear Pre-Alpha 0.0";
	DisplayMode dm;
	private Color clearColor = new Color(0,0,0);
	
	


	public BaseGame(int fps,int screenWidth,int screenHeight){
		this.fps = fps;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	

	public void init(){
		try {
			dm = new DisplayMode(screenWidth,screenHeight);
			Display.setDisplayMode(dm);
			Display.create();
			GL11.glViewport(0, 0, screenWidth, screenHeight);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		setup();
		run();
	}


	public void run() {
		long dt;
		long lastUpdate = getTime();
		lastFPS = getTime();
		while(!Display.isCloseRequested()){
//			GL11.glEnable(GL11.GL_DEPTH_TEST);
			
			GL11.glClearColor((float)this.clearColor.getRed()/255.0f, (float)this.clearColor.getGreen()/255.0f, (float)this.clearColor.getBlue()/255.0f, 1.0f);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			dt = getTime() - lastUpdate;
			lastUpdate = getTime();
			updateFPS();
			onTick((float)dt/1000.0f);
			
			
			Display.update();
			Display.sync(fps);
			
		}
		exit();
	}

	public void exit() {
		Display.destroy();
	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}


	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle(title + "    " + "FPS:\t" + currentFPS);
			currentFPS = 0;
			lastFPS += 1000;
		}
		currentFPS++;
	}
	
	public int getFPS() {
		return currentFPS;
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
	
	public Color getClearColor() {
		return clearColor;
	}

	public void setClearColor(Color clearColor) {
		this.clearColor = clearColor;
	}

	
	public abstract void setup();
	public abstract void onTick(float dt);
}
