package core;

public abstract class BaseGame implements Runnable{
	Thread loop;
	private int fps;

	public BaseGame(int fps){
		this.fps = fps;
	}

	public void init(){
		loop = new Thread(this);
		loop.setPriority(Thread.MAX_PRIORITY);
		loop.start();
	}

	@Override
	public void run() {
		double lastUpdate = System.currentTimeMillis();
		double dt;
		double nowTime;
		double fraction = 1000.0/(double)fps;

		while(true){
			nowTime = System.currentTimeMillis(); 
			dt = nowTime - lastUpdate;

			while(dt < fraction){
				try{
					Thread.sleep((long) (fraction - dt));

				} catch (Exception e){
					e.printStackTrace();
				}
				dt = System.currentTimeMillis() - lastUpdate;
			}
			lastUpdate = System.currentTimeMillis();
			onTick(dt);
		}
	}

	public abstract void onTick(double dt);
}
