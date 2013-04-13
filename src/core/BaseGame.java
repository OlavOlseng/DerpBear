package core;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public abstract class BaseGame implements Runnable{
	
	private int fps, currentFPS, screenWidth, screenHeight;
	private long lastFPS;
	private boolean hasWindow;
	String title = "DerpBear Pre-Alpha 0.0.1";
	DisplayMode dm;
	private Color clearColor = new Color(0,0,0);
	
	

	public BaseGame(int fps){
		this(fps,0,0);
	}
	public BaseGame(int fps,int screenWidth,int screenHeight){
		this.fps = fps;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	
	/**
	 * Creates a window and a valid opengl context.
	 * Calls {@link #setup()} 
	 * Starts the main loop
	 */
	public void init(){
		this.init(true);
	}
	
	/**
	 * Creates a window and a valid opengl context if specified.
	 * Calls {@link #setup()} 
	 * Starts the main loop
	 * @param createWindow - true if a window should be created.
	 */
	public void init(boolean createWindow){
		hasWindow = createWindow;
			
		Thread gameThread = new Thread(this);
		gameThread.start();
		
		
	}


	public void run() {
		if(hasWindow){
			this.hasWindow = true;
			try {
				dm = new DisplayMode(screenWidth,screenHeight);
				Display.setDisplayMode(dm);
				Display.create();
				GL11.glViewport(0, 0, screenWidth, screenHeight);
			} catch (LWJGLException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		setup();
		long dt;
		long lastUpdate = getTime();
		lastFPS = getTime();
		while(!hasWindow || !Display.isCloseRequested()){
			if(hasWindow){
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glClearColor((float)this.clearColor.getRed()/255.0f, (float)this.clearColor.getGreen()/255.0f, (float)this.clearColor.getBlue()/255.0f, 1.0f);
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			}
			dt = getTime() - lastUpdate;
			lastUpdate = getTime();
			updateFPS();
			onTick((float)dt/1000.0f);
			
			if(hasWindow){
				Display.update();
				Display.sync(fps);
			}
			
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

	/**
	 * Called after {@link #init()}. Init game state and load resources here.
	 * 
	 */
	public abstract void setup();
	/**
	 * Called once every frame. Put game logic and rendering in this method
	 * 
	 * @param dt time since last tick
	 */
	public abstract void onTick(float dt);
}
