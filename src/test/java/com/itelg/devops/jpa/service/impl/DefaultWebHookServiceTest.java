package com.itelg.devops.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.easymock.annotation.MockStrict;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;
import com.itelg.devops.jpa.repository.WebHookRepository;
import com.itelg.devops.jpa.service.ProjectService;
import com.itelg.devops.jpa.service.WebHookService;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
public class DefaultWebHookServiceTest
{
    private WebHookService service;

    @MockStrict
    private WebHookRepository webHookRepository;

    @MockStrict
    private ProjectService projectService;

    @Before
    public void before()
    {
        service = new DefaultWebHookService();
        Whitebox.setInternalState(service, webHookRepository);
        Whitebox.setInternalState(service, projectService);
    }

    @Test
    public void testDeleteWebHooksByJenkinsHost()
    {
        projectService.getAllProjects();
        PowerMock.expectLastCall().andAnswer(() ->
        {
            List<Project> projects = new ArrayList<>();

            Project project1 = new Project();
            project1.setId(1);
            projects.add(project1);

            Project project2 = new Project();
            project2.setId(2);
            projects.add(project2);

            return projects;
        });

        webHookRepository.getWebHooksByProject(EasyMock.anyObject(Project.class));
        PowerMock.expectLastCall().andAnswer(() ->
        {
            Project project = (Project) EasyMock.getCurrentArguments()[0];
            Assert.assertEquals(1, project.getId());

            List<WebHook> webHooks = new ArrayList<>();

            WebHook webHook1 = new WebHook();
            webHook1.setId(1);
            webHook1.setUrl("http://jenkins-server1/git/notifyCommit?url=git@gitlab-server:test/project.git");
            webHooks.add(webHook1);

            WebHook webHook2 = new WebHook();
            webHook2.setId(2);
            webHook2.setUrl("http://jenkins-server2/git/notifyCommit?url=git@gitlab-server:test/project.git");
            webHooks.add(webHook2);

            return webHooks;
        });

        webHookRepository.deleteWebhook(EasyMock.anyObject(WebHook.class));
        PowerMock.expectLastCall().andAnswer(() ->
        {
            WebHook webHook = (WebHook) EasyMock.getCurrentArguments()[0];
            Assert.assertEquals(1, webHook.getId());
            return null;
        });

        webHookRepository.getWebHooksByProject(EasyMock.anyObject(Project.class));
        PowerMock.expectLastCall().andAnswer(() ->
        {
            Project project = (Project) EasyMock.getCurrentArguments()[0];
            Assert.assertEquals(2, project.getId());

            List<WebHook> webHooks = new ArrayList<>();

            WebHook webHook1 = new WebHook();
            webHook1.setId(3);
            webHook1.setUrl("http://jenkins-server1/git/notifyCommit?url=git@gitlab-server:test/project.git");
            webHooks.add(webHook1);

            WebHook webHook2 = new WebHook();
            webHook2.setId(2);
            webHook2.setUrl("http://jenkins-server1/api/push?url=git@gitlab-server:test/project.git");
            webHooks.add(webHook2);

            return webHooks;
        });

        webHookRepository.deleteWebhook(EasyMock.anyObject(WebHook.class));
        PowerMock.expectLastCall().andAnswer(() ->
        {
            WebHook webHook = (WebHook) EasyMock.getCurrentArguments()[0];
            Assert.assertEquals(3, webHook.getId());
            return null;
        });

        PowerMock.replayAll();
        service.deleteWebHooksByJenkinsHost("jenkins-server1");
        PowerMock.verifyAll();
    }

    @Test
    public void testGetJenkinsHosts()
    {
        projectService.getAllProjects();
        PowerMock.expectLastCall().andAnswer(() ->
        {
            List<Project> projects = new ArrayList<>();

            Project project1 = new Project();
            project1.setId(1);
            projects.add(project1);

            Project project2 = new Project();
            project2.setId(2);
            projects.add(project2);

            return projects;
        });

        webHookRepository.getWebHooksByProject(EasyMock.anyObject(Project.class));
        PowerMock.expectLastCall().andAnswer(() ->
        {
            Project project = (Project) EasyMock.getCurrentArguments()[0];
            Assert.assertEquals(1, project.getId());

            List<WebHook> webHooks = new ArrayList<>();

            WebHook webHook1 = new WebHook();
            webHook1.setUrl("http://jenkins-server1/git/notifyCommit?url=git@gitlab-server:test/project.git");
            webHooks.add(webHook1);

            WebHook webHook2 = new WebHook();
            webHook2.setUrl("http://jenkins-server2/git/notifyCommit?url=git@gitlab-server:test/project.git");
            webHooks.add(webHook2);

            return webHooks;
        });

        webHookRepository.getWebHooksByProject(EasyMock.anyObject(Project.class));
        PowerMock.expectLastCall().andAnswer(() ->
        {
            Project project = (Project) EasyMock.getCurrentArguments()[0];
            Assert.assertEquals(2, project.getId());

            List<WebHook> webHooks = new ArrayList<>();

            WebHook webHook1 = new WebHook();
            webHook1.setUrl("http://jenkins-server1/git/notifyCommit?url=git@gitlab-server:test/project.git");
            webHooks.add(webHook1);

            return webHooks;
        });

        PowerMock.replayAll();
        List<String> jenkinsUrls = service.getJenkinsHosts();
        PowerMock.verifyAll();

        Assert.assertEquals(2, jenkinsUrls.size());
        Assert.assertTrue(jenkinsUrls.contains("jenkins-server1"));
        Assert.assertTrue(jenkinsUrls.contains("jenkins-server2"));
    }
}