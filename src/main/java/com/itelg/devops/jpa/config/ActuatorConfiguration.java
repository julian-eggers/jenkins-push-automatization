package com.itelg.devops.jpa.config;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.itelg.spring.actuator.jenkins.JenkinsHealthIndicator;

@Configuration
public class ActuatorConfiguration
{
    @NotBlank
    @Value("${jenkins.url}")
    private String jenkinsUrl;

    @Value("${jenkins.username}")
    private String jenkinsUsername;

    @Value("${jenkins.password}")
    private String jenkinsPassword;

    @Bean
    public HealthIndicator jenkinsHealthIndicator()
    {
        if (StringUtils.isNotBlank(jenkinsUsername) && StringUtils.isNotBlank(jenkinsPassword))
        {
            return new JenkinsHealthIndicator(jenkinsUrl, jenkinsUsername, jenkinsPassword);
        }

        return new JenkinsHealthIndicator(jenkinsUrl);
    }
}
