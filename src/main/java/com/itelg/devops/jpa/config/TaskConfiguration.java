package com.itelg.devops.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@EnableAsync
public class TaskConfiguration implements SchedulingConfigurer
{
	@Bean
	public ThreadPoolTaskScheduler taskScheduler()
	{
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setBeanName("taskScheduler");
		scheduler.setPoolSize(5);
		scheduler.setThreadNamePrefix("Task-");
		scheduler.setAwaitTerminationSeconds(2);
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		return scheduler;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar registrar)
	{
		registrar.setTaskScheduler(taskScheduler());
	}
}