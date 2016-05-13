import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class Framework {

//Security Credentials for the AWS account where the SQS Queue and the Dynamo DB table is going to created is intialized
	
static AWSCredentials credentials = new ProfileCredentialsProvider("default").getCredentials();
static AmazonSQS sqs = new AmazonSQSClient(credentials);

public static void main(String[] args) throws IOException, AmazonServiceException, AmazonClientException, InterruptedException{
		int cargt =0;
		long start = 0;
		long stop = 0;
		long time = 0;

		/* TO write the output into the file (Local)*/
		File f = new File("Response");
		FileWriter fw = new FileWriter(f);
		RClient c = new RClient();
	    RWorkers worker = new RWorkers();
	    RemoteWorkers rw = new RemoteWorkers();	   
	    /*AWS account's region is set where the queue and table will be created*/
		try {
            Region useast1 = Region.getRegion(Regions.US_EAST_1);
            sqs.setRegion(useast1);	    
        } catch (Exception e) {
            extracted(e);
        }
	    
	    //Local Command Line Interface of Client
	    
		if(args[0].equalsIgnoreCase("client") && args[1].equalsIgnoreCase("-s") && args[2].equalsIgnoreCase("LOCAL") && args[3].equalsIgnoreCase("-t")&& args[5].equalsIgnoreCase("-w")&& args.length ==7){
			
			System.out.println("Running Client Local");
			//-t which is the number of threads/workers in case of LOCAL implementation is taken from the command line arguments
			cargt = Integer.parseInt(args[4]); 
			//taking from the arguments and passing to local worker implementation class as number of threads
			RWorkers.numofthreads = cargt;
			start = System.currentTimeMillis();
			//Function that calls the Local Client Implementation logic
			c.add2q(args);
			// Putting elements of Scheduler to Cqueue
			RWorkers.Cqueue.addAll(RClient.Scheduler);
			// Function that calls the Local Worker Implementation Logic
			worker.taskexecution();
			stop = System.currentTimeMillis();
			// time taken to implement the local task execution is measured
			time = stop - start;
			System.out.println("Time Taken for Task Execution: "+time+" ms");
			// Comparing the response and source queue for completion
			Iterator<String> RIterator = RClient.Response.iterator();
			Iterator<String> SIterator = RClient.Scheduler.iterator();
			//The Task ID in the source queue is checked with the task ID in the response queue if equal then the result is written to a file as success else failed
			while(SIterator.hasNext()){
				String icontent = SIterator.next();
				String ocontent = RIterator.next();
				if(icontent.split("\\s+")[0].equals(ocontent)){
				fw.write("Task ID-"+(icontent.split("\\s+")[0])+" -Success[0]"+"\n");
				}
				else{
					fw.write("Task ID-"+(icontent.split("\\s+")[0])+" -Failed[1]"+"\n");	
				}
			}
			fw.close();
		}
		
		//Remote Command Line Interface of Client
		
		else if(args[0].equalsIgnoreCase("client") && args[1].equalsIgnoreCase("-s") && args[3].equalsIgnoreCase("-w")){
		  	System.out.println("Running Client Remote");
				//Validates if the queue name argument is the queue that is created, if yes then executes the task
			  	if(!args[2].equals("")){
			  		// calls the remote client to add the messages into amazon's SQS
			  		c.add2sqs(args);
			  	}else{
			  		//if the argument is not valid returns error message
			  		System.out.println("Queue not created in SQS");
			  	}	
		}

		// Remote Worker Interface
		
		else if(args[0].equalsIgnoreCase("worker") && args[1].equalsIgnoreCase("-s") && args[3].equalsIgnoreCase("-t")){
			//checks for the interface of the worker (Remote) 
			System.out.println("Running worker Remote");
			if(!args[2].equals("")){
				//queue name is valid of he SQS then execute the tasks i.e pick up one task and perform the task
				rw.taskexecution(args);
			}
		}
		
		// Invalid Arguments passed
		
		else{
			//if the arguments doesnot match any of the interfaces mentioned returns error message
			System.out.println("Invalid command line arguments");
		}
	}

private static void extracted(Exception e) {
	//if the credentials of the account is corrupted returns exception
	throw new AmazonClientException(
	        "Cannot load the credentials from the credential profiles file. " +
	        "Please make sure that your credentials file is at the correct " +
	        "location (/home/pprakas8/.aws/credentials), and is in valid format.",
	        e);
}	
}


