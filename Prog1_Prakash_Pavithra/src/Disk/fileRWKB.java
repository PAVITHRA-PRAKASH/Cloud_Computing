package Disk;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class fileRWKB implements Runnable {

	byte []a = new byte[1];
	byte []b = new byte[1024];
	byte []c = new byte[1048576];

	/*Three block sizes that needs to be read and written from/to the disc is initialized
	  here with 1Byte,1KByte(1024 Bytes),1MByte(1048576)*/
	static Map<String,Double> rbs = new HashMap<String,Double>();
	static Map<String,Double> wbs = new HashMap<String,Double>();
	static Map<String,Double> rkbs = new HashMap<String,Double>();
	static Map<String,Double> wkbs = new HashMap<String,Double>();
	static Map<String,Double> rmbs = new HashMap<String,Double>();
	static Map<String,Double> wmbs = new HashMap<String,Double>();
	static Map<String,Double> rbr = new HashMap<String,Double>();
	static Map<String,Double> wbr = new HashMap<String,Double>();
	static Map<String,Double> rkbr = new HashMap<String,Double>();
	static Map<String,Double> wkbr = new HashMap<String,Double>();
	static Map<String,Double> rmbr = new HashMap<String,Double>();
	static Map<String,Double> wmbr = new HashMap<String,Double>();

	static Map<String,Double> rbsl = new HashMap<String,Double>();
	static Map<String,Double> wbsl = new HashMap<String,Double>();
	static Map<String,Double> rkbsl = new HashMap<String,Double>();
	static Map<String,Double> wkbsl = new HashMap<String,Double>();
	static Map<String,Double> rmbsl = new HashMap<String,Double>();
	static Map<String,Double> wmbsl = new HashMap<String,Double>();
	static Map<String,Double> rbrl = new HashMap<String,Double>();
	static Map<String,Double> wbrl = new HashMap<String,Double>();
	static Map<String,Double> rkbrl = new HashMap<String,Double>();
	static Map<String,Double> wkbrl = new HashMap<String,Double>();
	static Map<String,Double> rmbrl = new HashMap<String,Double>();
	static Map<String,Double> wmbrl = new HashMap<String,Double>();

    /*The hashmap is used to generate a key value pair and can be used here to obtain value of throughput or latency 
     of the currentThread so that average values of two threads is calculated  */
	
        long timews = 0;
	long timewe = 0;

	double time = 0;
	double timeinmicro = 0;
	double throughput = 0;
	double latency =0;

	Random randombyte = new Random();
	int rb = randombyte.nextInt(1048576);
	
        /*Random() generates a random number and here it provides a random number between(0-1048575) 
	 every time the object of the random function is called*/

	/*Sequential read and write of the files: Method to read 1Byte of data from one
	 *  file with timer on and to write 1Byte of data to one file with timer on*/
	
          public void readwrite1bs() throws IOException{

		FileInputStream i = new FileInputStream("dummyfile.txt"); 
		FileOutputStream o = new FileOutputStream("dummyfile.txt");

		timews = System.nanoTime();
		for(int e=0;e<1000;e++){
			i.read(a);
		}
		timewe = System.nanoTime();
		/*System.nanoTime() provides the system time when called, time before reading and after reading 1 byte 1000
		 times i.e - 1000 Bytes is calculated*/

		time = (timewe - timews);
		throughput = (1000000000000.0/(1024*1024))/time;
		/*Throughput is number of bytes read or written divided by the time taken to do the operation,the throughput
		 in this program is finally simplified to Megabytes per second*/

		rbs.put(Thread.currentThread().getName(),throughput);
		/*The map is used to retrieve the throughput(value) for the currentThread with its name as a key*/
		
		rbsl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the latency i.e time(value) for the currentThread with its name as a key*/

		i.close();	

		timews = System.nanoTime();
		for(int e=0;e<1000;e++){
			o.write(a);
		}
		timewe = System.nanoTime();
		/*System.nanoTime() provides the system time when called, time before writing and after writing 1 byte 1000
		 times i.e - 1000 Bytes is calculated*/

		time = (timewe - timews);
		throughput = (1000000000000.0/(1024*1024))/time;
				wbs.put(Thread.currentThread().getName(),throughput);
		wbsl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency i.e time(value) for the currentThread with its name as a key*/
		o.close();
	}

	/*Sequential read and write of the files: Method to read 1KByte of data from one
	 file with timer on and to write 1KByte of data to one file with timer on*/
	
          public void readwrite1kbs() throws IOException{
		FileInputStream	i = new FileInputStream("dummyfile.txt"); 
		FileOutputStream	o = new FileOutputStream("dummyfile.txt");

		timews = System.nanoTime();
		for(int e=0;e<100;e++){
			i.read(b);
		}
		timewe = System.nanoTime();

		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before reading and after reading 1 KByte 100
		 times i.e - 100 KBytes is calculated*/
		throughput = (100000000000.0/1024)/time;
		rkbs.put(Thread.currentThread().getName(),throughput);
		rkbsl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency i.e time(value) for the currentThread with its name as a key*/
		i.close();

		timews = System.nanoTime();
		for(int e=0;e<100;e++){
			o.write(b);
		}
		timewe = System.nanoTime();

		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before writing and after writing 1 KByte 100
		 times i.e - 100 KBytes is calculated*/
		throughput = (100000000000.0/1024)/time;

		wkbs.put(Thread.currentThread().getName(),throughput);
		wkbsl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency i.e (value) for the currentThread with its name as a key*/

		o.close();	
	}

	/*Sequential read and write of the files: Method to read 1MByte of data from one
	 *  file with timer on and to write 1MByte of data to one file with timer on*/

	public void readwrite1mbs() throws IOException{
		FileInputStream i = new FileInputStream("dummyfile.txt");
		DataInputStream bs = new DataInputStream(i); 
		FileOutputStream o = new FileOutputStream("dummyfile.txt");  

		timews = System.nanoTime();
		for(int e=0;e<2;e++){
			bs.read(c);
		}
		timewe = System.nanoTime();
		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before reading and after reading 1 MByte 2
		 times i.e - 2MBytes is calculated*/

		throughput = (100000000.0 *2)/time;
		rmbs.put(Thread.currentThread().getName(),throughput);
		rmbsl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency i.e time (value) for the currentThread with its name as a key*/
		
		bs.close();
		i.close();

		timews = System.nanoTime();
		for(int e=0;e<2;e++){
			o.write(c);	
		}
		timewe = System.nanoTime();
		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before writing and after writing 1 MByte 2
		 times i.e - 2 MBytes is calculated*/
		
		throughput = (1000000000*2.0)/time;
		
		wmbs.put(Thread.currentThread().getName(),throughput);
		wmbsl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency i.e (value) for the currentThread with its name as a key*/
		o.close();
	}

	/*Random read and write of the files:Method to read 1Byte of data from one
	 *  file with timer on and to write 1Byte of data to one file with timer on */
	public void readwrite1br() throws IOException{
		RandomAccessFile w = new RandomAccessFile("dummyfile.txt","rw");
		rb = randombyte.nextInt(1048576);
		timews = System.nanoTime();
		for(int e=0;e<1000;e++){
			w.seek(rb);
			w.read(a);
		}
		timewe = System.nanoTime();
		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before reading and after reading 1 Byte 1000
		 times i.e - 1000 Bytes is calculated*/
		throughput = (1000000000000.0/(1024*1024))/time;
	
        	rbr.put(Thread.currentThread().getName(),throughput);
		rbrl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency(value) for the currentThread with its name as a key*/
		w.close();

		rb = randombyte.nextInt(1048576);
		w = new RandomAccessFile("dummyfile.txt","rw");
		timews = System.nanoTime();
		for(int e=0;e<1000;e++){
			w.seek(rb);
			w.write(a);
		}
		timewe = System.nanoTime();
		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before writing and after writing 1 Byte 1000
		 times i.e - 1000 Bytes is calculated*/
	
		throughput =  (1000000000000.0/(1024*1024))/time;

		wbr.put(Thread.currentThread().getName(),throughput);
		wbrl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency i.e time(value) for the currentThread with its name as a key*/
		
		w.close();	
	}


	/*Random read and write of the files:Method to read 1KByte of data from one
	 *  file with timer on and to write 1KByte of data to one file with timer on */
	public void readwrite1kbr()throws IOException{

		RandomAccessFile w = new RandomAccessFile("dummyfile.txt","rw");
		rb = randombyte.nextInt(1048576);
		timews = System.nanoTime();
		for(int e=0;e<100;e++){
			w.seek(rb);
			w.read(b);
		}
		timewe = System.nanoTime();
		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before reading and after reading 1 KByte 100
		 times i.e - 100 KBytes is calculated*/

		throughput = (100000000000.0/1024)/time;
		rkbr.put(Thread.currentThread().getName(),throughput);
		rkbrl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency i.e time(value) for the currentThread with its name as a key*/
		
		w.close();


		rb = randombyte.nextInt(1048576);
		w = new RandomAccessFile("dummyfile.txt","rw");
		timews = System.nanoTime();
		for(int e=0;e<100;e++){
			w.seek(rb);
			w.write(b);
		}
		timewe = System.nanoTime();
		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before writing and after writing 1 KByte 100
		 times i.e - 100 KBytes is calculated*/
		throughput = (100000000000.0/1024)/time;

		wkbr.put(Thread.currentThread().getName(),throughput);
		wkbrl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency i.e time(value) for the currentThread with its name as a key*/
		
		w.close();	
	}

	/*Random read and write of the files:Method to read 1KByte of data from one
	 *  file with timer on and to write 1KByte of data to one file with timer on */
	public void readwrite1mbr()throws IOException{
		RandomAccessFile w = new RandomAccessFile("dummyfile.txt","rw");
		rb = randombyte.nextInt(1048576);
		timews = System.nanoTime();
		for(int e=0;e<2;e++){
			w.seek(rb);
			w.read(c);
		}
		timewe = System.nanoTime();
		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before reading and after reading 1 MByte 2
		 times i.e - 2 MBytes is calculated*/
		
		throughput = (1000000000*2.0)/time;
		rmbr.put(Thread.currentThread().getName(),throughput);
		rmbrl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency i.e time(value) for the currentThread with its name as a key*/
		
		w.close();

		rb = randombyte.nextInt(1048576);
		w = new RandomAccessFile("dummyfile.txt","rw");
		timews = System.nanoTime();
		for(int e=0;e<2;e++){
			w.seek(rb);
			w.write(c);
		}
		timewe = System.nanoTime();
		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before writing and after writing 1 MByte 2
		 times i.e - 2 MBytes is calculated*/
		throughput = (1000000000*2.0)/time;
		/*Latency is time taken to write 1MByte, hence time is divided by 100 as time for 100 MBytes is captured*/

		wmbr.put(Thread.currentThread().getName(),throughput);
		wmbrl.put(Thread.currentThread().getName(),time);
		/*The map is used to put the throughput(value) and latency i.e time(value) for the currentThread with its name as a key*/
		w.close();

	}

	public void run() {
		fileRWKB tobj = new fileRWKB();
		try{
			tobj.readwrite1bs();
			tobj.readwrite1kbs();
			tobj.readwrite1mbs();
			tobj.readwrite1br();
			tobj.readwrite1kbr();
			tobj.readwrite1mbr();
		}catch(Exception e){
			System.out.println(e);
		}

	}

	public static void main(String[] args) throws Exception {

		fileRWKB object = new fileRWKB();
		Thread threadone = new Thread(object);
		Thread threadtwo = new Thread(object);
		threadone.setName("one");
		threadtwo.setName("two");
		threadone.start();
		threadtwo.start();
		
		threadone.join();
		threadtwo.join();
		/*thread join() helps in making threads run one after the other and keep away from overlapping*/
		
		double a=0;
		double b=0;
		
		a = rbs.get("one");
		b = rbs.get("two");
		/*Retrieving the values from the hash map for thread one and thread two*/
		double avgThroughput = (a+b)/2;
		/*Finding the average of the values(Throughput) from both the threads*/
		a = rbsl.get("one");
		b = rbsl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two*/
		double avgLatency = (a+b)/2;
		/*Finding the average of the values(latency) from both the threads*/

                System.out.println("\nConcurrency - 2 threads");
		System.out.println("\nAvg Latency - 1Byte Sequential Read:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1Byte Sequential Read: "+avgThroughput+" MBps\n\n");
		a = wbs.get("one");
		b = wbs.get("two");

		/*Retrieving the values from the hash map for thread one and thread two*/
		avgThroughput = (a+b)/2;
		/*Average throughput calculated*/
		a = wbsl.get("one");
		b = wbsl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two*/
		avgLatency = (a+b)/2;
		System.out.println("Avg Latency - 1Byte Sequential Write:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1Byte Sequential Write: "+avgThroughput+" MBps\n\n");      
		a = rkbs.get("one");
		b = rkbs.get("two");
		/*Retrieving the values from the hash map for thread one and thread two*/
		avgThroughput = (a+b)/2;
		/*Average throughput calculated*/
		a = rkbsl.get("one");
		b = rkbsl.get("two");
		avgLatency = (a+b)/2;
		/*Retrieving the values from the hash map for thread one and thread two*/
		System.out.println("Latency - 1KByte Sequential Read:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1KByte Sequential Read: "+avgThroughput+" MBps\n\n");
		a = wkbs.get("one");
		b = wkbs.get("two");

		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgThroughput = (a+b)/2;
		a = wkbsl.get("one");
		b = wkbsl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgLatency = (a+b)/2;
		System.out.println("Latency - 1KByte Sequential Write:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1KByte Sequential Write: "+avgThroughput+" MBps\n\n");
		a = rmbs.get("one");
		b = rmbs.get("two");

		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgThroughput = (a+b)/2;
		a = rmbsl.get("one");
		b = rmbsl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgLatency = (a+b)/2;
		System.out.println("Latency - 1MByte Sequential Read:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1MByte Sequential Read: "+avgThroughput+" MBps\n\n");
		a = wmbs.get("one");
		b = wmbs.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgThroughput = (a+b)/2;
		a = wmbsl.get("one");
		b = wmbsl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgLatency = (a+b)/2;
		System.out.println("Latency - 1MByte Sequential Write:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1MByte Sequential Write: "+avgThroughput+" MBps\n\n");
		a = rbr.get("one");
		b = rbr.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgThroughput = (a+b)/2;
		a = rbrl.get("one");
		b = rbrl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgLatency = (a+b)/2;
		System.out.println("Latency - 1Byte Random Read:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1Byte Random Read: "+avgThroughput+" MBps\n\n");
		a = wbr.get("one");
		b = wbr.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgThroughput = (a+b)/2;
		a = wbrl.get("one");
		b = wbrl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgLatency = (a+b)/2;
		System.out.println("Latency - 1Byte Random Write:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1Byte Random Write: "+avgThroughput+" MBps\n\n");
		a = rkbr.get("one");
		b = rkbr.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgThroughput = (a+b)/2;
		a = rkbrl.get("one");
		b = rkbrl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgLatency = (a+b)/2;
		System.out.println("Latency - 1KByte Random Read:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1KByte Random Read: "+avgThroughput+" MBps\n\n");
		a = wkbr.get("one");
		b = wkbr.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgThroughput = (a+b)/2;
		a = wkbrl.get("one");
		b = wkbrl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgLatency = (a+b)/2;
		System.out.println("Latency - 1KByte Random Write:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1KByte Random Write: "+avgThroughput+" MBps\n\n");
		a = rmbr.get("one");
		b = rmbr.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgThroughput = (a+b)/2;
		a = rmbrl.get("one");
		b = rmbrl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgLatency = (a+b)/2;
		System.out.println("Latency - 1MByte Random Read:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1MByte Random Read: "+avgThroughput+" MBps\n\n");
		a = wmbr.get("one");
		b = wmbr.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgThroughput = (a+b)/2;
		a = wmbrl.get("one");
		b = wmbrl.get("two");
		/*Retrieving the values from the hash map for thread one and thread two and calculating average of the values(Latency/throughput)
		 retrieved*/
		avgLatency = (a+b)/2;
		System.out.println("Latency - 1MByte Random Write:"+avgLatency+" nano seconds");
		System.out.println("Avg Throughput - 1MByte Random Write: "+avgThroughput+" MBps\n\n");



	}

}