import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

@SuppressWarnings("deprecation")
public class RemoteWorkers {
	int tasktime = 0;
	int itaskid=1;
	String msgs;
	//File Response = new File("Response");
	static AmazonDynamoDBClient dynamoDB;


	//Defines the attribute of the dynamoDB table
	static Map<String, AttributeValue> newItem(String taskid) {
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		//Result is the Column name i.e attribute name
		item.put("Result", new AttributeValue(taskid));
		return item;
	}


	public void taskexecution(String[] args) throws IOException{
		// Number of threads the worker should implement is passed through the arguments
		int numofthreads = Integer.parseInt(args[4]);
		int cqueuesize1 = 0;

		System.out.println("Remotes Workers Called");
		String tableName = "Validation-table";		 

		//create response queue
		CreateQueueRequest createQueueRequest = new CreateQueueRequest("Responsequeue");
		String RUrl = Framework.sqs.createQueue(createQueueRequest).getQueueUrl();

		dynamoDB = new AmazonDynamoDBClient(Framework.credentials);
		dynamoDB.setRegion(Region.getRegion(Regions.US_EAST_1));

		//get content of the source queue
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(Framework.sqs.getQueueUrl(args[2]).getQueueUrl());



		RQueueTask queueTask = new RQueueTask();	

		//To check the current number of messages on the SQS queue

		GetQueueAttributesRequest request = new GetQueueAttributesRequest(Framework.sqs.getQueueUrl(args[2]).getQueueUrl()).withAttributeNames("All");
		Map<String,String> nummsg = Framework.sqs.getQueueAttributes(request).getAttributes();
		cqueuesize1 = Integer.parseInt(nummsg.get("ApproximateNumberOfMessages"));

		// Implementation logic where the workers pick message and perform the task, message is received one at a time from SQS
		/*System.out.println("start"+start);*/
		while(cqueuesize1 != 0){	
			request = new GetQueueAttributesRequest(Framework.sqs.getQueueUrl(args[2]).getQueueUrl()).withAttributeNames("All");
			nummsg = Framework.sqs.getQueueAttributes(request).getAttributes();
			cqueuesize1 = Integer.parseInt(nummsg.get("ApproximateNumberOfMessages"));
			try{


				List<Message> messages = Framework.sqs.receiveMessage(receiveMessageRequest).getMessages();
				if(messages.size()>0){
					msgs = (messages.get(0)).getBody().toString();	
					//System.out.println(msgs);

					// DynamoDB

					/*	try{*/
					//while(messages.size() != 0){
					if (Tables.doesTableExist(dynamoDB, tableName)) {
						//System.out.println("Table " + tableName + " is already ACTIVE");
					} else {

						// Create a table with a primary hash key named 'name', which holds a string
						CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
								.withKeySchema(new KeySchemaElement().withAttributeName("Result").withKeyType(KeyType.HASH))
								.withAttributeDefinitions(new AttributeDefinition().withAttributeName("Result").withAttributeType(ScalarAttributeType.S))
								.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(20L).withWriteCapacityUnits(20L));
						TableDescription createdTableDescription = dynamoDB.createTable(createTableRequest).getTableDescription();

						// Wait for it to become active
						System.out.println("Waiting for " + tableName + " to become ACTIVE...");
						Tables.awaitTableToBecomeActive(dynamoDB, tableName);
					}
					// Describe our new table
					DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
					TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();



					// Add an item to DynamoDB table
					Map<String, AttributeValue> item = newItem(String.valueOf(itaskid));
					PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
					PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);


					/*		try {
								// Sleep for 60 seconds.
								Thread.sleep(60 * 1000);
							} catch (Exception e) {
								// Do nothing because it woke up early.
							}*/
					//System.out.println("Printing before Execution");

					// recieves the task a part of the message which is split 	
					queueTask.tasktime = Integer.parseInt(msgs.split("\\s+")[1]);
					//System.out.println(queueTask.tasktime);
					//Executes the task as a pool of threads
					ExecutorService e= Executors.newFixedThreadPool(numofthreads);
					e.execute(queueTask);
					//System.out.println("Execution of task done :"+queueTask.tasktime);
					//	System.out.println("Task Executed: "+itaskid);
					/*		stop = System.currentTimeMillis();
							System.out.println("stop"+stop);
							time += (stop - start);
							System.out.println(time);*/
					e.shutdown();
					while(!e.isTerminated()){

					}

					Framework.sqs.sendMessage(new SendMessageRequest(RUrl, itaskid+" "+msgs.split("\\s+")[1]));
					itaskid++;
					//delete message
					//if(messages.size() > 0){
					request = new GetQueueAttributesRequest(Framework.sqs.getQueueUrl(args[2]).getQueueUrl()).withAttributeNames("All");
					nummsg = Framework.sqs.getQueueAttributes(request).getAttributes();
					cqueuesize1 = Integer.parseInt(nummsg.get("ApproximateNumberOfMessages"));

					// Deletes the message from the source queue to keep track of the messages and uniqueness
					String messageReceiptHandle = messages.get(0).getReceiptHandle();

					Framework.sqs.deleteMessage(new DeleteMessageRequest(Framework.sqs.getQueueUrl(args[2]).getQueueUrl(), messageReceiptHandle));
					//}

					// Number of messages in the source queue after deletion of the current message
					request = new GetQueueAttributesRequest(Framework.sqs.getQueueUrl(args[2]).getQueueUrl()).withAttributeNames("All");
					nummsg = Framework.sqs.getQueueAttributes(request).getAttributes();
					cqueuesize1 = Integer.parseInt(nummsg.get("ApproximateNumberOfMessages"));


				}

			}catch(Exception e1){
				e1.printStackTrace();
				//System.out.println();
			}
			// Time taken by the worker using the multithreading is measured
		}             

		//System.out.println("Time for 1 worker's task completion "+time);

	}
}
