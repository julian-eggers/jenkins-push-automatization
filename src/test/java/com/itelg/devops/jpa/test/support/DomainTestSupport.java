package com.itelg.devops.jpa.test.support;

import org.gitlab.api.models.GitlabNamespace;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabProjectHook;

import com.itelg.devops.jpa.domain.Project;
import com.itelg.devops.jpa.domain.WebHook;

public interface DomainTestSupport
{
    default GitlabProject getCompleteGitlabProject()
    {
        GitlabProject gitlabProject = new GitlabProject();
        gitlabProject.setId(Integer.valueOf(1));
        gitlabProject.setNamespace(new GitlabNamespace());
        gitlabProject.getNamespace().setName("devops");
        gitlabProject.setPath("devops/jenkins-push-automatization");
        gitlabProject.setSshUrl("git@gitlab-server:devops/jenkins-push-automatization.git");
        gitlabProject.setHttpUrl("http://gitlab-server/devops/jenkins-push-automatization.git");
        return gitlabProject;
    }

    default GitlabProjectHook getCompleteGitlabProjectHook()
    {
        GitlabProjectHook gitlabProjectHook = new GitlabProjectHook();
        gitlabProjectHook.setId("0");
        gitlabProjectHook.setProjectId(Integer.valueOf(1));
        gitlabProjectHook.setUrl("http://jenkins-host/git/notifyCommit?url=git@gitlab-server:devops/jenkins-push-automatization.git");
        return gitlabProjectHook;
    }

    default Project getCompleteProject()
    {
        return getCompleteProject(1, "jenkins-push-automatization");
    }

    default Project getCompleteProject(long id, String name)
    {
        Project project = new Project();
        project.setId(id);
        project.setNamespace("devops");
        project.setPath("devops/" + name);
        project.setCheckoutUrlSsh("git@gitlab-server:devops/" + name + ".git");
        project.setCheckoutUrlHttp("http://gitlab-server/devops/" + name + ".git");
        return project;
    }

    default WebHook getCompleteWebHookWithSslScheme()
    {
        WebHook webHook = new WebHook();
        webHook.setId(0);
        webHook.setProjectId(1);
        webHook.setUrl("http://jenkins-host/git/notifyCommit?url=git@gitlab-server:devops/jenkins-push-automatization.git");
        return webHook;
    }

    default WebHook getCompleteWebHookWithHttpScheme()
    {
        WebHook webHook = new WebHook();
        webHook.setId(0);
        webHook.setProjectId(1);
        webHook.setUrl("http://jenkins-host/git/notifyCommit?url=http://gitlab-server/devops/jenkins-push-automatization.git");
        return webHook;
    }
}
