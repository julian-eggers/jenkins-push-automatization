package com.itelg.devops.jpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.repository.ProjectRepository;
import com.itelg.devops.jpa.service.ProjectService;

@Service
public class DefaultProjectService implements ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects()
    {
        return projectRepository.getAllProjects();
    }
}