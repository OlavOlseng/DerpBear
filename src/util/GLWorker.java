package util;

public class GLWorker {

	/**
	 * Invoke a method on the GL thread, as soon as possible
	 * @param worker - Job to me invoked
	 */
	public static void invokeLater(Runnable worker){
		GLWorkerManager.addWorker(worker);
	}
}
