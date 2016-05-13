import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*smt4 class implements multithreading of concurrency 4 which calls the fourthreads to read the blocks
 and sort the blocks as well as merge the files once the data is sorted*/

public class smt4 implements Runnable {

/*run method includes the two methods which the four threads perform,readBlock and mergefiles*/
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
		
                smt4 robject = new smt4();
//creation of threads
		Thread threadone = new Thread(robject);
		Thread threadtwo = new Thread(robject);
		Thread threadthree = new Thread(robject);
		Thread threadfour = new Thread(robject);
// Time taken by the threads to sort is noted and the difference is calculated
		long start = System.nanoTime();
//start function starts the thread 
		threadone.start();
		threadtwo.start();
		threadthree.start();
		threadfour.start();
/*join function helps to maintain concurrency where one thread can start only after
completion of other*/
		threadone.join();	
		threadtwo.join();
		threadthree.join();
		threadfour.join();
		long end = System.nanoTime();
		System.out.println("Start time: "+start);
		System.out.println("End time:"+end);
		System.out.println("Time Taken to Sort(4 Threads Used) is "+(end-start)+"nano seconds\n");


	}

}

