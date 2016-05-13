package CPU;

public class ThreadCPUI4 implements Runnable{

	/*The class ThreadCPUI4 calculates the Giga Iops when Integer operations 
	 are performed using a single thread*/
	
	int a=10,b=5,c=5,d=10;
	
	public void run(){
		for(int i=0;i<1500000;i++){	
						a= (a+b)*(a+b)-((a*a)+(2*a*b)+(b*b));
						b= (a-b)*(a-b)-((a*a)-(2*a*b)+(b*b));
					        c= a*(b*a)-(b+d)*(c+a)+c-(d+a)*c;
					}	
		
		/*The operations performed are repeated a number of times to calculate Iops
		 accurately*/
	}		
	public static void main(String[] args) {
			
			ThreadCPUI4 runobj = new ThreadCPUI4();
	
	System.out.println("For 4 Threads in use");
	
	Thread one = new Thread(runobj);
	Thread two = new Thread(runobj);
	Thread three = new Thread(runobj);
	Thread four = new Thread(runobj);
	
	/*Thread creation*/
	
	long start = System.nanoTime();
	one.start();
	two.start();
	three.start();
	four.start();
	long stop = System.nanoTime();
	/*System.nanoTime() gives the system time when the function is called in
	 nano seconds*/
	/*Starting the threads*/
	
	double time = stop - start;
	/*The time before starting the threads and time after both threads have
	ran(Threads stops automatically after performing the steps in the run method)
	is calculated, the difference in this time gives the elapsed time to perform 
	the number of operations provided*/
	double Iops = (4*45000000)/time;
	   /*Iops is given by total number of Integer operations performed 
    divided by the time taken to perform these operations*/
	/*Since four threads run parallely each thread will perform the number of 
	 operations provided hence four times the number of operations are performed in
	 elapsed time*/
	
	System.out.println("GIops for thread is "+Iops);

	}
}
	
