package com.itelg.devops.jpa.domain;

import lombok.Data;

@Data
public class WebHook
{
    private long id;
    private long projectId;
    private String url;
}