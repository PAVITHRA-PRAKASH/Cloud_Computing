package CPU;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class tenFI implements Runnable{
	/*The class tenF calculates the Giga Flops when four threads are running 
	parallely and writes the Gflops into a file*/
	
int a=10,b=5,c=5,d=10,e=8,l=90,m=6,n=9,r=34,s=45,t=9,x=8,y=9,z=2,h=9,g=34,j=7,k=8;
/*Arrays that will store the value of the flops every second*/       
 static double []a1 = new double[600];
 static double []a2 = new double[600];
        

/*Map will map the thread's name to the Iops that is got every seconds*/
        static Map<String,double[]> tenfI = new HashMap<String,double[]>(); 
	public void run() {
                for(int as=0;as<600;as++){
                	long start = System.nanoTime();
		/*System.nanoTime() gives the system time when the function is called in
		 nano seconds*/
		for(int i=0;i<950000;i++){	
			                        a = (a+b)*(a+b)-((a*a)+(2*a*b)+(b*b));
						b = (a-b)*(a-b)-((a*a)-(2*a*b)+(b*b));
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
		/*The operations performed are repeated a number of times to calculate Iops
		 accurately*/
		long stop = System.nanoTime();
		double time = stop-start;
		/*The time before and time after threads have
		ran is calculated, the difference in this time gives the elapsed time to perform 
		the number of operations provided*/
		double Iops = (44 * 950000)/time;
	    /*Flops is given by total number of Integer operations performed 
	     divided by the time taken to perform these operations*/
		a1[as] = Iops;
	
try{
Thread.sleep(1000);
}catch(InterruptedException e){
System.out.println("Error");
}
     }
      tenfI.put(Thread.currentThread().getName(), a1);          
 }
	public static void main(String[] args) throws IOException,FileNotFoundException, InterruptedException {

		tenFI obj = new tenFI();

		System.out.println("Please wait for ten mins the program is running");
		Thread one = new Thread(obj);
		Thread two = new Thread(obj);
		Thread three = new Thread(obj);
		Thread four = new Thread(obj);
		
		one.setName("tOne");
		two.setName("tTwo");
		three.setName("tThree");
		four.setName("tFour");
		

		one.start();
		two.start();
		three.start();
		four.start();
		


		/*Joining the threads allow to have sync when four threads are running parallely, join helps
                 one thread to complete excecution and then start the second one*/
		one.join();
		two.join();
		three.join();
		four.join();

		
		/*PrintWriter is used to write String into the file, hence the flops in
		 double is printed as a string by appending new line to it*/
		
		FileWriter filew;
		PrintWriter fpw;
		try {
			filew = new FileWriter("tenFI.txt",true);
			/*FileWriter with true parameter helps to append the values and 
			 not overwrite*/
			fpw = new PrintWriter(filew);
			/*PrintWriter taking the FileWriter object which in turn takes file 
			on which writing must be performed a parameter*/
			for(int t=0;t<600;t++){
			a2[t] = tenfI.get("tOne")[t] + tenfI.get("tTwo")[t] + tenfI.get("tThree")[t]+tenfI.get("tFour")[t];
			//String Gflops =Flops+"\n";
			fpw.write(a2[t]+"\n");
			/*Writes the string Gflops to the file*/
			
			}
			fpw.flush();
			fpw.close();
			/*Closes the PrintWriter once the writing to file is done*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
