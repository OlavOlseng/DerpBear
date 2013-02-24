package core;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public abstract class BaseGame{
	Thread loop;
	private int fps, currentFPS;
	private long lastFPS;
	String title = "DerpBear Pre-Alpha 0.0";
	DisplayMode dm;
	
	public BaseGame(int fps){
		this.fps = fps;
	}

	public void init(){
		try {
			dm = new DisplayMode(800,600);
			Display.setDisplayMode(dm);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		run();
	}


	public void run() {
		long dt;
		long lastUpdate = getTime();
		lastFPS = getTime();
		while(!Display.isCloseRequested()){
			
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
	
	public abstract void onTick(float dt);
}
