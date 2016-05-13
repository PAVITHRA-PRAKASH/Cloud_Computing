package Disk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class fileRWB implements Runnable{

	byte []a = new byte[1];
	byte []b = new byte[1024];
	byte []c = new byte[1024*1024];

	/*Three block sizes that needs to be read and written from/to the disc is initialized
	  here with 1Byte,1KByte(1024 Bytes),1MByte(1048576)*/
	
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
		System.out.println("\n\nSingle thread experiment");

		System.out.println("Latency - 1Byte Sequential Read: "+time+" nano seconds");
		System.out.println("Throughput - 1Byte Sequential Read: "+throughput+" MBps\n\n");

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
		
		System.out.println("Latency - 1Byte Sequential Write: "+time+" nano seconds"); 
		System.out.println("Throughput - 1Byte Sequential Write: "+throughput+" MBps\n\n");

		o.close();
	}

	/*Sequential read and write of the files: Method to read 1KByte of data from one
	 file with timer on and to write 1KByte of data to one file with timer on*/
	public void readwrite1kbs() throws IOException{
		FileInputStream	i = new FileInputStream("dummyfile.txt"); 
		FileOutputStream o = new FileOutputStream("dummyfile.txt");

		timews = System.nanoTime();
		for(int e=0;e<100;e++){
		i.read(b);
		}
		timewe = System.nanoTime();

		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before reading and after reading 1 KByte 100
		 times i.e - 100 KBytes is calculated*/
		
	
		throughput = (100000000000.0/1024)/time;
		System.out.println("Latency - 1KByte Sequential Read: "+time+" nano seconds");
		System.out.println("Throughput - 1KByte Sequential Read: "+throughput+" MBps\n\n");

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

		System.out.println("Latency - 1KByte Sequential Write: "+time+" nano seconds"); 
		System.out.println("Throughput - 1KByte Sequential Write: "+throughput+" MBps\n\n");

		o.close();	
	}

	/*Sequential read and write of the files: Method to read 1MByte of data from one
	  file with timer on and to write 1MByte of data to one file with timer on*/
	public void readwrite1mbs() throws IOException{
		FileInputStream i = new FileInputStream("dummyfile.txt"); 
		FileOutputStream o = new FileOutputStream("dummyfile.txt");  

		timews = System.nanoTime();
		for(int e=0;e<2;e++){
		i.read(c);
		}
		timewe = System.nanoTime();
		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before reading and after reading 1 MByte 2
		 times i.e - 2 MBytes is calculated*/
		
		throughput = (100000000*2.0)/time;
		
		System.out.println("Latency - 1MByte Sequential Read: "+time+" nano seconds");
		System.out.println("Throughput - 1MByte Sequential Read: "+throughput+" MBps\n\n");

		i.close();

		timews = System.nanoTime();
		for(int e=0;e<2;e++){
		o.write(c);	
		}
		timewe = System.nanoTime();
		time = (timewe - timews);
		/*System.nanoTime() provides the system time when called, time before writing and after writing 1 MByte 10
		 times i.e - 10 MBytes is calculated*/
		throughput = (1000000000*2.0)/time;
		System.out.println("Latency - 1MByte Sequential Write: "+time+" nano seconds");  
		System.out.println("Throughput - 1MByte Sequential Write: "+throughput+" MBps\n\n");

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
		System.out.println("Latency - 1Byte Random Read:"+time+" nano seconds");
		System.out.println("Throughput - 1Byte Random Read:"+throughput+" MBps\n\n");
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
		throughput = 1000000000000.0/(1024*1024*time);
		System.out.println("Latency - 1Byte Random Write:"+time+" nano seconds");
		System.out.println("Throughput - 1Byte Random Write:"+throughput+" MBps\n\n");
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
		
		System.out.println("Latency - 1KByte Random Read:"+time+" nano seconds");
		System.out.println("Throughput - 1KByte Random Read:"+throughput+" MBps\n");
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
                System.out.println("Latency - 1KByte Random Write:"+time+" nano seconds");
		System.out.println("Throughput - 1KByte Random Write:"+throughput+" MBps\n\n");
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
		/*System.nanoTime() provides the system time when called, time before reading and after reading 1 MByte 100
		 times i.e - 100 MBytes is calculated*/
		
		throughput = 10000000000.0/time;
	
		/*Latency is time taken to read 1MByte, hence time is divided by 100 as time for 2 MBytes is captured*/
		
		System.out.println("Latency - 1MByte Random Read:"+time+" nano seconds");
		System.out.println("Throughput - 1MByte Random Read:"+throughput+" MBps\n\n");
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
		throughput = 10000000000.0/time;
		System.out.println("Latency - 1MByte Random Write:"+time+" nano seconds");
		System.out.println("Throughput - 1MByte Random Write:"+throughput+" MBps\n\n");
		w.close();

	}
	
	public void run() {
		fileRWB tobj = new fileRWB();
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

		fileRWB object = new fileRWB();
        object.run();	
	}

}