package com.itelg.devops.jpa.repository;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;

@Validated
public interface WebHookRepository
{
    void insertWebHook(@NotNull @Valid WebHook webHook);

    void deleteWebHook(@NotNull @Valid WebHook webHook);

    List<WebHook> getWebHooksByProject(Project project);
}