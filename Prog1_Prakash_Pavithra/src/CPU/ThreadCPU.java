package CPU;

public class ThreadCPU implements Runnable{
	/*The class ThreadCPU calculates the Giga Flops when Floating point operations 
	 are performed using a single thread*/
	
	float a=10.00f,b=5.00f,c=5.00f,d=10.00f,e=8.9f,l=90.6f,m=6.7f,n=9.7f,r=34.5f,s=45.0f,t=9.0f,x=8.9f,y=9.8f,z=2.0f,h=9.8f,g=34.5f,j=7.5f,k=8.6f;

	public void run(){
		/*System.nanoTime() gives the system time when the function is called in
		 nano seconds*/
		long start = System.nanoTime();
		for(int i=0;i<50000000;i++){	
						a = (a+b)*(a+b)-((a*a)+(2.0f*a*b)+(b*b));
						b = (a-b)*(a-b)-((a*a)-(2.0f*a*b)+(b*b));
					        c = a*(b*a)-(b+d)*(c+a)+c-(d+a)*c;
                                                e = c*l-b;
                                                n = m+n;
                                                s = r-s;
                                                l = l*r+m;
                                                c = l*s+n;
                                                t = n+e;
                                                x=y+z;
                                                g=j*h+d;
                                                j=m-n;
                                                k=s+b;
					}
	/*The operations performed are repeated a number of times to calculate flops
	 accurately*/
		long stop = System.nanoTime();
		double time = stop - start;
	/*The time before starting the operations and time after ending is calculated
	 the difference in this time gives the elapsed time to perform the number of
	 operations provided*/
		double Flops = 2200000000.0/time;
    /*Flops is given by total number of floating point operations performed divided
    by the time taken to perform these operations*/
               
		System.out.println("Gflops for thread is "+Flops);			
	}
	public static void main(String[] args){
		ThreadCPU runobj = new ThreadCPU();
		Thread one = new Thread(runobj);
		/*Since this class calculates flops when only one thread is running,
		 single thread is created and started */
                System.out.println("Flops\n");
		System.out.println("For 1 Thread in use");
		one.start();
	}
}