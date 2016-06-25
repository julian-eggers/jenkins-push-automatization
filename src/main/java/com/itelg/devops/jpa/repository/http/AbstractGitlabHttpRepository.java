package com.itelg.devops.jpa.repository.http;

import javax.annotation.PostConstruct;

import org.gitlab.api.GitlabAPI;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractGitlabHttpRepository
{
	protected GitlabAPI gitlabAPI;
	
	@NotBlank
	@Value("${gitlab.url}")
	private String gitlabUrl;
	
	@NotBlank
	@Value("${gitlab.token}")
	private String gitlabToken;
	
	@PostConstruct
	public void init()
	{
		gitlabAPI = GitlabAPI.connect(gitlabUrl, gitlabToken);
	}
	
	public Integer toInteger(long id)
	{
		return Integer.valueOf(Long.valueOf(id).intValue());
	}
}