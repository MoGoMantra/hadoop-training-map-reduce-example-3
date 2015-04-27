/** 
 * Copyright 2015 MoGo Mantra Inc <http://www.mogomantra.com> 
 *  
 * Created Apr 26, 2015 
 * 
 * Last edited by:      $Author: $  
 *             on:      $Date: $  
 *       Filename:      $Id: $ 
 *       Revision:      $Rev: $ 
 *            URL:      $URL: $ 
 */
package com.mogo.training.hadoop.example3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author mohangoyal
 * 
 */
public class FormatConverterSequenceToTextDriver extends Configured implements
		Tool {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
	 */
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.out
					.printf("Two parameters need to be supplied - <input dir> and <output dir>\n");
			return -1;
		}

		Job job = Job.getInstance(getConf());
		job.setJarByClass(FormatConverterSequenceToTextDriver.class);
		job.setJobName("Convert Sequence File and Output as Text");

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setMapperClass(FormatConverterMapper.class);
		job.setNumReduceTasks(0);

		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(),
				new FormatConverterSequenceToTextDriver(), args);
		System.exit(exitCode);
	}
}