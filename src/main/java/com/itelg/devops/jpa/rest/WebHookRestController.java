package com.itelg.devops.jpa.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itelg.devops.jpa.service.WebHookService;

@RestController
@RequestMapping("webhook")
public class WebHookRestController
{
    @Autowired
    private WebHookService webHookService;

    @GetMapping("jenkins")
    public List<String> getJenkinsHosts()
    {
        return webHookService.getJenkinsHosts();
    }

    @GetMapping("jenkins/delete/{jenkinsHost}")
    public void deleteWebHooksByJenkinsHost(@PathVariable String jenkinsHost)
    {
        webHookService.deleteWebHooksByJenkinsHost(jenkinsHost);
    }
}