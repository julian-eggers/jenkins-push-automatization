package com.itelg.devops.jpa.repository.http;

import java.util.ArrayList;
import java.util.List;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.repository.ProjectRepository;

import lombok.SneakyThrows;

@Repository
public class HttpProjectRepository implements ProjectRepository
{
    @Autowired
    private GitlabAPI gitlabApi;

    @SneakyThrows
    @Override
    public List<Project> getAllProjects()
    {
        List<Project> projects = new ArrayList<>();

        for (GitlabProject gitlabProject : gitlabApi.getProjects())
        {
            Project project = new Project();
            project.setId(gitlabProject.getId().longValue());
            project.setNamespace(gitlabProject.getNamespace().getName());
            project.setPath(gitlabProject.getPath());
            project.setCheckoutUrlHttp(gitlabProject.getHttpUrl());
            project.setCheckoutUrlSsh(gitlabProject.getSshUrl());
            projects.add(project);
        }

        return projects;
    }
}