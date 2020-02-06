package com.javainuse.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.javainuse.listener.JobCompletionListener;
import com.javainuse.objects.DataObject;
import com.javainuse.step.Processor;
import com.javainuse.step.Reader;
import com.javainuse.step.Writer;

@Configuration
@EnableScheduling
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job processJob() {
		return jobBuilderFactory.get("processJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(step1())
				.end()
				.build();
	}

	@Bean
	public TaskExecutor taskExecutor(){
		return new SimpleAsyncTaskExecutor("spring_batch");
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
		        .<DataObject, DataObject> chunk(10)
				.reader(new Reader())
				.processor(new Processor())
				.writer(new Writer())
				.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}

}