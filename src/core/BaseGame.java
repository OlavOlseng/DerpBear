package core;


import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public abstract class BaseGame implements Runnable{
	Thread loop;
	private int fps, currentFPS;
	private long lastFPS;
	String title = "DerpBear Pre-Alpha 0.0";

	public BaseGame(int fps){
		this.fps = fps;
	}

	public void init(){
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		run();
	}


	@Override
	public void run() {
		float dt;
		float lastUpdate = getTime();
		lastFPS = getTime();
		while(!Display.isCloseRequested()){
			dt = getTime() - lastUpdate;

			updateFPS();
			onTick(dt);

			lastUpdate = getTime();
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
	
	public abstract void onTick(double dt);
}
