package com.itelg.devops.jpa.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WebHook
{
    private long id;
    private long projectId;
    private String url;
}