package com.itelg.devops.jpa.service.impl;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itelg.devops.jpa.domain.CheckoutUrlScheme;
import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;
import com.itelg.devops.jpa.repository.WebHookRepository;
import com.itelg.devops.jpa.service.WebHookService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultWebHookService implements WebHookService
{
    @Autowired
    private WebHookRepository webHookRepository;

    @NotBlank
    @Value("${jenkins.url}")
    private String jenkinsUrl;

    @NotBlank
    @Value("${gitlab.checkout.url.scheme}")
    private CheckoutUrlScheme checkoutUrlScheme;

    @Override
    public void addMissingWebHooks(Project project)
    {
        String webhookUrl = generateWebhookUrl(project);

        if (!getWebHooksByProject(project).stream().anyMatch(webhook -> webhook.getUrl().equals(webhookUrl)))
        {
            WebHook webHook = new WebHook();
            webHook.setUrl(webhookUrl);
            webHook.setProjectId(project.getId());
            insertWebHook(webHook);
        }
    }

    String generateWebhookUrl(Project project)
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
        webHookRepository.insertWebHook(webHook);
        log.info("WebHook inserted (" + webHook + ")");
    }

    @Override
    public List<WebHook> getWebHooksByProject(Project project)
    {
        return webHookRepository.getWebHooksByProject(project);
    }
}