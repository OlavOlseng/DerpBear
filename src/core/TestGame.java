package core;


import world.GameWorld;

public class TestGame extends BaseGame{

	GameWorld world;
	
	public TestGame(int fps) {
		super(fps);
		world  = new GameWorld();
	}
	
	@Override
	public void onTick(float dt) {
		world.update(dt);
	}
	
	public void debugDraw() {
		
	}
	
	public static void main(String argv[]) {
		TestGame game = new TestGame(10000);
		game.init();
	}
}
