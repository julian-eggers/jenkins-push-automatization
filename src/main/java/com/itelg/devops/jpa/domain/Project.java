package com.itelg.devops.jpa.domain;

import lombok.Data;

@Data
public class Project
{
    private long id;
    private String namespace;
    private String path;
    private String checkoutUrlSsh;
    private String checkoutUrlHttp;
}