package com.itelg.devops.jpa.service;

import java.util.List;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;

public interface WebHookService
{
    void addMissingWebHooks(Project project);

    void insertWebHook(WebHook webHook);

    List<WebHook> getWebHooksByProject(Project project);
}