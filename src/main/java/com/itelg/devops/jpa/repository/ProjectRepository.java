package com.itelg.devops.jpa.repository;

import java.util.List;

import com.itelg.devops.jpa.domain.Project;

public interface ProjectRepository
{
    List<Project> getAllProjects();
}