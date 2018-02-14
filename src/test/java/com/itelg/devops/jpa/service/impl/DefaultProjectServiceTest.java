package com.itelg.devops.jpa.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import java.util.Collections;
import java.util.List;

import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.MockStrict;
import org.powermock.modules.junit4.PowerMockRunner;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.repository.ProjectRepository;
import com.itelg.devops.jpa.service.ProjectService;
import com.itelg.devops.jpa.test.support.DomainTestSupport;

@RunWith(PowerMockRunner.class)
public class DefaultProjectServiceTest implements DomainTestSupport
{
    @TestSubject
    private ProjectService service = new DefaultProjectService();

    @MockStrict
    private ProjectRepository projectRepository;

    @Test
    public void getAllProjects()
    {
        projectRepository.getAllProjects();
        expectLastCall().andReturn(Collections.singletonList(getCompleteProject()));

        replayAll();
        List<Project> projects = service.getAllProjects();
        verifyAll();

        assertEquals(1, projects.size());
        assertThat(projects.get(0)).isEqualToComparingFieldByFieldRecursively(getCompleteProject());
    }
}
