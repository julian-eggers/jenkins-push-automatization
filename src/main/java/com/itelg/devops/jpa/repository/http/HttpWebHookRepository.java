package com.itelg.devops.jpa.repository.http;

import java.util.ArrayList;
import java.util.List;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProjectHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;
import com.itelg.devops.jpa.repository.WebHookRepository;

import lombok.SneakyThrows;

@Repository
public class HttpWebHookRepository implements WebHookRepository
{
    @Autowired
    private GitlabAPI gitlabApi;

    @SneakyThrows
    @Override
    public void insertWebHook(WebHook webHook)
    {
        gitlabApi.addProjectHook(Long.valueOf(webHook.getProjectId()), webHook.getUrl(), true, false, false, false, false);
    }

    @SneakyThrows
    @Override
    public void deleteWebHook(WebHook webHook)
    {
        GitlabProjectHook hook = new GitlabProjectHook();
        hook.setId(String.valueOf(webHook.getId()));
        hook.setProjectId(Integer.valueOf(Long.valueOf(webHook.getProjectId()).intValue()));
        hook.setUrl(webHook.getUrl());
        gitlabApi.deleteProjectHook(hook);
    }

    @SneakyThrows
    @Override
    public List<WebHook> getWebHooksByProject(Project project)
    {
        List<WebHook> webHooks = new ArrayList<>();

        for (GitlabProjectHook gitlabProjectHook : gitlabApi.getProjectHooks(Long.valueOf(project.getId())))
        {
            WebHook webHook = new WebHook();
            webHook.setId(Long.parseLong(gitlabProjectHook.getId()));
            webHook.setProjectId(gitlabProjectHook.getProjectId().longValue());
            webHook.setUrl(gitlabProjectHook.getUrl());
            webHooks.add(webHook);
        }

        return webHooks;
    }
}