package com.itelg.devops.jpa.repository.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.gitlab.api.models.GitlabProject;
import org.springframework.stereotype.Repository;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.repository.ProjectRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class HttpProjectRepository extends AbstractGitlabHttpRepository implements ProjectRepository
{
    @Override
    public List<Project> getAllProjects()
    {
        List<Project> projects = new ArrayList<>();

        try
        {
            for (GitlabProject gitlabProject : gitlabAPI.getAllProjects())
            {
                Project project = new Project();
                project.setId(gitlabProject.getId().longValue());
                project.setNamespace(gitlabProject.getNamespace().getName());
                project.setPath(gitlabProject.getPath());
                project.setCheckoutUrlHttp(gitlabProject.getHttpUrl());
                project.setCheckoutUrlSsh(gitlabProject.getSshUrl());
                projects.add(project);
            }
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }

        return projects;
    }
}