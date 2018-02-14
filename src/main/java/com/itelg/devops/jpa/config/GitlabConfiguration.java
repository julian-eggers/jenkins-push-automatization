package com.itelg.devops.jpa.config;

import javax.validation.constraints.NotBlank;

import org.gitlab.api.GitlabAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitlabConfiguration
{
    @NotBlank
    @Value("${gitlab.url}")
    private String gitlabUrl;

    @NotBlank
    @Value("${gitlab.token}")
    private String gitlabToken;

    @Bean
    public GitlabAPI gitlabApi()
    {
        return GitlabAPI.connect(gitlabUrl, gitlabToken);
    }
}
