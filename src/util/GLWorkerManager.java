package util;

import java.util.ArrayList;

public class GLWorkerManager {

	private static ArrayList<Runnable> workers = new ArrayList<Runnable>();
		
	
	public static void addWorker(Runnable runnable){
		synchronized (workers) {
			workers.add(runnable);
		}
		
	}
	
	/**
	 * Must be called on a GL thread
	 */
	public static void invokeAllJobs(){
		synchronized (workers) {
			for(Runnable worker: workers){
				worker.run();
			}
			workers.clear();
		}
		
	}
		
	
	
}
