package com.itelg.devops.jpa.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Project
{
    private long id;
    private String namespace;
    private String path;
    private String checkoutUrlSsh;
    private String checkoutUrlHttp;
}