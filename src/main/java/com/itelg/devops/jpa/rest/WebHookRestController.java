package com.itelg.devops.jpa.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itelg.devops.jpa.service.WebHookService;

@RestController
@RequestMapping("webhook")
public class WebHookRestController
{
    @Autowired
    private WebHookService webHookService;

    @RequestMapping(method = RequestMethod.GET, value = "jenkins")
    public List<String> getJenkinsHosts()
    {
        return webHookService.getJenkinsHosts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "jenkins/delete/{jenkinsHost}")
    public void deleteWebHooksByJenkinsHost(@PathVariable String jenkinsHost)
    {
        webHookService.deleteWebHooksByJenkinsHost(jenkinsHost);
    }
}