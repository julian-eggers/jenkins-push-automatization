package com.itelg.devops.jpa.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.easymock.annotation.MockStrict;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.itelg.devops.jpa.domain.CheckoutUrlScheme;
import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;
import com.itelg.devops.jpa.repository.WebHookRepository;
import com.itelg.devops.jpa.service.ProjectService;
import com.itelg.devops.jpa.service.WebHookService;
import com.itelg.devops.jpa.test.support.DomainTestSupport;

@RunWith(PowerMockRunner.class)
public class DefaultWebHookServiceTest implements DomainTestSupport
{
    @TestSubject
    private WebHookService service = new DefaultWebHookService();

    @MockStrict
    private WebHookRepository webHookRepository;

    @MockStrict
    private ProjectService projectService;

    @Before
    public void before()
    {
        Whitebox.setInternalState(service, "http://jenkins-host/");
        Whitebox.setInternalState(service, CheckoutUrlScheme.SSH);
    }

    @Test
    public void addMissingWebHooksWithInsertAndSslScheme()
    {
        webHookRepository.getWebHooksByProject(anyObject(Project.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteProject());
            return Collections.emptyList();
        });

        webHookRepository.insertWebHook(anyObject(WebHook.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteWebHookWithSslScheme());
            return null;
        });

        replayAll();
        service.addMissingWebHooks(getCompleteProject());
        verifyAll();
    }

    @Test
    public void addMissingWebHooksWithInsertAndHttpScheme()
    {
        // Preconditions
        Whitebox.setInternalState(service, CheckoutUrlScheme.HTTP);

        webHookRepository.getWebHooksByProject(anyObject(Project.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteProject());
            return Collections.emptyList();
        });

        webHookRepository.insertWebHook(anyObject(WebHook.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteWebHookWithHttpScheme());
            return null;
        });

        replayAll();
        service.addMissingWebHooks(getCompleteProject());
        verifyAll();
    }

    @Test
    public void addMissingWebHooksWithAlreadyExists()
    {
        webHookRepository.getWebHooksByProject(anyObject(Project.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteProject());
            return Collections.singletonList(getCompleteWebHookWithSslScheme());
        });

        replayAll();
        service.addMissingWebHooks(getCompleteProject());
        verifyAll();
    }

    @Test
    public void testInsertWebHook()
    {
        webHookRepository.insertWebHook(anyObject(WebHook.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteWebHookWithSslScheme());
            return null;
        });

        replayAll();
        service.insertWebHook(getCompleteWebHookWithSslScheme());
        verifyAll();
    }

    @Test
    public void testDeleteWebHook()
    {
        webHookRepository.deleteWebHook(anyObject(WebHook.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteWebHookWithSslScheme());
            return null;
        });

        replayAll();
        service.deleteWebHook(getCompleteWebHookWithSslScheme());
        verifyAll();
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

        webHookRepository.deleteWebHook(EasyMock.anyObject(WebHook.class));
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

        webHookRepository.deleteWebHook(EasyMock.anyObject(WebHook.class));
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