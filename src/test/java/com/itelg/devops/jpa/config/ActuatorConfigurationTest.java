package com.itelg.devops.jpa.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import com.itelg.spring.actuator.jenkins.JenkinsHealthIndicator;

public class ActuatorConfigurationTest
{
    private ApplicationContextRunner contextRunner = new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(ActuatorConfiguration.class));

    @Test
    public void testJenkinsHealthIndicatorWithAuthentication()
    {
        contextRunner.withPropertyValues("jenkins.url=http://jenkins-server:8080/", "jenkins.username=admin", "jenkins.password=secretPassword").run((
                context) ->
        {
            assertThat(context).hasSingleBean(JenkinsHealthIndicator.class);
            JenkinsHealthIndicator healthIndicator = context.getBean(JenkinsHealthIndicator.class);
            assertEquals("http://jenkins-server:8080/", healthIndicator.getUrl());
            assertEquals("admin", healthIndicator.getUsername());
            assertEquals("secretPassword", healthIndicator.getPassword());
        });
    }

    @Test
    public void testJenkinsHealthIndicatorWithAuthenticationButWithoutUsername()
    {
        contextRunner.withPropertyValues("jenkins.url=http://jenkins-server:8080/", "jenkins.username=", "jenkins.password=secretPassword").run((context) ->
        {
            assertThat(context).hasSingleBean(JenkinsHealthIndicator.class);
            JenkinsHealthIndicator healthIndicator = context.getBean(JenkinsHealthIndicator.class);
            assertEquals("http://jenkins-server:8080/", healthIndicator.getUrl());
            assertNull(healthIndicator.getUsername());
            assertNull(healthIndicator.getPassword());
        });
    }

    @Test
    public void testJenkinsHealthIndicatorWithAuthenticationButWithoutPassword()
    {
        contextRunner.withPropertyValues("jenkins.url=http://jenkins-server:8080/", "jenkins.username=admin", "jenkins.password=").run((context) ->
        {
            assertThat(context).hasSingleBean(JenkinsHealthIndicator.class);
            JenkinsHealthIndicator healthIndicator = context.getBean(JenkinsHealthIndicator.class);
            assertEquals("http://jenkins-server:8080/", healthIndicator.getUrl());
            assertNull(healthIndicator.getUsername());
            assertNull(healthIndicator.getPassword());
        });
    }

    @Test
    public void testJenkinsHealthIndicatorWithoutAuthentication()
    {
        contextRunner.withPropertyValues("jenkins.url=http://jenkins-server:8080/", "jenkins.username=", "jenkins.password=").run((context) ->
        {
            assertThat(context).hasSingleBean(JenkinsHealthIndicator.class);
            JenkinsHealthIndicator healthIndicator = context.getBean(JenkinsHealthIndicator.class);
            assertEquals("http://jenkins-server:8080/", healthIndicator.getUrl());
            assertNull(healthIndicator.getUsername());
            assertNull(healthIndicator.getPassword());
        });
    }
}
