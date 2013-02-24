package core;


import world.GameWorld;

public class TestGame extends BaseGame{

	GameWorld world;
	
	public TestGame(int fps) {
		super(fps, 800, 600);
		
	}
	
	@Override
	public void onTick(float dt) {
		world.update(dt);
		world.render();
	}
	
	public void debugDraw() {
		
	}
	
	public static void main(String argv[]) {
		TestGame game = new TestGame(10000);
		game.init();
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		world  = new GameWorld();
	}
}
