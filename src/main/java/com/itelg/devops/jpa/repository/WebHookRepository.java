package com.itelg.devops.jpa.repository;

import java.util.List;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;

public interface WebHookRepository
{
    void insertWebHook(WebHook webHook);

    void deleteWebhook(WebHook webHook);

    List<WebHook> getWebHooksByProject(Project project);
}