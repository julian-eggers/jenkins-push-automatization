package com.itelg.devops.jpa.repository.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import java.util.Collections;
import java.util.List;

import org.easymock.TestSubject;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProjectHook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.MockStrict;
import org.powermock.modules.junit4.PowerMockRunner;

import com.itelg.devops.jpa.domain.WebHook;
import com.itelg.devops.jpa.repository.WebHookRepository;
import com.itelg.devops.jpa.test.support.DomainTestSupport;

@RunWith(PowerMockRunner.class)
public class HttpWebHookRepositoryTest implements DomainTestSupport
{
    @TestSubject
    private WebHookRepository repository = new HttpWebHookRepository();

    @MockStrict
    private GitlabAPI gitlabApi;

    @Test
    public void testInsertWebHook() throws Exception
    {
        gitlabApi.addProjectHook(Long.valueOf(1), "http://jenkins-host/git/notifyCommit?url=git@gitlab-server:devops/jenkins-push-automatization.git", true, false, false, false, false);
        expectLastCall().andReturn(null);

        replayAll();
        repository.insertWebHook(getCompleteWebHookWithSslScheme());
        verifyAll();
    }

    @Test
    public void testDeleteWebHook() throws Exception
    {
        gitlabApi.deleteProjectHook(anyObject(GitlabProjectHook.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteGitlabProjectHook());
            return null;
        });

        replayAll();
        repository.deleteWebHook(getCompleteWebHookWithSslScheme());
        verifyAll();
    }

    @Test
    public void testWebHooksByProject() throws Exception
    {
        gitlabApi.getProjectHooks(Long.valueOf(1));
        expectLastCall().andReturn(Collections.singletonList(getCompleteGitlabProjectHook()));

        replayAll();
        List<WebHook> webHooks = repository.getWebHooksByProject(getCompleteProject());
        verifyAll();

        assertEquals(1, webHooks.size());
        assertThat(webHooks.get(0)).isEqualToComparingFieldByFieldRecursively(getCompleteWebHookWithSslScheme());
    }
}
