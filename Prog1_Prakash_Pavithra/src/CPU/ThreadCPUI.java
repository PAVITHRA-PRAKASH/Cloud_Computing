package CPU;

	public class ThreadCPUI implements Runnable{
		int a=10,b=5,c=5,d=10,e=8,n=8,s=3,l=4,t=6,m=6,r=3,x=2,y=3,z=4,g=7,j=4,h=2;

		/*The class ThreadCPUI calculates the Giga Iops when Integer operations 
		 are performed using a single thread*/
		
		public void run(){
			long start = System.nanoTime();
		/*System.nanoTime() gives the system time when the function is called in
		 nano seconds*/
			for(int i=0;i<50000000;i++){	
						a = (a+b)*(a+b)-((a*a)+(2*a*b)+(b*b));
						b = (a-b)*(a-b)-((a*a)-(2*a*b)+(b*b));
					        c = a*(b*a)-(b+d)*(c+a)+c-(d+a)*c;
                                                e = c*l-b;
                                                n = m+n;
                                                s = r-s;
                                                l = l*r+m;
                                                c = l*s+n;
                                                t = n+e;
                                                x = y+z;
                                                g = j*h+d;
                                                j = m-n;
						}
   /*The operations performed are repeated a number of times to calculate Iops
		accurately*/

			long stop = System.nanoTime();
			double time = stop - start;
       /*The time before starting the operations and time after ending is calculated
	    the difference in this time gives the elapsed time to perform the number of
	    operations provided*/
			
			double Iops = (215000000/time)*10;
	  /*Iops is given by total number of integer operations performed divided by the
	  time taken to perform these operations*/
			
			System.out.println("GIops for thread is "+Iops);			
		}
		
		public static void main(String[] args) {
			ThreadCPUI runobj = new ThreadCPUI();
			Thread one = new Thread(runobj);
                        System.out.println("Iops\n");
			System.out.println("For 1 Thread in use");
			one.start();
		/*Since this class calculates Iops when only one thread is running,
		 single thread is created and started */			
			
		}
	}

