package CPU;

public class ThreadCPUI2 implements Runnable{

	/*The class ThreadCPUI2 calculates the Giga Iops when Integer operations 
	 are performed using a single thread*/
	
	int a=10,b=5,c=5,d=10;

	public void run(){
		for(int i=0;i<300000;i++){	
						a= (a+b)*(a+b)-((a*a)+(2*a*b)+(b*b));
						b= (a-b)*(a-b)-((a*a)-(2*a*b)+(b*b));
					        c=a*(b*a)-(b+d)*(c+a)+c-(d+a)*c;
					}
		
		/*The operations performed are repeated a number of times to calculate Iops
		 accurately*/
	}

	public static void main(String[] args) {

		ThreadCPUI2 runobj = new ThreadCPUI2();
		System.out.println("For 2 Threads in use");
		
		Thread t1 = new Thread(runobj);
		Thread t2 = new Thread(runobj);
		
		/*Thread creation*/
		
		long start = System.nanoTime();
		t1.start();
		t2.start();
		/*System.nanoTime() gives the system time when the function is called in
		 nano seconds*/
		/*Starting the threads*/
		
		
		long stop = System.nanoTime();
		
		double time = stop - start;
		/*The time before starting the threads and time after both threads have
		ran(Threads stops automatically after performing the steps in the run method)
		is calculated, the difference in this time gives the elapsed time to perform 
		the number of operations provided*/
		
		double Iops = (2*9000000)/time;
		
	   /*Iops is given by total number of Integer operations performed 
	     divided by the time taken to perform these operations*/
		/*Since two threads run parallely each thread will perform the number of 
		 operations provided hence double number of operations are performed in
		 elapsed time*/
		
		System.out.println("GIops for thread is "+Iops);	
		
	}
}

