package CPU;

public class ThreadCPU2 implements Runnable{
	
	/*The class ThreadCPU2 calculates the Giga Flops when Floating point operations 
	 are performed using double threads*/
	float a=10.00f,b=5.00f,c=5.00f,d=10.00f;

	public void run(){
		
		for(int i=0;i<300000;i++){	
			a= (a+b)*(a+b)-((a*a)+(2.0f*a*b)+(b*b));
			b= (a-b)*(a-b)-((a*a)-(2.0f*a*b)+(b*b));
			c=a/(b*a)-(b+d)*(c+a)+c-(d+a)*c;
		}
	
		/*The operations performed are repeated a number of times to calculate flops
		 accurately*/
		
	}

	public static void main(String[] args) {

		ThreadCPU2 runobj = new ThreadCPU2();
		System.out.println("For 2 Threads in use");
		Thread t1 = new Thread(runobj);
		Thread t2 = new Thread(runobj);
		
		/*Thread creation*/
		
		long start = System.nanoTime();
		
		/*System.nanoTime() gives the system time when the function is called in
		 nano seconds*/
		
		t1.start();
		t2.start();
		
		/*Starting the threads*/
		
		long stop = System.nanoTime();

		double time = stop - start;
		
		/*The time before starting the threads and time after both threads have
		ran(Threads stops automatically after performing the steps in the run method)
		is calculated, the difference in this time gives the elapsed time to perform 
		the number of operations provided*/
		
		double Flops = (2*9000000.0)/time;
		
	    /*Flops is given by total number of floating point operations performed 
	     divided by the time taken to perform these operations*/
		/*Since two threads run parallely each thread will perform the number of 
		 operations provided hence double number of operations are performed in
		 elapsed time*/
		
		System.out.println("Gflops for thread is "+Flops);	
		
	}
}

