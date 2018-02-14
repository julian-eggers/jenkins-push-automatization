package com.itelg.devops.jpa.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.service.ProjectService;
import com.itelg.devops.jpa.service.WebHookService;

import lombok.extern.slf4j.Slf4j;

@Component
@ManagedResource
@Slf4j
public class WebHooksCreateTask
{
    @Autowired
    private ProjectService projectService;

    @Autowired
    private WebHookService webHookService;

    @Scheduled(fixedDelayString = "${task.delay}")
    @ManagedOperation
    public void run()
    {
        log.info("WebHooksCreateTask started");

        for (Project project : projectService.getAllProjects())
        {
            log.debug("Start checking Project (" + project + ")");
            webHookService.addMissingWebHooks(project);
            log.debug("Finished checking Project (" + project + ")");
        }

        log.info("WebHooksCreateTask finished");
    }
}