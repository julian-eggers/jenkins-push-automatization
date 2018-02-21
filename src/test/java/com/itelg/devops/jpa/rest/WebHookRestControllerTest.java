package com.itelg.devops.jpa.rest;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import java.util.Collections;
import java.util.List;

import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.MockStrict;
import org.powermock.modules.junit4.PowerMockRunner;

import com.itelg.devops.jpa.service.WebHookService;

@RunWith(PowerMockRunner.class)
public class WebHookRestControllerTest
{
    @TestSubject
    private WebHookRestController controller = new WebHookRestController();

    @MockStrict
    private WebHookService webHookService;

    @Test
    public void testGetJenkinsHosts() throws Exception
    {
        webHookService.getJenkinsHosts();
        expectLastCall().andReturn(Collections.singletonList("jenkins-host"));

        replayAll();
        List<String> jenkinsHosts = controller.getJenkinsHosts();
        verifyAll();

        assertEquals(1, jenkinsHosts.size());
    }

    @Test
    public void testDeleteWebHooksByJenkinsHost() throws Exception
    {
        webHookService.deleteWebHooksByJenkinsHost("jenkins-host");

        replayAll();
        controller.deleteWebHooksByJenkinsHost("jenkins-host");
        verifyAll();
    }
}
