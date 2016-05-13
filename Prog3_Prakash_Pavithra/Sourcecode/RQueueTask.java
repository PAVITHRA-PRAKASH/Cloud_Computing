public class RQueueTask implements Runnable {	
	int tasktime = 0;
	//This class implements the runnable interface and is the main execution of the task which the threads/workers execute
	public void run() {
		try {
			//Task to be executed is sleep tasks
			Thread.sleep(this.tasktime);
		}
		//catches exception if any 
		catch (NumberFormatException e) {
			System.out.println("Error in numberformatting");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Error due to interruption");
			e.printStackTrace();
		}
	}
}

