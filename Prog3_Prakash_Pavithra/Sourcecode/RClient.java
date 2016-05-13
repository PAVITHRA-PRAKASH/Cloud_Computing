import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class RClient {

	static Queue<String> Scheduler = new LinkedList<String>();
	static Queue<String> Response = new LinkedList<String>();

	int cqueuesize1;
	long start = 0;
	long stop = 0;
	long time = 0;

	String read = null;
	int itaskid = 1;
	static int queueLength = 0;
	// This is the method for Local task execution where the messages are added by the client to local queue
	public void add2q(String[] args) throws IOException{
		FileReader input = new FileReader(args[6]);
		BufferedReader br = new BufferedReader(input);
		while((read= br.readLine())!= null){
			//adding the line read from the workload file into the queue 
			Scheduler.add(itaskid+" "+read.split("\\s+")[1]);
			itaskid++;
		}	
		br.close();	
	}

	public void add2sqs(String[] args) throws AmazonServiceException, AmazonClientException, IOException, InterruptedException{

		FileReader input = new FileReader(args[4]);
		BufferedReader br = new BufferedReader(input);

		//create queue this is the SQS Sample code which is implemented to create SQS queue
		CreateQueueRequest createQueueRequest = new CreateQueueRequest(args[2]);
		String QUrl = Framework.sqs.createQueue(createQueueRequest).getQueueUrl();

		start = System.currentTimeMillis();
		while((read= br.readLine())!= null){
			//Tasks from the workload file are added to SQS queue
			Framework.sqs.sendMessage(new SendMessageRequest(QUrl, itaskid+" "+read.split("\\s+")[1]));
			itaskid++;
			queueLength++;
		}
		GetQueueAttributesRequest request = new GetQueueAttributesRequest(Framework.sqs.getQueueUrl(args[2]).getQueueUrl()).withAttributeNames("All");
		Map<String,String> nummsg = Framework.sqs.getQueueAttributes(request).getAttributes();
		cqueuesize1 = Integer.parseInt(nummsg.get("ApproximateNumberOfMessages"));

		while(cqueuesize1 != 0){
			request = new GetQueueAttributesRequest(Framework.sqs.getQueueUrl(args[2]).getQueueUrl()).withAttributeNames("All");
			nummsg = Framework.sqs.getQueueAttributes(request).getAttributes();
			cqueuesize1 = Integer.parseInt(nummsg.get("ApproximateNumberOfMessages"));	
		}

		stop = System.currentTimeMillis();
		time = stop - start;

		System.out.println("Time taken by remote worker is "+time);
		br.close();	
	}	


}

