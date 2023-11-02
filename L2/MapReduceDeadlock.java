import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

class Locks {
  public static final Object lock1 = new Object();
  public static final Object lock2 = new Object();
}

class DeadlockMapper extends Mapper<Object, Text, Text, IntWritable> {
  private Text key2 = new Text();
  private static final IntWritable value2 = new IntWritable();

  @Override
  public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
    key2.set("a");
    value2.set(1);

    synchronized (Locks.lock1) {
      synchronized (Locks.lock2) {
        context.write(key2, value2);
      }
    }
  }
}

class DeadlockReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
  @Override
  public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
    synchronized (Locks.lock2) {
      synchronized (Locks.lock1) {
        context.write(key, new IntWritable(1));
      }
    }
  }
}

public class MapReduceDeadlock {
  public static void main(String[] args) throws Exception {
    Job job = new Job();
    job.setJobName("MapReduceDeadlock");

    job.setMapperClass(DeadlockMapper.class);
    job.setReducerClass(DeadlockReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
