package util;

public class GLWorker {

	public static void invokeLater(Runnable worker){
		GLWorkerManager.addWorker(worker);
	}
}
