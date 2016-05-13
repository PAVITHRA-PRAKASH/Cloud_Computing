import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
	
public class hadoopsn extends Configured implements Tool {

	  public static class MyMapper
	       extends Mapper<LongWritable, Text, Text, Text>{

	    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
       String line = value.toString();	    
       String mykey = line.substring(0,10);
       String myvalue = line.substring(10,value.toString().length());
       Text mkey =new Text();
       mkey.set(mykey);
       Text mvalue =new Text();
       mvalue.set(myvalue);
       context.write(mkey,mvalue);
	    }
	    
	  }

	  public static class MyReducer
	       extends Reducer<Text,Text,Text,Text> {

	    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	      for (Text val : values) {
	        context.write(key,val);
	      }
	    }
	    
	  }

	  public static void main(String[] args) throws Exception {
	      int res = ToolRunner.run(new hadoopsn(),args);
	        System.exit(res);
	  }
	  
	public int run(String[] args) throws Exception {
	    Configuration conf = getConf();
	    Job job = new Job(conf, "hadoopsn");
	    job.setJarByClass(hadoopsn.class);
	    job.setMapperClass(MyMapper.class);
	    job.setCombinerClass(MyReducer.class);
	    job.setReducerClass(MyReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    job.setNumReduceTasks(2);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    return job.waitForCompletion(true) ? 0 : 1;
	  }
	}