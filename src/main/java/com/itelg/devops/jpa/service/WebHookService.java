package com.itelg.devops.jpa.service;

import java.util.List;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;

public interface WebHookService
{
    void addMissingWebHooks(Project project);

    void insertWebHook(WebHook webHook);

    void deleteWebHook(WebHook webHook);

    void deleteWebHooksByJenkinsHost(String jenkins);

    List<WebHook> getWebHooksByProject(Project project);

    List<String> getJenkinsHosts();
}