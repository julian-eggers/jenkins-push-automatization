package com.itelg.devops.jpa.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import java.util.Collections;

import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.MockStrict;
import org.powermock.modules.junit4.PowerMockRunner;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.service.ProjectService;
import com.itelg.devops.jpa.service.WebHookService;
import com.itelg.devops.jpa.test.support.DomainTestSupport;

@RunWith(PowerMockRunner.class)
public class WebHooksCreateTaskTest implements DomainTestSupport
{
    @TestSubject
    private WebHooksCreateTask task = new WebHooksCreateTask();

    @MockStrict
    private ProjectService projectService;

    @MockStrict
    private WebHookService webHookService;

    @Test
    public void testRun()
    {
        projectService.getAllProjects();
        expectLastCall().andReturn(Collections.singletonList(getCompleteProject()));

        webHookService.addMissingWebHooks(anyObject(Project.class));
        expectLastCall().andAnswer(() ->
        {
            assertThat(getCurrentArguments()[0]).isEqualToComparingFieldByFieldRecursively(getCompleteProject());
            return null;
        });

        replayAll();
        task.run();
        verifyAll();
    }
}
