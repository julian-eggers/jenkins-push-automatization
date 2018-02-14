package com.itelg.devops.jpa.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itelg.devops.jpa.domain.CheckoutUrlScheme;
import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;
import com.itelg.devops.jpa.repository.WebHookRepository;
import com.itelg.devops.jpa.service.ProjectService;
import com.itelg.devops.jpa.service.WebHookService;
import com.itelg.devops.jpa.util.URLUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultWebHookService implements WebHookService
{
    @Autowired
    private WebHookRepository webHookRepository;

    @Autowired
    private ProjectService projectService;

    @NotBlank
    @Value("${jenkins.url}")
    private String jenkinsUrl;

    @NotNull
    @Value("${gitlab.checkout.url.scheme}")
    private CheckoutUrlScheme checkoutUrlScheme;

    @Override
    public void addMissingWebHooks(Project project)
    {
        String webhookUrl = buildWebhookUrl(project);

        if (!getWebHooksByProject(project).stream().anyMatch(webhook -> webhook.getUrl().equals(webhookUrl)))
        {
            WebHook webHook = new WebHook();
            webHook.setUrl(webhookUrl);
            webHook.setProjectId(project.getId());
            insertWebHook(webHook);
        }
    }

    private String buildWebhookUrl(Project project)
    {
        String webhookUrl = jenkinsUrl + "git/notifyCommit?url=";

        if (checkoutUrlScheme == CheckoutUrlScheme.SSH)
        {
            webhookUrl = webhookUrl + project.getCheckoutUrlSsh();
        }
        else
        {
            webhookUrl = webhookUrl + project.getCheckoutUrlHttp();
        }

        return webhookUrl;
    }

    @Override
    public void insertWebHook(WebHook webHook)
    {
        log.info("Inserting {}", webHook);
        webHookRepository.insertWebHook(webHook);
    }

    @Override
    public void deleteWebHook(WebHook webHook)
    {
        log.info("Deleting {}", webHook);
        webHookRepository.deleteWebHook(webHook);
    }

    @Override
    public void deleteWebHooksByJenkinsHost(String jenkinsHost)
    {
        log.info("Start deleting webHooks of jenkins \"" + jenkinsHost + "\"");

        for (Project project : projectService.getAllProjects())
        {
            for (WebHook webHook : getWebHooksByProject(project))
            {
                if (URLUtil.getHost(webHook.getUrl()).equals(jenkinsHost) && webHook.getUrl().contains("git/notifyCommit?url="))
                {
                    deleteWebHook(webHook);
                }
            }
        }

        log.info("Finished deleting webHooks");
    }

    @Override
    public List<WebHook> getWebHooksByProject(Project project)
    {
        return webHookRepository.getWebHooksByProject(project);
    }

    @Override
    public List<String> getJenkinsHosts()
    {
        Set<String> jenkinsUrls = new HashSet<>();

        for (Project project : projectService.getAllProjects())
        {
            for (WebHook webHook : getWebHooksByProject(project))
            {
                if (webHook.getUrl().contains("git/notifyCommit?url="))
                {
                    jenkinsUrls.add(URLUtil.getHost(webHook.getUrl()));
                }
            }
        }

        return new ArrayList<>(jenkinsUrls);
    }
}