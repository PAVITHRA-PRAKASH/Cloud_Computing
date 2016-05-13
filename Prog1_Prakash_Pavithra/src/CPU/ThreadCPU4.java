package CPU;

public class ThreadCPU4 implements Runnable{
	/*The class ThreadCPU4 calculates the Giga Flops when Floating point operations 
	 are performed using four threads*/
	float a=10.00f,b=5.00f,c=5.00f,d=10.00f;

	public void run(){

		for(int i=0;i<1500000;i++){	
			a= (a+b)*(a+b)-((a*a)+(2.0f*a*b)+(b*b));
			b= (a-b)*(a-b)-((a*a)-(2.0f*a*b)+(b*b));
			c=a/(b*a)-(b+d)*(c+a)+c-(d+a)*c;
		}

		/*The operations performed are repeated a number of times to calculate flops
		 accurately*/
		
	}

	public static void main(String[] args) {

		ThreadCPU4 runobj = new ThreadCPU4();

		System.out.println("For 4 Threads in use");
		Thread one = new Thread(runobj);
		Thread two = new Thread(runobj);
		Thread three = new Thread(runobj);
		Thread four = new Thread(runobj);
       /*Thread Creation*/
		long start = System.nanoTime();
		/*System.nanoTime() gives the system time when the function is called in
		 nano seconds*/
		
		one.start();
		two.start();
		three.start();
		four.start();
		/*Starting the threads*/
		long stop = System.nanoTime();

		/*The time before starting the threads and time after both threads have
		ran(Threads stops automatically after performing the steps in the run method)
		is calculated, the difference in this time gives the elapsed time to perform 
		the number of operations provided*/
		
		double time = stop - start;
		double Flops = (4*45000000.0)/time;
		
	    /*Flops is given by total number of floating point operations performed 
	     divided by the time taken to perform these operations*/
		/*Since four threads run parallely each thread will perform the number of 
		 operations provided hence 4 times the number of operations are performed in
		 elapsed time*/
		
		System.out.println("Gflops for thread is "+Flops);

	}
}

