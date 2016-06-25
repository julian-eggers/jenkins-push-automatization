package com.itelg.devops.jpa.repository.http;

import java.util.ArrayList;
import java.util.List;

import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabProjectHook;
import org.springframework.stereotype.Repository;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;
import com.itelg.devops.jpa.repository.WebHookRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class HttpWebHookRepository extends AbstractGitlabHttpRepository implements WebHookRepository
{
    @Override
    public void insertWebHook(WebHook webHook)
    {
        try
        {
            gitlabAPI.addProjectHook(Long.valueOf(webHook.getProjectId()), webHook.getUrl(), true, false, false, false);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<WebHook> getWebHooksByProject(Project project)
    {
        List<WebHook> webHooks = new ArrayList<>();

        try
        {
            GitlabProject gitlabProject = new GitlabProject();
            gitlabProject.setId(toInteger(project.getId()));

            for (GitlabProjectHook gitlabProjectHook : gitlabAPI.getProjectHooks(gitlabProject))
            {
                WebHook webHook = new WebHook();
                webHook.setId(Long.parseLong(gitlabProjectHook.getId()));
                webHook.setProjectId(gitlabProjectHook.getProjectId().longValue());
                webHook.setUrl(gitlabProjectHook.getUrl());
                webHooks.add(webHook);
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }

        return webHooks;
    }
}