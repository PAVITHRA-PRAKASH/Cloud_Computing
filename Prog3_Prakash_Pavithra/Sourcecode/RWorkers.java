import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RWorkers {
	// This class implements the functionality required for the execution of task for the local queue.
	static int numofthreads = 0;
	static Queue<String> Cqueue = new LinkedList<String>();
	int tasktime = 0;
	long start = 0;
	long stop = 0;
	long time = 0;

	public void taskexecution(){
		System.out.println("Local Workers Called");
		//Iterates over the source queue
		Iterator<String> queueIterator = Cqueue.iterator();
		//Creates a threadpool
		ExecutorService e= Executors.newFixedThreadPool(numofthreads);
		while(queueIterator.hasNext()){
			RQueueTask queueTask = new RQueueTask();
			//for each element in the source queue
			String content = (queueIterator.next());
			//takes the task ie time to sleep from the source queue by splitting over the space
			queueTask.tasktime = Integer.parseInt(content.split("\\s+")[1]);
			//Thredpool executes the task mentioned in the queuetask class
			e.execute(queueTask);
			//Result of the task execution is returned to response queue
			RClient.Response.add(content.split("\\s+")[0]);
		}
//Threadpool terminates
		e.shutdown();
		while(!e.isTerminated()){

		}
	}
}
