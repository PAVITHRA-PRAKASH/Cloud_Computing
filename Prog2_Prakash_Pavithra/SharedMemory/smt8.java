import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*smt8 class implements multithreading of concurrency 8 which calls the eight threads to read the blocks
 and sort the blocks as well as merge the files once the data is sorted*/

public class smt8 implements Runnable {

/*run method includes the two methods which the eight threads perform,readBlock and mergefiles*/
                SharedMem obj = new SharedMem();
	public void run() {
		
		File dataset = new File("/mnt/raid/1gbdata");
		FileReader r;
		try {
			r = new FileReader(dataset);
			obj.readBlock(r);
			obj.mergefiles(obj.listofFiles);
			r.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		smt8 robject = new smt8();
//creation of threads 
		Thread threadone = new Thread(robject);
		Thread threadtwo = new Thread(robject);
		Thread threadthree = new Thread(robject);
		Thread threadfour = new Thread(robject);
		Thread threadfive = new Thread(robject);
		Thread threadsix = new Thread(robject);
		Thread threadseven = new Thread(robject);
		Thread threadeight = new Thread(robject);
		long start = System.nanoTime();
/*start function starts the threads
		threadone.start();
		threadtwo.start();
		threadthree.start();
		threadfour.start();
		threadfive.start();
		threadsix.start();
		threadseven.start();
		threadeight.start();
/*join function helps to maintain concurrency where one thread can start only after
completion of other*/
		threadone.join();	
		threadtwo.join();
		threadthree.join();
		threadfour.join();
		threadfive.join();	
		threadsix.join();
		threadseven.join();
		threadeight.join();
		long end = System.nanoTime();
		System.out.println("Start time: "+start);
		System.out.println("End time:"+end);
		System.out.println("Time Taken to Sort(8 Threads Used) is "+(end-start)+"nano seconds\n");


	}

}


