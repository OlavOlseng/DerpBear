package core;


import org.jbox2d.callbacks.DebugDraw;

import rendering.LineDrawer;
import rendering.MatrixUtil;
import rendering.Pipeline;
import world.GameWorld;
import world.dbDebugDraw;

public class TestGame extends BaseGame{

	public static final float PIXELSCALE = 32/2;
	
	GameWorld world;
	LineDrawer ldr;
	dbDebugDraw dbgDraw;
	Pipeline pipeline;
	
	public TestGame(int fps) {
		super(fps, 800, 600);
	}
	
	@Override
	public void onTick(float dt) {
		world.update(dt);
		render();
	}
	
	public void render() {
		world.render(pipeline);
		ldr.render(pipeline);
		ldr.clear();
	}
	
	@Override
	public void setup() {
		pipeline = new Pipeline();
		pipeline.setProjectionMatrix(MatrixUtil.getOrthographicProjection(0, 800, 0, 600));
		ldr = new LineDrawer(100000);
		dbgDraw = new dbDebugDraw(ldr);
		
		
		world  = new GameWorld();
		world.getPhysWorld().setDebugDraw(dbgDraw);
		dbgDraw.setFlags(DebugDraw.e_shapeBit);
	}
	
	public static void main(String argv[]) {
		TestGame game = new TestGame(10000);
		game.init();
	}
}
