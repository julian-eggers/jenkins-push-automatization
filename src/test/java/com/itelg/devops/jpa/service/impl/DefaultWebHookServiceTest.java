package com.itelg.devops.jpa.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        expectLastCall().andAnswer(() ->
        {
            List<Project> projects = new ArrayList<>();
            projects.add(getCompleteProject(1, "jenkins-push-automatization"));
            projects.add(getCompleteProject(2, "docker-autodeploy"));
            return projects;
        });

        webHookRepository.getWebHooksByProject(anyObject(Project.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteProject(1, "jenkins-push-automatization"));

            WebHook webHook1 = getCompleteWebHookWithSslScheme();
            webHook1.setUrl("http://jenkins-server1/git/notifyCommit?url=git@gitlab-server:test/project.git");

            WebHook webHook2 = new WebHook();
            webHook2.setUrl("http://jenkins-server2/git/notifyCommit?url=git@gitlab-server:test/project.git");

            return List.of(webHook1, webHook2);
        });

        webHookRepository.deleteWebHook(anyObject(WebHook.class));
        expectLastCall().andAnswer(() ->
        {
            WebHook expected = getCompleteWebHookWithSslScheme();
            expected.setUrl("http://jenkins-server1/git/notifyCommit?url=git@gitlab-server:test/project.git");
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(expected);
            return null;
        });

        webHookRepository.getWebHooksByProject(anyObject(Project.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteProject(2, "docker-autodeploy"));

            WebHook webHook1 = getCompleteWebHookWithSslScheme();
            webHook1.setUrl("http://jenkins-server1/git/notifyCommit?url=git@gitlab-server:test/project.git");

            WebHook webHook2 = getCompleteWebHookWithSslScheme();
            webHook2.setUrl("http://jenkins-server1/api/push?url=git@gitlab-server:test/project.git");

            return List.of(webHook1, webHook2);
        });

        webHookRepository.deleteWebHook(anyObject(WebHook.class));
        expectLastCall().andAnswer(() ->
        {
            WebHook expected = getCompleteWebHookWithSslScheme();
            expected.setUrl("http://jenkins-server1/git/notifyCommit?url=git@gitlab-server:test/project.git");
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(expected);
            return null;
        });

        replayAll();
        service.deleteWebHooksByJenkinsHost("jenkins-server1");
        verifyAll();
    }

    @Test
    public void testGetJenkinsHosts()
    {
        projectService.getAllProjects();
        expectLastCall().andAnswer(() ->
        {
            List<Project> projects = new ArrayList<>();
            projects.add(getCompleteProject(1, "jenkins-push-automatization"));
            projects.add(getCompleteProject(2, "docker-autodeploy"));
            return projects;
        });

        webHookRepository.getWebHooksByProject(anyObject(Project.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteProject(1, "jenkins-push-automatization"));

            List<WebHook> webHooks = new ArrayList<>();

            WebHook webHook1 = new WebHook();
            webHook1.setUrl("http://jenkins-server1/git/notifyCommit?url=git@gitlab-server:test/project.git");
            webHooks.add(webHook1);

            WebHook webHook2 = new WebHook();
            webHook2.setUrl("http://jenkins-server2/git/notifyCommit?url=git@gitlab-server:test/project.git");
            webHooks.add(webHook2);

            return webHooks;
        });

        webHookRepository.getWebHooksByProject(anyObject(Project.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteProject(2, "docker-autodeploy"));

            WebHook webHook1 = getCompleteWebHookWithSslScheme();
            webHook1.setUrl("http://jenkins-server1/git/notifyCommit?url=git@gitlab-server:test/project.git");

            WebHook webHook2 = getCompleteWebHookWithSslScheme();
            webHook2.setUrl("http://jenkins-server3/api/push?url=git@gitlab-server:test/project.git");

            return List.of(webHook1, webHook2);
        });

        replayAll();
        List<String> jenkinsUrls = service.getJenkinsHosts();
        verifyAll();

        assertEquals(2, jenkinsUrls.size());
        assertTrue(jenkinsUrls.contains("jenkins-server1"));
        assertTrue(jenkinsUrls.contains("jenkins-server2"));
    }
}