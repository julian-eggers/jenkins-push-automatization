package com.itelg.devops.jpa.repository.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import java.util.Collections;
import java.util.List;

import org.easymock.TestSubject;
import org.gitlab.api.GitlabAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.MockStrict;
import org.powermock.modules.junit4.PowerMockRunner;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.repository.ProjectRepository;
import com.itelg.devops.jpa.test.support.DomainTestSupport;

@RunWith(PowerMockRunner.class)
public class HttpProjectRepositoryTest implements DomainTestSupport
{
    @TestSubject
    private ProjectRepository repository = new HttpProjectRepository();

    @MockStrict
    private GitlabAPI gitlabApi;

    @Test
    public void testGetAllProjects() throws Exception
    {
        gitlabApi.getProjects();
        expectLastCall().andReturn(Collections.singletonList(getCompleteGitlabProject()));

        replayAll();
        List<Project> projects = repository.getAllProjects();
        verifyAll();

        assertEquals(1, projects.size());
        assertThat(projects.get(0)).isEqualToComparingFieldByFieldRecursively(getCompleteProject());
    }
}
